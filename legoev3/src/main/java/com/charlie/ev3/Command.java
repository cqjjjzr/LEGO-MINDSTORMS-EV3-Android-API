package com.charlie.ev3;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 需要发送到EV3主机的命令串。
 * @author Charlie Jiang
 */
public class Command {
    private ByteArrayOutputStream temp = new ByteArrayOutputStream();
    protected CommandType commandType;
    protected Response response;

    /**
     * 使用指定的命令类型来创建Command对象。
     * @param type 指定的命令类型
     */
    protected Command(CommandType type){
        init(type);
    }
    /**
     * 使用指定的命令类型、全局缓冲区大小和局部缓冲区来创建Command对象。
     * @param type       指定的命令类型
     * @param globalSize 指定的全局缓冲区大小（最大1024字节）
     * @param localSize  指定的局部缓冲区大小（最大64字节）
     */
    protected Command(CommandType type, short globalSize, int localSize){
        init(type, globalSize, localSize);
    }
    private void init(CommandType type) {
        init(type, (short) 0, 0);
    }

    private void init(CommandType type, short globalSize, int localSize) {
        if (globalSize > 1024)
            throw new IllegalArgumentException("全局缓冲区大小不能大于1024字节。");
        if (localSize > 64)
            throw new IllegalArgumentException("局部缓冲区大小不能大于64字节。");
        this.commandType = type;
        response = ResponseManager.createResponse();
        temp.write((byte) 0xff);
        temp.write((byte) 0xff);
        temp.write(response.getSequence());
        temp.write(response.getSequence()>>8);
        temp.write(type.getByte());
        if (commandType == CommandType.COMMAND_DIRECT_NO_REPLY || commandType == CommandType.COMMAND_DIRECT_REPLY) {
            temp.write((byte) globalSize);
            temp.write((byte) ((localSize << 2) | (globalSize >> 8) & 0x03));
        }
    }

    /**
     * Add OpCode to command.
     *
     * @param opCode OpCode to add
     */
    protected void addOpCode(OpCode opCode) {
        if (opCode.getShort() > OpCode.TSP.getShort()) {
            temp.write((byte) (opCode.getShort() >> 8));
        }
        temp.write((byte) opCode.getShort());
    }

    /**
     * Add System OpCode to command.
     *
     * @param opCode System OpCode to add
     */
    protected void addOpCode(SystemOpCode opCode) {
        temp.write(opCode.getByte());
    }

    /**
     * Add global index setting.
     *
     * @param index Global index.
     */
    protected void addGlobalIndex(byte index) {
        temp.write((byte) 0xe1);
        temp.write(index);
    }

    /**
     * Add parameter to command.
     *
     * @param b Byte data to add.
     */
    protected void addParameter(byte b) {
        temp.write(ArgumentSize.BYTE.getByte());
        temp.write(b);
    }

    /**
     * Add parameter to command.
     *
     * @param s Short data to add.
     */
    protected void addParameter(short s) {
        temp.write(ArgumentSize.SHORT.getByte());
        temp.write(s);
        temp.write(s>>8);
    }

    /**
     * Add parameter to command.
     *
     * @param i Integer data to add.
     */
    protected void addParameter(int i) {
        temp.write(ArgumentSize.INT.getByte());
        temp.write(i);
        temp.write(i>>8);
        temp.write(i>>16);
        temp.write(i>>24);
    }

    /**
     * Add parameter to command.
     *
     * @param st String data to add.
     */
    protected void addParameter(String st) {
        temp.write(ArgumentSize.STRING.getByte());
        try {
            temp.write(st.getBytes());
        } catch (IOException ex) {
            ex.printStackTrace();

        }
        temp.write((byte) 0x00);
    }

