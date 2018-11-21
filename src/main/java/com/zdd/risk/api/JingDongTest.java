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


/*
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


    }*/

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

    @ApiOperation("3.1请求ZRobot风控服务接口")
    @RequestMapping(value = "/test4")
    public void test4() {

        JSONObject jsonObject = new JSONObject();
        //测试数据
        jsonObject.put("payIndex", "1");
        jsonObject.put("uid", "1233");
        jsonObject.put("applyId", "1233");
        jsonObject.put("productId","");



        JSONObject a= jingdongController.approve(jsonObject.toJSONString());

        System.out.println(jsonObject.toString());

    }


    @ApiOperation("4.1获取用户申请额度接口")
    @RequestMapping(value = "/test5")
    public void test5() {



        String result= jingdongController.getApplyAmountFromZDD("123");

     //   System.out.println(result);

    }


    @ApiOperation("4.2获取用户申请额度接口")
    @RequestMapping(value = "/test6")
    public void test6() {



        String result= jingdongController.getBaseInfoFromZDD("123");

        //   System.out.println(result);

    }

    @ApiOperation("4.4用户申请额度信息存储")
    @RequestMapping(value = "/test7")
    public void test7() {


        JSONObject jsonObject = new JSONObject();
        //测试数据
        jsonObject.put("userid", "1");
        jsonObject.put("modelno", "1");
        jsonObject.put("applyamount","100");
        jsonObject.put("applydays","10");
        jsonObject.put("addressPermanent","北京");
        jsonObject.put("age","13");


        jingdongController.insertApplyAmount(jsonObject.toJSONString());


    }

    @ApiOperation("4.5用户基本信息存储")
    @RequestMapping(value = "/test8")
    public void test8() {


        JSONObject jsonObject = new JSONObject();
        //测试数据
        jsonObject.put("userid", "1");


        jingdongController.insertBaseInfo(jsonObject.toJSONString());


    }







}
