package com.zdd.risk.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zdd.risk.bean.Certification;
import com.zdd.risk.dao.ICertificationDAO;
import org.rosuda.JRI.REXP;
import org.rosuda.JRI.Rengine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * R model
 *
 * @author 租无忧科技有限公司
 * @date 2018-11-16.
 */
@RestController
@RequestMapping("/risk/Rmodel")
public class RmodelController {
    private static final Logger log = LoggerFactory.getLogger(RmodelController.class);
    private static Rengine re = null;

    private static int count = 0;
    @Autowired
    private ICertificationDAO iCertificationDAO;

    static {
        re = new Rengine(new String[] { "--vanilla" }, false, null);

        if (!(re.waitForR())) {
            System.out.println("Cannot load R");
        } else {

            //re.eval("Sys.setlocale('LC_CTYPE','zh_CN.GBK')");
            re.eval("Sys.setlocale('LC_CTYPE','de_DE.utf8')");
            re.eval("load('/usr/src/score_dz.rdata')");
            re.eval("library(dplyr)");
            re.eval("library(dbplyr)");
            re.eval("library(magrittr)");
            re.eval("library(data.table)");
            re.eval("library(DBI)");
            re.eval("library(RJSONIO)");
            re.eval("library(jsonlite)");
            re.eval("library(RSQLite)");
            re.eval("library(log4r)");
        }
    }
//    public static void main(String[] args){
//        Rmodel demo = new Rmodel();
//        demo.callRJava();
//    }

    @RequestMapping(value = "/zdd")
    public JSONObject callRJava(@RequestBody String params){
        log.info("检查借款信息接口入参 params= "+params);
        JSONObject result = new JSONObject();
        String  infos="[{\"zm_jianmian\":1000,\"zmscore\":\"Z1( <600 )\",\"real_mianya_ratio\":0.6,\"loans_score\":199,\"latest_three_month\":2,\"loans_credibility\":90,\"sex\":\"male\",\"orderid\":\"123124124\",\"id_card\":\"450703199410121852\",\"apply_score\":189,\"apply_credibility\":84,\"q> ry_org_count\":7,\"query_sum_count\":13,\"loans_org_count\":5,\"latest_one_month_fail\":25,\"loans_credit_limit\":\"1400\",\"consfin_product_count\":\"5\",\"consfin_org_count\":3,\"consfin_max_limit\":5000}]";

        log.info("java.library.path====="+System.getProperty("java.library.path"));

        String version =re.eval("R.version.string").asString();
        log.info("version="+version);

        re.assign("infos",infos);
        log.info("Rpara infos = "+re.eval("infos").asString());

        REXP x=re.eval("score_dz(infos)");
        if(x!=null && x.getContent()!=null){
            result = JSONObject.parseObject(JSON.toJSONString(x.getContent()));

            Certification record = new Certification();
            record.setCertificationType(String.valueOf(1));
            record.setCertificationItem(infos);
            record.setCertificationResult(JSONObject.toJSONString(result));
            record.setCertificationLimit(new Date());
            record.setFlag(0);
            record.setCreatTime(new Date());

            iCertificationDAO.insert(record);
        }


        log.info("R result="+ JSON.toJSONString(x));
        re.end();
        log.info("end");
        return result;
    }



}
