package com.charlie.ev3;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * EV3主机。
 * @author Charlie Jiang
 */
public class Brick implements DataReceiveListener{
    /**
     * LCD屏幕宽度。
     */
    public static final int LCD_WIDTH = 178;
    /**
     * LCD屏幕高度。
     */
    public static final int LCD_HEIGHT = 128;
    /**
     * 状态栏高度。
     */
    public static final int TOP_STATUS_LINE_HEIGHT = 10;

    private final Communication communication;
    private final boolean alwaysSendEvent;
    private final ExecutorService threads = Executors.newCachedThreadPool();
    /**
     * 主机的Direct Command发送器。
     */
    public final DirectCommand directCommand;
    /**
     * 主机的System Command发送器。
     */
    public final SystemCommand systemCommand;

    /**
     * 主机上的接口。
     */
    public Map<InputPort,Port> ports = new LinkedHashMap<>();
    /**
     * 主机上的按钮
     */
    public Map<BrickButtons,BrickButton> buttons = new LinkedHashMap<>();
    private List<BrickChangedListener> events= new LinkedList<>();

    /**
     * 构造一个新的指定只在主机状态发生变化时发出BrickChanged事件的主机对象，使用指定的连接。
     * @param communication 指定的连接
     */
    public Brick(Communication communication){
        this(communication,false);
    }

    /**
     * 构造一个新的指定是否总是发出BrickChanged事件的主机对象，使用指定的连接。
     * @param communication 指定的连接
     * @param alwaysSendEvent 是否总是发出BrickChanged事件
     */
    public Brick(Communication communication,boolean alwaysSendEvent){
        this.communication = communication;
        this.alwaysSendEvent = alwaysSendEvent;
        directCommand = new DirectCommand(this);
        systemCommand = new SystemCommand(this);
        int index = 0;
        communication.addDataReceiveListener(this);
        for(InputPort inputPort : InputPort.values()){
            ports.put(inputPort,new Port(inputPort.toString(),inputPort,index++));
        }
        for(BrickButtons brickButtons:BrickButtons.values()){
            buttons.put(brickButtons,new BrickButton());
        }
    }

