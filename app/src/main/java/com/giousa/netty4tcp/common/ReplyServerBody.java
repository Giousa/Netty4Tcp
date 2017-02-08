package com.giousa.netty4tcp.common;

/**
 * Description:
 * Author:Giousa
 * Date:2017/2/8
 * Email:65489469@qq.com
 */
public class ReplyServerBody extends ReplyBody {
    private String serverInfo;
    public ReplyServerBody(String serverInfo) {
        this.serverInfo = serverInfo;
    }
    public String getServerInfo() {
        return serverInfo;
    }
    public void setServerInfo(String serverInfo) {
        this.serverInfo = serverInfo;
    }
}
