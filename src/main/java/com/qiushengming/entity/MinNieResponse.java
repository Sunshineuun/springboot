package com.qiushengming.entity;

/**
 * 响应包装类
 * <p>作者: qiushengming</p>
 * <p>日期: 2018/3/31</p>
 * @author MinMin
 */
public class MinNieResponse {
    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 说明
     */
    private String msg;

    /**
     * 返回数据
     */
    private Object data;

    public MinNieResponse() {

    }

    public MinNieResponse(boolean success, String msg, Object data) {
        this.success = success;
        this.msg = msg;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
