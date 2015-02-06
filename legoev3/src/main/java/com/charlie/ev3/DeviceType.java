package com.charlie.ev3;

/**
 * 输入、输出设备的类型。
 * @author Charlie Jiang
 */
public enum DeviceType {
    //NXT Devices.
    /**
     * NXT 触碰传感器。
     */
    NXT_TOUCH(1),
    /**
     * NXT 光电传感器。
     */
    NXT_LIGHT(2),
    /**
     * NXT 声音传感器。
     */
    NXT_SOUND(3),
    /**
     * NXT 颜色传感器。
     */
    NXT_COLOR(4),
    /**
     * NXT 超声波距离传感器。
     */
    NXT_ULTRASONIC(5),
    /**
     * NXT 温度传感器。
     */
    NXT_TEMPERATE(6),

    //Motors
    /**
     * 大型电机。
     */
    LARGE_MOTOR(7),
    /**
     * 中型电机。
     */
    MEDIUM_MOTOR(8),

    //EV3 Devices.
    /**
     * EV3 触碰传感器。
     */
    TOUCH(16),
    /**
     * EV3 颜色传感器。
     */
    COLOR(29),
    /**
     * EV3 超声波距离传感器。
     */
    ULTRASONIC(30),
    /**
     * EV3 陀螺仪传感器。
     */
    GYROSCOPE(32),
    /**
     * EV3 红外传感器。
     */
    IR(33),

    //Others.
    /**
     * 端口正在初始化。
     */
    INITIALIZING(0x7d),
    /**
     * 空端口。
     */
    EMPTY(0x7e),
    /**
     * 传感器插入了输出接口，或相反。
     */
    ERROR(0x7f),
    /**
     * 未知传感器。
     */
    UNKNOWN(0xff);
    protected int getValue() {
        return value;
    }
    int value;
    private DeviceType(int value){
        this.value = value;
    }
}
