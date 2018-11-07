package com.zdd.risk.utils;

import com.alibaba.fastjson.JSONObject;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RSAUtils {
	 /** *//** 
     * 加密算法RSA 
     */  
    public static final String KEY_ALGORITHM = "RSA";  
    /** *//** 
     * RSA最大解密密文大小 
     */  
    private static final int MAX_DECRYPT_BLOCK = 128;
    /** *//**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

  
    /** *//** 
     * <P> 
     * 私钥解密 
     * </p> 
     *  
     * @param encryptedData 已加密数据 
     * @param privateKey 私钥(BASE64编码) 
     * @return 
     * @throws Exception 
     */  
    public static byte[] decryptByPrivateKey(byte[] encryptedData, String privateKey)  
            throws Exception {  
        byte[] keyBytes = Base64Utils.decodeByte(privateKey);  
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);  
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);  
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);  
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());  
        cipher.init(Cipher.DECRYPT_MODE, privateK);  
        int inputLen = encryptedData.length;  
        ByteArrayOutputStream out = new ByteArrayOutputStream();  
        int offSet = 0;  
        byte[] cache;  
        int i = 0;  
        // 对数据分段解密  
        while (inputLen - offSet > 0) {  
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {  
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);  
            } else {  
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);  
            }  
            out.write(cache, 0, cache.length);  
            i++;  
            offSet = i * MAX_DECRYPT_BLOCK;  
        }  
        byte[] decryptedData = out.toByteArray();  
        out.close();  
        return decryptedData;  
    }  

    
    /** *//** 
     * <p> 
     * 公钥加密 
     * </p> 
     *  
     * @param data 源数据 
     * @param publicKey 公钥(BASE64编码) 
     * @return 
     * @throws Exception 
     */  
    public static byte[] encryptByPublicKey(byte[] data, String publicKey)  
            throws Exception {  
        byte[] keyBytes = Base64Utils.decodeByte(publicKey);  
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);  
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);  
        Key publicK = keyFactory.generatePublic(x509KeySpec);  
        // 对数据加密  
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());  
        cipher.init(Cipher.ENCRYPT_MODE, publicK);  
        int inputLen = data.length;  
        ByteArrayOutputStream out = new ByteArrayOutputStream();  
        int offSet = 0;  
        byte[] cache;  
        int i = 0;  
        // 对数据分段加密  
        while (inputLen - offSet > 0) {  
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {  
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);  
            } else {  
                cache = cipher.doFinal(data, offSet, inputLen - offSet);  
            }  
            out.write(cache, 0, cache.length);  
            i++;  
            offSet = i * MAX_ENCRYPT_BLOCK;  
        }  
        byte[] encryptedData = out.toByteArray();  
        out.close();  
        return encryptedData;  
    }  
  
  
    public static void main(String[] args) {
		//加密
		String testData="{\"success\":true,\"errorCode\":\"\",\"errorMessage\":\"\",\"data\":{\"mobile\":\"15240639520\",\"realName\":\"梁文\",\"idcard\":\"452527198703071510\",\"nation\":\"汉\",\"age\":\"36\",\"addressPermanent\":\"广东省清远市英德市 英德市大湾镇上洞村委会31号\",\"idcardValidDate\":\"20170802-20270802\",\"maritalStatus\":\"1\",\"education\":\"4\",\"institution\":\"四川大学 软件学院\",\"addressWork\":\"广东省清远市英德市 英德市大湾镇上洞村委会31号\",\"addressHome\":\"广东省清远市英德市 英德市英城宝晶路石尾区石苟山南侧\",\"payday\":\"15\",\"companyName\":\"北京信息技术有限公司\",\"companyPhone\":\"07757082201\",\"sosContactName\":\"王五\",\"sosContactRelation\":\"1\",\"sosContactPhone\":\"18376558846\",\"sosContactName1\":\"周二\",\"sosContactRelation1\":\"4\",\"sosContactPhone1\":\"13807752929\",\"longitude\":\"110.22323\",\"latitude\":\"22.559668\",\"gpsAddress\":\"中国广东省清远市英德市\",\"gpsHistory\":[{\"address\":\"中国广东省清远市英德市\",\"latitude\":\"24.1472700\",\"longitude\":\"113.3962370\",\"time\":\"2017-06-13T09:36:22.830294946+08:00\"}],\"addressBook\":[{\"name\":\"老四  .\",\"phones\":\"15816127836\"},{\"name\":\"戴文平\",\"phones\":\"13788112608\"},{\"name\":\"梁羊生\",\"phones\":\"13679512203\"},{\"name\":\"梁成/手机\",\"phones\":\"665973\"},{\"name\":\"光辉\",\"phones\":\"15876313129\"},{\"name\":\"范生\",\"phones\":\"18165680120\"},{\"name\":\"仙公\",\"phones\":\"13729619129\"},{\"name\":\"逢师傅\",\"phones\":\"1830761232\"},{\"name\":\"列\",\"phones\":\"1392662521228\"}],\"regOs\":\"Android,5.1,OPPO R9t\",\"regAppVersion\":\"1.4.0\",\"regFrom\":\"2\",\"regIp\":\"117.136.40.131\",\"lastLoginIp\":\"117.136.40.131\",\"bankCard\":[\"127347497474744\",\"127347321298844\"],\"bankMobile\":[\"13988886666\",\"13988886666\"]}}";
		String data= JSONObject.parseObject(testData).getString("data");
		String publicKey="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCXoybMY/5uNy2ZXDUjR4OWK070kb9DAscEPhvo1f0l1+iTrfja+eXarqvqIL/wHUtpprp8N6IBfr0lrJAvYKVrBj+WS/+PmyhMF45A4ZOz8xhGXdQc6hc+L+/ga/3fAZK4zD1DE8mAiAcTvb7mCO3rZOcGJKDXqnQi0nFcAP2o7QIDAQAB";
		try {
			byte[] encryptByPublicKey = RSAUtils.encryptByPublicKey(data.getBytes(),
					 publicKey);
			String encode = Base64Utils.encode(encryptByPublicKey);
			System.out.println(encode);
            System.out.println("***************************");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		//解密
    	try {
    		String s="ghxyz2l3TNPL4fu9ysjm6b3Be6R/4J9eGFXdAGwOWaws/YsxRIacW2q4XejBrDi+9hh7hnkbHcI4l/TmC+BVZIFKHQhaL1kDe/32clWXPvaf+Y0+XPdVnQTqKAeWKyeOMFfyGOpgOZaWmEwzoceZEI6BYDRhC7KncLZrcF+wSnRpILosMmBBS/MzCiWeZl9nN3bIYR3sHv4J5F8jm5A+gVdTVwHhnGa3ItIyp6963LBh0dL3NeynNw4ruR4MbnktckJVnweyan7N2tvYemlRpHjeczi4Na83wn9U6UB/WnQt+I9WPQ5vd1cc5ub3yN2HfDHiGxTOGwWUrlt03yNFYHzxU0oizTbeeEevkBmL87Nao4i2Ts+5Vt5kyLfYIM+TQvDo9U9Ja36ufMYt3HpoYAb+AlTVKJX9XOQbDgM5vgCSuW4SsD5ZnT0d/clFTWewegXE1vRjSFzcqi4oigLhn02OI8EkOoq8WYoqToGoFchHn5hDXe12A63EFw9wkIQ6PenF8HzofGN9ZiUXdbrsSuO+Sw4Z1WCfgJrJEMbGZWiR7eVcAyGOAUT70mvSTze8bPCUmiJBd2n3dQGPnhlMFYYh3VrzVCveHMA4TXHK/PWTv53+hLhfNwoTQo/08nomLUccg5U/+nv6GeLrfE1aK3JUr+jZgh7YNSAxIM3sCOtMev4Fbx5b7Pt42NTKD2giLxKF5ntEFyNQEGE2W2/Gu2nZla0uvZtNw6PG0waOi7qESXqSVlUfJioKmDJu/aZs/qSldP5+ElDhqgekWjFBgl3x3CTcLJQntkMiR41K362+DVuSHj+KJKcRqkqke/OatrEF2TVAVJnbovl0QDPUSQ==";
    		byte[] c=decryptByPrivateKey(Base64Utils.decodeByte(s), "密钥");
    		System.out.println(new String(c));
    	}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
