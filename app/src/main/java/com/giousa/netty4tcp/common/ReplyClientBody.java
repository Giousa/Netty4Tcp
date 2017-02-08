package com.giousa.netty4tcp.common;

/**
 * Description:
 * Author:Giousa
 * Date:2017/2/8
 * Email:65489469@qq.com
 */
public class ReplyClientBody extends ReplyBody {
    private String clientInfo;

    public ReplyClientBody(String clientInfo) {
        this.clientInfo = clientInfo;
    }

    public String getClientInfo() {
        return clientInfo;
    }

    public void setClientInfo(String clientInfo) {
        this.clientInfo = clientInfo;
    }
}
