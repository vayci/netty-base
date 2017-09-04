package com.gospell.nms.service.netty.base.thread;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class TestReadThread implements ApplicationListener<ContextRefreshedEvent>{

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		//CommonReadThread crmt = new CommonReadThread("tcp id");
		//crmt.start();
	}

}
