package com.giousa.netty4tcp.common;

/**
 * Description:心跳检测的消息类型
 * Author:Giousa
 * Date:2017/2/8
 * Email:65489469@qq.com
 */
public class PingMsg extends BaseMsg {
    public PingMsg() {
        super();
        setType(MsgType.PING);
    }
}
