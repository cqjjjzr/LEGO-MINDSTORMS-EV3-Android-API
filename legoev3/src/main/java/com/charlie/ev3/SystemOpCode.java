package com.charlie.ev3;

/**
 * System Command的操作码。
 * @author Charlie Jiang
 */
public enum SystemOpCode {
    BEGIN_DOWNLOAD((byte)0x92),
    CONTINUE_DOWNLOAD((byte)0x93),
    CLOSE_FILE_HANDLE((byte)0x98),
    CREATE_DIRECTORY((byte)0x9b),
    DELETE_FILE((byte)0x9c);
    private byte b;
    private SystemOpCode(byte b){
        this.b = b;
    }
    public byte getByte(){
        return b;
    }
    public static SystemOpCode getByByte(byte b){
        for(SystemOpCode op:SystemOpCode.values()){
            if(op.getByte()==b)
                return op;
        }
        return null;
    }
}
