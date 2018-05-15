package com.qiushengming.handle;

import com.qiushengming.entity.code.Result;
import com.qiushengming.utils.ResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author qiushengming
 * @date 2018/5/15
 */
@ControllerAdvice
public class ExceptionHandle {

    private final static Logger logger =
        LoggerFactory.getLogger(ExceptionHandle.class);

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Result handle(Exception e) {
        logger.info("[系统异常]{}", e);
        return ResultUtils.error(e.hashCode(), e.getMessage());
    }
}
