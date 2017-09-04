package com.gospell.nms.service.netty.base.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

import com.gospell.nms.service.netty.base.frame.Frame;
import com.gospell.nms.service.netty.base.util.ConnectManager;
import com.gospell.nms.service.netty.base.util.Connector;
import com.gospell.nms.service.netty.base.util.MsgPool;

public class BaseTcpHandler extends SimpleChannelInboundHandler<Object>{
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println("TCP 收到消息,解码后为:"+msg);
		Frame frame = (Frame) msg;
		ConnectManager.hmap.put(frame.getCommTerminalId(), new Connector(ctx));
		MsgPool.getMsgGroup(frame).put(msg.toString()); //放入对应消息队列
	}
	
	@Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		//System.out.println("TCP channel注册:"+ctx);
        ctx.fireChannelRegistered();
    }

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("TCP channel 连接:"+ctx);
     }
	
	
    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
    	System.out.println("TCP channel 断开:"+ctx);
        ctx.fireChannelUnregistered();
    }

	@Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
       System.out.println("TCP Handler 异常:"+ctx);
       ConnectManager.removeTcpConnector(ctx);
       ctx.close();
    }
    
    @Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt)
			throws Exception {
		IdleStateEvent event = (IdleStateEvent) evt;
		if (event.state().equals(IdleState.READER_IDLE)) {
			System.out.println("TCP channel 超时:"+ctx);
			ConnectManager.removeTcpConnector(ctx);
			ctx.close();
		} 
       
	}
}
