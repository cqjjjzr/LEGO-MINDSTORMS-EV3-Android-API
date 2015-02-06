package com.charlie.ev3;

/**
 * 操作码。
 * @author Charlie Jiang
 */
public enum OpCode {
    UIREAD_GET_FIRMWARE((short)0x810a),

    UIWRITE_LED((short)0x821b),

    UIBUTTON_PRESSED((short)0x8309),

    UIDRAW_UPDATE((short)0x8400),
    UIDRAW_CLEAN((short)0x8401),
    UIDRAW_PIXEL((short)0x8402),
    UIDRAW_LINE((short)0x8403),
    UIDRAW_CIRCLE((short)0x8404),
    UIDRAW_TEXT((short)0x8405),
    UIDRAW_FILL_RECT((short)0x8409),
    UIDRAW_RECT((short)0x840a),
    UIDRAW_INVERSE_RECT((short)0x8410),
    UIDRAW_SELECT_FONT((short)0x8411),
    UIDRAW_TOP_LINE((short)0x8412),
    UIDRAW_DOT_LINE((short)0x8413),
    UIDRAW_FILL_WINDOW((short)0x8415),
    UIDRAW_FILL_CIRCLE((short)0x8418),
    UIDRAW_BMP_FILE((short)0x841c),

    SOUND_BREAK((short)0x9400),
    SOUND_TONE((short)0x9401),
    SOUND_PLAY((short)0x9402),
    SOUND_REPEAT((short)0x9403),
    SOUND_SERVICE((short)0x9404),


    INPUT_GET_TYPE_MODE((short)0x9905),
    INPUT_GET_DEVICE_NAME((short)0x9915),
    INPUT_GET_MODE_NAME((short)0x9916),
    INPUT_READY_PCT((short)0x991b),
    INPUT_READY_RAW((short)0x991c),
    INPUT_READY_SI((short)0x991d),
    INPUT_CLEAR_ALL((short)0x990a),
    INPUT_CLEAR_CHANGES((short)0x991a),

    INPUT_READ((short)0x9a),
    INPUT_READ_EXT((short)0x9e),
    INPUT_READ_SI((short)0x9d),

    OUTPUT_STOP((short)0xa3),
    OUTPUT_POWER((short)0xa4),
    OUTPUT_SPEED((short)0xa5),
    OUTPUT_START((short)0xa6),
    OUTPUT_POLARITY((short)0xa7),
    OUTPUT_STEP_POWER((short)0xac),
    OUTPUT_TIME_POWER((short)0xad),
    OUTPUT_STEP_SPEED((short)0xae),
    OUTPUT_TIME_SPEED((short)0xaf),
    OUTPUT_STEP_SYNC((short)0xb0),
    OUTPUT_TIME_SYNC((short)0xb1),

    TSP((short)0xff);

    private short b;
    private OpCode(short b){
        this.b = b;
    }
    public short getShort(){
        return b;
    }
}
