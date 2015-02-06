package com.charlie.ev3;

/**
 * EV3主机的LED模式。
 * @author Charlie Jiang
 */
public enum LEDPattern {
    /**
     * LED关。
     */
    OFF,
    /**
     * 静态绿。
     */
    GREEN,
    /**
     * 静态红。
     */
    RED,
    /**
     * 静态橙。
     */
    ORANGE,
    /**
     * 闪烁绿。
     */
    GREEN_FLASH,
    /**
     * 闪烁红。
     */
    RED_FLASH,
    /**
     * 闪烁橙。
     */
    ORANGE_FLASH,
    /**
     * 脉冲绿。
     */
    GREEN_PULSE,
    /**
     * 脉冲红。
     */
    RED_PULSE,
    /**
     * 脉冲橙。
     */
    ORANGE_PULSE
}
