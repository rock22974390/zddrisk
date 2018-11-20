package com.zdd.risk.api;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ZWYnqj on 2018/11/14.
 */


@RestController
@RequestMapping("/risk/jingdong/test")
public class JingDongTest {

    @Autowired
    JingdongController jingdongController;


    private static final Logger log = LoggerFactory.getLogger(JingdongController.class);
/*

    @Autowired
    private IAccreditDAO accreditDAO;
    @Autowired
    private IApplyAmountDAO applyAmountDAO;
    @Autowired
    private IApproveResultDAO approveResultDAO;
    @Autowired
    private ICertificationUserInfoDAO certificationUserInfoDAO;

*/



    @ApiOperation("4.0用户申请额度及其基本信息的存储")
    @RequestMapping(value = "/test1")
    public void test1() {


        JSONObject jsonObject = new JSONObject();
        //测试数据
        jsonObject.put("userid", "1");
        jsonObject.put("modelno", "1");
        jsonObject.put("applyamount","100");
        jsonObject.put("applydays","10");
        jsonObject.put("addressPermanent","北京");
        jsonObject.put("age","13");


        jingdongController.insertApplyAmountAndBaseInfo(jsonObject.toJSONString());




    }

    @ApiOperation("4.3插入用户授权信息数据接口")
    @RequestMapping(value = "/test2")
    public void test2() {
        Map reMap = new HashMap();
        JSONObject jsonObject = new JSONObject();
        //测试数据
        jsonObject.put("orderid", "1233");
        jsonObject.put("userId", "1233");
        jsonObject.put("accredit_Info", "1233");
        jsonObject.put("type", "1");
        System.out.println(jsonObject);


jingdongController.insertDataTaskIdFromZDD(jsonObject.toJSONString());

        System.out.println("1");

    }


    @ApiOperation("3.3授权额度回调接口")
    @RequestMapping(value = "/test3")
    public void test3() {

        JSONObject jsonObject = new JSONObject();
        //测试数据
        jsonObject.put("bizno", "1233");
        jsonObject.put("userId", "1233");
        jsonObject.put("applyid", "1233");




        String a= jingdongController.resultApproveToZRobot(jsonObject.toJSONString());

        System.out.println(a);

    }




}
