package com.zdd.risk.api;

import com.alibaba.fastjson.JSONObject;
import com.zdd.risk.bean.*;
import com.zdd.risk.dao.IAccreditDAO;
import com.zdd.risk.dao.IApplyAmountDAO;
import com.zdd.risk.dao.IApproveResultDAO;
import com.zdd.risk.dao.ICertificationUserInfoDAO;
import com.zdd.risk.utils.Base64Utils;
import com.zdd.risk.utils.OrderSequence;
import com.zdd.risk.utils.RSAUtils;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@PropertySource(value = "classpath:jingdong/jingdong.properties", ignoreResourceNotFound = true)
public class JingdongController {



    @Value("${getApplyAmount}")
    String getApplyAmount;

    @Value("${getBaseInfo}")
    String getBaseInfo;
    @Value("${approveResult}")
    String approveResult;

    @Value("${orgCode}")
    String orgCode;

    @Value("${appId}")
    String appId;

    @Value("${appKey}")
    String appKey;

    @Value("${appSecret}")
    String appSecret;




    private static final Logger log = LoggerFactory.getLogger(JingdongController.class);

    @Autowired
    private IAccreditDAO accreditDAO;
    @Autowired
    private IApplyAmountDAO applyAmountDAO;
    @Autowired
    private IApproveResultDAO approveResultDAO;
    @Autowired
    private ICertificationUserInfoDAO certificationUserInfoDAO;

    @Autowired
   private OrderSequence orderSequence;

    @Autowired
    private RSAUtils rsaUtils;

    @Autowired
    private Base64Utils base64Utils;


    @ApiOperation("3.1请求ZRobot风控服务接口")
    @RequestMapping(value = "/approve")
    public JSONObject approve(@RequestBody String param) {

        log.info("获取请求ZRobot风控服务接口接口入参 param= " + param);
        Map reMap = new HashMap();
        reMap.put("code", "100000");
        reMap.put("codeMsg", "操作成功！");
//        JSONObject result = new JSONObject();
//        JSONObject params = JSONObject.parseObject(param);

      //  byte[] rsa=rsaUtils.encryptByPublicKey(appKey,appSecret);




        JSONObject jsonObject = new JSONObject();
        //测试数据
        jsonObject.put("orderid", "1233");
        jsonObject.put("userId", "1233");
        jsonObject.put("accreditInfo", "1233");
        jsonObject.put("type", "1");


        //insert DB
        Accredit record = jsonObject.toJavaObject(Accredit.class);
        record.setCreateTime(new Date());
        log.info(JSONObject.toJSONString(record));
        accreditDAO.insert(record);

//        if(a!=1){
//            reMap.put("code", "0");
//            reMap.put("codeMsg", "操作失败！");
//        }
//
//        System.out.println(a);
        //TODO
        //回调业务系统
        return new JSONObject(reMap);

    }

    @ApiOperation("3.2ZRobot风控审批结果回调接口")
    @RequestMapping(value = "/approveResultFromZRobot")
    public JSONObject approveResultFromZRobot() {

//        public JSONObject approveResultFromZRobot(@RequestBody String param) {
        log.info("获取ZRobot风控审批结果回调接口入参 param= ");
        Map reMap = new HashMap();
        reMap.put("code", "100000");
        reMap.put("codeMsg", "操作成功！");


        JSONObject jsonObject = new JSONObject();
        //测试数据
        jsonObject.put("bizno", "1233");
        jsonObject.put("userId", "1233");
        jsonObject.put("applyid", "1233");
        jsonObject.put("payindex", "1");
        jsonObject.put("approveresult", "1233");
        jsonObject.put("approveCredit", "1233");
        jsonObject.put("approveAPR", "1233");
        jsonObject.put("approveDPR", "1");


        //insert DB
        ApproveResult record = jsonObject.toJavaObject(ApproveResult.class);
        record.setCreateTime(new Date());
        log.info(JSONObject.toJSONString(record));
        int a = approveResultDAO.insert(record);

        if(a!=1){
            reMap.put("code", "0");
            reMap.put("codeMsg", "操作失败！");
        }

        System.out.println(a);
        //TODO
        //回调业务系统
        return new JSONObject(reMap);
    }


    @ApiOperation("3.3授权额度回调接口")
    @RequestMapping(value = "/resultApproveToZRobot")
//    public JSONObject resultApproveToZRobot() {

        public String resultApproveToZRobot(@RequestBody String param) {
        log.info("获取ZRobot风控审批结果回调接口入参 param= "+param);
        Map reMap = new HashMap();
        reMap.put("code", "100000");
        reMap.put("codeMsg", "操作成功！");


        JSONObject jsonObject = new JSONObject();
        //测试数据
        jsonObject.put("bizno", "1233");
        jsonObject.put("userid", "1233");
        jsonObject.put("applyid", "1233");

        ApproveResultExample example = new ApproveResultExample();
        ApproveResultExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(jsonObject.getString("userid"));
        //通过userID查询申请额度信息
        List<ApproveResult> approveResute1 = approveResultDAO.selectByExample(example);

        JSONObject result = new JSONObject();


        System.out.println(result.toJSONString(approveResute1));

        return result.toJSONString(approveResute1);
    }




