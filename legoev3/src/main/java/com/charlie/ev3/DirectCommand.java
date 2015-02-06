package com.charlie.ev3;

import com.google.common.primitives.Bytes;

import java.io.IOException;

/**
 * EV3主机的Direct Command。
 * @author Charlie Jiang
 */
public class DirectCommand {
    private final Brick brick;
    protected DirectCommand(Brick brick){
        this.brick = brick;
    }

    //==========MOTOR OPERATION BEGIN==========//

    /**
     * 用指定的功率打开电机。
     * @param power 指定的功率（-100 到 100）
     * @param ports 指定的端口
     * @throws java.io.IOException 发送失败时抛出。
     */
    public void turnMotorPower(int power,OutputPort... ports) throws IOException{
        Command command = new Command(CommandType.COMMAND_DIRECT_NO_REPLY);
        command.turnMotorPower(power, ports);
        command.startMotor(ports);
        brick.sendCommand(command);
    }

    /**
     * 用指定的速度打开电机。
     * @param speed 指定的速度（-100 到 100）
     * @param ports 指定的端口
     * @throws java.io.IOException 发送失败时抛出。
     */
    public void turnMotorSpeed(int speed,OutputPort... ports) throws IOException{
        Command command = new Command(CommandType.COMMAND_DIRECT_NO_REPLY);
        command.turnMotorSpeed(speed, ports);
        command.startMotor(ports);
        brick.sendCommand(command);
    }

    /**
     * 用指定的功率、步数步进电机。
     * @param power 指定的功率（-100 到 100）
     * @param steps 步数
     * @param brake 是否刹车
     * @param ports 指定的电机
     * @throws java.io.IOException 发送失败时抛出。
     */
    public void stepMotorPower(int power,int steps,boolean brake,OutputPort... ports) throws IOException{
        stepMotorPower(power,0,steps,0,brake,ports);
    }
    /**
     * 用指定的功率、步数、渐入降出步数步进电机。
     * @param power 指定的功率（-100 到 100）
     * @param constantSteps 步数
     * @param brake 是否刹车
     * @param ports 指定的电机
     * @param rampDownSteps 电机渐出的步数
     * @param rampUpSteps 电机渐入的步数
     * @throws java.io.IOException 发送失败时抛出。
     */
    public void stepMotorPower(int power,int rampUpSteps, int constantSteps, int rampDownSteps, boolean brake,OutputPort... ports) throws IOException{
        Command command = new Command(CommandType.COMMAND_DIRECT_NO_REPLY);
        command.stepMotorPower(power, rampUpSteps, constantSteps, rampDownSteps, brake, ports);
        brick.sendCommand(command);
    }

    /**
     * 用指定的速度、步数步进电机。
     * @param speed 指定的速度（-100 到 100）
     * @param steps 步数
     * @param brake 是否刹车
     * @param ports 指定的电机
     * @throws java.io.IOException 发送失败时抛出。
     */
    public void stepMotorSpeed(int speed,int steps,boolean brake,OutputPort... ports) throws IOException{
        stepMotorSpeed(speed,0,steps,0,brake,ports);
    }

    /**
     * 用指定的速度、步数、渐入降出步数步进电机。
     * @param speed 指定的速度（-100 到 100）
     * @param constantSteps 步数
     * @param brake 是否刹车
     * @param ports 指定的电机
     * @param rampDownSteps 电机渐出的步数
     * @param rampUpSteps 电机渐入的步数
     * @throws java.io.IOException 发送失败时抛出。
     */
    public void stepMotorSpeed(int speed,int rampUpSteps, int constantSteps, int rampDownSteps, boolean brake,OutputPort... ports) throws IOException{
        Command command = new Command(CommandType.COMMAND_DIRECT_NO_REPLY);
        command.stepMotorSpeed(speed, rampUpSteps, constantSteps, rampDownSteps, brake, ports);
        brick.sendCommand(command);
    }

    /**
     * 使用指定功率打开电机一段时间。
     * @param power 指定的功率（-100 到 100）
     * @param milliseconds 打开的时间（毫秒）
     * @param brake 是否刹车
     * @param ports 指定的端口
     * @throws java.io.IOException 发送失败时抛出。
     */
    public void timeMotorPower(int power,int milliseconds,boolean brake,OutputPort... ports) throws IOException{
        timeMotorPower(power,0,milliseconds,0,brake,ports);
    }

