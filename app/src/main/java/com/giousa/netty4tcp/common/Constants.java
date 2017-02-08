package com.giousa.netty4tcp.common;

/**
 * Description:
 * Author:Giousa
 * Date:2017/2/8
 * Email:65489469@qq.com
 */
public class Constants {
    private static String clientId;

    public static String getClientId() {
        return clientId;
    }

    public static void setClientId(String clientId) {
        Constants.clientId = clientId;
    }
}
