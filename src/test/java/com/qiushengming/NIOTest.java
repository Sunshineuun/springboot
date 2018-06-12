package com.qiushengming;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author qiushengming
 * @date 2018/5/15
 */
public class NIOTest {
    InetAddress inet = null;

    public static void main(String args[]) {

        System.out.println("Your host IP is: " + getLIP());
        System.out.println("Your host Name is: " + getLName());
        System.out.println();
        System.out.println(
            "The Server IP is :" + getRIP("www.sohu.com"));
        System.out.println(
            "The Server Name is :" + getRName("199.181.132.250"));
    }

    //取得Localhost的IP地址
    private static String getLIP() {
        return getLAddress().getHostAddress();
    }

    //取得Localhost的主机名称
    private static String getLName() {
        return getLAddress().getHostName();
    }

    //取得Remotehost的IP地址
    private static String getRIP(String host) {
        return getRAddress(host).getHostAddress();
    }

    //取得Remotehost的主机名称
    private static String getRName(String ip) {
        return getRAddress(ip).getHostName();
    }

    private static InetAddress getLAddress() {
        try {
            return InetAddress.getLocalHost();
        } catch (UnknownHostException e) {}
        return null;
    }

    public static InetAddress getRAddress(String IP_or_Name) {
        try {
            return InetAddress.getByName(IP_or_Name);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }
}
