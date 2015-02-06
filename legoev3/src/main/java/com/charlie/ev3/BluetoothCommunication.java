package com.charlie.ev3;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Build;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.Set;
import java.util.UUID;

/**
 *  通过蓝牙连接到EV3.
 *  需要 {@link android.Manifest.permission#BLUETOOTH}
 *  @author Charlie Jiang
 */
public class BluetoothCommunication implements Communication{
	private LinkedList<DataReceiveListener> onDataReceive = new LinkedList<>();
	private final String deviceName;
	private BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
	private BluetoothDevice device;
	private BluetoothSocket socket;
    private InputStream in;
    private OutputStream out;
	/**
	 * 使用默认名称创建连接。
	 */
	public BluetoothCommunication(){
		this.deviceName = "EV3";
	}
	/**
	 * 使用指定名称创建连接。
     * @param deviceName 指定的名称
	 */
	public BluetoothCommunication(String deviceName){
		this.deviceName = deviceName;
	}
	/**
	 * 连接到EV3主机。
	 * @throws Exception 连接失败时抛出。
	 */
	@Override
	public void connect() throws Exception{
		if(!adapter.isEnabled())
			throw new Exception("Bluetooth is not unable.");
		Set<BluetoothDevice> bind = adapter.getBondedDevices();
		boolean s = false;
		for(BluetoothDevice dev:bind){
			if(dev.getName().equals(deviceName)){
				device = dev;
				s = true;
			}
		}
		if(!s)
			throw new Exception("Brick not found.");
		socket = device.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
		socket.connect();
        in = socket.getInputStream();
        out = socket.getOutputStream();
	}
	/**
	 * 断开到EV3的连接。
     * @throws java.io.IOException 断开失败时抛出。
	 */
	@Override
	public void disconnect() throws IOException {
		socket.close();
	}
	/**
	 * 向EV3主机写数据。
     * @param data 需要写的数据
     * @throws java.io.IOException 发送失败时抛出。
	 */
	@Override
	public void write(byte[] data) throws IOException{
        out.write(data);
	}
	/**
	 * 从EV3主机拉取数据。
	 */
	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private void pollInput() throws IOException{
		while(socket.isConnected()){
            if(in.available()!=0){
                int len = in.read()+in.read()<<8;
                byte[] bytes = new byte[len];
                in.read(bytes);
                for(DataReceiveListener listener:onDataReceive){
                    listener.onDataReceive(bytes);
                }
            }
        }
	}

    /**
     * 增加一个获取数据的监听器。
     * @param listener 需要增加的监听器
     */
    public void addDataReceiveListener(DataReceiveListener listener){
        if(!onDataReceive.contains(listener))
            onDataReceive.add(listener);
    }

    /**
     * 删除一个获取数据的监听器。
     * @param listener 需要删除的监听器
     */
    public void removeDataReceiveListener(DataReceiveListener listener){
        onDataReceive.remove(listener);
    }
}