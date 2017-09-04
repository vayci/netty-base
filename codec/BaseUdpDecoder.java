package com.gospell.nms.service.netty.base.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

import com.gospell.nms.service.netty.base.entity.BaseUdpDataPackage;

public class BaseUdpDecoder extends MessageToMessageDecoder<DatagramPacket>{

	@Override
	protected void decode(ChannelHandlerContext ctx, DatagramPacket msg, List<Object> out) throws Exception {
		
		BaseUdpDataPackage dataPakage = new BaseUdpDataPackage();
		ByteBuf in = msg.content();
		int frameLen = in.readableBytes();
		byte[] arr = new byte[frameLen];
        in.readBytes(arr);
        dataPakage.setContent(new String(arr));
        dataPakage.setInetSocketAddress(msg.sender());
        out.add(dataPakage);
		
	}

}
