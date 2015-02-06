package com.charlie.ev3;

/**
 * 发送到EV3的参数大小。
 * @author Charlie Jiang
 */
public enum ArgumentSize {
    /**
     * 字节类型（1字节）。
     */
    BYTE((byte)0x81),
    /**
     * 短整数型（2字节）。
     */
    SHORT((byte)0x82),
    /**
     * 整型（4字节）。
     */
    INT((byte)0x83),
    /**
     * 字符串型。
     */
    STRING((byte)0x84);
    private byte b;
    private ArgumentSize(byte b){
        this.b = b;
    }
    public byte getByte(){
        return b;
    }
}
