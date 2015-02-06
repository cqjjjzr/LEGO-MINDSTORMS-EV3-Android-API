package com.charlie.ev3;

import java.util.LinkedList;

/**
 * 一个EV3主机上的端口。
 * @author Charlie Jiang
 */
public class Port {
    protected int index;
    protected InputPort inputPort;
    private String name;
    private byte mode;
    private DeviceType deviceType;

    private float SIValue;
    private int rawValue;
    private byte percentValue;

    private LinkedList<PropertyChangedListener> listeners = new LinkedList<>();

    protected Port(String name, InputPort inputPort, int index) {
        this.name = name;
        this.inputPort = inputPort;
        this.index = index;
        for(PropertyChangedListener listener:listeners) listener.onPropertyChanged(this);
    }

    /**
     * 获取端口的名称。
     * @return 端口的名称。
     */
    public String getName() {
        return name;
    }

    /**
     * 设置端口的名称。
     * @param name 端口的名称。
     */
    public void setName(String name) {
        this.name = name;
        for(PropertyChangedListener listener:listeners) listener.onPropertyChanged(this);
    }

    /**
     * 获取此端口的设备类型。
     * @return 设备的类型。
     */
    public DeviceType getDeviceType() {
        return deviceType;
    }

    /**
     * 设置此端口的设备类型。
     * @param deviceType 设备的类型。
     */
    protected void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
        for(PropertyChangedListener listener:listeners) listener.onPropertyChanged(this);
    }

    /**
     * 获取设备当前模式。有些设备有多重模式。
     * @return 当前模式。
     */
    public byte getMode() {
        return mode;
    }

    /**
     * 设置设备当前类型。有些设备有多重模式。
     * @param mode 设备当前模式
     */
    public void setMode(byte mode) {
        this.mode = mode;
        for(PropertyChangedListener listener:listeners) listener.onPropertyChanged(this);
    }

    /**
     * 设置设备当前类型。有些设备有多重模式。
     * @param mode 设备当前模式
     */
    public void setMode(TouchMode mode) {
        this.mode = (byte)mode.ordinal();
        for(PropertyChangedListener listener:listeners) listener.onPropertyChanged(this);
    }

    /**
     * 设置设备当前类型。有些设备有多重模式。
     * @param mode 设备当前模式
     */
    public void setMode(NXTLightMode mode) {
        this.mode = (byte)mode.ordinal();
        for(PropertyChangedListener listener:listeners) listener.onPropertyChanged(this);
    }

    /**
     * 设置设备当前类型。有些设备有多重模式。
     * @param mode 设备当前模式
     */
    public void setMode(NXTColorMode mode) {
        this.mode = (byte)mode.ordinal();
        for(PropertyChangedListener listener:listeners) listener.onPropertyChanged(this);
    }

    /**
     * 设置设备当前类型。有些设备有多重模式。
     * @param mode 设备当前模式
     */
    public void setMode(NXTSoundMode mode) {
        this.mode = (byte)mode.ordinal();
        for(PropertyChangedListener listener:listeners) listener.onPropertyChanged(this);
    }

    /**
     * 设置设备当前类型。有些设备有多重模式。
     * @param mode 设备当前模式
     */
    public void setMode(NXTTemperatureMode mode) {
        this.mode = (byte)mode.ordinal();
        for(PropertyChangedListener listener:listeners) listener.onPropertyChanged(this);
    }

    /**
     * 设置设备当前类型。有些设备有多重模式。
     * @param mode 设备当前模式
     */
    public void setMode(NXTUltrasonicMode mode) {
        this.mode = (byte)mode.ordinal();
        for(PropertyChangedListener listener:listeners) listener.onPropertyChanged(this);
    }

    /**
     * 设置设备当前类型。有些设备有多重模式。
     * @param mode 设备当前模式
     */
    public void setMode(MotorMode mode) {
        this.mode = (byte)mode.ordinal();
        for(PropertyChangedListener listener:listeners) listener.onPropertyChanged(this);
    }

    /**
     * 设置设备当前类型。有些设备有多重模式。
     * @param mode 设备当前模式
     */
    public void setMode(ColorMode mode) {
        this.mode = (byte)mode.ordinal();
        for(PropertyChangedListener listener:listeners) listener.onPropertyChanged(this);
    }

    /**
     * 设置设备当前类型。有些设备有多重模式。
     * @param mode 设备当前模式
     */
    public void setMode(UltrasonicMode mode) {
        this.mode = (byte)mode.ordinal();
        for(PropertyChangedListener listener:listeners) listener.onPropertyChanged(this);
    }

    /**
     * 设置设备当前类型。有些设备有多重模式。
     * @param mode 设备当前模式
     */
    public void setMode(GyroscopeMode mode) {
        this.mode = (byte)mode.ordinal();
        for(PropertyChangedListener listener:listeners) listener.onPropertyChanged(this);
    }

    /**
     * 设置设备当前类型。有些设备有多重模式。
     * @param mode 设备当前模式
     */
    public void setMode(IRMode mode) {
        this.mode = (byte) mode.ordinal();
        for (PropertyChangedListener listener : listeners) listener.onPropertyChanged(this);
    }

    /**
     * 获取设备的当前SI（国际系统单位）值。
     * @return 当前SI（国际系统单位）值
     */
    public float getSIValue() {
        return SIValue;
    }

    /**
     * 设置当前SI（国际系统单位）值。
     * @param SIValue 当前SI（国际系统单位）值
     */
    protected void setSIValue(float SIValue) {
        this.SIValue = SIValue;
        for(PropertyChangedListener listener:listeners) listener.onPropertyChanged(this);
    }

    /**
     * 获取当前原始传感器值。
     * @return 当前原始传感器值
     */
    public int getRawValue() {
        return rawValue;
    }

    /**
     * 设置当前原始传感器值。
     * @param rawValue 当前原始传感器值
     */
    protected void setRawValue(int rawValue) {
        this.rawValue = rawValue;
        for(PropertyChangedListener listener:listeners) listener.onPropertyChanged(this);
    }

    /**
     * 获取当前百分比值。
     * @return 当前百分比值
     */
    public byte getPercentValue() {
        return percentValue;
    }

    /**
     * 设置当前原始百分比值。
     * @param percentValue 当前百分比值
     */
    protected void setPercentValue(byte percentValue) {
        this.percentValue = percentValue;
        for(PropertyChangedListener listener:listeners) listener.onPropertyChanged(this);
    }

    /**
     * 增加一个监听属性更改事件的监听器。
     * @param propertyChangedListener 监听属性更改事件的监听器
     */
    public void addPropertyChangedListener(PropertyChangedListener propertyChangedListener){
        listeners.add(propertyChangedListener);
    }

    /**
     * 移除一个监听属性更改事件的监听器。
     * @param propertyChangedListener 监听属性更改事件的监听器
     */
    public void removePropertyChangedListener(PropertyChangedListener propertyChangedListener){
        listeners.remove(propertyChangedListener);
    }
}
