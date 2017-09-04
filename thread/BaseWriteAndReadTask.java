package com.gospell.nms.service.netty.base.thread;

import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import com.gospell.nms.service.netty.base.entity.BaseUdpDataPackage;
import com.gospell.nms.service.netty.base.frame.Frame;
import com.gospell.nms.service.netty.base.util.ConnectManager;
import com.gospell.nms.service.netty.base.util.Connector;
import com.gospell.nms.service.netty.base.util.MsgPool;

/**
 * 发送消息并读取响应线程
 * @author zhaohw
 * 2017年8月31日上午9:54:16
 */
public class BaseWriteAndReadTask<T> implements Callable<Object>{
	
	
	private String deviceId;
	
	private T frame;
	
	public BaseWriteAndReadTask(String deviceId ,T frame){
		this.deviceId = deviceId;
		this.frame = frame;
	}
	
	@Override
	public Object call() throws Exception {
		
		Frame castFrame  = (Frame) frame;
		Connector connector = ConnectManager.hmap.get(deviceId);
		if(connector==null){
			System.out.println("connector 为null");
			return MsgPool.getMsgGroup(castFrame.getCommTerminalId(),castFrame.getOrderType())
					.poll(120,TimeUnit.SECONDS);
		}
		
		ChannelHandlerContext ctx= connector.getContext();
		
		if(connector.getConnectMode()==Connector.TCP_MODE){
			ctx.writeAndFlush(frame);
		}
		else if(connector.getConnectMode()==Connector.UDP_MODE){
			BaseUdpDataPackage udpPackage = new BaseUdpDataPackage();
			udpPackage.setContent(frame);
			udpPackage.setInetSocketAddress(connector.getSocketAddress());
			ctx.writeAndFlush(udpPackage);
		}
		
		try {
			System.out.println("开始等待设备返回信息");
			return MsgPool.getMsgGroup(castFrame.getCommTerminalId(),castFrame.getOrderType())
					.poll(120,TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
			return null;
		}
	}

}
