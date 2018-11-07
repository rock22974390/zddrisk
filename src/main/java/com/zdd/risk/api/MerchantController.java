package com.zdd.risk.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gxb.sdk.des.exception.GxbApiException;
import com.gxb.sdk.des.model.dto.DataExchangeDetailDto;
import com.gxb.sdk.des.model.dto.DataExchangeDto;
import com.gxb.sdk.des.model.enums.DataExchangeStatusEnum;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/risk/check")
public class MerchantController {
    private static final Logger log = LoggerFactory.getLogger(MerchantController.class);
    private static final int PRODUCT_ID_2=2;
    private static final int PRODUCT_ID_5=5;
    private static final String LEVEL_A="A";
    private static final String LEVEL_D="D";
    private static final String LEVEL_E="E";

    private com.gxb.sdk.des.client.MerchantClient client;

    public void init() {
        //1、初始化
        //创建client，入参为私钥、主链的账号
        client = new com.gxb.sdk.des.client.MerchantClient("5HzQ6FoVikaVVag56Vw8zQBConbXoFtdJWBwWucNG3LuLg544fc", "1.2.1053551");
//        log.info("MerchantClient初始化完成");
   }
    @ApiOperation("检查借款信息接口")
    @RequestMapping(value = "/LoanInfo")
    public JSONObject checkLoanInfo(@RequestBody String param){
        log.info("检查借款信息接口入参 param= "+param);
        JSONObject result = new JSONObject();
        JSONObject  params = JSONObject.parseObject(param);
        return checkLoanInfo2(params.getString("name"),params.getString("idcard"),params.getString("mobile"));
    }

    @ApiOperation("检查借款信息接口")
    @RequestMapping(value = "/LoanInfo3")
    public JSONObject checkLoanInfo3(String param){
        log.info("检查借款信息接口入参 param= "+param);
        JSONObject result = new JSONObject();
        JSONObject  params = JSONObject.parseObject(param);
        return checkLoanInfo2(params.getString("name"),params.getString("idcard"),params.getString("mobile"));
    }

    @ApiOperation("检查借款信息接口")
    @GetMapping(value = "/LoanInfo2")
    public JSONObject checkLoanInfo2(String name,String idcard,String mobile){
        log.info("检查借款信息接口入参 name= "+name+" idcard"+idcard+" mobile="+mobile);
        JSONObject result = new JSONObject();
        Map reMap = new HashMap();
        reMap.put("code","100000");
        reMap.put("codeMsg","");
        if(StringUtils.isEmpty(name) || StringUtils.isEmpty(idcard) || StringUtils.isEmpty(mobile) ){
            reMap.put("code","100001");
            reMap.put("codeMsg","姓名,身份证号码,手机号不能为空");
            log.info("检查借款信息接口出参 reMap= "+new JSONObject(reMap).toString());
            return new JSONObject(reMap);
        }
        Map paras = new HashMap();
        paras.put("name",name);
        paras.put("idcard",idcard);
        paras.put("mobile",mobile);
        JSONObject  params = new JSONObject(paras);

        //初始化--初始化执行一次就够了
        init();

        result = third(params);
        if(result!=null) {
            reMap.put("data",result);
            log.info("检查借款信息接口出参 reMap= "+new JSONObject(reMap).toString());
            return new JSONObject(reMap);
        }

        result = netleng(params);
        if(result!=null) {
            reMap.put("data",result);
            log.info("检查借款信息接口出参 reMap= "+new JSONObject(reMap).toString());
            return new JSONObject(reMap);
        }
        if(result==null) {
            Map para = new HashMap();
            para.put("level",LEVEL_A);
            para.put("recommend","低风险客户");
            reMap.put("data",para);
        }
        log.info("检查借款信息接口出参 reMap= "+new JSONObject(reMap).toString());
        return new JSONObject(reMap);
    }
    private JSONObject third(JSONObject param) {
        JSONObject result = null;
        DataExchangeDetailDto detailDto = callGXB(PRODUCT_ID_2,param);
        if(detailDto!=null ) {
            String  detailDtoData = detailDto.getData();
            JSONObject  params = JSONObject.parseObject(detailDtoData);
            Map<String,Object> paramMap = (Map<String,Object>)params;
            boolean flag = (boolean) paramMap.get("success");
            String msg = (String) paramMap.get("msg");
            if(!flag){
                Map para = new HashMap();
                para.put("level",LEVEL_E);
                para.put("recommend","高风险客户建议拒绝");
                result = new JSONObject(para);
            }
        }else{
            Map para = new HashMap();
            para.put("level",LEVEL_D);
            para.put("recommend","第三方数据接口故障,建议人工");
            result = new JSONObject(para);
        }
       return result;

    }

