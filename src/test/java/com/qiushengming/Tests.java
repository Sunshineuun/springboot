package com.qiushengming;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class Tests {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        /*File file = new File("D:/text.txt");
        FileOutputStream out = new FileOutputStream(file);
        int a = 0;
        for (int i = 0; i < 50000; i++) {
            char b = (char) i;
            out.write((String.valueOf(b) + '-' + i + '\n').getBytes());
        }
        out.flush();
        out.close();*/
        //确定计算方法
        /*String str = "邱胜明1";
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64en = new BASE64Encoder();
        //加密后的字符串
        String newstr = base64en.encode(md5.digest(str.getBytes("utf-8")));
        System.out.println(newstr);*/
        /*int a = 20121025;
        while (a != 1) {
            int b = a % 2;
            a = a / 2;
            System.out.print(b);
            System.out.print("-");
            System.out.println(a);
        }*/
        String t = "hello";
        char c[] = {'h', 'e', 'l', 'l', 'o'};
        System.out.println(t.equals(c));
    }
}
