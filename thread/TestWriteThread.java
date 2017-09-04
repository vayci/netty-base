package com.gospell.nms.service.netty.base.thread;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class TestWriteThread implements ApplicationListener<ContextRefreshedEvent>{

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if(event.getApplicationContext().getParent() == null){
		}
	}

}

class resultThread extends Thread{
		
	@Override
	public void run() {
		try {
			ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3); 
			 Future<Object> f= fixedThreadPool.submit((new BaseWriteAndReadTask<String>("tcp id", "输出测试")));
			System.out.println("返回消息:"+f.get());
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		
	}
	
}