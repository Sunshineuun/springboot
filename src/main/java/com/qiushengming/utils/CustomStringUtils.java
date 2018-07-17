package com.qiushengming.utils;

import com.qiushengming.exception.SystemException;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.lang.reflect.Field;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author qiushengming
 * @date 2018/6/22
 */
@Slf4j
public class CustomStringUtils {

    /**
     * 该字符为0宽度啊的空白字符，unicode编码为[\u200b]
     */
    private static final String ZERO_WIDTH_SPACE = "​";
    /**
     * 该字符为一个不打破空间， unicode编码为[\u00a0]
     */
    private static final String NON_BREAKING_SPACE = " ";

    /**
     * 将s第一个字符转为大写
     *
     * @param s string
     * @return string
     */
    public static String captureName(String s) {
        if (isChinese(s)) {
            return s;
        }
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    /**
     * 将s第一个字符转为大写
     *
     * @param s string
     * @return string
     */
    public static String captureName1(String s) {
        if (isChinese(s)) {
            return s;
        }
        char[] c = s.toCharArray();
        c[0] -= 32;
        return String.valueOf(c);
    }

    /**
     * 是否是英文
     *
     * @param s String
     * @return true or false
     */

    public static boolean isEnglish(String s) {
        return s.matches("^[a-zA-Z0-9]*");
    }

    /**
     * 是否为中文
     *
     * @param s String
     * @return true or false
     */
    public static boolean isChinese(String s) {
        return s.matches("^[\\u4e00-\\u9fa5]*");
    }

    /***
     * 驼峰命名转为下划线命名
     *
     * @param para 驼峰命名的字符串
     */
    public static String humpToUnderline(String para) {
        StringBuilder sb = new StringBuilder(para);
        //定位
        int temp = 0;
        for (int i = 0; i < para.length(); i++) {
            if (Character.isUpperCase(para.charAt(i))) {
                sb.insert(i + temp, "_");
                temp += 1;
            }
        }
        return sb.toString().toUpperCase();
    }

    /***
     * 下划线命名转为驼峰命名
     *
     * @param para 下划线命名的字符串
     */
    public static String underlineToHump(String para) {
        StringBuilder sb = new StringBuilder();
        String a[] = para.split("_");
        for (String s : a) {
            if (sb.length() == 0) {
                sb.append(s.toLowerCase());
            } else {
                sb.append(s.substring(0, 1).toUpperCase());
                sb.append(s.substring(1).toLowerCase());
            }
        }
        return sb.toString();
    }

    /**
     * 2017年4月30日
     * qiushengming
     *
     * @param unicode 字符串
     * @return String
     * <p>unicode编码转中文，单个字符[111]</p>
     */
    public static String unicodeToString(Integer unicode) {
        StringBuilder sb = new StringBuilder();

        int data = Integer.parseInt(unicode.toString(), 16);
        sb.append((char) data);
        //            sb.append(unicode.substring(4));
        return sb.toString();
    }

    /**
     * 2017年4月30日
     * qiushengming
     *
     * @param unicode 字符串
     * @return String
     * <p>unicode编码转中文，格式[\u1111]</p>
     */
    public static String unicodeToString(String unicode) {
        StringBuilder sb = new StringBuilder();

        String[] hex = unicode.split("\\\\u");
        for (int i = 1; i < hex.length; i++) {
            int data = Integer.parseInt(hex[i].substring(0, 4), 16);
            sb.append((char) data);
            sb.append(hex[i].substring(4));
        }
        return sb.toString();
    }

    public static String stringToUnicode(String str) {
        str = (str == null ? "" : str);
        String tmp;
        StringBuffer sb = new StringBuffer(1000);
        char c;
        int i, j;
        sb.setLength(0);
        for (i = 0; i < str.length(); i++) {
            c = str.charAt(i);
            sb.append("\\u");
            j = (c >>> 8); // 取出高8位
            tmp = Integer.toHexString(j);
            if (tmp.length() == 1)
                sb.append("0");
            sb.append(tmp);
            j = (c & 0xFF); // 取出低8位
            tmp = Integer.toHexString(j);
            if (tmp.length() == 1)
                sb.append("0");
            sb.append(tmp);

        }
        return (new String(sb));
    }

    /**
     * 获取指定字符串出现的次数
     *
     * @param srcText  源字符串
     * @param findText 要查找的字符串
     * @return int
     * @author qiushengming
     */
    public static int appearNumber(String srcText, String findText) {
        int count = 0;
        Pattern p = Pattern.compile(findText);
        Matcher m = p.matcher(srcText);
        while (m.find()) {
            count++;
        }
        return count;
    }

    /**
     * 查找第一次出现汉字的位置,包含汉字和中文符号</br>
     * 逻辑：通过正则去分割(split)字符串，取得第0个字符数据的长度</br>
     * 2017年7月5日</br>
     *
     * @param str 被操作的字符串
     * @return int 成功返回索引位置；失败返回-1；
     * @author qiushengming
     */
    public static int findHanZiFirstIndexOf(String str) {
        String reg = "[\u4e00-\u9fa5]";
        String regex = "[。，！？（）《》……、：——【】；’”‘“]";
        int index = -1;

        if (str.matches(".*" + reg + ".*")) {
            /*汉字*/
            index = str.split(reg)[0].length();
        } else if (str.matches(".*" + regex + ".*")) {
            /*中文符号*/
            index = str.split(regex)[0].length();
        }
        return index;
    }

    /**
     * 按照len长度分割s
     *
     * @param s   被分割的字符
     * @param len 长度
     * @return 分割后的数据
     */
    public static String[] stringSpilt(String s, int len) {
        if (s == null || s.length() == 0) {
            return null;
        }


        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (true) {
            int start = i * len;
            int end = (i + 1) * len;
            try {
                sb.append(s.substring(start, end)).append("#");
            } catch (StringIndexOutOfBoundsException e) {
                System.out.println(
                        "start >> " + start + "; end >> " + end + "; string >> "
                                + s);
            }
            if (end >= s.length()) {
                break;
            }
            i++;
        }
        return sb.toString().split("#");
    }

    /**
     * 将clob对象转换为string类型；clob == null，返回""；
     *
     * @param clob java.sql.Clob
     * @return clob == null ? return "";
     */
    public static String clobToString(Clob clob) {
        if (clob == null) {
            return "";
        }

        BufferedReader br; // 字符流
        StringBuilder sb = new StringBuilder(); // 转换的存储

        try {
            br = new BufferedReader(clob.getCharacterStream());
            String s = br.readLine();
            // 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING
            while (s != null) {
                sb.append(s);
                s = br.readLine();
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    /**
     * 将文件转换为二进制流的形式</br>
     * 2017年7月3日</br>
     *
     * @param in 输入流
     * @return byte字节码
     * @throws IOException byte[]
     * @author qiushengming
     */
    public static byte[] fileToBinaryFile(InputStream in) throws IOException {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();

        int ch;

        /*返回值byte字节数组*/
        byte[] data;

        while ((ch = in.read()) != -1) {
            byteOut.write(ch);
        }

        /*转换为byte*/
        data = byteOut.toByteArray();

        /*关闭流*/
        byteOut.close();

        return data;
    }

    /**
     * 1. 支持Map
     * 2. 支持继承 Serializable的对象
     * @param obj obj
     */
    public static void replaceExceptionWidthSpace(Object obj) throws SystemException {
        try {
            if (obj instanceof Map) {
                Map map = (Map) obj;
                for (Object keyObj : map.keySet()) {
                    if (!String.class.equals(keyObj.getClass())) {
                        throw new SystemException("不是String类型，期望String类型的key");
                    }
                    String key = (String) keyObj;
                    Object valueObj = map.get(key);
                    if (!String.class.equals(valueObj.getClass())) {
                        continue;
                    }
                    valueObj = ((String) valueObj)
                            .replaceAll(ZERO_WIDTH_SPACE, "")
                            .replaceAll(NON_BREAKING_SPACE, " ");
                    map.put(keyObj, valueObj);
                }
            } else if (obj instanceof Serializable) {
                Class<?> clazz;
                /*获取类信息,获取不到将会抛出ClassNotFoundException异常	*/
                clazz = Class.forName(obj.getClass().getName());
                Field[] fields = clazz.getDeclaredFields();

                /*循环类中的所有属性，对ZERO_WIDTH_SPACE字符进行替换*/
                for (Field f : fields) {
                    if (f.getType() == String.class) {
                        /*取消java语言访问检查，使得可以访问private变量 */
                        f.setAccessible(true);
                        String temp = (String) f.get(obj);
                        if (temp != null) {
                            String temp1 = temp.replaceAll(ZERO_WIDTH_SPACE, "")
                                    .replace(NON_BREAKING_SPACE, " ");
                            f.set(obj, temp1);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        System.out.println(stringToUnicode("    "));
        System.out.println(stringToUnicode(" "));
        System.out.println(stringToUnicode("    "));
    }
}