    /**
     * 使用指定功率、渐入渐出时间打开电机一段时间。
     * @param power 指定的功率（-100 到 100）
     * @param milliseconds 打开的时间（毫秒）
     * @param brake 是否刹车
     * @param ports 指定的端口
     * @param rampDownMilliseconds 渐出时间（毫秒）
     * @param rampUpMilliseconds 渐入时间（毫秒）
     * @throws java.io.IOException 发送失败时抛出。
     */
    public void timeMotorPower(int power,int rampUpMilliseconds, int milliseconds, int rampDownMilliseconds,boolean brake,OutputPort... ports) throws IOException{
        Command command = new Command(CommandType.COMMAND_DIRECT_NO_REPLY);
        command.timeMotorPower(power, rampUpMilliseconds, milliseconds, rampDownMilliseconds, brake, ports);
        brick.sendCommand(command);
    }

    /**
     * 使用指定速度打开电机一段时间。
     * @param speed 指定的速度（-100 到 100）
     * @param milliseconds 打开的时间（毫秒）
     * @param brake 是否刹车
     * @param ports 指定的端口
     * @throws java.io.IOException 发送失败时抛出。
     */
    public void timeMotorSpeed(int speed,int milliseconds,boolean brake,OutputPort... ports) throws IOException{
        timeMotorSpeed(speed,0,milliseconds,0,brake,ports);
    }

    /**
     * 使用指定速度、渐入渐出时间打开电机一段时间。
     * @param speed 指定的速度（-100 到 100）
     * @param milliseconds 打开的时间（毫秒）
     * @param brake 是否刹车
     * @param ports 指定的端口
     * @param rampDownMilliseconds 渐出时间（毫秒）
     * @param rampUpMilliseconds 渐入时间（毫秒）
     * @throws java.io.IOException 发送失败时抛出。
     */
    public void timeMotorSpeed(int speed,int rampUpMilliseconds, int milliseconds, int rampDownMilliseconds,boolean brake,OutputPort... ports) throws IOException{
        Command command = new Command(CommandType.COMMAND_DIRECT_NO_REPLY);
        command.timeMotorSpeed(speed, rampUpMilliseconds, milliseconds, rampDownMilliseconds, brake, ports);
        brick.sendCommand(command);
    }

    /**
     * 设置指定电机的方向。
     * @param port 指定的电机
     * @param polarity 新的方向值
     * @throws java.io.IOException 发送失败时抛出。
     */
    public void setMotorPolarity(OutputPort port,Polarity polarity) throws IOException{
        Command command = new Command(CommandType.COMMAND_DIRECT_NO_REPLY);
        command.setMotorPolarity(port, polarity);
        brick.sendCommand(command);
    }

    /**
     * 打开指定端口的电机。
     * @param port 指定的端口
     * @throws java.io.IOException 发送失败时抛出。
     */
    public void startMotor(OutputPort... port) throws IOException{
        Command command = new Command(CommandType.COMMAND_DIRECT_NO_REPLY);
        command.startMotor(port);
        brick.sendCommand(command);
    }

    /**
     * 停止指定端口的电机。
     * @param brake 是否刹车
     * @param port 指定的端口
     * @throws java.io.IOException 发送失败时抛出。
     */
    public void stopMotor(boolean brake,OutputPort... port) throws IOException{
        Command command = new Command(CommandType.COMMAND_DIRECT_NO_REPLY);
        command.stopMotor(brake,port);
        brick.sendCommand(command);
    }

    /**
     * 同步步进电机。
     * @param speed 指定的速度（-100 到 100）
     * @param turnRatio 转动比率（-200 到 200）
     * @param step 步数
     * @param brake 是否刹车
     * @param ports 指定的端口
     * @throws java.io.IOException 发送失败时抛出。
     */
    public void stepMotorSync(int speed, short turnRatio, int step, boolean brake,OutputPort... ports) throws IOException{
        Command command = new Command(CommandType.COMMAND_DIRECT_NO_REPLY);
        command.stepMotorSync(speed, turnRatio, step, brake, ports);
        brick.sendCommand(command);
    }

    /**
     * 同步调速电机。
     * @param speed 指定的速度（-100 到 100）
     * @param turnRatio 转动比率（-200 到 200）
     * @param milliseconds 时间
     * @param brake 是否刹车
     * @param ports 指定的端口
     * @throws java.io.IOException 发送失败时抛出。
     */
    public void timeMotorSync(int speed, short turnRatio, int milliseconds, boolean brake,OutputPort... ports) throws IOException{
        Command command = new Command(CommandType.COMMAND_DIRECT_REPLY);
        command.stepMotorSync(speed, turnRatio, milliseconds, brake, ports);
        brick.sendCommand(command);
    }

