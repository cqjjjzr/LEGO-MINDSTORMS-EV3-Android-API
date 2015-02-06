package com.charlie.ev3;

/**
 * 从EV3返回的状态信息。
 * @author Charlie Jiang
 */
public enum SystemReplyStatus
{
    SUCCESS,
    UNKNOWN_HANDLE,
    HANDLE_NOT_READY,
    CORRUPT_FILE,
    NO_HANDLES_AVAILABLE,
    NO_PERMISSION,
    ILLEGAL_PATH,
    FILE_EXISTS,
    END_OF_FILE,
    SIZE_ERROR,
    UNKNOWN_ERROR,
    ILLEGAL_FILENAME,
    ILLEGAL_CONNECTION
}