    /**
     * Add raw parameter to command.
     * @param b Byte data to add.
     */
    protected void addRawParameter(byte b){
        temp.write(b);
    }
    /**
     * Add raw parameter to command.
     * @param s Byte data to add.
     */
    protected void addRawParameter(short s){
        temp.write(s);
        temp.write(s>>8);
    }
    /**
     * Add raw parameter to command.
     * @param i Byte data to add.
     */
    protected void addRawParameter(int i){
        temp.write(i);
        temp.write(i>>8);
        temp.write(i>>16);
        temp.write(i>>24);
    }
    /**
     * Add raw parameter to command.
     *
     * @param st String data to add.
     */
    protected void addRawParameter(String st) {
        try {
            temp.write(st.getBytes());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        temp.write((byte) 0x00);
    }

    protected void addRawParameter(byte[] buffer,int off,int len){
        temp.write(buffer, off, len);
    }

    /**
     * Get bytes of this command.
     * @return Byte array
     */
    public byte[] toBytes(){
        byte[] buff = temp.toByteArray();
        short size = (short)(buff.length-2);
        buff[0] = (byte)size;
        buff[1] = (byte)(size >> 8);
        return buff;
    }

    //=========COMMAND OPERATION BEGIN=========//

    //==========MOTOR OPERATION BEGIN==========//
    /**
     * 打开指定端口的电机。
     * @param port 指定的端口
     */
    public void startMotor(OutputPort... port){
        for(OutputPort outputPort:port){
            addOpCode(OpCode.OUTPUT_START);
            addParameter((byte)0x00);
            addParameter(outputPort.getByte());
        }
    }
    /**
     * 停止指定端口的电机。
     * @param brake 是否刹车
     * @param port 指定的端口
     */
    public void stopMotor(boolean brake,OutputPort... port){
        for(OutputPort outputPort:port){
            addOpCode(OpCode.OUTPUT_STOP);
            addParameter((byte)0x00);
            addParameter(outputPort.getByte());
            addParameter((byte)(brake ? 0x01 : 0x00));		// brake (0 = coast, 1 = brake)
        }
    }

    /**
     * 用指定的功率打开电机。
     * @param power 指定的功率（-100 到 100）
     * @param ports 指定的端口
     */
    public void turnMotorPower(int power,OutputPort... ports)
    {
        if(power < -100 || power > 100)
            throw new IllegalArgumentException("功率必须介于100与-100之间。");
        for(OutputPort outputPort:ports){
            addOpCode(OpCode.OUTPUT_POWER);
            addParameter((byte)0x00);			// layer
            addParameter(outputPort.getByte());	// ports
            addParameter((byte)power);	// power
        }
    }
    /**
     * 用指定的速度打开电机。
     * @param speed 指定的速度（-100 到 100）
     * @param ports 指定的端口
     */
    public void turnMotorSpeed(int speed,OutputPort... ports)
    {
        if(speed < -100 || speed > 100)
            throw new IllegalArgumentException("速度必须介于100与-100之间。");
        for(OutputPort outputPort:ports){
            addOpCode(OpCode.OUTPUT_SPEED);
            addParameter((byte)0x00);			// layer
            addParameter(outputPort.getByte());	// ports
            addParameter((byte)speed);	// power
        }
    }

    /**
     * 用指定的功率、步数步进电机。
     * @param power 指定的功率（-100 到 100）
     * @param steps 步数
     * @param brake 是否刹车
     * @param ports 指定的电机
     */
    public void stepMotorPower(int power,int steps, boolean brake,OutputPort... ports){
        stepMotorPower(power, 0,steps, 10,brake, ports);
    }
     /**
      * 用指定的功率、步数、渐入降出步数步进电机。
      * @param power 指定的功率（-100 到 100）
      * @param constantSteps 步数
      * @param brake 是否刹车
      * @param ports 指定的电机
      * @param rampDownSteps 电机渐出的步数
      * @param rampUpSteps 电机渐入的步数
      */
    public void stepMotorPower(int power,int rampUpSteps, int constantSteps, int rampDownSteps,boolean brake,OutputPort... ports){
        if(power < -100 || power > 100)
            throw new IllegalArgumentException("功率必须介于100与-100之间。");
        for(OutputPort outputPort:ports){
            addOpCode(OpCode.OUTPUT_STEP_POWER);
            addParameter((byte)0x00);			// layer
            addParameter(outputPort.getByte());	// ports
            addParameter((byte)power);			// power
            addParameter(rampUpSteps);	// step1
            addParameter(constantSteps);	// step2
            addParameter(rampDownSteps);	// step3
            addParameter((byte)(brake ? 0x01 : 0x00));		// brake (0 = coast, 1 = brake)
        }
    }
    /**
     * 用指定的速度、步数步进电机。
     * @param speed 指定的速度（-100 到 100）
     * @param steps 步数
     * @param brake 是否刹车
     * @param ports 指定的电机
     */
    public void stepMotorSpeed(int speed,int steps, boolean brake,OutputPort... ports){
        stepMotorSpeed(speed, 0,steps, 10,brake, ports);
    }
    /**
     * 用指定的速度、步数、渐入降出步数步进电机。
     * @param speed 指定的速度（-100 到 100）
     * @param constantSteps 步数
     * @param brake 是否刹车
     * @param ports 指定的电机
     * @param rampDownSteps 电机渐出的步数
     * @param rampUpSteps 电机渐入的步数
     */
    public void stepMotorSpeed(int speed,int rampUpSteps, int constantSteps, int rampDownSteps,boolean brake,OutputPort... ports){
        if(speed < -100 || speed > 100)
            throw new IllegalArgumentException("速度必须介于100与-100之间。");
        for(OutputPort outputPort:ports){
            addOpCode(OpCode.OUTPUT_STEP_SPEED);
            addParameter((byte)0x00);			// layer
            addParameter(outputPort.getByte());	// ports
            addParameter((byte)speed);			// speed
            addParameter(rampUpSteps);	// step1
            addParameter(constantSteps);	// step2
            addParameter(rampDownSteps);	// step3
            addParameter((byte)(brake ? 0x01 : 0x00));		// brake (0 = coast, 1 = brake)
        }
    }
    /**
     * 使用指定功率打开电机一段时间。
     * @param power 指定的功率（-100 到 100）
     * @param times 打开的时间（毫秒）
     * @param brake 是否刹车
     * @param ports 指定的端口
     */
    public void timeMotorPower(int power,int times, boolean brake,OutputPort... ports){
        timeMotorPower(power, 0,times, 10,brake, ports);
    }
    /**
     * 使用指定功率、渐入渐出时间打开电机一段时间。
     * @param power 指定的功率（-100 到 100）
     * @param milliseconds 打开的时间（毫秒）
     * @param brake 是否刹车
     * @param ports 指定的端口
     * @param rampDownMilliseconds 渐出时间（毫秒）
     * @param rampUpMilliseconds 渐入时间（毫秒）
     */
    public void timeMotorPower(int power,int rampUpMilliseconds, int milliseconds, int rampDownMilliseconds,boolean brake,OutputPort... ports){
        if(power < -100 || power > 100)
            throw new IllegalArgumentException("功率必须介于100与-100之间。");
        for(OutputPort outputPort:ports){
            addOpCode(OpCode.OUTPUT_TIME_POWER);
            addParameter((byte)0x00);			// layer
            addParameter(outputPort.getByte());	// ports
            addParameter((byte)power);			// power
            addParameter(rampUpMilliseconds);	// time1
            addParameter(milliseconds);	// time2
            addParameter(rampDownMilliseconds);	// time3
            addParameter((byte)(brake ? 0x01 : 0x00));		// brake (0 = coast, 1 = brake)
        }
    }
    /**
     * 使用指定速度打开电机一段时间。
     * @param speed 指定的速度（-100 到 100）
     * @param times 打开的时间（毫秒）
     * @param brake 是否刹车
     * @param ports 指定的端口
     */
    public void timeMotorSpeed(int speed,int times, boolean brake,OutputPort... ports){
        timeMotorSpeed(speed, 0,times, 10,brake, ports);
    }
    /**
     * 使用指定速度、渐入渐出时间打开电机一段时间。
     * @param speed 指定的速度（-100 到 100）
     * @param milliseconds 打开的时间（毫秒）
     * @param brake 是否刹车
     * @param ports 指定的端口
     * @param rampDownMilliseconds 渐出时间（毫秒）
     * @param rampUpMilliseconds 渐入时间（毫秒）
     */
    public void timeMotorSpeed(int speed,int rampUpMilliseconds, int milliseconds, int rampDownMilliseconds,boolean brake,OutputPort... ports){
        if(speed < -100 || speed > 100)
            throw new IllegalArgumentException("速度必须介于100与-100之间。");
        for(OutputPort outputPort:ports){
            addOpCode(OpCode.OUTPUT_TIME_SPEED);
            addParameter((byte)0x00);			// layer
            addParameter(outputPort.getByte());	// ports
            addParameter((byte)speed);			// speed
            addParameter(rampUpMilliseconds);	// time1
            addParameter(milliseconds);	// time2
            addParameter(rampDownMilliseconds);	// time3
            addParameter((byte)(brake ? 0x01 : 0x00));		// brake (0 = coast, 1 = brake)
        }
    }

    /**
     * 添加设置电机方向命令到此命令串。
     * @param port 指定的端口
     * @param polarity 新的方向
     */
    public void setMotorPolarity(OutputPort port,Polarity polarity){
        addOpCode(OpCode.OUTPUT_POLARITY);
        addParameter(port.getByte());
        addParameter(polarity.getByte());
    }

    /**
     * 同步步进电机。
     * @param speed 指定的速度（-100 到 100）
     * @param turnRatio 转动比率（-200 到 200）
     * @param step 步数
     * @param brake 是否刹车
     * @param ports 指定的端口
     */
    public void stepMotorSync(int speed, short turnRatio, int step, boolean brake,OutputPort... ports){
        if(speed < -100 || speed > 100)
            throw new IllegalArgumentException("速度必须介于100与-100之间。");
        if(turnRatio < -200 || turnRatio > 200)
            throw new IllegalArgumentException("转动比率必须介于200与-200之间。");
        for(OutputPort outputPort:ports){
            addOpCode(OpCode.OUTPUT_STEP_SPEED);
            addParameter(0x00);			// layer
            addParameter(outputPort.getByte());	// ports
            addParameter((byte)speed);			// speed
            addParameter(turnRatio);
            addParameter(step);
            addParameter((byte)(brake ? 0x01 : 0x00));		// brake (0 = coast, 1 = brake)
        }
    }

    /**
     * 同步调速电机。
     * @param speed 指定的速度（-100 到 100）
     * @param turnRatio 转动比率（-200 到 200）
     * @param milliseconds 时间
     * @param brake 是否刹车
     * @param ports 指定的端口
     */
    public void timeMotorSync(int speed, short turnRatio, int milliseconds, boolean brake,OutputPort... ports){
        if(speed < -100 || speed > 100)
            throw new IllegalArgumentException("速度必须介于100与-100之间。");
        if(turnRatio < -200 || turnRatio > 200)
            throw new IllegalArgumentException("转动比率必须介于200与-200之间。");
        for(OutputPort outputPort:ports){
            addOpCode(OpCode.OUTPUT_TIME_SPEED);
            addParameter(0x00);			// layer
            addParameter(outputPort.getByte());	// ports
            addParameter((byte)speed);			// speed
            addParameter(turnRatio);
            addParameter(milliseconds);
            addParameter((byte)(brake ? 0x01 : 0x00));		// brake (0 = coast, 1 = brake)
        }
    }

    /**
     * 添加清除更改命令到此命令串。
     * @param ports 指定的端口
     */
    public void clearAllChanges(InputPort... ports){
        for(InputPort outputPort:ports) {
            addOpCode(OpCode.INPUT_CLEAR_CHANGES);
            addParameter((byte) 0x00);
        }
    }

    /**
     * 添加清除所有更改命令到此命令串。
     */
    public void clearAllDevices(){
        addOpCode(OpCode.INPUT_CLEAR_ALL);
        addParameter((byte)0x00);
    }

    //==========MOTOR OPERATION END==========//

    //=========SOUND OPERATION BEGIN=========//

    /**
     * 添加播放声音命令到此命令串。
     * @param volume 声音的音量（0到100）
     * @param duration 声音的持续时间，单位为毫秒
     * @param frequency 声音的频率，单位为赫兹
     */
    public void playTone(int volume,short duration,short frequency){
        if(volume < -100 || volume > 100)
            throw new IllegalArgumentException("音量必须介于0到100之间。");
        addOpCode(OpCode.SOUND_TONE);
        addParameter((byte)volume);
        addParameter(frequency);
        addParameter(duration);

    }
    /**
     * 添加播放音频命令到此命令串。
     * @param volume 音频的音量（0到100）
     * @param name 音频文件在EV3主机上的路径
     */
    public void playSound(int volume,String name){
        if(volume < -100 || volume > 100)
            throw new IllegalArgumentException("音量必须介于0到100之间。");
        addOpCode(OpCode.SOUND_PLAY);
        addParameter((byte)volume);
        addParameter(volume);

    }
    //==========SOUND OPERATION END==========//

    //==========UI OPERATION BEGIN===========//

    /**
     * 添加获取固件版本命令到此命令串。
     * @param maxLength 返回的字符串的最大长度
     * @param index 返回字符串在全局缓存中的索引
     */
    public void getFirmwareVersion(int maxLength,int index){
        if(maxLength > 0xff)
            throw new IllegalArgumentException("字符串长度不能超过256字节。");
        if(index > 1024)
            throw new IllegalArgumentException("索引位置不能大于1024。");
        addOpCode(OpCode.UIREAD_GET_FIRMWARE);
        addParameter((byte)maxLength);
        addGlobalIndex((byte)index);
    }

    /**
     * 添加获取按钮按下状态命令到此命令串。
     * @param brickButton 需要检测的按钮
     * @param index 返回值在全局缓存中的索引
     */
    public void isButtonPressed(BrickButtons brickButton,int index){
        if(index > 1024)
            throw new IllegalArgumentException("Index cannot be greater than 1024.");
        addOpCode(OpCode.UIBUTTON_PRESSED);
        addParameter(brickButton.ordinal());
        addGlobalIndex((byte)index);
    }

    /**
     * 添加设置LED模式命令到此命令串。
     * @param pattern LED模式
     */
    public void setLEDPattern(LEDPattern pattern){
        addOpCode(OpCode.UIWRITE_LED);
        addParameter((byte)pattern.ordinal());

    }

    /**
     * 添加清空屏幕命令到此命令串。
     */
    public void cleanUI(){
        addOpCode(OpCode.UIDRAW_CLEAN);
    }

    /**
     * 添加画线命令到此命令串。
     * @param color 线的颜色
     * @param x0 X 起点
     * @param y0 Y 起点
     * @param x1 X 终点
     * @param y1 Y 终点
     */
    public void drawLine(UIColor color,short x0,short y0,short x1,short y1){
        addOpCode(OpCode.UIDRAW_LINE);
        addParameter((byte)color.ordinal());
        addParameter(x0);
        addParameter(y0);
        addParameter(x1);
        addParameter(y1);
    }

    /**
     * 添加画虚线命令到此命令串。
     * @param color 线的颜色
     * @param x0 X 起点
     * @param y0 Y 起点
     * @param x1 X 终点
     * @param y1 Y 终点
     * @param offPixels 虚线中无颜色的像素数量
     * @param onPixels 虚线中有颜色的像素数量
     */
    public void drawDottedLine(UIColor color,short x0,short y0,short x1,short y1,short onPixels,short offPixels){
        addOpCode(OpCode.UIDRAW_DOT_LINE);
        addParameter((byte)color.ordinal());
        addParameter(x0);
        addParameter(y0);
        addParameter(x1);
        addParameter(y1);
        addParameter(onPixels);
        addParameter(offPixels);
    }

    /**
     * 添加画点命令到此命令串。
     * @param color 点的颜色
     * @param x X 位置
     * @param y Y 位置
     */
    public void drawPixel(UIColor color,short x,short y){
        addOpCode(OpCode.UIDRAW_PIXEL);
        addParameter((byte)color.ordinal());
        addParameter(x);
        addParameter(y);
    }

    /**
     * 添加画矩形命令到此命令串。
     * @param color 矩形的颜色
     * @param x X 位置
     * @param y Y 位置
     * @param width 矩形的宽
     * @param height 矩形的高
     * @param filled 矩形是否填充
     */
    public void drawRectangle(UIColor color,short x,short y,short width,short height,boolean filled){
        addOpCode(filled?(OpCode.UIDRAW_FILL_RECT):(OpCode.UIDRAW_RECT));
        addParameter(color.ordinal());
        addParameter(x);
        addParameter(y);
        addParameter(width);
        addParameter(height);
    }

    /**
     * 添加画逆矩形命令到此命令串。
     * @param x X 位置
     * @param y Y 位置
     * @param width 矩形的宽
     * @param height 矩形的高
     */
    public void drawInverseRectangle(short x,short y,short width,short height){
        addOpCode(OpCode.UIDRAW_INVERSE_RECT);
        addParameter(x);
        addParameter(y);
        addParameter(width);
        addParameter(height);
    }

    /**
     * 添加画圆命令到此命令串。
     * @param color 圆的颜色
     * @param x X 位置
     * @param y Y 位置
     * @param radius 圆的半径
     * @param filled 圆是否填充
     */
    public void drawCircle(UIColor color,short x,short y,short radius,boolean filled){
        addOpCode(filled?(OpCode.UIDRAW_FILL_CIRCLE):(OpCode.UIDRAW_CIRCLE));
        addParameter(color.ordinal());
        addParameter(x);
        addParameter(y);
        addParameter(radius);

    }

    /**
     * 添加画文本命令到此命令串。
     * @param color 文本的颜色
     * @param x X 位置
     * @param y Y 位置
     * @param text 需要画的文本
     */
    public void drawText(UIColor color,short x,short y,String text){
        addOpCode(OpCode.UIDRAW_TEXT);
        addParameter(color.ordinal());
        addParameter(x);
        addParameter(y);
        addParameter(text);
    }
    /**
     * 添加画填充窗口命令到此命令串。
     * @param color 填充的颜色
     * @param y0 Y 起点
     * @param y1 Y 终点
     */
    public void drawFillWindow(UIColor color,short y0,short y1){
        addOpCode(OpCode.UIDRAW_FILL_WINDOW);
        addParameter(color.ordinal());
        addParameter(y0);
        addParameter(y1);
    }

    /**
     * 添加画图像命令到此命令串。
     * @param color 图像的颜色
     * @param x X 位置
     * @param y Y 位置
     * @param devicePath 图像文件在EV3主机上的位置
     */
    public void drawImage(UIColor color,short x,short y,String devicePath) {
        addOpCode(OpCode.UIDRAW_BMP_FILE);
        addParameter(color.ordinal());
        addParameter(x);
        addParameter(y);
        addParameter(devicePath);
    }

    /**
     * 添加切换字体命令到此命令串。
     * @param font 切换到的字体
     */
    public void selectFont(UIFont font){
        addOpCode(OpCode.UIDRAW_SELECT_FONT);
        addParameter((byte)font.ordinal());
    }

    /**
     * 添加设置状态栏命令到此命令串。
     * @param isEnabled 是否显示状态栏
     */
    public void enableTopLine(boolean isEnabled){
        addOpCode(OpCode.UIDRAW_TOP_LINE);
        addParameter((byte)(isEnabled?1:0));
    }

    /**
     * 添加更新界面命令到此命令串。
     */
    public void updateUI(){
        addOpCode(OpCode.UIDRAW_UPDATE);
    }

    //============UI OPERATION END===========//

    //=========SENSOR OPERATION START========//

    /**
     * 添加创建目录命令到此命令串。
     * @param deviceName 目录名称
     */
    public void createDirectory(String deviceName){
        addOpCode(SystemOpCode.CREATE_DIRECTORY);
        addRawParameter(deviceName);
    }

    /**
     * 添加删除文件命令到此命令串。
     * @param deviceName 要删除的文件名
     */
    public void deleteFile(String deviceName){
        addOpCode(SystemOpCode.DELETE_FILE);
        addRawParameter(deviceName);
    }

    //==========SENSOR OPERATION END=========//

    //=========SENSOR OPERATION START========//

    /**
     * 添加获取模式名称命令到此命令串。
     * @param inputPort 访问的端口
     * @param mode 需要获取模式名称的模式
     * @param bufferSize 用于存放返回数据的缓存大小
     * @param index 存放返回数据的引导位置
     */
    public void getModeName(InputPort inputPort,int mode,int bufferSize,int index){
        if(index > 1024)
            throw new IllegalArgumentException("引导位置不能大于1024。");
        addOpCode(OpCode.INPUT_GET_MODE_NAME);
        addParameter((byte)0x00);
        addParameter(inputPort.getByte());
        addParameter((byte)mode);
        addParameter((byte)bufferSize);
        addGlobalIndex((byte)index);
    }

    /**
     * 添加获取设备名称命令到此命令串。
     * @param inputPort 访问的端口
     * @param bufferSize 用于存放返回数据的缓存大小
     * @param index 存放返回数据的引导位置
     */
    public void getDeviceName(InputPort inputPort,int bufferSize,int index){
        if(index > 1024)
            throw new IllegalArgumentException("引导位置不能大于1024。");
        addOpCode(OpCode.INPUT_GET_DEVICE_NAME);
        addParameter((byte)0x00);
        addParameter(inputPort.getByte());
        addParameter((byte)bufferSize);
        addGlobalIndex((byte)index);
    }

    /**
     * 添加获取类型/模式命令到此命令串。
     * @param inputPort 访问的端口
     * @param typeIndex 存放返回类型数据的引导位置
     * @param modeIndex 存放返回模式数据的引导位置
     */
    public void getTypeMode(InputPort inputPort,int typeIndex,int modeIndex){
        if(typeIndex > 1024){
            throw new IllegalArgumentException("类型数据的引导位置不能大于1024。");
        }
        if(modeIndex > 1024){
            throw new IllegalArgumentException("模式数据的引导位置不能大于1024。");
        }

        addOpCode(OpCode.INPUT_GET_TYPE_MODE);
        addParameter((byte)0x00);
        addParameter(inputPort.getByte());
        addParameter((byte)typeIndex);
        addParameter((byte)modeIndex);
    }
    /**
     * 添加获取SI值命令到此命令串。
     * @param port 访问的端口
     * @param mode 访问的端口的模式
     * @param index 存放返回SI值的引导位置
     */
    public void readySI(InputPort port,int mode,int index){
	    if(index > 1024){
		    throw new IllegalArgumentException("数据的引导位置不能大于1024。");
	    }
        addOpCode(OpCode.INPUT_READY_SI);
        addParameter((byte)0x00);
        addParameter(port.getByte());
        addParameter((byte)0x00);
        addParameter((byte)mode);
        addParameter((byte)0x01);
	    addGlobalIndex((byte)index);
    }

    /**
     * 添加获取原始传感器值命令到此命令串。
     * @param port 访问的端口
     * @param mode 访问的端口的模式
     * @param index 存放返回原始值的引导位置
     */
    public void readyRaw(InputPort port,int mode,int index){
	    if(index > 1024){
		    throw new IllegalArgumentException("数据的引导位置不能大于1024。");
	    }
        addOpCode(OpCode.INPUT_READY_RAW);
        addParameter((byte)0x00);
        addParameter(port.getByte());
        addParameter((byte)0x00);
        addParameter((byte)mode);
        addParameter((byte)0x01);
	    addGlobalIndex((byte)index);
    }

    /**
     * 添加获取传感器百分比值命令到此命令串。
     * @param port 访问的端口
     * @param mode 访问的端口的模式
     * @param index 存放返回百分比值的引导位置
     */
    public void readyPercent(InputPort port,int mode,int index){
	    if(index > 1024){
		    throw new IllegalArgumentException("数据的引导位置不能大于1024。");
	    }
        addOpCode(OpCode.INPUT_READY_PCT);
        addParameter((byte)0x00);
        addParameter(port.getByte());
        addParameter((byte)0x00);
        addParameter((byte)mode);
        addParameter((byte)0x01);
	    addGlobalIndex((byte)index);
    }

    //==========SENSOR OPERATION END=========//

    //=========COMMAND OPERATION END=========//

    //Test main
    public static void main(String[] args) throws IOException, InterruptedException{
        Command command = new Command(CommandType.COMMAND_DIRECT_NO_REPLY);
        command.timeMotorSpeed(100,1000,true,OutputPort.A);
        byte[] bytes = command.toBytes();
        FileOutputStream fos = new FileOutputStream("E:\\ev3java.bin");
        fos.write(bytes);
        fos.close();
        for(byte b:bytes){
            System.out.print(((int)b)+",");
        }
        System.out.println();
    }
}
