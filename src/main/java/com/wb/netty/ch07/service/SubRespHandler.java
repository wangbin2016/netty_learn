package com.wb.netty.ch07.service;

import com.wb.netty.ch07.entity.SubscribeReq;
import com.wb.netty.ch07.entity.SubscribeResp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

public class SubRespHandler extends ChannelOutboundHandlerAdapter{
	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
		SubscribeReq req = (SubscribeReq)msg;
		ctx.writeAndFlush(resp(req.getSubReqId()));
		ctx.flush();
	}
	
	private SubscribeResp resp(int subReqID){
		SubscribeResp resp = new SubscribeResp();
		resp.setDesc("hehe");
		resp.setSubReqId(subReqID);
		resp.setRespCode("0");
		return resp;
	}
}
