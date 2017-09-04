package com.gospell.nms.service.netty.base.util;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.gospell.nms.service.netty.base.thread.BaseWriteAndReadTask;

/**
 * 工具包
 * @author zhaohw
 * 2017年8月31日下午5:13:44
 */
public class ConnectUtil {
	
	private final static int maxThreadNum = 500;
	
	static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(maxThreadNum); 
	
	/**
	 * 向设备发送消息
	 * @param id  设备id
	 * @param msg 数据
	 * @return 返回设备回传数据
	 */
	public static Object sendMsg(String id,Object msg){
		Connector connector = ConnectManager.hmap.get(id);
		Object res = null;
		if(connector==null){
			System.out.println("未找到设备:"+id+" 的连接");
			return res;
		}
		
		try {
			Future<Object> f= fixedThreadPool.submit((new BaseWriteAndReadTask<Object>(id, msg)));
			res = f.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		return res;
	}
}
