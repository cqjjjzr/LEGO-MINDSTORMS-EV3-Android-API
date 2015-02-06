package com.charlie.ev3;

/**
 * 发送到EV3主机的命令类型。
 * @author Charlie Jiang
 */
public enum CommandType {
    /**
     * 预期回复的Direct Command。
     */
    COMMAND_DIRECT_REPLY((byte)0x00),
    /**
     * 没有回复的Direct Command。
     */
    COMMAND_DIRECT_NO_REPLY((byte)0x80),
    /**
     * 预期回复的System Command。
     */
    COMMAND_SYSTEM_REPLY((byte)0x01),
    /**
     * 没有回复的System Command。
     */
    COMMAND_SYSTEM_NO_REPLY((byte)0x81);
    private byte b;
    private CommandType(byte b){
        this.b = b;
    }
    public byte getByte(){
        return b;
    }
}
