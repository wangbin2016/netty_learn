package com.wb.netty.ch02;

import java.net.SocketAddress;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

public class MyChannelHandler implements ChannelHandler {

	public void bind(ChannelHandlerContext arg0, SocketAddress arg1, ChannelPromise arg2) throws Exception {
		System.out.println("bind");
	}

	public void channelActive(ChannelHandlerContext arg0) throws Exception {
		System.out.println("channelActive");

	}

	public void channelInactive(ChannelHandlerContext arg0) throws Exception {
		System.out.println("channelInactive");

	}

	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println("channelRead");
		ByteBuf buf = (ByteBuf)msg;
		byte[] req = new byte[buf.readableBytes()];
		buf.readBytes(req);
		String body = new String(req,"UTF-8");
		System.out.println("thi time server recive order:"+body);
		
		String currenttime = "QUERY".equalsIgnoreCase(body)?System.currentTimeMillis()+"":"bad";
		
		ByteBuf resp = Unpooled.copiedBuffer(currenttime.getBytes());
		ctx.write(resp);
		
	}

	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		System.out.println("channelReadComplete");
		ctx.flush();

	}

	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		System.out.println("channelRegistered");
		ctx.close();
	}

	public void channelUnregistered(ChannelHandlerContext arg0) throws Exception {
		System.out.println("channelUnregistered");

	}

	public void channelWritabilityChanged(ChannelHandlerContext arg0) throws Exception {
		System.out.println("channelWritabilityChanged");

	}

	public void close(ChannelHandlerContext arg0, ChannelPromise arg1) throws Exception {
		System.out.println("close");

	}

	public void connect(ChannelHandlerContext arg0, SocketAddress arg1, SocketAddress arg2, ChannelPromise arg3)
			throws Exception {
		System.out.println("connect");

	}

	public void deregister(ChannelHandlerContext arg0, ChannelPromise arg1) throws Exception {
		System.out.println("deregister");

	}

	public void disconnect(ChannelHandlerContext arg0, ChannelPromise arg1) throws Exception {
		System.out.println("disconnect");

	}

	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		System.out.println("exceptionCaught");

	}

	public void flush(ChannelHandlerContext arg0) throws Exception {
		System.out.println("flush");

	}

	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		System.out.println("handlerAdded");

	}

	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		System.out.println("handlerRemoved");

	}

	public void read(ChannelHandlerContext arg0) throws Exception {
		System.out.println("read");

	}

	public void userEventTriggered(ChannelHandlerContext arg0, Object arg1) throws Exception {
		System.out.println("userEventTriggered");

	}

	public void write(ChannelHandlerContext arg0, Object arg1, ChannelPromise arg2) throws Exception {
		System.out.println("write");

	}

}
