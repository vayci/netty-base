package com.gospell.nms.service.netty.base.util;

import io.netty.channel.ChannelHandlerContext;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map.Entry;

public class ConnectManager {
	
	public static Hashtable<String, Connector> hmap = new Hashtable<>();
	
	/**
	 * 通过ctx移除TCP连接(超时、异常时调用)
	 * @param context
	 */
	public static void removeTcpConnector(ChannelHandlerContext context){
		Iterator<Entry<String, Connector>> it =hmap.entrySet().iterator();
		Object key = null;
		while(it.hasNext()){
			key = it.next().getKey();
			Connector connector = hmap.get(key);
			if(connector.getContext() == context){
				break;
			}
		}
		if(key!=null) hmap.remove(key);
	}
}