    /**
     * 重置所有端口到默认值。
     * @throws java.io.IOException 发送失败时抛出。
     */
    public void clearAllDevices() throws IOException{
        Command command = new Command(CommandType.COMMAND_DIRECT_NO_REPLY);
        command.clearAllDevices();
        brick.sendCommand(command);
    }

    /**
     * 重置指定端口到默认值。
     * @param ports 指定的端口
     * @throws java.io.IOException 发送失败时抛出。
     */
    public void clearAllChanges(InputPort... ports) throws IOException{
        Command command = new Command(CommandType.COMMAND_DIRECT_NO_REPLY);
        command.clearAllChanges(ports);
        brick.sendCommand(command);
    }

    //==========MOTOR OPERATION END==========//

    //=========SOUND OPERATION BEGIN=========//

    /**
     * 播放指定的频率的声音一段时间。
     * @param volume 声音的音量（0到100）
     * @param duration 声音的持续时间，单位为毫秒
     * @param frequency 声音的频率，单位为赫兹
     * @throws java.io.IOException 发送失败时抛出。
     */
    public void playTone(int volume,short duration,short frequency) throws IOException{
        Command command = new Command(CommandType.COMMAND_DIRECT_NO_REPLY);
        command.playTone(volume, duration, frequency);
        brick.sendCommand(command);
    }

    /**
     * 播放一段存放在EV3主机上的音频。
     * @param volume 音频的音量（0到100）
     * @param name 音频文件在EV3主机上的路径，如果是RSF类型则不包括.RSF后缀
     * @throws java.io.IOException 发送失败时抛出。
     */
    public void playSound(int volume,String name) throws IOException{
        Command command = new Command(CommandType.COMMAND_DIRECT_NO_REPLY);
        command.playSound(volume, name);
        brick.sendCommand(command);
    }

    //==========SOUND OPERATION END==========//

    //==========UI OPERATION BEGIN===========//

    /**
     * 获取当前EV3主机的固件版本。
     * @return 当前固件版本
     * @throws java.io.IOException 发送失败时抛出。
    */
    public String getFirmwareVersion() throws IOException{
        Command command = new Command(CommandType.COMMAND_DIRECT_REPLY,(short)0x10,0);
        command.getFirmwareVersion(0x10,0);
        brick.sendCommand(command);
        byte[] data = command.response.getData();
        if(data == null)
            return null;
        int index = Bytes.indexOf(data,(byte)0);
        return new String(data,0,index);
    }

    /**
     * 获取按钮是否被按下。
     * @param button 需要获取的按钮
     * @return 按钮是否被按下
     * @throws java.io.IOException 发送失败时抛出。
     */
    public boolean isButtonPressed(BrickButtons button) throws IOException{
        Command command = new Command(CommandType.COMMAND_DIRECT_REPLY,(short)1,0);
        command.isButtonPressed(button,0);
        brick.sendCommand(command);
        System.out.println((int)(command.response.getData()[0]));
        return command.response.getData()[0] != 0;
    }

    /**
     * 设置EV3主机的LED模式。
     * @param pattern LED模式
     * @throws java.io.IOException 发送失败时抛出。
     */
    public void setLEDPattern(LEDPattern pattern) throws IOException{
        Command command = new Command(CommandType.COMMAND_DIRECT_NO_REPLY);
        command.setLEDPattern(pattern);
        brick.sendCommand(command);
    }

    /**
     * 清空EV3界面。
     * @throws java.io.IOException 发送失败时抛出。
     */
    public void cleanUI() throws IOException{
        Command command = new Command(CommandType.COMMAND_DIRECT_NO_REPLY);
        command.cleanUI();
        brick.sendCommand(command);
    }

    /**
     * 在EV3屏幕上画线。
     * @param color 线的颜色
     * @param x0 X 起点
     * @param y0 Y 起点
     * @param x1 X 终点
     * @param y1 Y 终点
     * @throws java.io.IOException 发送失败时抛出。
     */
    public void drawLine(UIColor color,short x0,short y0,short x1,short y1) throws IOException{
        Command command = new Command(CommandType.COMMAND_DIRECT_NO_REPLY);
        command.drawLine(color, x0, y0, x1, y1);
        brick.sendCommand(command);
    }

