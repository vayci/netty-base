package com.gospell.nms.service.netty.base.thread;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;

import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;

import com.gospell.nms.service.netty.base.util.ConnectManager;
import com.gospell.nms.service.netty.base.util.Connector;

/**
 * 向设备写入消息线程
 * 消息处理逻辑放入callback
 * @author zhaohw
 * 2017年8月28日下午2:36:31
 */
public class BaseWriteThread extends Thread{
	
	private String deviceId ;
	
	private String msg;
	
	public BaseWriteThread(String deviceId ,String msg){
		this.deviceId = deviceId;
		this.msg = msg;
	}
	
	@Override
	public  void run() {
		Connector connector = ConnectManager.hmap.get(deviceId);
		if(connector==null){
			System.out.println("未找到设备:"+deviceId+" 的连接");
			return;
		}
		ChannelHandlerContext ctx= connector.getContext();
		try {
			byte[] bytes = msg.getBytes("UTF-8");
			if(connector.getConnectMode()==Connector.TCP_MODE){
				ctx.writeAndFlush(Unpooled.copiedBuffer(bytes));
			}else if(connector.getConnectMode()==Connector.UDP_MODE){
				InetSocketAddress isa =connector.getSocketAddress();
				DatagramPacket data = new DatagramPacket(Unpooled.copiedBuffer(bytes), isa);
				ctx.writeAndFlush(data);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
