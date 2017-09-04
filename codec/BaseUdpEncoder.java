package com.gospell.nms.service.netty.base.codec;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

import com.gospell.nms.service.netty.base.entity.BaseUdpDataPackage;

public class BaseUdpEncoder extends MessageToMessageEncoder<BaseUdpDataPackage>{

	@Override
	protected void encode(ChannelHandlerContext ctx, BaseUdpDataPackage msg , List<Object> out) throws Exception {
		DatagramPacket data = new DatagramPacket(Unpooled.copiedBuffer(msg.getContent().toString().getBytes()), 
				msg.getInetSocketAddress());
		out.add(data);
		
	}

}
