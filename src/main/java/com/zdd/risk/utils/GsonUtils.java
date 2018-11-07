package com.zdd.risk.utils;

import com.google.gson.Gson;

/**
 * json转换工具
 * @author 租无忧科技有限公司
 * @date 2018-11-01.
 */
public class GsonUtils {

    private static final Gson gson = new Gson();

    public static String toJsonString(Object object){
      return object==null?null:gson.toJson(object);
    }
}
