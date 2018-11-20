package com.zdd.risk.api;

import com.alibaba.fastjson.JSONObject;
import com.zdd.risk.bean.*;
import com.zdd.risk.dao.IAccreditDAO;
import com.zdd.risk.dao.IApplyAmountDAO;
import com.zdd.risk.dao.IApproveResultDAO;
import com.zdd.risk.dao.ICertificationUserInfoDAO;
import com.zdd.risk.utils.*;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.PublicKey;
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

    @Value("${version}")
    String version;

    @Value("${appKey}")
    String appKey;

    @Value("${appSecret}")
    String appSecret;

    @Value("${APIurl}")
    String APIurl;


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
        JSONObject params = JSONObject.parseObject(param);
        params.put("orgCode", orgCode);
        params.put("appId", appId);
        params.put("appId", appId);
        params.put("version", version);
        params.put("appKey", appKey);
        params.put("approveResult", approveResult);





        //查询bizNo
        long bizno1 = orderSequence.getOrderNo1();
        String bizNo = bizno1 + "";
        params.put("bizNo", bizNo);

        JSONObject database1 = new JSONObject();
        database1.put("getApplyAmount", getApplyAmount);
        database1.put("getBaseInfo", getBaseInfo);
        params.put("database", database1.toJSONString());

        JSONObject database2 = new JSONObject();
        database2.put("getTaobaoInfo", "4787959537775966181");
        database2.put("getTaobaoReport", "4787959537775966181");
        params.put("dataTaskId", database2.toJSONString());


        System.out.println(params.toJSONString());

        System.out.println(APIurl);

        //调用京东风控策略接口
        HttpUtils http = new HttpUtils();
        String result = http.post(APIurl, params.toJSONString());

        System.out.println("======================:" + result);

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
   // public JSONObject approveResultFromZRobot() {

        public JSONObject approveResultFromZRobot(@RequestBody String param) {
        log.info("获取ZRobot风控审批结果回调接口入参 param= ");
        Map reMap = new HashMap();
        reMap.put("code", "100000");
        reMap.put("codeMsg", "操作成功！");

        JSONObject  params = JSONObject.parseObject(param);

        //测试数据
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("bizno", "1233");
        jsonObject.put("userId", "1233");
        jsonObject.put("applyid", "1233");
        jsonObject.put("payindex", "1");
        jsonObject.put("approveresult", "1233");
        jsonObject.put("approveCredit", "1233");
        jsonObject.put("approveAPR", "1233");
        jsonObject.put("approveDPR", "1");


        //insert DB
        ApproveResult record = params.toJavaObject(ApproveResult.class);
        record.setCreateTime(new Date());
        log.info(JSONObject.toJSONString(record));
        int a = approveResultDAO.insert(record);

        if (a != 1) {
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
        log.info("获取ZRobot风控审批结果回调接口入参 param= " + param);
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

/*
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
        jsonObject.put("applyamount", "100");
        jsonObject.put("applydays", "10");
        jsonObject.put("addressPermanent", "北京");
        jsonObject.put("age", "13");

        System.out.println("#######################################");

        ApplyAmount record = jsonObject.toJavaObject(ApplyAmount.class);
        log.info(JSONObject.toJSONString(record));
        int a = applyAmountDAO.insert(record);
        CertificationUserInfo record1 = jsonObject.toJavaObject(CertificationUserInfo.class);
        log.info(JSONObject.toJSONString(record1));
        int a1 = certificationUserInfoDAO.insert(record1);
        System.out.println(a);
        if (a != 1) {
            reMap.put("code", "0");
            reMap.put("codeMsg", "操作失败！");
        }
        return new JSONObject(reMap);
    }*/


    @ApiOperation("4.1获取用户申请额度接口")
    @RequestMapping(value = "/getApplyAmountFromZDD")
    public String getApplyAmountFromZDD(@RequestBody String uid) {

        log.info("获取用户授权信息数据接口入参 param= " + uid);

        JSONObject params = new JSONObject();
        JSONObject jsonObject = new JSONObject();
//        //测试数据
        jsonObject.put("uid", uid);

        //select DB
        ApplyAmountExample example = new ApplyAmountExample();
        ApplyAmountExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(jsonObject.getString("uid"));
        //通过userID查询申请额度信息
        List<ApplyAmount> applyamountlist = applyAmountDAO.selectByExample(example);


        if (applyamountlist.size() == 0) {
            params.put("success", "fails");
            params.put("errorCode", "查询成功");
            params.put("errorMessage", "没有数据！");
        }else {
            JSONObject result = new JSONObject();

            System.out.println(applyamountlist.size());

            String a = StringUtils.strip(result.toJSONString(applyamountlist.get(0)), "[]");

            result.put("data",a);

            try {
                byte[] publicEncrypt = rsaUtils.encryptByPublicKey(result.toJSONString().getBytes("UTF-8"), appSecret);

                String pb = new String(publicEncrypt, "UTF-8");

                System.out.println(new String(publicEncrypt, "UTF-8"));

                params.put("data", pb);

            } catch (Exception e) {
                e.printStackTrace();
            }
            params.put("success", "true");
            params.put("errorCode", "");
            params.put("errorMessage", "");

        }

System.out.println("==========================================="+params.toJSONString());


        return  params.toJSONString();

    }

    @ApiOperation("4.2获取用户基本信息数据接口")
    @RequestMapping(value = "/getBaseInfoFromZDD")
//    public String getBaseInfoFromZDD() {
    public String getBaseInfoFromZDD(@RequestBody String uid) {
        log.info("获取用户基本信息数据接口入参 uid= " + uid);



        JSONObject params = new JSONObject();
        JSONObject jsonObject = new JSONObject();
//        //测试数据
        jsonObject.put("uid", uid);

        //JSONObject params = JSONObject.parseObject(uid);


//        JSONObject jsonObject = new JSONObject();
//        //测试数据
//        jsonObject.put("uid", "123");
//        jsonObject.put("username", "宋发元");
//

        CertificationUserInfoExample example = new CertificationUserInfoExample();
        CertificationUserInfoExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(jsonObject.getString("uid"));
        //通过userID查询用户基本信息数据
        List<CertificationUserInfo> certificationuserinfolist = certificationUserInfoDAO.selectByExample(example);

//        JSONObject result = new JSONObject();
//        System.out.println(result.toJSONString(certificationuserinfolist));
//        return result.toJSONString(certificationuserinfolist);



        if (certificationuserinfolist.size() == 0) {
            params.put("success", "fails");
            params.put("errorCode", "查询失败");
            params.put("errorMessage", "没有数据！");
        }else {
            JSONObject result = new JSONObject();

            System.out.println(certificationuserinfolist.size());

            String rs = StringUtils.strip(result.toJSONString(certificationuserinfolist.get(0)), "[]");

            result.put("data",rs);
            try {
                byte[] publicEncrypt = rsaUtils.encryptByPublicKey(result.toJSONString().getBytes("UTF-8"), appSecret);
                String byte2Base64 = rsaUtils.byte2Base64(publicEncrypt);

                //String pb = new String(publicEncrypt, "UTF-8");

                //System.out.println(new String(publicEncrypt, "UTF-8"));
                System.out.println(byte2Base64);

                params.put("data", byte2Base64);

            } catch (Exception e) {
                e.printStackTrace();
            }
            params.put("success", "true");
            params.put("errorCode", "");
            params.put("errorMessage", "");

        }

        System.out.println("==========================================="+params.toJSONString());


        return  params.toJSONString();
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
        JSONObject params = JSONObject.parseObject(param);
//        JSONObject jsonObject = new JSONObject();
//        //测试数据
//        jsonObject.put("orderid", "1233");
//        jsonObject.put("userId", "1233");
//        jsonObject.put("accredit_Info", "1233");
//        jsonObject.put("type", "1");
//        System.out.println(jsonObject);

        System.out.println("===============================");
        //insert DB
        Accredit record = params.toJavaObject(Accredit.class);
        record.setCreateTime(new Date());
        log.info(JSONObject.toJSONString(record));
        int a = accreditDAO.insert(record);

        if (a != 1) {
            reMap.put("code", "0");
            reMap.put("codeMsg", "操作失败！");
        }
        System.out.println(a);

        return new JSONObject(reMap);
    }




    @ApiOperation("4.4用户申请额度信息存储")
    @RequestMapping(value = "/insertApplyAmount")
    public JSONObject insertApplyAmount(@RequestBody String param) {
        // public JSONObject insertApplyAmountAndBaseInfo() {
        log.info("4用户申请额度信息存储 param= " + param);

        Map reMap = new HashMap();
        reMap.put("code", "100000");
        reMap.put("codeMsg", "操作成功！");

        JSONObject  params = JSONObject.parseObject(param);
        params.put("createTime",new Date());

//        //自动生成bizNo
//        long bizno1 = orderSequence.getOrderNo1();
//        String bizNo = bizno1 + "";
//        params.put("bizNo", bizNo);

        JSONObject jsonObject = new JSONObject();
        //测试数据
        jsonObject.put("userId", "1");
        jsonObject.put("modelNo", "1");
        jsonObject.put("applyAmount", "100");
        jsonObject.put("applyDays", "10");
        jsonObject.put("applyMonths", "北京");
        jsonObject.put("contractAmount", "13");
        jsonObject.put("applyId", "1");
        jsonObject.put("realName", "1");
        jsonObject.put("idCard", "100");
        jsonObject.put("bankCard", "10");
        jsonObject.put("bankName", "北京");
        jsonObject.put("bankMobile", "13");

       // System.out.println("#######################################"+jsonObject);

        ApplyAmount record = params.toJavaObject(ApplyAmount.class);
        log.info(JSONObject.toJSONString(record));
        int a=1;
       //  a = applyAmountDAO.insert(record);
        System.out.println(a);
        if (a != 1) {
            reMap.put("code", "0");
            reMap.put("codeMsg", "操作失败！");
        }
        return new JSONObject(reMap);
    }

    @ApiOperation("4.5用户基本信息存储")
    @RequestMapping(value = "/insertBaseInfo")
    public JSONObject insertBaseInfo(@RequestBody String param) {
        // public JSONObject insertApplyAmountAndBaseInfo() {
        log.info("4.5用户基本信息存储 param= " + param);

        JSONObject  params = JSONObject.parseObject(param);
        params.put("createTime",new Date());


        Map reMap = new HashMap();
        reMap.put("code", "100000");
        reMap.put("codeMsg", "操作成功！");

        JSONObject jsonObject = new JSONObject();
        //测试数据
        jsonObject.put("userId", "1");
        jsonObject.put("mobile", "1");
        jsonObject.put("realName", "100");
        jsonObject.put("nation", "10");
        jsonObject.put("age", "1");
        jsonObject.put("addressPermanent", "1");
        jsonObject.put("idCardValidDate", "100");
        jsonObject.put("maritalStatus", "1");
        jsonObject.put("education","1");
        jsonObject.put("institution", "1");
        jsonObject.put("addressWork", "100");
        jsonObject.put("addressHome", "10");
        jsonObject.put("payday", "北京");
        jsonObject.put("companyName", "13");
        jsonObject.put("companyPhone", "1");
        jsonObject.put("sosContactName", "100");
        jsonObject.put("sosContactRelation","1");
        jsonObject.put("sosContactPhone", "北京");
        jsonObject.put("sosContactName1", "13");
        jsonObject.put("sosContactRelation1", "2");
        jsonObject.put("sosContactPhone1", "13");
        jsonObject.put("lastLoginIp", "北京");
        jsonObject.put("longitude", "13");
        jsonObject.put("latitude", "1");
        jsonObject.put("gpsAddress", "100");
        jsonObject.put("regOs", "10");
        jsonObject.put("regAppVersion", "北京");
        jsonObject.put("regFrom", "13");
        jsonObject.put("regIp", "北京");
        jsonObject.put("mobilelog", "[{\"name\":\"唐瑜璟\",\"tel\":\"+8615517813221\"},{\"name\":\"北京租无忧科技有限公司多人通话\",\"tel\":\"01052729739\"}]");

        //System.out.println("#######################################"+jsonObject);

        CertificationUserInfo record1 = params.toJavaObject(CertificationUserInfo.class);
        log.info(JSONObject.toJSONString(record1));
        int a = certificationUserInfoDAO.insert(record1);
        System.out.println(a);
        if (a != 1) {
            reMap.put("code", "0");
            reMap.put("codeMsg", "操作失败！");
        }
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
        jsonObject.put("appKey", appKey);


        System.out.println(appKey);

        byte[] apsdfp = base64Utils.decodeByte(appKey);

        System.out.println(apsdfp);


        //byte[] rsa=rsaUtils.encryptByPublicKey(appKey,appSecret);

        System.out.println(getApplyAmount + "\t" + appKey + "\t" + orderSequence.getOrderNo1() + "\t" + apsdfp);

        return new JSONObject(reMap);
    }


    public static void main(String[] args) {


        RSAUtils rsaUtils = new RSAUtils();

        String appSecret = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCXoybMY/5uNy2ZXDUjR4OWK070kb9DAscEPhvo1f0l1+iTrfja+eXarqvqIL/wHUtpprp8N6IBfr0lrJAvYKVrBj+WS/+PmyhMF45A4ZOz8xhGXdQc6hc+L+/ga/3fAZK4zD1DE8mAiAcTvb7mCO3rZOcGJKDXqnQi0nFcAP2o7QIDAQAB";//接口提供的公钥
        String appKey = "diofi9329y23h2d8723e2df34";
//        JSONObject result = new JSONObject();
        JSONObject params = new JSONObject();


        JSONObject database2 = new JSONObject();
        database2.put("getTaobaoInfo", "4787959537775966181");
        database2.put("getTaobaoReport", "4787959537775966181");
        database2.put("appKey", appKey);


        PublicKey publicKey = null;
        try {
            publicKey = rsaUtils.string2PublicKey(appSecret);
            System.out.println(appSecret);

            byte[] publicEncrypt = rsaUtils.publicEncrypt(params.toJSONString().getBytes("UTF-8"), publicKey);

            String pb = new String(publicEncrypt, "UTF-8");

            System.out.println(publicEncrypt);
            System.out.println(new String(publicEncrypt, "UTF-8"));

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("1");


    }


}
