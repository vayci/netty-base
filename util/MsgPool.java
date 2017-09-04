package com.gospell.nms.service.netty.base.util;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

import com.gospell.nms.service.netty.base.frame.Frame;

public class MsgPool{
	/**
	 * 消息分组
	 * K 设备id.帧类型 如(258066dfd469231a.13)
	 * V 该设备的消息队列
	 */
	private static ConcurrentHashMap<String, BlockingQueue<Object>> msgGroup = new ConcurrentHashMap<>();
	
	/**
	 * 获取某设备id的消息队列,不存在则创建
	 * @param frame 传入Frame 用于区分队列
	 * @return
	 */
	public static BlockingQueue<Object> getMsgGroup(Frame frame){
		String msgMapKey = getMsgMapKey(frame);
		if(msgGroup.get(msgMapKey)!=null){
			return msgGroup.get(msgMapKey);
		}else{
			BlockingQueue<Object> queue = new LinkedBlockingQueue<Object>();
			msgGroup.put(msgMapKey, queue);
			return queue;
		}
	}
	
	/**
	 * 获取某设备id的消息队列,不存在则创建
	 * @param deviceId 设备号
	 * @param frameType 帧类型
	 * @return
	 */
	public static BlockingQueue<Object> getMsgGroup(String deviceId , int frameType){
		String msgMapKey = deviceId+"."+frameType;
		if(msgGroup.get(msgMapKey)!=null){
			return msgGroup.get(msgMapKey);
		}else{
			BlockingQueue<Object> queue = new LinkedBlockingQueue<Object>();
			msgGroup.put(msgMapKey, queue);
			return queue;
		}
	}
	
	/**
	 * 获取消息分组key
	 * @param id
	 * @param frameType
	 * @return
	 */
	private static String getMsgMapKey(Frame frame){
		return frame.getCommTerminalId()+"."+frame.getOrderType();
	}
}
