package com.giousa.netty4tcp.common;

/**
 * Description:登录验证类型的消息
 * Author:Giousa
 * Date:2017/2/8
 * Email:65489469@qq.com
 */
public class LoginMsg extends BaseMsg {
    private String userName;
    private String password;
    public LoginMsg() {
        super();
        setType(MsgType.LOGIN);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
