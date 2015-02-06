package com.charlie.ev3;

/**
 * 电机的方向。
 * @author Charlie Jiang
 */
public enum Polarity {
    /**
     * 向后。
     */
    BACKWARD((byte)-1),
    /**
     * 向前。
     */
    FORWARD((byte)1),
    /**
     * 与当前相反的方向。
     */
    OPPOSITE((byte)0);
    private byte b;
    private Polarity(byte b){
        this.b = b;
    }
    public byte getByte(){
        return b;
    }
}
