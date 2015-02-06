package com.charlie.ev3;

/**
 * 输出端口。
 * @author Charlie Jiang
 */
public enum OutputPort {
    /**
     * 电机端口A。
     */
    A((byte)0x01),
    /**
     * 电机端口B。
     */
    B((byte)0x02),
    /**
     * 电机端口C。
     */
    C((byte)0x04),
    /**
     * 电机端口D。
     */
    D((byte)0x08),
    /**
     * 所有电机。
     */
    ALL((byte)0x0f);
    private byte b;
    private OutputPort(byte b){
        this.b = b;
    }
    public byte getByte(){
        return b;
    }
    public static OutputPort getByByte(byte b){
        for(OutputPort op:OutputPort.values()){
            if(op.getByte()==b)
                return op;
        }
        return null;
    }
}
