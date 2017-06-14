package com.wb.netty.ch02;

import java.net.SocketAddress;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

public class TimeClientHandler implements ChannelHandler {
	
	private final ByteBuf firstMessage;
	
	public TimeClientHandler(){
		byte[] req = "QUERY".getBytes();
		firstMessage = Unpooled.buffer(req.length);
		firstMessage.writeBytes(req);
	}
	
	
	public void bind(ChannelHandlerContext ctx, SocketAddress arg1, ChannelPromise arg2) throws Exception {
		System.out.println("bind");

	}

	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("channelActive");
		ctx.writeAndFlush(firstMessage);
	}

	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("channelInactive");

	}

	public void channelRead(ChannelHandlerContext ctx, Object reviceMsg) throws Exception {
		System.out.println("channelRead");
		ByteBuf buf = (ByteBuf)reviceMsg;
		byte[] req = new byte[buf.readableBytes()];
		buf.readBytes(req);
		String body = new String(req,"UTF-8");
		System.out.println(body);
	}

	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		System.out.println("channelReadComplete");

	}

	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		System.out.println("channelRegistered");

	}

	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		System.out.println("channelUnregistered");

	}

	public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
		System.out.println("channelWritabilityChanged");

	}

	public void close(ChannelHandlerContext ctx, ChannelPromise arg1) throws Exception {
		System.out.println("close");

	}

	public void connect(ChannelHandlerContext ctx, SocketAddress arg1, SocketAddress arg2, ChannelPromise arg3)
			throws Exception {
		System.out.println("connect");

	}

	public void deregister(ChannelHandlerContext ctx, ChannelPromise arg1) throws Exception {
		System.out.println("deregister");

	}

	public void disconnect(ChannelHandlerContext ctx, ChannelPromise arg1) throws Exception {
		System.out.println("disconnect");

	}

	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		System.out.println("exceptionCaught");
		cause.printStackTrace();

	}

	public void flush(ChannelHandlerContext ctx) throws Exception {
		System.out.println("flush");

	}

	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		System.out.println("handlerAdded");

	}

	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		System.out.println("handlerRemoved");

	}

	public void read(ChannelHandlerContext ctx) throws Exception {
		System.out.println("read");

	}

	public void userEventTriggered(ChannelHandlerContext ctx, Object arg1) throws Exception {
		System.out.println("userEventTriggered");

	}

	public void write(ChannelHandlerContext ctx, Object arg1, ChannelPromise arg2) throws Exception {
		System.out.println("write");

	}

}
