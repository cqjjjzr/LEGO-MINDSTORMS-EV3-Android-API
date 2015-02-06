package com.charlie.ev3;

/**
 * EV3颜色传感器的模式。
 * @author Charlie Jiang
 */
public enum ColorMode {
    /**
     * 反射值。
     */
    REFLECTIVE,
    /**
     * 环境值。
     */
    AMBIENT,
    /**
     * 获取颜色。
     */
    COLOR,
    /**
     * 原始传感器的反射值。
     */
    REFLECTIVE_RAW,
    /**
     * 原始传感器的RGB值。
     */
    REFLECTIVE_RGB,
    /**
     * 校准值。
     */
    CALIBRATION
}
