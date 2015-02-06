package com.charlie.ev3;

/**
 * EV3红外传感器的模式。
 */
public enum IRMode {
    /**
     * 接近。
     */
    PROXIMITY,
    /**
     * 寻找。
     */
    SEEK,
    /**
     * EV3遥控器控制。
     */
    REMOTE,
    REMOTE_A,
    SA_LT,
    /**
     * 校准。
     */
    CALIBRATE
}
