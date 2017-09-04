package com.gospell.nms.service.netty.base.thread;

/**
 * 通用消息读取线程示例
 * @author zhaohw
 * 2017年8月28日下午2:40:59
 */
public class BaseReadThread<T> extends AbstractReadThread<T>{

	
	
	public BaseReadThread(String id, int frameType) {
		super(id,frameType);
	}

	@Override
	public void callBack(T reciveMsg) {
		System.out.println("处理设备回复的消息:"+reciveMsg);
	}

}
