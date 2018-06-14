package com.qiushengming.core.controller;

import com.qiushengming.utils.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 请求路径定向处理
 *
 * @author qiushengming
 * @date 2018/5/7
 */
@Controller
public class ForwardController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping("/forward/**")
    public String forward(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        String requestURI = request.getRequestURI();

        logger.info("当前请求客户机的IP：{}", WebUtils.getIpAddr(request));

        logger.info("当前请求的基地址：{}，uri：{}", contextPath, requestURI);

        String ctx = contextPath + "/forward";
        String path =
                requestURI.substring(requestURI.indexOf(ctx) + ctx.length());

        char symbol1 = '.';
        String symbol2 = "/";

        if (path.lastIndexOf(symbol1) > 0) {
            path = path.substring(0, path.lastIndexOf('.'));
        } else if (path.endsWith(symbol2)) {
            path = path + "index";
        }

        logger.info("访问路径：{}", path);
        return path;
    }

    @RequestMapping("/")
    public String index() {
        return redirect("/forward/index");
    }
}
