package com.wb.netty.ch10_file_service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMessage;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;

import static io.netty.handler.codec.http.HttpResponseStatus.*;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class HttpFileServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

	public static Logger log = LoggerFactory.getLogger(HttpFileServerHandler.class);

	private final String url;

	public HttpFileServerHandler(String url) {
		this.url = url;
	}

	public void messageReceived(ChannelHandlerContext arg0, FullHttpRequest request) {
		log.info("messageReceived");

	}

	public void exceptionCaught(ChannelHandlerContext arg0, Throwable arg1) throws Exception {
		log.info("exceptionCaught");

	}

	public void handlerAdded(ChannelHandlerContext arg0) throws Exception {
		log.info("handlerAdded");

	}

	public void handlerRemoved(ChannelHandlerContext arg0) throws Exception {
		log.info("handlerRemoved");

	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest req) throws Exception {
		log.info("channelRead0");
		if (!req.decoderResult().isSuccess()) {
			sendError(ctx, BAD_REQUEST);
			return;
		}
		if(req.method() != HttpMethod.GET){
			sendError(ctx, METHOD_NOT_ALLOWED);
			return;
		}
		final String uri = req.uri();
		final String path = sanitizeUri(uri);
		
		

	}

	private String sanitizeUri(String uri) {
		try {
			uri = URLDecoder.decode(uri, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			try {
				uri = URLDecoder.decode(uri, "IOS-8859-1");
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		if(!uri.startsWith(url)){
			return null;
		}
		
		if(!uri.startsWith("/"))
			return null;
		uri = uri.replace('/', File.separatorChar);
		
		if(uri.contains(File.separator+".") || uri.contains('.'+File.separator) || uri.startsWith(".") || uri.endsWith(".")){
			return null;
		}
		return null;
	}

	private void sendError(ChannelHandlerContext ctx, HttpResponseStatus status) {
		String content = "Failure:" + status.toString() + "\r\n";
		FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status,
				Unpooled.copiedBuffer(content, CharsetUtil.UTF_8));
		response.headers().set("content_type","text/plain");
		response.headers().set("charset","utf-8");
		ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);

	}

}
