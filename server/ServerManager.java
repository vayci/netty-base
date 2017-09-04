package com.gospell.nms.service.netty.base.server;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.gospell.nms.service.netty.base.codec.BaseTcpDecoder;
import com.gospell.nms.service.netty.base.codec.BaseTcpEncoder;
import com.gospell.nms.service.netty.base.codec.BaseUdpDecoder;
import com.gospell.nms.service.netty.base.codec.BaseUdpEncoder;
import com.gospell.nms.service.netty.base.handler.BaseTcpHandler;
import com.gospell.nms.service.netty.base.handler.BaseUdpHandler;
@Component
public class ServerManager implements ApplicationListener<ContextRefreshedEvent>{

	public static void startTcpServer(int tcpPort) throws InterruptedException{
		EventLoopGroup tcpBossGroup = new NioEventLoopGroup();
        ServerBootstrap b = new ServerBootstrap();
        b.group(tcpBossGroup)
        .channel(NioServerSocketChannel.class)
        .localAddress(new InetSocketAddress(tcpPort))
        .option(ChannelOption.SO_BACKLOG,20000)  
        .childOption(ChannelOption.SO_KEEPALIVE, true)
        .option(ChannelOption.TCP_NODELAY, true)
        .childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast(new IdleStateHandler(60, 60, 60, TimeUnit.SECONDS));
                pipeline.addLast("encoder", new BaseTcpEncoder());
                pipeline.addLast("decoder", new BaseTcpDecoder());
                pipeline.addLast("CommonTcpHandler", new BaseTcpHandler());
            }
        });
        b.bind().sync();
        System.out.println("启动tcp common server , 端口:"+tcpPort);
	}
	
	public static void startUdpServer(int udpPort){
    	try {
    		EventLoopGroup udpBossGroup = new NioEventLoopGroup();
    		Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(udpBossGroup)
                    .option(ChannelOption.SO_BROADCAST, true)
                    .channel(NioDatagramChannel.class)
                    .handler(new ChannelInitializer<NioDatagramChannel>(){
						@Override
						protected void initChannel(NioDatagramChannel ch) throws Exception {
							ChannelPipeline pipeline = ch.pipeline();
							pipeline.addLast("encoder", new BaseUdpEncoder());
			                pipeline.addLast("decoder", new BaseUdpDecoder());
			                pipeline.addLast("CommonUdpHandler", new BaseUdpHandler());
							
						}
                    	
                    });
            bootstrap.bind(udpPort).sync();
            System.out.println("启动udp common server , 端口:"+udpPort);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		 if(event.getApplicationContext().getParent() == null){
			 try {
					startTcpServer(64000);
					startUdpServer(64001);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		}
	}
}
