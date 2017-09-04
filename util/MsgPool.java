package com.gospell.nms.service.netty.base.util;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

import com.google.common.collect.Maps;
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
	 * @param frame
	 * @return
	 */
	private static String getMsgMapKey(Object frame){
		Map<String,Object> result = Maps.newHashMap();
		try {
			getField(frame,frame.getClass(),result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(result.containsKey("repeat")){
			System.err.println("类:"+frame.getClass()+" GroupField 注解 isId 或 isType 重复");
			return null;
		}
		
		if(result.containsKey("id")&&result.containsKey("type")){
			if(result.get("id")==null||result.get("type")==null){
				System.err.println("类:"+frame.getClass()+" 分组注解参数为空 :"+result);
				return null;
			}
			return result.get("id")+"."+result.get("type");
		}else{
			System.err.println("类:"+frame.getClass()+" 不包含 GroupField 注解或注解不完整");
			return null;
		}
	}
	
	/**
	 * 递归反射 帧 注解的 设备id 与 帧类型
	 * @param frame
	 * @param clazz
	 * @param result
	 * @throws IntrospectionException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public static void getField(Object frame,Class<? extends Object> clazz ,Map<String,Object> result) 
			throws IntrospectionException, IllegalAccessException, 
			IllegalArgumentException, InvocationTargetException{  
	      
		Field[] fields = clazz.getDeclaredFields(); 
	       for (Field field:fields){
	            if(field.getAnnotation(GroupField.class)!=null){
	            	PropertyDescriptor pd=new PropertyDescriptor(field.getName(),clazz);
	                 Method getMethod=pd.getReadMethod();
	                 Object res = getMethod.invoke(frame);
	            	if(((GroupField)field.getAnnotation(GroupField.class)).isId()){
	            		 if(result.get("id")!=null){
	            		 	result.put("repeat", true);
	            		 }
		                 result.put("id", res);
                    }
	            	if(((GroupField)field.getAnnotation(GroupField.class)).isType()){
	            		 if(result.get("type")!=null){
	            		 	result.put("repeat", true);
	            		 }
		                 result.put("type", res);
                    }
	            }
	       }
	       if(!(clazz.getSuperclass().equals(Object.class))){
	    	   getField(frame, clazz.getSuperclass(), result);  
	       }
	}  
	
	public static void main(String[] args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException {
		Frame frame = new Frame();
		frame.setTransId(123);
		frame.setOrderType(1);
		getMsgMapKey(frame);
	}
}
