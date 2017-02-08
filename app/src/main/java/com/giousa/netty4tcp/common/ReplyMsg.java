package com.giousa.netty4tcp.common;

/**
 * Description:
 * Author:Giousa
 * Date:2017/2/8
 * Email:65489469@qq.com
 */
public class ReplyMsg extends BaseMsg {
    public ReplyMsg() {
        super();
        setType(MsgType.REPLY);
    }
    private ReplyBody body;

    public ReplyBody getBody() {
        return body;
    }

    public void setBody(ReplyBody body) {
        this.body = body;
    }
}
