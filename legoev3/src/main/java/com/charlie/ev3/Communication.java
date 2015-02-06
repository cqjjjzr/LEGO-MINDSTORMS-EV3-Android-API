package com.charlie.ev3;

import java.io.IOException;

/**
 * 与EV3的连接的接口。
 * @author Charlie Jiang
*/
public interface Communication{
    /**
     * 连接到EV3主机。
     * @throws Exception 连接失败时抛出。
     */
	public void connect() throws Exception;
    /**
     * 断开到EV3的连接。
     * @throws java.io.IOException 断开失败时抛出。
     */
	public void disconnect() throws IOException;
    /**
     * 向EV3主机写数据。
     * @param data 需要写的数据
     * @throws java.io.IOException 发送失败时抛出。
     */
	public void write(byte[] data) throws IOException;

    /**
     * 增加一个获取数据的监听器。
     * @param listener 需要增加的监听器
     */
    public void addDataReceiveListener(DataReceiveListener listener);
    /**
     * 删除一个获取数据的监听器。
     * @param listener 需要删除的监听器
     */
    public void removeDataReceiveListener(DataReceiveListener listener);
}