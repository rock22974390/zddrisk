package com.zdd.risk.constant;

/**
 * @author 租无忧科技有限公司
 * @date 2018-11-01.
 */
public class ResponseCode {

    /**
     * 调用成功响应码
     */
    public final static int REQUEST_SUCCESS = 200;

    /**
     * 拒绝IP响应码
     */
    public static final int REQUEST_ERROR_REFUSE_IP = -60;

    /**
     * 程序异常
     */
    public static final int REQUEST_ERROR_PROGRAM_EXCEPTION = -100;
    /**
     * 参数格式
     */
    public static final int RESPONSE_CODE_ILLEGAL_ARGUMENT = -10;

    /**
     * 业务参数不能为空
     */
    public static final int BUSINESS_PARAM_ERROR = -1;

    /**
     * 操作成功
     */
    public static final int ACTIVE_SUCCESS = 01;
}