    private JSONObject netleng(JSONObject param) {
        JSONObject result = null;
        DataExchangeDetailDto detailDto = callGXB(PRODUCT_ID_5,param);
        if(detailDto!=null ) {
            String  detailDtoData = detailDto.getData();
            JSONObject  params = JSONObject.parseObject(detailDtoData);
            Map<String,Object> paramMap = (Map<String,Object>)params;
            Integer duration = (Integer) paramMap.get("duration");
            String msg = (String) paramMap.get("msg");
            if(duration<2){
                Map para = new HashMap();
                para.put("level",LEVEL_E);
                para.put("recommend","高风险客户建议拒绝");
                result = new JSONObject(para);
            }
        }else{
            Map para = new HashMap();
            para.put("level",LEVEL_D);
            para.put("recommend","第三方数据接口故障,建议人工");
            result = new JSONObject(para);
        }
        return result;
    }

    private DataExchangeDetailDto callGXB(Integer productId, JSONObject params) {

        long l1 = System.currentTimeMillis();
        List<DataExchangeDetailDto> resultlist = null;
        DataExchangeDetailDto detailDto=null;
        try {
//            JSONObject param = doParam();
            //创建交易
            String requestId = client.createDataExchangeRequest(productId, params);
            //获取结果
            DataExchangeDto result = client.getResult(requestId);
            if(result!=null &&  DataExchangeStatusEnum.FINISHED.equals(result.getStatus())) {
                resultlist = result.getDatasources();
                detailDto = resultlist.get(0);
                log.info("GXB：detailDto=" + JSONObject.toJSONString(detailDto));
            }else{
                log.info("GXB：result =" + JSONObject.toJSONString(result));
            }
        } catch (GxbApiException e) {
            log.error(e.getMessage(), e);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            long l2 = System.currentTimeMillis();
            log.info("总耗时：" + (l2 - l1) + "ms");
            return detailDto;

        }
    }

    public static JSONObject doParam(){
        Map para = new HashMap();
        para.put("name","黄潇振");
        para.put("idcard","450703199410121852");
        para.put("mobile","18376695004");
        //商户自己组织参数
        return  new JSONObject(para);
    }

    public static void main(String[] args) {
//        String testData="{\"name\":\"黄潇振\",\"idcard\":\"450703199410121852\",\"mobile\":\"18376695003\"}";
//        JSONObject  params = JSONObject.parseObject(testData);
//        Map<String,Object> paramMap = (Map<String,Object>)params;
//        初始化--初始化执行一次就够了
//        init();
//        long l1 = System.currentTimeMillis();
//        try {
//            JSONObject param = doParam();
//            //创建交易
//            String requestId = client.createDataExchangeRequest(5, param);
//            //获取结果
//            DataExchangeDto result = client.getResult(requestId);
//            System.out.println(result.getStatus());
//            List<DataExchangeDetailDto> resultlist = result.getDatasources();
//            if(resultlist!=null){
//                for (int i=0;i<resultlist.size();i++){
//                    DataExchangeDetailDto detailDto = resultlist.get(i);
//                    System.out.println(detailDto.getStatus());
//                    System.out.println(detailDto.getComment());
//                    System.out.println(detailDto.getData());
//                }
//            }
//            log.info(JSON.toJSONString(result));
//        } catch (GxbApiException e) {
//            log.error(e.getMessage(), e);
//        } catch (Exception e) {
//            log.error(e.getMessage(), e);
//        } finally {
//            long l2 = System.currentTimeMillis();
//            log.info("总耗时：" + (l2 - l1) + "ms");
//        }
    }
}
