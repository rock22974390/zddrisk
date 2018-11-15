package com.zdd.risk.api;

import com.alibaba.fastjson.JSONObject;
import com.zdd.risk.utils.HttpUtils;
import com.zdd.risk.utils.MD5Utils;
import com.zdd.risk.utils.SecurityUtil;
import com.zdd.risk.utils.rsa.RsaCodingUtil;
import com.zdd.risk.service.IConfigService;
import io.swagger.annotations.ApiOperation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * @author 租无忧科技有限公司
 * @createtime 2016年9月26日下午8:58:24
 */
@RestController
@RequestMapping("/risk/model")
public class CreditRatingController{

	private static final Logger log = LoggerFactory.getLogger(CreditRatingController.class);

	@Value("${member.id}")
	private String memberId;
	@Value("${terminal.id}")
	private String terminalId;
	@Value("${pfx.name}")
	private String pfxName;
	@Value("${pfx.pwd}")
	private String pfxPwd;
	@Value("${radar.url}")
	private String radarUrl;

	@ApiOperation("检查借款信息接口")
	@RequestMapping(value = "/xinyan")
	public JSONObject getXinyan(@RequestBody String param){
		JSONObject inputParams = JSONObject.parseObject(param);

		/** 1、 商户号 **/
		String member_id = memberId;
		/** 2、终端号 **/
		String terminal_id =terminalId;
		/** 3、请求地址 **/
		String url;
		String PostString = null;
		Map<String, String> headers = new HashMap<String, String>();


		String urlType = "ZX-RadarUrl";
		String id_no = inputParams.getString("id_no");
		String id_name = inputParams.getString("id_name");
		String phone_no = inputParams.getString("phone_no");
		String bankcard_no = inputParams.getString("bankcard_no");
		String versions = inputParams.getString("versions");

		log(" 原始数据:id_no:" + id_no + ",id_name:" + id_name + ",phone_no:" + phone_no + ",bankcard_no:" + bankcard_no);

		id_no = MD5Utils.encryptMD5(id_no.trim());
		id_name = MD5Utils.encryptMD5(id_name.trim());
		bankcard_no = MD5Utils.encryptMD5(bankcard_no.trim());
		phone_no = MD5Utils.encryptMD5(phone_no.trim());

		log("32位小写MD5加密后数据:id_no:" + id_no + ",id_name:" + id_name + ",phone_no:" + phone_no + ",bankcard_no:"
				+ bankcard_no);

		String trade_date = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());// 订单日期
		String trans_id = "" + System.currentTimeMillis();// 商户订单号

		String XmlOrJson = "";
		/** 组装参数 **/
		Map ArrayData = new HashMap<Object, Object>();
		ArrayData.put("member_id", member_id);
		ArrayData.put("terminal_id", terminal_id);
		ArrayData.put("trade_date", trade_date);
		ArrayData.put("trans_id", trans_id);
		ArrayData.put("phone_no", phone_no);
		ArrayData.put("bankcard_no", bankcard_no);
		ArrayData.put("versions", versions);
		ArrayData.put("industry_type", "A1");// 参照文档传自己公司对应的行业参数
		ArrayData.put("id_no", id_no);
        ArrayData.put("id_name", id_name);

		JSONObject jsonObjectFromMap = new JSONObject(ArrayData);
		XmlOrJson = jsonObjectFromMap.toString();
		log("====请求明文:" + XmlOrJson);

		/** base64 编码 **/
		String base64str = null;
		try {
			base64str = SecurityUtil.Base64Encode(XmlOrJson);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		log("base64str:" + base64str);
		/** rsa加密 **/
		String pfxpath = pfxName;// 商户私钥

		File pfxfile = new File(pfxpath);
		if (!pfxfile.exists()) {
			log("私钥文件不存在！");
			throw new RuntimeException("私钥文件不存在！");
		}
		String pfxpwd = pfxPwd;// 私钥密码

		String data_content = RsaCodingUtil.encryptByPriPfxFile(base64str, pfxpath, pfxpwd);// 加密数据
		log("====加密串:" + data_content);

		url = radarUrl;
		log("url:" + url);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("member_id", member_id);
		params.put("terminal_id", terminal_id);
		params.put("data_type", "json");
		params.put("data_content", data_content);

		PostString = HttpUtils.doPostByForm(url, headers, params);
		log("请求返回：" + PostString);

		/** ================处理返回结果============= **/
		if (PostString.isEmpty()) {// 判断参数是否为空
			log("=====返回数据为空");
			throw new RuntimeException("返回数据为空");
		}
		return null;
	}

	public void log(String msg) {
		log.info(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "\t: " + msg);
	}
}
