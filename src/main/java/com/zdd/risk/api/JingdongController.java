package com.zdd.risk.api;

import com.alibaba.fastjson.JSONObject;
import com.zdd.risk.bean.*;
import com.zdd.risk.dao.*;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * JingDong Service
 *
 * @author 租无忧科技有限公司
 * @date 2018-11-01.
 */
@RestController
@RequestMapping("/risk/jingdong")
public class JingdongController {

    private static final Logger log = LoggerFactory.getLogger(JingdongController.class);

    @Autowired
    private IAccreditDAO accreditDAO;
    @Autowired
    private IApplyAmountDAO applyAmountDAO;
    @Autowired
    private IApproveResultDAO approveResultDAO;
    @Autowired
    private ICertificationUserInfoDAO certificationUserInfoDAO;

    @ApiOperation("3.1请求ZRobot风控服务接口")
    @RequestMapping(value = "/approve")
    public JSONObject approve(@RequestBody String param){
        log.info("获取请求ZRobot风控服务接口接口入参 param= "+param);
        Map reMap = new HashMap();
        reMap.put("returnCode","100000");
        reMap.put("returnMessage","");
        JSONObject result = new JSONObject();
        JSONObject  params = JSONObject.parseObject(param);

        //insert DB
        return new JSONObject(reMap);
    }

    @ApiOperation("3.2ZRobot风控审批结果回调接口")
    @RequestMapping(value = "/approveResultFromZRobot")
    public JSONObject approveResultFromZRobot(@RequestBody String param){
        log.info("获取ZRobot风控审批结果回调接口入参 param= "+param);
        Map reMap = new HashMap();
        reMap.put("returnCode","100000");
        reMap.put("returnMessage","");

        JSONObject  params = JSONObject.parseObject(param);
        //insert DB
        ApproveResult record = params.toJavaObject(ApproveResult.class);
        log.info(JSONObject.toJSONString(record));
        approveResultDAO.insert(record);
        //TODO
        //回调业务系统
        JSONObject result = new JSONObject();
        //        result = XXX();
        return new JSONObject(reMap);
    }

    @ApiOperation("4.1获取用户申请额度接口")
    @RequestMapping(value = "/getApplyAmountFromZDD")
    public JSONObject getApplyAmountFromZDD(@RequestBody String uid){
        log.info("获取用户申请额度接口入参 uid= "+uid);
        Map reMap = new HashMap();
        reMap.put("returnCode","100000");
        reMap.put("returnMessage","");

        JSONObject  params = JSONObject.parseObject(uid);
        //调用业务系统
        //TODO
        JSONObject result = new JSONObject();
//        result = XXX();
        //insert DB
        ApplyAmount record = result.toJavaObject(ApplyAmount.class);
        log.info(JSONObject.toJSONString(record));
        applyAmountDAO.insert(record);

        return new JSONObject(reMap);
    }

    @ApiOperation("4.2获取用户基本信息数据接口")
    @RequestMapping(value = "/getBaseInfoFromZDD")
    public JSONObject getBaseInfoFromZDD(@RequestBody String uid){
        log.info("获取用户基本信息数据接口入参 uid= "+uid);
        Map reMap = new HashMap();
        reMap.put("returnCode","100000");
        reMap.put("returnMessage","");

        JSONObject  params = JSONObject.parseObject(uid);
        //TODO
        //调用业务系统
        JSONObject result = new JSONObject();
//        result =XXX();
        //insert DB
        CertificationUserInfo record = result.toJavaObject(CertificationUserInfo.class);
        log.info(JSONObject.toJSONString(record));

        certificationUserInfoDAO.insert(record);

        return new JSONObject(reMap);
    }

    @ApiOperation("4.3获取用户授权信息数据接口")
    @RequestMapping(value = "/getDataTaskIdFromZDD")
    public JSONObject getDataTaskIdFromZDD(@RequestBody String param){
        log.info("获取用户授权信息数据接口入参 param= "+param);
        Map reMap = new HashMap();
        reMap.put("returnCode","100000");
        reMap.put("returnMessage","");
        JSONObject result = new JSONObject();
        JSONObject  params = JSONObject.parseObject(param);

        //insert DB
        Accredit record = params.toJavaObject(Accredit.class);
        record.setCreatetime(new Date());
        log.info(JSONObject.toJSONString(record));
        accreditDAO.insert(record);
        return new JSONObject(reMap);
    }

    @ApiOperation("4.4发送用户授权信息数据接口")
    @RequestMapping(value = "/sendTaskToZDD")
    public String sendTaskToZDD(@RequestBody String param){
        log.info("获取用户授权信息数据接口入参 param= "+param);
        Map reMap = new HashMap();
        reMap.put("returnCode","100000");
        reMap.put("returnMessage","");
        JSONObject result = new JSONObject();
        JSONObject  params = JSONObject.parseObject(param);

        //select DB
        AccreditExample example = new AccreditExample();
        AccreditExample.Criteria criteria = example.createCriteria();
        criteria.andOrderidEqualTo(params.getString("orderId"));
        List<Accredit>  accreditList = accreditDAO.selectByExample(example);
        return JSONObject.toJSONString(accreditList);
    }
}
