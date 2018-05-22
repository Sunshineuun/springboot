package com.qiushengming.utils;

import org.apache.commons.collections.map.CaseInsensitiveMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.GZIPOutputStream;

/**
 * Created by qiushengming on 2018/5/22.
 */
public class WebUtils {
    /*
     * 检测需要依赖的Class是否存在
     */
    static {
        try {
            Class.forName("javax.servlet.http.Cookie");
            Class.forName("javax.servlet.http.HttpServletRequest");
            Class.forName("org.apache.commons.logging.Log");
            Class.forName("org.apache.commons.logging.LogFactory");
        } catch (ClassNotFoundException e) {
            System.err.println("RequestHelpers" + " could not be loaded by classloader because classes it depends"
                    + " on could not be found in the classpath...");
            e.printStackTrace();
        }
    }

    private static Log log = LogFactory.getLog(WebUtils.class);
    /**
     * 一年的秒数
     */
    public static final long ONE_YEAR_SECONDS = 60 * 60 * 24 * 365;
    private final static String ATTRIBUTES = "attributes";
    private final static String PARAMETERS = "parameters";
    private final static String HEADERS = "headers";

    /**
     * 显示所有的参数，属性和标题
     *
     * @param request 请求
     */
    public static void logAllRequestInfo(HttpServletRequest request) {

        if (request != null) {
            log.info("logAllRequestInfo: Attributes = " + getRequestAttributes(request));
            log.info("logAllRequestInfo: Parameters = " + getRequestParameters(request));
            log.info("logAllRequestInfo: Headers = " + getRequestHeaders(request));
        }

    }

    /**
     * 获得所有的参数，属性和标题,存放在Map中
     *
     * @param request 请求
     * @return Map
     */
    public static Map getAllRequestInfo(HttpServletRequest request) {
        HashMap<String, Map> hm = null;
        if (request != null) {
            hm = new HashMap<>();
            hm.put(ATTRIBUTES, getRequestAttributes(request));
            hm.put(PARAMETERS, getRequestParameters(request));
            hm.put(HEADERS, getRequestHeaders(request));
        }
        return hm;

    }

    /**
     * 获得所有的属性，存放在Map中
     *
     * @param request 请求
     * @return Map
     */
    private static Map getRequestAttributes(HttpServletRequest request) {
        HashMap<String, Object> hm = new HashMap<>();
        if (request != null) {
            for (Enumeration e = request.getAttributeNames(); e.hasMoreElements(); ) {
                String next = (String) e.nextElement();
                hm.put(next, request.getAttribute(next));
            }
        }
        return hm;
    }

    /**
     * 获得所有的参数，存放在Map中
     *
     * @param request 请求
     * @return Map
     */
    private static Map getRequestParameters(HttpServletRequest request) {

        HashMap<String, Object> hm = new HashMap<>();
        if (request != null) {
            for (Enumeration e = request.getParameterNames(); e.hasMoreElements(); ) {
                String next = (String) e.nextElement();
                List values = Arrays.asList(request.getParameterValues(next));
                if (values.size() == 1) {
                    hm.put(next, values.get(0));
                } else {
                    hm.put(next, values);
                }
            }
        }
        return hm;

    }

    public static Map getSessionAttributes(HttpSession session) {
        HashMap<String, Object> hm = new HashMap<>();
        if (session != null) {
            for (Enumeration e = session.getAttributeNames(); e.hasMoreElements(); ) {
                String next = (String) e.nextElement();
                hm.put(next, session.getAttribute(next));
            }
        }

        return hm;
    }