    /**
     * 在EV3屏幕上画点。
     * @param color 点的颜色
     * @param x X 位置
     * @param y Y 位置
     * @throws java.io.IOException 发送失败时抛出。
     */
    public void drawPixel(UIColor color,short x,short y) throws IOException{
        Command command = new Command(CommandType.COMMAND_DIRECT_NO_REPLY);
        command.drawPixel(color, x, y);
        brick.sendCommand(command);
    }

    /**
     * 在EV3屏幕上画一个矩形。
     * @param color 矩形的颜色
     * @param x X 位置
     * @param y Y 位置
     * @param width 矩形的宽
     * @param height 矩形的高
     * @param filled 矩形是否填充
     * @throws java.io.IOException 发送失败时抛出。
     */
    public void drawRectangle(UIColor color,short x,short y,short width,short height,boolean filled) throws IOException{
        Command command = new Command(CommandType.COMMAND_DIRECT_NO_REPLY);
        command.drawRectangle(color, x, y, width, height, filled);
        brick.sendCommand(command);
    }

    /**
     * 在EV3屏幕上画一个填充矩形，反相其下面的像素。
     * @param x X 位置
     * @param y Y 位置
     * @param width 矩形的宽
     * @param height 矩形的高
     * @throws java.io.IOException 发送失败时抛出。
     */
    public void drawInverseRectangle(short x,short y,short width,short height) throws IOException{
        Command command = new Command(CommandType.COMMAND_DIRECT_NO_REPLY);
        command.drawInverseRectangle(x, y, width, height);
        brick.sendCommand(command);
    }

    /**
     * 在EV3主机屏幕上画一个圆。
     * @param color 圆的颜色
     * @param x X 位置
     * @param y Y 位置
     * @param radius 圆的半径
     * @param filled 圆是否填充
     * @throws java.io.IOException 发送失败时抛出。
     */
    public void drawCircle(UIColor color,short x,short y,short radius,boolean filled) throws IOException{
        Command command = new Command(CommandType.COMMAND_DIRECT_NO_REPLY);
        command.drawCircle(color, x, y, radius, filled);
        brick.sendCommand(command);
    }

    /**
     * 在EV3屏幕上画文本。
     * @param color 文本的颜色
     * @param x X 位置
     * @param y Y 位置
     * @param text 需要画的文本
     * @throws java.io.IOException 发送失败时抛出。
     */
    public void drawText(UIColor color,short x,short y,String text) throws IOException{
        Command command = new Command(CommandType.COMMAND_DIRECT_NO_REPLY);
        command.drawText(color, x, y, text);
        brick.sendCommand(command);
    }

    /**
     * 填充指定Y坐标之间的屏幕的宽。
     * @param color 填充的颜色
     * @param y0 Y 起点
     * @param y1 Y 终点
     * @throws java.io.IOException 发送失败时抛出。
     */
    public void drawFillWindow(UIColor color,short y0,short y1) throws IOException{
        Command command = new Command(CommandType.COMMAND_DIRECT_NO_REPLY);
        command.drawFillWindow(color, y0, y1);
        brick.sendCommand(command);
    }

    /**
     * 在EV3屏幕上画图像。
     * @param color 图像的颜色
     * @param x X 位置
     * @param y Y 位置
     * @param devicePath 图像文件在EV3主机上的位置
     * @throws java.io.IOException 发送失败时抛出。
     */
    public void drawImage(UIColor color,short x,short y,String devicePath) throws IOException{
        Command command = new Command(CommandType.COMMAND_DIRECT_NO_REPLY);
        command.drawImage(color, x, y, devicePath);
        brick.sendCommand(command);
    }

    /**
     * 切换画文本的字体。
     * @param font Type of font to use
     * @throws java.io.IOException 发送失败时抛出。
     */
    public void selectFont(UIFont font) throws IOException{
        Command command = new Command(CommandType.COMMAND_DIRECT_NO_REPLY);
        command.selectFont(font);
        brick.sendCommand(command);
    }

    /**
     * 启用/禁用顶部状态栏。
     * @param isEnabled 启用或禁用
     * @throws java.io.IOException 发送失败时抛出。
     */
    public void enableStatusTopLine(boolean isEnabled) throws IOException{
        Command command = new Command(CommandType.COMMAND_DIRECT_NO_REPLY);
        command.enableTopLine(isEnabled);
        brick.sendCommand(command);
    }

