package com.qiushengming.core.controller;

import com.qiushengming.commons.BooleanEditor;
import com.qiushengming.commons.DoubleEditor;
import com.qiushengming.commons.IntEditor;
import com.qiushengming.commons.ListEditor;
import com.qiushengming.utils.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author qiushengming
 * @date 2018/5/22.
 */
public class BaseController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 获取HttpServletRequest
     *
     * @return javax.servlet.http.HttpServletRequest
     */
    private HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
            .getRequest();
    }

    /**
     * 获取HttpSession
     *
     * @return javax.servlet.http.HttpSession
     */
    public HttpSession getHttpSession() {
        return getRequest().getSession();
    }

    protected HttpSession getSession() {
        // return SecurityUtils.getSubject ().getSession ();
        return getHttpSession();
    }

    /**
     * 获得Web应用的完整根目录URL
     *
     * @return 请求路径
     */
    protected String getServerUrlPath() {
        return WebUtils.getServerUrlPath(getRequest());
    }

    /**
     * 获得Web应用的物理根目录
     *
     * @return request
     */
    protected String getServerPath() {
        return WebUtils.getRealServerPath(getRequest());
    }

    /**
     * 获取请求参数
     *
     * @return key/value的map集合。
     */
    protected Map<String, Object> getRequestParameters() {
        return getRequestParameters(getRequest());
    }

    private Map<String, Object> getRequestParameters(
        HttpServletRequest request) {
        Iterator<String> keys = request.getParameterMap().keySet().iterator();
        Map<String, Object> result = new HashMap<>();
        while (keys.hasNext()) {
            String key = keys.next();
            result.put(key, this.getParamByName(request, key));
        }
        return result;
    }

    /**
     * 通过参数名称获取请求参数
     *
     * @param name 参数名称，即是key
     * @return 参数值，即是value
     */
    protected Object getParamByName(String name) {
        return getParamByName(getRequest(), name);
    }

    private Object getParamByName(HttpServletRequest request, String name) {
        Object[] obs = request.getParameterMap().get(name);
        if (obs != null && obs.length == 1) {
            return obs[0];
        } else {
            return obs;
        }
    }

    /**
     * 获取inpustream内容
     *
     * @return 拼接的inputstream中的数据
     * @throws IOException IO异常
     */
    protected String getInputStr() throws IOException {
        BufferedReader br =
            new BufferedReader(new InputStreamReader(getRequest().getInputStream(),
                "UTF-8"));
        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }

        br.close();
        return sb.toString();
    }

    /**
     * spring mvc 重定向
     *
     * @param path 路径
     * @return "redirect:" + path
     */
    protected String redirect(String path) {
        logger.debug("重定向地址：redirect:/forward" + path);

        return "redirect:" + path;
    }

    /**
     * spring mvc 请求转发
     *
     * @param path 路径
     * @return "forward:" + path
     */
    protected String forward(String path) {
        logger.debug("请求转发地址：forward:/forward" + path);

        return "forward:" + path;
    }

    /**
     * 获取请求的IP地址
     *
     * @return IP地址
     */
    protected String getIpAddr() {
        return WebUtils.getIpAddr(getRequest());
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        IntEditor intEditor = new IntEditor();
        DoubleEditor doubleEditor = new DoubleEditor();
        BooleanEditor booleanEditor = new BooleanEditor();
        ListEditor listEditor = new ListEditor();
        binder.registerCustomEditor(String.class,
            new StringTrimmerEditor(false));
        binder.registerCustomEditor(int.class, intEditor);
        binder.registerCustomEditor(Integer.class, intEditor);
        binder.registerCustomEditor(Double.class, doubleEditor);
        binder.registerCustomEditor(double.class, doubleEditor);
        binder.registerCustomEditor(boolean.class, booleanEditor);
        binder.registerCustomEditor(Boolean.class, booleanEditor);
        binder.registerCustomEditor(List.class, listEditor);
    }

    /*public User getCurrUser() {
        return (User) SecurityUtils.getSubject().getPrincipal();
    }*/
}
