package com.gospell.nms.service.netty.base.codec;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class BaseTcpDecoder extends ByteToMessageDecoder{

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		int frameLen = in.readableBytes();
		 byte[] arr = new byte[frameLen];
         in.readBytes(arr);
         out.add(new String(arr,"GBK"));
	}

}
