package com.wb.netty.ch10_file_service;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

public class HttpFileServer {
	public static final String DEFAULT_URL = "/com/wb/netty"; 
	
	public void bind(int port,final String url) throws Exception {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();

		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup);
			b.channel(NioServerSocketChannel.class);
			b.option(ChannelOption.SO_BACKLOG, 100);
			b.handler(new LoggingHandler(LogLevel.INFO));
			b.childHandler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast("http-decoder",new HttpRequestDecoder());
					ch.pipeline().addLast("http-aggregator",new HttpObjectAggregator(65536));
					ch.pipeline().addLast("http-encoder",new HttpResponseEncoder());
					ch.pipeline().addLast("http-chunked",new ChunkedWriteHandler());
					ch.pipeline().addLast("fileServerHandler",new HttpFileServerHandler(url));
				}
			});
			System.out.println("启动成功 port:" + port);
			ChannelFuture f = b.bind("127.0.0.1",port).sync();
			f.channel().closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}

	public static void main(String[] args) {
		int port = 8083;
		if (args != null && args.length > 0) {
			port = Integer.valueOf(args[0]);
		}
		try {
			new HttpFileServer().bind(port,DEFAULT_URL);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
