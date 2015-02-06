package com.charlie.ev3;

/**
 * EV3主机的响应。
 * @author Charlie Jiang
 */
public class Response {
    private ReplyType replyType;
    private short sequence;
    private byte[] data;
    private SystemOpCode systemCommand;
    private SystemReplyStatus systemReplyStatus;
    public Object event = new Object();
    protected Response(short sequence){
        this.sequence = sequence;
    }
    public void setReplyType(ReplyType replyType){
        this.replyType = replyType;
    }
    public ReplyType getReplyType(){
        return replyType;
    }
    public void setSequence(short sequence){
        this.sequence = sequence;
    }
    public short getSequence(){
        return sequence;
    }
    public void setData(byte[] data){
        this.data = data;
    }
    public byte[] getData(){
        return data;
    }
    public void setSystemCommand(SystemOpCode systemCommand){
        this.systemCommand = systemCommand;
    }
    public SystemOpCode getSystemCommand(){
        return systemCommand;
    }
    public void setSystemReplyStatus(SystemReplyStatus systemReplyStatus){
        this.systemReplyStatus = systemReplyStatus;
    }
    public SystemReplyStatus getSystemReplyStatus(){
        return systemReplyStatus;
    }
}
