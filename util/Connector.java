package com.gospell.nms.service.netty.base.util;

import io.netty.channel.ChannelHandlerContext;

import java.net.InetSocketAddress;
import java.util.Date;
/**
 * 单个netty连接
 * @author zhaohw
 * 2017年8月25日上午11:13:57
 */
public class Connector {
	
	private int connectMode;
	
	private ChannelHandlerContext context;
	
	private InetSocketAddress socketAddress; //TCP时为null UDP时保存ip端口
	
	private Date createTime = new Date();;

	public static final int TCP_MODE = 1;//连接模式  1:TCP 2:UDP
	
	public static final int UDP_MODE = 2;
	
	public int getConnectMode() {
		return connectMode;
	}

	/**
	 * Connector TCP连接构造方法
	 * @param context
	 */
	public Connector(ChannelHandlerContext context) {
		this.connectMode = TCP_MODE;
		this.context = context;
	}
	
	/**
	 * Connector UDP构造方法
	 * @param context
	 * @param socketAddress
	 */
	public Connector(ChannelHandlerContext context, InetSocketAddress socketAddress) {
		this.connectMode = UDP_MODE;
		this.context = context;
		this.socketAddress = socketAddress;
	}

	public void setConnectMode(int connectMode) {
		this.connectMode = connectMode;
	}

	public ChannelHandlerContext getContext() {
		return context;
	}

	public void setContext(ChannelHandlerContext context) {
		this.context = context;
	}

	public InetSocketAddress getSocketAddress() {
		return socketAddress;
	}

	public void setSocketAddress(InetSocketAddress socketAddress) {
		this.socketAddress = socketAddress;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