    @ApiOperation("4.0用户申请额度及其基本信息的存储")
    @RequestMapping(value = "/insertApplyAmountAndBaseInfo")
    public JSONObject insertApplyAmountAndBaseInfo(@RequestBody String param) {
       // public JSONObject insertApplyAmountAndBaseInfo() {
        log.info("获取用户申请额度接口入参 uid= " + param);

        Map reMap = new HashMap();
        reMap.put("code", "100000");
        reMap.put("codeMsg", "操作成功！");

        JSONObject jsonObject = new JSONObject();
        //测试数据
        jsonObject.put("userid", "1");
        jsonObject.put("modelno", "1");
        jsonObject.put("applyamount","100");
        jsonObject.put("applydays","10");
        jsonObject.put("addressPermanent","北京");
        jsonObject.put("age","13");

        System.out.println("#######################################");

  /*      ApplyAmount record = jsonObject.toJavaObject(ApplyAmount.class);
        log.info(JSONObject.toJSONString(record));
        int a = applyAmountDAO.insert(record);
        CertificationUserInfo record1 = jsonObject.toJavaObject(CertificationUserInfo.class);
        log.info(JSONObject.toJSONString(record1));
        int a1 = certificationUserInfoDAO.insert(record1);
        System.out.println(a);
        if(a!=1){
            reMap.put("code", "0");
            reMap.put("codeMsg", "操作失败！");
        }*/
        return new JSONObject(reMap);
    }


    @ApiOperation("4.1获取用户申请额度接口")
    @RequestMapping(value = "/getApplyAmountFromZDD")
    public String getApplyAmountFromZDD(@RequestBody String param) {

        log.info("获取用户授权信息数据接口入参 param= " + param);

        JSONObject jsonObject = new JSONObject();
        //测试数据
        jsonObject.put("uid", "123");
        jsonObject.put("username", "宋发元");

        // System.out.println(jsonObject.toString());
        //select DB
        ApplyAmountExample example = new ApplyAmountExample();
        ApplyAmountExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(jsonObject.getString("uid"));
        //通过userID查询申请额度信息
        List<ApplyAmount> applyamountlist = applyAmountDAO.selectByExample(example);

        JSONObject result = new JSONObject();

        System.out.println(result.toJSONString(applyamountlist));

        return result.toJSONString(applyamountlist);

    }

    @ApiOperation("4.2获取用户基本信息数据接口")
    @RequestMapping(value = "/getBaseInfoFromZDD")
    public String getBaseInfoFromZDD() {
        //public String getBaseInfoFromZDD(@RequestBody String uid) {
        //log.info("获取用户基本信息数据接口入参 uid= " + uid);


        Map reMap = new HashMap();
        reMap.put("code", "100000");
        reMap.put("codeMsg", "操作成功！");

        // JSONObject params = JSONObject.parseObject(uid);


        JSONObject jsonObject = new JSONObject();
        //测试数据
        jsonObject.put("uid", "123");
        jsonObject.put("username", "宋发元");


        CertificationUserInfoExample example = new CertificationUserInfoExample();
        CertificationUserInfoExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(jsonObject.getString("uid"));
        //通过userID查询用户基本信息数据
        List<CertificationUserInfo> certificationuserinfolist = certificationUserInfoDAO.selectByExample(example);

        JSONObject result = new JSONObject();
        System.out.println(result.toJSONString(certificationuserinfolist));
        return result.toJSONString(certificationuserinfolist);
    }

    @ApiOperation("4.3插入用户授权信息数据接口")
    @RequestMapping(value = "/insertDataTaskIdFromZDD")
 //   public JSONObject insertDataTaskIdFromZDD() {
        public JSONObject insertDataTaskIdFromZDD(@RequestBody String param) {
         log.info("获取用户授权信息数据接口入参 param= " + param);
        Map reMap = new HashMap();
        reMap.put("code", "100000");
        reMap.put("codeMsg", "操作成功！");
        //JSONObject result = new JSONObject();
        //JSONObject params = JSONObject.parseObject(param);
        JSONObject jsonObject = new JSONObject();
        //测试数据
        jsonObject.put("orderid", "1233");
        jsonObject.put("userId", "1233");
        jsonObject.put("accredit_Info", "1233");
        jsonObject.put("type", "1");
        System.out.println(jsonObject);

System.out.println("===============================");
/*        //insert DB
        Accredit record = jsonObject.toJavaObject(Accredit.class);
        record.setCreatetime(new Date());
        log.info(JSONObject.toJSONString(record));
        int a = accreditDAO.insert(record);

        if(a!=1){
            reMap.put("code", "0");
            reMap.put("codeMsg", "操作失败！");
        }
        System.out.println(a);*/

        return new JSONObject(reMap);
    }






    @ApiOperation("测试类")
    @RequestMapping(value = "/testFromZDD")
    public JSONObject testFromZDD() {
        //public JSONObject insertDataTaskIdFromZDD(@RequestBody String param) {
        // log.info("获取用户授权信息数据接口入参 param= " + param);
        Map reMap = new HashMap();
        reMap.put("code", "100000");
        reMap.put("codeMsg", "操作成功！");

        JSONObject jsonObject = new JSONObject();
        //测试数据
        jsonObject.put("orderid", "1233");
        jsonObject.put("userId", "1233");
        jsonObject.put("accreditInfo", "1233");
        jsonObject.put("type", "1");
        jsonObject.put("appKey",appKey);


        System.out.println(appKey);

        byte[] apsdfp= base64Utils.decodeByte(appKey);

        System.out.println(apsdfp);


        //byte[] rsa=rsaUtils.encryptByPublicKey(appKey,appSecret);

       System.out.println(getApplyAmount+"\t"+appKey+"\t"+ orderSequence.getOrderNo1()+"\t"+apsdfp);

        return new JSONObject(reMap);
    }


}
