package com.gospell.nms.service.netty.base.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import com.gospell.nms.service.netty.base.entity.BaseUdpDataPackage;
import com.gospell.nms.service.netty.base.frame.Frame;
import com.gospell.nms.service.netty.base.util.ConnectManager;
import com.gospell.nms.service.netty.base.util.Connector;
import com.gospell.nms.service.netty.base.util.MsgPool;

public class BaseUdpHandler extends SimpleChannelInboundHandler<BaseUdpDataPackage>{
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx,  BaseUdpDataPackage msg) throws Exception {
		Frame frame = (Frame) msg.getContent();
		ConnectManager.hmap.put(frame.getCommTerminalId(), new Connector(ctx,msg.getInetSocketAddress()));
		System.out.println("UDP 收到消息:"+msg.getContent());
		MsgPool.getMsgGroup(frame).put(msg.toString()); //放入对应消息队列
        ctx.writeAndFlush(msg);
	}
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("UDP channel 连接:"+ctx);
     }
	
	@Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		//System.out.println("UDP channel注册:"+ctx);
        ctx.fireChannelRegistered();
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
    	System.out.println("UDP channel 断开:"+ctx);
        ctx.fireChannelUnregistered();
    }
    
	@Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
       System.out.println("UDP Handler 异常:"+ctx);
    }
	
}
