package com.charlie.ev3;

/**
 * 获取数据的监听器。
 * @author Charlie Jiang
 */
public interface DataReceiveListener {
	/**
	 * 在拉取到数据时调用。
	 * @param data 拉取到的数据
	 */
	public void onDataReceive(byte[] data);
}
