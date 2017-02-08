package com.giousa.netty4tcp.common;

import java.io.Serializable;

/**
 * Description:必须实现序列,serialVersionUID 一定要有
 * Author:Giousa
 * Date:2017/2/8
 * Email:65489469@qq.com
 */
public abstract class BaseMsg  implements Serializable {
    private static final long serialVersionUID = 1L;
    private MsgType type;
    //必须唯一，否者会出现channel调用混乱
    private String clientId;

    //初始化客户端id
    public BaseMsg() {
        this.clientId = Constants.getClientId();
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public MsgType getType() {
        return type;
    }

    public void setType(MsgType type) {
        this.type = type;
    }
}
