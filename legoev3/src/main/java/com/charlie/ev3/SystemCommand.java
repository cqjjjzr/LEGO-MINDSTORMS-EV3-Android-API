package com.charlie.ev3;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * EV3主机的System Command。
 * @author Charlie Jiang
 */
public class SystemCommand {
    private final Brick brick;
    protected SystemCommand(Brick brick){
        this.brick = brick;
    }

    /**
     * 向EV3主机写文件。
     * @param buffer 需要写的数据
     * @param devicePath 主机上的文件名 (devicePath 相对于EV3主机上的lms2012/sys。 如果不存在会自动创建。  路径必须以"apps", "prjs", 或 "tools"开头)
     * @throws java.io.IOException 写入失败或发送失败时抛出。
     */
    public void writeFile(byte[] buffer,String devicePath) throws IOException{
        int chunkSize = 960;

        //Begin download
        Command commandBegin = new Command(CommandType.COMMAND_SYSTEM_REPLY);
        commandBegin.addOpCode(SystemOpCode.BEGIN_DOWNLOAD);
        commandBegin.addRawParameter(buffer.length);
        commandBegin.addRawParameter(devicePath);
        brick.sendCommand(commandBegin);
        if(commandBegin.response.getSystemReplyStatus() != SystemReplyStatus.SUCCESS){
            throw new IOException("无法开始文件写入，返回状态："+commandBegin.response.getSystemReplyStatus().toString());
        }

        //Downloading
        byte handle = commandBegin.response.getData()[0];
        int sizeSent = 0;

        while(sizeSent < buffer.length){
            Command commandContinue = new Command(CommandType.COMMAND_SYSTEM_REPLY);
            commandContinue.addOpCode(SystemOpCode.CONTINUE_DOWNLOAD);
            commandContinue.addRawParameter(handle);
            int sizeToSend = Math.min(chunkSize,buffer.length - sizeSent);
            commandContinue.addRawParameter(buffer,sizeSent,sizeToSend);
            sizeSent += sizeToSend;

            brick.sendCommand(commandContinue);
            if(commandContinue.response.getSystemReplyStatus() != SystemReplyStatus.SUCCESS
                    && (commandContinue.response.getSystemReplyStatus() != SystemReplyStatus.END_OF_FILE && sizeSent == buffer.length)){
                throw new IOException("无法保存文件，返回状态："+commandContinue.response.getSystemReplyStatus().toString());
            }
        }
    }

    /**
     * 将文件从本地拷贝到EV3主机。
     * 需要文件读取权限。({@link android.Manifest.permission#READ_EXTERNAL_STORAGE})
     * @param localPath Android设备上的文件名
     * @param devicePath 主机上的文件名 (devicePath 相对于EV3主机上的lms2012/sys。 如果不存在会自动创建。  路径必须以"apps", "prjs", 或 "tools"开头)
     * @throws java.io.IOException 写入失败或发送失败时抛出。
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void copyFile(String localPath,String devicePath) throws IOException {
        //Read local file
        FileInputStream fileInputStream = new FileInputStream(localPath);
        byte[] buffer = new byte[fileInputStream.available()];
        fileInputStream.read(buffer);
        writeFile(buffer,devicePath);
    }

    /**
     * 将文件从EV3主机上删除。
     * @param devicePath 主机上的文件名 (devicePath 相对于EV3主机上的lms2012/sys。 如果不存在会自动创建。  路径必须以"apps", "prjs", 或 "tools"开头)
     * @throws java.io.IOException 写入失败或发送失败时抛出。
     */
    public void deleteFile(String devicePath) throws IOException{
        Command command = new Command(CommandType.COMMAND_SYSTEM_REPLY);
        command.deleteFile(devicePath);
        brick.sendCommand(command);
        if(command.response.getSystemReplyStatus() != SystemReplyStatus.SUCCESS){
            throw new IOException("无法删除文件，返回状态："+command.response.getSystemReplyStatus().toString());
        }
    }

    /**
     * 在主机上新建文件夹。
     * @param directoryName 主机上的文件夹名 (devicePath 相对于EV3主机上的lms2012/sys。 如果不存在会自动创建。  路径必须以"apps", "prjs", 或 "tools"开头)
     * @throws java.io.IOException 写入失败或发送失败时抛出。
     */
    public void createDirectory(String directoryName) throws IOException{
        Command command = new Command(CommandType.COMMAND_SYSTEM_REPLY);
        command.createDirectory(directoryName);
        brick.sendCommand(command);
        if(command.response.getSystemReplyStatus() != SystemReplyStatus.SUCCESS){
            throw new IOException("Error creating directory, reply status:"+command.response.getSystemReplyStatus().toString());
        }
    }
}
