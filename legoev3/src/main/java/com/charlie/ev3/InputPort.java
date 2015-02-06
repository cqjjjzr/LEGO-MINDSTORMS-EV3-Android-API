package com.charlie.ev3;

/**
 * 输入端口。
 * @author Charlie Jiang
 */
public enum InputPort {
    /**
     * 端口1。
     */
    One((byte)0x01),
    /**
     * 端口2。
     */
    Two((byte)0x02),
    /**
     * 端口3。
     */
    Three((byte)0x03),
    /**
     * 端口4。
     */
    Four((byte)0x04),
    /**
     * 端口A。
     */
    A((byte)0x10),
    /**
     * 端口B。
     */
    B((byte)0x11),
    /**
     * 端口C。
     */
    C((byte)0x12),
    /**
     * 端口D。
     */
    D((byte)0x13);
    private byte b;
    private InputPort(byte b){
        this.b = b;
    }
    public byte getByte(){
        return b;
    }
    public static InputPort getByByte(byte b){
        for(InputPort op:InputPort.values()){
            if(op.getByte()==b)
                return op;
        }
        return null;
    }
}
