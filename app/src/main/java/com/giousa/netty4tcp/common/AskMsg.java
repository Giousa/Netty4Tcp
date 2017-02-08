package com.giousa.netty4tcp.common;

/**
 * Description:请求类型的消息
 * Author:Giousa
 * Date:2017/2/8
 * Email:65489469@qq.com
 */
public class AskMsg extends BaseMsg {
    public AskMsg() {
        super();
        setType(MsgType.ASK);
    }
    private AskParams params;

    public AskParams getParams() {
        return params;
    }

    public void setParams(AskParams params) {
        this.params = params;
    }
}
