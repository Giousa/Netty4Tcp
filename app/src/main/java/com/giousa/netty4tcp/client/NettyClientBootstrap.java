package com.giousa.netty4tcp.client;

import com.giousa.netty4tcp.common.AskMsg;
import com.giousa.netty4tcp.common.AskParams;
import com.giousa.netty4tcp.common.Constants;
import com.giousa.netty4tcp.common.LoginMsg;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

/**
 * Description:
 * Author:Giousa
 * Date:2017/2/8
 * Email:65489469@qq.com
 */
public class NettyClientBootstrap {
    private int port;
    private String host;
    private SocketChannel socketChannel;
    private static ChannelFutureListener channelFutureListener = null;
    private static final EventExecutorGroup group = new DefaultEventExecutorGroup(20);

    public static void main(String[]args) throws InterruptedException {
        Constants.setClientId("001");
        NettyClientBootstrap bootstrap=new NettyClientBootstrap(9999,"localhost");

//        LoginMsg loginMsg=new LoginMsg();
//        loginMsg.setPassword("yao");
//        loginMsg.setUserName("robin");
//        bootstrap.socketChannel.writeAndFlush(loginMsg);
//        while (true){
//            TimeUnit.SECONDS.sleep(3);
//            AskMsg askMsg=new AskMsg();
//            AskParams askParams=new AskParams();
//            askParams.setAuth("authToken");
//            askMsg.setParams(askParams);
//            bootstrap.socketChannel.writeAndFlush(askMsg);
//        }
    }

    public NettyClientBootstrap(int port, String host) throws InterruptedException {
        this.port = port;
        this.host = host;
        start();
    }

    private void start() throws InterruptedException {
        EventLoopGroup eventLoopGroup=new NioEventLoopGroup();
        final Bootstrap bootstrap=new Bootstrap();
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.option(ChannelOption.SO_KEEPALIVE,true);
        bootstrap.option(ChannelOption.TCP_NODELAY, true);
        bootstrap.group(eventLoopGroup);
        bootstrap.remoteAddress(host,port);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                socketChannel.pipeline().addLast(new IdleStateHandler(20,10,0));
                socketChannel.pipeline().addLast(new ObjectEncoder());
                socketChannel.pipeline().addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(null)));
                socketChannel.pipeline().addLast(new NettyClientHandler());
            }
        });

        ChannelFuture future =bootstrap.connect(host,port).sync();
        if (future.isSuccess()) {
//            socketChannel = (SocketChannel)future.channel();
            System.out.println("connect server  成功---------");
        }

    }
}
