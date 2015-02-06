package com.charlie.ev3;

/**
 * 从EV3主机返回的数据类型。
 * @author Charlie Jiang
 */
public enum ReplyType {
    /**
     * Direct Command的返回。
     */
    DIRECT_REPLY((byte)0x02),
    /**
     * System Command的返回。
     */
    SYSTEM_REPLY((byte)0x03),
    /**
     * 错误Direct Command的返回。
     */
    DIRECT_REPLY_ERROR((byte)0x04),
    /**
     * 错误Direct Command的返回。
     */
    SYSTEM_REPLY_ERROR((byte)0x05);
    private byte b;
    private ReplyType(byte b){
        this.b = b;
    }
    public byte getByte(){
        return b;
    }
    public static ReplyType getByByte(byte b){
        switch (b){
            case 0x02:return DIRECT_REPLY;
            case 0x03:return SYSTEM_REPLY;
            case 0x04:return DIRECT_REPLY_ERROR;
            case 0x05:return SYSTEM_REPLY_ERROR;
            default:return null;
        }
    }
}