    /**
     * 刷新EV3屏幕。
     * @throws java.io.IOException 发送失败时抛出。
     */
    public void updateUI() throws IOException{
        Command command = new Command(CommandType.COMMAND_DIRECT_NO_REPLY);
        command.updateUI();
        brick.sendCommand(command);
    }

    /**
     * 在EV3屏幕上画虚线。
     * @param color 线的颜色
     * @param x0 X 起点
     * @param y0 Y 起点
     * @param x1 X 终点
     * @param y1 Y 终点
     * @param offPixels 虚线中无颜色的像素数量
     * @param onPixels 虚线中有颜色的像素数量
     * @throws java.io.IOException 发送失败时抛出。
     */
    public void drawDottedLine(UIColor color,short x0,short y0,short x1,short y1,short onPixels,short offPixels) throws IOException{
        Command command = new Command(CommandType.COMMAND_DIRECT_NO_REPLY);
        command.drawDottedLine(color, x0, y0, x1, y1, onPixels, offPixels);
        brick.sendCommand(command);
    }

    //============UI OPERATION END===========//

    //=========SENSOR OPERATION START========//

    /**
     * 获取指定端口上指定模式的名称。
     * @param inputPort 访问的端口
     * @param mode 需要获取模式名称的模式
     * @return 模式的名称。
     * @throws java.io.IOException 发送失败时抛出。
     */
    public String getModeName(InputPort inputPort,int mode) throws IOException{
        Command command = new Command(CommandType.COMMAND_DIRECT_REPLY,(short)0x7f,0);
        command.getModeName(inputPort,mode,0x7f,0);
        brick.sendCommand(command);
        byte[] data = command.response.getData();
        int index = Bytes.indexOf(data,(byte)0);
        return new String(data,0,index);
    }

    /**
     * 获取指定端口上的设备名称。
     * @param inputPort 访问的端口
     * @return 设备的名称。
     * @throws java.io.IOException 发送失败时抛出。
     */
    public String getDeviceName(InputPort inputPort) throws IOException{
        Command command = new Command(CommandType.COMMAND_DIRECT_REPLY,(short)0x7f,0);
        command.getDeviceName(inputPort, 0x7f, 0);
        brick.sendCommand(command);
        byte[] data = command.response.getData();
        int index = Bytes.indexOf(data,(byte)0);
        return new String(data,0,index);
    }

    /**
     * 使用指定的模式从指定端口上获取SI值。
     * @param port 指定的端口
     * @param mode 指定的模式
     * @return 获取到的SI值。
     * @throws java.io.IOException 发送失败时抛出。
     */
    public float readySI(InputPort port,int mode) throws IOException{
        Command command = new Command(CommandType.COMMAND_DIRECT_REPLY,(short)4,0);
        command.readySI(port,mode,0);
        brick.sendCommand(command);
        byte[] data = command.response.getData();
        int res = (data[0] & 0xff) | ((data[1] << 8) & 0xff00)| ((data[2] << 24) >>> 8) | (data[3] << 24); // byte[] to int
        return Float.intBitsToFloat(res);
    }

    /**
     * 使用指定的模式从指定端口上获取原始传感器值。
     * @param port 指定的端口
     * @param mode 指定的模式
     * @return 获取到的原始传感器值。
     * @throws java.io.IOException 发送失败时抛出。
     */
    public int readyRaw(InputPort port,int mode) throws IOException{
        Command command = new Command(CommandType.COMMAND_DIRECT_REPLY,(short)4,0);
        command.readyRaw(port,mode,0);
        brick.sendCommand(command);
        byte[] data = command.response.getData();
        return (data[0] & 0xff) | ((data[1] << 8) & 0xff00)| ((data[2] << 24) >>> 8) | (data[3] << 24); // byte[] to int
    }

    /**
     * 使用指定的模式从指定端口上获取百分比值。
     * @param port 指定的端口
     * @param mode 指定的模式
     * @return 获取到的百分比值。
     * @throws java.io.IOException 发送失败时抛出。
     */
    public int readyPercent(InputPort port,int mode) throws IOException {
        Command command = new Command(CommandType.COMMAND_DIRECT_REPLY,(short)1,0);
        command.readyPercent(port,mode,0);
        brick.sendCommand(command);
        byte[] data = command.response.getData();
        return (int)data[0];
    }

    //==========SENSOR OPERATION END=========//
}
