package com.wb.netty.ch07.service;
import org.apache.log4j.Logger;

import com.wb.netty.ch07.entity.SubscribeReq;
import com.wb.netty.ch07.entity.SubscribeResp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class SubReqServerHandler extends ChannelInboundHandlerAdapter {
	Logger logger = Logger.getLogger(this.getClass());
	@Override
	public void channelRead(ChannelHandlerContext ctx,Object msg) throws Exception{
		SubscribeReq req = (SubscribeReq)msg;
		if("Lilinfeng".equalsIgnoreCase(req.getUserName()) || true){
			ctx.writeAndFlush(resp(req.getSubReqId()));
			ctx.flush();
		}
		System.out.println(req.toString());
	}
	
	private SubscribeResp resp(int subReqID){
		SubscribeResp resp = new SubscribeResp();
		resp.setDesc("hehe");
		resp.setSubReqId(subReqID);
		resp.setRespCode("0");
		return resp;
	}
	
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable exception){
		exception.printStackTrace();
		ctx.close();
	}
	
	@Override  
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {  
        logger.info("InboundHandler1.channelReadComplete");  
        ctx.flush();  
    }
}
