package com.gospell.nms.service.netty.base.thread;

import java.util.concurrent.BlockingQueue;

import com.gospell.nms.service.netty.base.util.MsgPool;

/**
 * 抽象消息读取线程
 * 消息处理逻辑放入callback
 * @author zhaohw
 * 2017年8月28日下午2:36:31
 */
public abstract class AbstractReadThread<T> extends Thread{
	
	
	protected BlockingQueue<Object> queue;
	
	public AbstractReadThread(String id ,int frameType){
		this.queue = MsgPool.getMsgGroup(id,frameType);
	}
	
	/**
	 * 读取到消息回调函数
	 */
	public abstract void callBack(T msg);
	
	@Override
	public  void run() {
		try {
			System.out.println("读取线程启动");
			@SuppressWarnings("unchecked")
			T a = (T) queue.take();
			callBack(a);
		} catch (InterruptedException
				e) {
			e.printStackTrace();
		}
	}
}
