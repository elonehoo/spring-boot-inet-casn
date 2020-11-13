package com.inet.code.utils;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * 返回值模式
 * @author HCY
 * @since 2020-10-29
 */
public class Result {
    /**
     * 时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date timestamp;
    /**
     * 状态信息
     * 200 - 成功
     * 401 - 非法的
     * 403 - 禁止
     * 404 - 未找到
     * 500 - 错误
     */
    private Integer status;
    /**
     * 错误信息
     */
    private String error;
    /**
     * 错误详情
     */
    private String trace;
    /**
     * 返回信息
     */
    private Object message;
    /**
     * 调用URL
     */
    private String path;

    /**
     * 空参
     */
    public Result() {
    }

    /**
     * 全参
     * @author HCY
     * @since 2020-10-29
     * @param status
     * @param error
     * @param trace
     * @param message
     * @param path
     */
    public Result( Integer status, String error, String trace, Object message, String path) {
        this.timestamp = new Date();
        this.status = status;
        this.error = error;
        this.trace = trace;
        this.message = message;
        this.path = path;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp() {
        this.timestamp = new Date();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getTrace() {
        return trace;
    }

    public void setTrace(String trace) {
        this.trace = trace;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "Result{" +
                "timestamp=" + timestamp +
                ", status=" + status +
                ", error='" + error + '\'' +
                ", trace='" + trace + '\'' +
                ", message='" + message + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