    /**
     * 获得所有的标题，存放在Map中
     *
     * @param request 请求
     * @return Map
     */
    private static Map getRequestHeaders(HttpServletRequest request) {

        Map headersMap = new CaseInsensitiveMap();
        if (request != null) {
            /*
            * CaseInsensitiveMap 与 LinkedCaseInsensitiveMap 针对英文
            * 1. CaseInsensitiveMap : 在get的时候key不区分大小写。在打印出来之后，它是将所有key都转换为小写了。
            * 2. LinkedCaseInsensitiveMap : 在get的时候key不区分大小写。在打印出来之后，保留初始的大小写状态。
            * */
            for (Enumeration en = request.getHeaderNames(); en.hasMoreElements(); ) {
                String headerName = (String) en.nextElement();
                List headerValues = new ArrayList();
                for (Enumeration headerValuesEnum = request.getHeaders(headerName); headerValuesEnum.hasMoreElements(); ) {
                    headerValues.add(headerValuesEnum.nextElement());
                }
                headersMap.put(headerName, headerValues);
            }
        }
        return Collections.unmodifiableMap(headersMap);
    }

    /**
     * 根据Cookie名字获取对应的cookie,如果没有找到，返回null
     *
     * @param request    请求
     * @param cookieName cookie的名称
     * @return 返回cookie
     */
    private static Cookie getCookie(HttpServletRequest request, String cookieName) {

        Cookie c = null;
        if (request != null) {
            Cookie[] cookies = request.getCookies();
            boolean found = false;
            if (cookies != null && cookies.length > 0) {
                for (int i = 0; i < cookies.length && !found; i++) {
                    if (cookies[i].getName().equals(cookieName)) {
                        c = cookies[i];
                        found = true;
                    }
                }
            }
        }
        return c;

    }

    /**
     * 根据cookie的名字返回其中的值，如果没有此cookie,返回null
     *
     * @param request    请求
     * @param cookieName cookie的名称
     * @return String
     */
    public static String getCookieValue(HttpServletRequest request, String cookieName) {
        Cookie c = getCookie(request, cookieName);
        String value = null;
        if (c != null) {
            value = c.getValue();
        }
        return value;
    }

