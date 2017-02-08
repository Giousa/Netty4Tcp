package com.giousa.netty4tcp.server;

import com.giousa.netty4tcp.common.AskMsg;
import com.giousa.netty4tcp.common.BaseMsg;
import com.giousa.netty4tcp.common.LoginMsg;
import com.giousa.netty4tcp.common.MsgType;
import com.giousa.netty4tcp.common.PingMsg;
import com.giousa.netty4tcp.common.ReplyClientBody;
import com.giousa.netty4tcp.common.ReplyMsg;
import com.giousa.netty4tcp.common.ReplyServerBody;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.ReferenceCountUtil;

/**
 * Description:
 * Author:Giousa
 * Date:2017/2/8
 * Email:65489469@qq.com
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<BaseMsg> {

    private int ask = 0;
    private int reply = 0;
    private int ping = 0;

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        NettyChannelMap.remove((SocketChannel)ctx.channel());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, BaseMsg baseMsg) throws Exception {

        System.out.println("Server 获取类型："+baseMsg.getType());


        if(MsgType.LOGIN.equals(baseMsg.getType())){
            LoginMsg loginMsg=(LoginMsg)baseMsg;
            if("robin".equals(loginMsg.getUserName())&&"yao".equals(loginMsg.getPassword())){
                //登录成功,把channel存到服务端的map中
                NettyChannelMap.add(loginMsg.getClientId(),(SocketChannel)channelHandlerContext.channel());
                System.out.println("client"+loginMsg.getClientId()+" 登录成功");
            }
        }else{
            if(NettyChannelMap.get(baseMsg.getClientId())==null){
                //说明未登录，或者连接断了，服务器向客户端发起登录请求，让客户端重新登录
                LoginMsg loginMsg=new LoginMsg();
                channelHandlerContext.channel().writeAndFlush(loginMsg);
            }
        }
        switch (baseMsg.getType()){

            case PING:{
                System.out.println("Server-------------"+(ping++)+"---------------");
                PingMsg pingMsg=(PingMsg)baseMsg;
                PingMsg replyPing=new PingMsg();
                NettyChannelMap.get(pingMsg.getClientId()).writeAndFlush(replyPing);
            }break;

            case ASK:{
                //收到客户端的请求
                AskMsg askMsg=(AskMsg)baseMsg;
                if("authToken".equals(askMsg.getParams().getAuth())){
                    ReplyServerBody replyBody=new ReplyServerBody("服务端发送数据: "+ask++);
                    ReplyMsg replyMsg=new ReplyMsg();
                    replyMsg.setBody(replyBody);
                    Channel channel = NettyChannelMap.get(askMsg.getClientId());
                    if(channel == null){
                        System.out.println(askMsg.getClientId()+" 客户端已断开！");
                        return;
                    }
//                    channel.writeAndFlush(replyMsg);
                }
            }break;

            case REPLY:{
                //收到客户端回复
                ReplyMsg replyMsg=(ReplyMsg)baseMsg;
                ReplyClientBody clientBody=(ReplyClientBody)replyMsg.getBody();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ss");
                System.out.println("Server收到客户端回复: "
                        + clientBody.getClientInfo()+" 时间："+simpleDateFormat.format(new Date())
                        +"  次数："+reply++);
            }break;

            default:break;
        }
        ReferenceCountUtil.release(baseMsg);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("server exception:"+cause.toString());
    }
}
