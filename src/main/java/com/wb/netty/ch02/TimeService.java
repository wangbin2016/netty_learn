package com.wb.netty.ch02;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class TimeService {
	public void bind(int port) throws Exception{
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try{
			ServerBootstrap sb = new ServerBootstrap();
			sb.group(bossGroup,workerGroup);
			sb.channel(NioServerSocketChannel.class);
			sb.option(ChannelOption.SO_BACKLOG, 1024);
			sb.childHandler(new ChannelInitializer<SocketChannel>(){      //为accept channel的pipeline预添加的inboundhandler  
                @Override     //当新连接accept的时候，这个方法会调用  
                protected void initChannel(SocketChannel ch) throws Exception {
                	System.out.println("initChannel");
                    ch.pipeline().addLast(new MyChannelHandler());   //为当前的channel的pipeline添加自定义的处理函数  
                }
            });  
            //bind方法会创建一个serverchannel，并且会将当前的channel注册到eventloop上面，  
            //会为其绑定本地端口，并对其进行初始化，为其的pipeline加一些默认的handler 
            ChannelFuture f = sb.bind(port).sync();
            System.out.println("start");
            f.channel().closeFuture().sync();  //相当于在这里阻塞，直到serverchannel关闭  
		}catch(Exception e){
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		TimeService timeService = new TimeService();
		try {
			timeService.bind(9988);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
