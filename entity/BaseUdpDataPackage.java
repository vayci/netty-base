package com.gospell.nms.service.netty.base.entity;

import java.net.InetSocketAddress;

/**
 * 通用UDP数据包
 * @author zhaohw
 * 2017年8月30日下午5:34:11
 */
public class BaseUdpDataPackage{
	
	/**
	 * 数据包内容
	 */
	protected Object content;
	
	/**
	 * ip 端口信息
	 */
	protected InetSocketAddress inetSocketAddress;
	
	public Object getContent() {
		return content;
	}
	public void setContent(Object content) {
		this.content = content;
	}
	public InetSocketAddress getInetSocketAddress() {
		return inetSocketAddress;
	}
	public void setInetSocketAddress(InetSocketAddress inetSocketAddress) {
		this.inetSocketAddress = inetSocketAddress;
	}
}
