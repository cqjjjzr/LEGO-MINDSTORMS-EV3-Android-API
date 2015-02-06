package com.charlie.ev3;

/**
 * EV3主机按钮的状态。
 * @author Charlie Jiang
 */
public class BrickButton {
    /**
     * 检测按钮是否按下。
     * @return 按钮是否按下
     */
    public boolean isPressed() {
        return pressed;
    }

    /**
     * 设置按钮是否按下。
     * @param pressed 按钮是否按下
     */
    protected void setPressed(boolean pressed) {
        this.pressed = pressed;
    }

    private boolean pressed;
}