    /**
     * 获得所有的cookies，存放在Map中
     *
     * @param request 请求
     * @return Map
     */
    public static Map getCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        HashMap<String, String> hm = new HashMap<>();
        if (cookies != null) {
            for (Cookie cooky : cookies) {
                hm.put(cooky.getName(), cooky.getValue());
            }
        }
        return hm;
    }

    /**
     * 返回一个32位的唯一标识来确认此次的请求
     *
     * @param request 请求
     * @return String
     */
    public static String generateGUID(HttpServletRequest request) {

        String out = "";
        try {
            // Construct a string that is comprised of:
            // Remote IP Address + Host IP Address + Date (yyyyMMdd) +
            // Time (hhmmssSSa) + Requested Path + Session ID +
            // HashCode Of ParameterMap
            StringBuilder sb = new StringBuilder(1024);
            sb.append(request.getRemoteAddr());
            InetAddress ia = InetAddress.getLocalHost();
            sb.append(ia.getHostAddress());
            sb.append(new SimpleDateFormat("yyyyMMddhhmmssSSa").format(new Date()));
            String path = request.getServletPath();
            String pathInfo = request.getPathInfo();
            if (pathInfo != null) {
                path += pathInfo;
            }
            sb.append(path);
            sb.append(request.getSession(false));
            sb.append(request.getParameterMap().hashCode());
            String str = sb.toString();
            // Now encode the string using an MD5 encryption algorithm.
            MessageDigest md = MessageDigest.getInstance("md5");
            md.update(str.getBytes());
            byte[] digest = md.digest();
            StringBuilder hexStr = new StringBuilder(1024);
            for (byte aDigest : digest) {
                str = Integer.toHexString(0xFF & aDigest);
                if (str.length() < 2) {
                    str = "0" + str;
                }
                hexStr.append(str);
            }
            out = hexStr.toString();
        } catch (NoSuchAlgorithmException | UnknownHostException nsae) {
            log.error(nsae);
        }
        // Return the encrypted string. It should be unique based on the
        // components that comprise the plain text string, and should always be
        // 32 characters thanks to the MD5 algorithm.
        return out;

    }

    /**
     * 根据提供的Request，返回其所有的内容
     *
     * @param request 请求
     * @return String
     * @throws IOException
     */
    public static String getBodyContent(HttpServletRequest request) throws IOException {

        BufferedReader br = request.getReader();
        String nextLine = "";
        StringBuilder bodyContent = new StringBuilder();
        nextLine = br.readLine();
        while (nextLine != null) {
            bodyContent.append(nextLine);
            nextLine = br.readLine();
        }
        return bodyContent.toString();

    }

    /**
     * 获得 request 的 Parameter, default 为 "" .
     *
     * @param request 请求
     * @param key     parameterName
     * @return String
     */
    public static String getParameter(HttpServletRequest request, String key) {
        return getParameter(request, key, "");
    }

    /**
     * 获得 request 的 Attribute, default 为 "" .
     *
     * @param request 请求
     * @param key     attributeName
     * @return String
     */
    public static String getAttribute(HttpServletRequest request, String key) {
        return getAttribute(request, key, "");
    }

    /**
     * 获得一个 request 的 Parameter.如果没有值，返回提供的默认值
     *
     * @param request 请求
     * @param key     请求的key值
     * @param def     默认值
     * @return String
     */
    private static String getParameter(HttpServletRequest request, String key, String def) {
        String tmpstr;
        if ((tmpstr = request.getParameter(key)) != null)
            return tmpstr;
        else
            return def;
    }

    /**
     * 获得一个 request 的 attribute.如果没有值，返回提供的默认值
     *
     * @param request 请求
     * @param key     请求的key值
     * @param def     默认值
     * @return String
     */
    private static String getAttribute(HttpServletRequest request, String key, String def) {
        String tmpstr;
        if ((tmpstr = (String) request.getAttribute(key)) != null)
            return tmpstr;
        else
            return def;
    }

    /**
     * 获得Web应用的物理根目录
     *
     * @param request 请求
     * @return String
     */
    public static String getRealServerPath(HttpServletRequest request) {
        return request.getSession().getServletContext().getRealPath("/");
    }

    /**
     * 获得Web应用的根目录
     *
     * @param request 请求
     * @return String
     */
    private static String getServerPath(HttpServletRequest request) {
        return (request.getContextPath().equals("/") ? "" : request.getContextPath());
    }

    /**
     * 获得Web应用的完整根目录url
     *
     * @param request 请求
     * @return String
     */
    public static String getServerUrlPath(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + getServerPath(request);
    }

    /**
     * 获得有中文字的参数, 转成 UTF-8 encoding, default 为 "" .
     *
     * @param request 请求
     * @param name    请求参数的key
     * @return String
     */
    public static String getCParameter(HttpServletRequest request, String name) {
        return getCParameter(request, name, "");
    }

    /**
     * 获得有中文字的参数, 转成 UTF-8 encoding, 如果没有值，返回默认值 .
     *
     * @param request 请求
     * @param name    请求参数的key
     * @param def     默认值
     * @return String
     */
    private static String getCParameter(HttpServletRequest request, String name, String def) {
        String tmp = "";
        try {
            tmp = new String(getParameter(request, name, def).getBytes("ISO-8859-1"), "utf-8");
        } catch (UnsupportedEncodingException ignored) {
            log.error("{}", ignored);
        }
        return tmp;
    }

    /**
     * 设置让浏览器弹出下载对话框的Header.
     *
     * @param fileName 下载后的文件名.
     */
    public static void setDownloadableHeader(HttpServletResponse response, String fileName) {
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
    }

    /**
     * 设置LastModified Header.
     */
    public static void setLastModifiedHeader(HttpServletResponse response, long lastModifiedDate) {
        response.setDateHeader("Last-Modified", lastModifiedDate);
    }

    /**
     * 设置Etag Header.
     */
    public static void setEtag(HttpServletResponse response, String etag) {
        response.setHeader("ETag", etag);
    }

    /**
     * 设置过期时间 Header.
     */
    public static void setExpiresHeader(HttpServletResponse response, long expiresSeconds) {
        // Http 1.0 header
        response.setDateHeader("Expires", System.currentTimeMillis() + expiresSeconds * 1000);
        // Http 1.1 header
        response.setHeader("Cache-Control", "max-age=" + expiresSeconds);
    }

    /**
     * 设置无缓存Header.
     */
    public static void setNoCacheHeader(HttpServletResponse response) {
        // Http 1.0 header
        response.setDateHeader("Expires", 0);
        // Http 1.1 header
        response.setHeader("Cache-Control", "max-age=0");
    }

    /**
     * 检查浏览器客户端是否支持gzip编码.
     */
    public static boolean checkAccetptGzip(HttpServletRequest request) {
        // Http1.1 header
        String acceptEncoding = request.getHeader("Accept-Encoding");
        return StringUtils.contains(acceptEncoding, "gzip");
    }

    /**
     * 设置Gzip Header并返回GZIPOutputStream.
     */
    public static OutputStream buildGzipOutputStream(HttpServletResponse response) throws IOException {
        response.setHeader("Content-Encoding", "gzip");
        return new GZIPOutputStream(response.getOutputStream());
    }

    /**
     * 根据浏览器If-Modified-Since Header, 计算文件是否已修改. 如果无修改, checkIfModify返回false
     * ,设置304 not modify status.
     */
    public static boolean checkIfModifiedSince(HttpServletRequest request, HttpServletResponse response,
                                               long lastModified) {
        long ifModifiedSince = request.getDateHeader("If-Modified-Since");
        if ((ifModifiedSince != -1) && (lastModified < ifModifiedSince + 1000)) {
            response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
            return false;
        }
        return true;
    }

    /**
     * 根据浏览器 If-None-Match Header,计算Etag是否无效.
     * <p>
     * 如果Etag有效,checkIfNoneMatch返回false, 设置304 not modify status.
     */
    public static boolean checkIfNoneMatchEtag(HttpServletRequest request, HttpServletResponse response, String etag) {
        String headerValue = request.getHeader("If-None-Match");
        if (headerValue != null) {
            boolean conditionSatisfied = false;
            if (!headerValue.equals("*")) {
                StringTokenizer commaTokenizer = new StringTokenizer(headerValue, ",");

                while (!conditionSatisfied && commaTokenizer.hasMoreTokens()) {
                    String currentToken = commaTokenizer.nextToken();
                    if (currentToken.trim().equals(etag)) {
                        conditionSatisfied = true;
                    }
                }
            } else {
                conditionSatisfied = true;
            }

            if (conditionSatisfied) {
                response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
                response.setHeader("ETag", etag);
                return false;
            }
        }
        return true;
    }

    /**
     * 取得带相同前缀的Request Parameters.
     * <p>
     * 返回的结果Parameter名已去除前缀.
     */
    public static Map<String, Object> getParametersStartingWith(HttpServletRequest request, String prefix) {
        Enumeration paramNames = request.getParameterNames();
        Map<String, Object> params = new TreeMap<>();
        if (prefix == null) {
            prefix = "";
        }
        while (paramNames != null && paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            if ("".equals(prefix) || paramName.startsWith(prefix)) {
                String unprefixed = paramName.substring(prefix.length());
                String[] values = request.getParameterValues(paramName);
                if (values == null || values.length == 0) {
                    // Do nothing, no values found at all.
                } else if (values.length > 1) {
                    params.put(unprefixed, values);
                } else {
                    params.put(unprefixed, values[0]);
                }
            }
        }
        return params;
    }

    /**
     * 获取客户端IP
     *
     * @param request 请求
     * @return IP地址
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