    /**
     * 连接到EV3主机，每100毫秒拉取一次数据。
     * @throws Exception 连接失败时抛出。
     */
    public void connect() throws Exception{
        connect(100);
    }
    /**
     * 连接到EV3主机，指定拉取数据的间隔。
     * @param msPollTime 拉取数据的间隔，设置为0则不拉取数据
     * @throws Exception 连接失败时抛出。
     */
    public void connect(final int msPollTime) throws Exception{
        communication.connect();
        directCommand.stopMotor(false,OutputPort.ALL);
        if(!(msPollTime == 0)){
            threads.submit(new Runnable() {
                @Override
                public void run() {
                    while(!threads.isShutdown()){

                        try {
                            pollSensors();
                            TimeUnit.MILLISECONDS.sleep(msPollTime);
                        } catch (Exception e) {e.printStackTrace();}
                    }
                    try {
                        directCommand.stopMotor(false,OutputPort.ALL);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    /**
     * 断开到主机的连接。
     * @throws Exception 断开失败时抛出。
     */
    public void disconnect() throws Exception{
        threads.shutdown();
        communication.disconnect();
    }
    private void pollSensors() throws IOException {
        boolean changed = false;
        final int responseSize = 11;
        int index = 0;

        Command command = new Command(CommandType.COMMAND_DIRECT_REPLY,(short)((8*responseSize)+6),0);

        for(InputPort inputPort:InputPort.values()){
            Port p = ports.get(inputPort);
            index = p.index*responseSize;
            command.getTypeMode(p.inputPort,(byte)index,(byte)(index+1));
            command.readySI(p.inputPort,p.getMode(),(byte)index+2);
            command.readyRaw(p.inputPort,p.getMode(),(byte)index+6);
            command.readyPercent(p.inputPort,p.getMode(),(byte)index+10);
        }

        index += responseSize;

        command.isButtonPressed(BrickButtons.ESCAPE,(byte)(index));
        command.isButtonPressed(BrickButtons.LEFT,(byte)(index+1));
        command.isButtonPressed(BrickButtons.UP,(byte)(index+2));
        command.isButtonPressed(BrickButtons.RIGHT,(byte)(index+3));
        command.isButtonPressed(BrickButtons.DOWN,(byte)(index+4));
        command.isButtonPressed(BrickButtons.ENTER,(byte)(index+5));

        sendCommand(command);

        if(command.response.getData() == null)
            return;
        byte[] data = command.response.getData();
        for(InputPort inputPort:InputPort.values()){
            Port p = ports.get(inputPort);
            int type = command.response.getData()[(p.index * responseSize)];
            byte mode = command.response.getData()[(p.index * responseSize)+1];
            float siValue;
            siValue = Float.intBitsToFloat((data[((p.index * responseSize)+2)] & 0xff) |
                    ((data[((p.index * responseSize)+3)] << 8) & 0xff00) |
                    ((data[((p.index * responseSize)+4)] << 24) >>> 8) |
                    (data[((p.index * responseSize)+5)] << 24));
            int rawValue = (data[6] & 0xff) | ((data[7] << 8) & 0xff00)| ((data[8] << 24) >>> 8) | (data[9] << 24);
            byte percentValue = data[((p.index * responseSize)+10)];

            if(p.getDeviceType().getValue() != type || Math.abs(p.getSIValue()-siValue)>0.01f || p.getRawValue() != rawValue || p.getPercentValue() != percentValue)
                changed = true;

            p.setSIValue(siValue);
            p.setRawValue(rawValue);
            p.setPercentValue(percentValue);
        }

        if(buttons.get(BrickButtons.ESCAPE).isPressed() == (data[index] == 1)||
                buttons.get(BrickButtons.LEFT).isPressed() == (data[index + 1] == 1)||
                buttons.get(BrickButtons.UP).isPressed() == (data[index + 2] == 1)||
                buttons.get(BrickButtons.RIGHT).isPressed() == (data[index + 3] == 1)||
                buttons.get(BrickButtons.DOWN).isPressed() == (data[index + 4] == 1)||
                buttons.get(BrickButtons.ENTER).isPressed() == (data[index + 5] == 1))
            changed = true;

        buttons.get(BrickButtons.ESCAPE).setPressed((data[index] == 1));
        buttons.get(BrickButtons.LEFT).setPressed((data[index+1] == 1));
        buttons.get(BrickButtons.UP).setPressed((data[index+2] == 1));
        buttons.get(BrickButtons.RIGHT).setPressed((data[index+3] == 1));
        buttons.get(BrickButtons.DOWN).setPressed((data[index+4] == 1));
        buttons.get(BrickButtons.ENTER).setPressed((data[index+5] == 1));

        if(changed || alwaysSendEvent)
            for(BrickChangedListener listener:events) listener.brickChanged();
    }

    /**
     * 向EV3主机写一个Command（命令）对象
     * @param command 需要发送的命令
     * @throws java.io.IOException 发送失败时抛出。
     */
    public void sendCommand(Command command) throws IOException{
        communication.write(command.toBytes());
        if(command.commandType == CommandType.COMMAND_DIRECT_REPLY || command.commandType == CommandType.COMMAND_SYSTEM_REPLY)
            ResponseManager.waitForResponse(command.response);
    }

    @Override
    public void onDataReceive(byte[] data) {
        ResponseManager.handleResponse(data);
    }

    /**
     * 增加一个监听器来检测主机状态改变事件。
     * @param listener 要增加的监听器
     */
    public void addBrickChangedListener(BrickChangedListener listener){
        events.add(listener);
    }

    /**
     * 删除一个监听数据的监听器。
     * @param listener 要删除的监听器
     */
    public void removeBrickChangedListener(BrickChangedListener listener){
        events.remove(listener);
    }

    public Command createCommand(){
        return new Command(CommandType.COMMAND_DIRECT_NO_REPLY);
    }
}
