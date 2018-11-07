package com.zdd.risk.bean;

import com.alibaba.fastjson.JSONObject;
import com.zdd.risk.constant.ResponseCode;

/**
 * @author 租无忧科技有限公司
 * @date 2018-11-01.
 */
public class ResponseResult extends BaseBean{

    private String traceID; //链路ID
    private int code = ResponseCode.REQUEST_SUCCESS; //响应状态
    private String msg = "正常调用"; //响应状态说明
    private Object data = new JSONObject(); //响应数据

    public ResponseResult() {

    }

    public ResponseResult(int code) {
        this.code = code;
    }

    public ResponseResult(String traceID, int code, String msg) {
        this.traceID = traceID;
        this.code = code;
        this.msg = msg;
    }

    public ResponseResult(String traceID, int code, String msg, Object data) {
        this.traceID = traceID;
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResponseResult(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public String getTraceID() {
        return traceID;
    }

    public void setTraceID(String traceID) {
        this.traceID = traceID;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
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