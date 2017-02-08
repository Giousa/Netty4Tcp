package com.giousa.netty4tcp.client;

import com.giousa.netty4tcp.common.BaseMsg;
import com.giousa.netty4tcp.common.LoginMsg;
import com.giousa.netty4tcp.common.MsgType;
import com.giousa.netty4tcp.common.PingMsg;
import com.giousa.netty4tcp.common.ReplyClientBody;
import com.giousa.netty4tcp.common.ReplyMsg;
import com.giousa.netty4tcp.common.ReplyServerBody;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;

/**
 * Description:
 * Author:Giousa
 * Date:2017/2/8
 * Email:65489469@qq.com
 */
public class NettyClientHandler extends SimpleChannelInboundHandler<BaseMsg> {

    private int ask = 0;
    private int reply = 0;
    private int ping = 0;

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;
            switch (e.state()) {
                case WRITER_IDLE:
                    PingMsg pingMsg=new PingMsg();
                    ctx.writeAndFlush(pingMsg);
                    System.out.println("Client  send ping to server----------");
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, BaseMsg baseMsg) throws Exception {
        MsgType msgType=baseMsg.getType();
        switch (msgType){
            case LOGIN:{
                //向服务器发起登录
                LoginMsg loginMsg=new LoginMsg();
                loginMsg.setPassword("yao");
                loginMsg.setUserName("robin");
                channelHandlerContext.writeAndFlush(loginMsg);
            }break;

            case PING:{
                System.out.println("Client-------------"+(ping++)+"---------------");

            }break;

            case ASK:{
                //收到服务端的请求
                ReplyClientBody replyClientBody=new ReplyClientBody("客户端发送数据: "+ask++);
                ReplyMsg replyMsg=new ReplyMsg();
                replyMsg.setBody(replyClientBody);
                channelHandlerContext.writeAndFlush(replyMsg);
            }break;

            case REPLY:{
                //收到服务端回复
                ReplyMsg replyMsg=(ReplyMsg)baseMsg;
                ReplyServerBody replyServerBody=(ReplyServerBody)replyMsg.getBody();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ss");
                System.out.println("Client收到服务端回复: "+replyServerBody.getServerInfo()
                        +" 时间："+simpleDateFormat.format(new Date())
                        +"  次数："+reply++);
            }
            default:break;
        }
        ReferenceCountUtil.release(msgType);
    }
}
