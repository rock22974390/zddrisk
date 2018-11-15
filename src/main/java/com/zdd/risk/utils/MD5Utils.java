package com.zdd.risk.utils;

import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class MD5Utils
{
   
    private static final String HEX_NUMS_STR = "0123456789ABCDEF";
    private static final Integer SALT_LENGTH = Integer.valueOf(12);
    private static final String KEY_MD5 = "MD5";
    private static final String[] STR_DIGITS = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

    public static byte[] hexStringToByte(String hex)
    {
        int len = hex.length() / 2;
        byte[] result = new byte[len];
        char[] hexChars = hex.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i]
                    = (byte)("0123456789ABCDEF".indexOf(hexChars[pos]) << 4 | "0123456789ABCDEF"
                    .indexOf(hexChars[(pos + 1)]));
        }

        return result;
    }

    public static String byteToHexString(byte[] b)
    {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = new StringBuilder().append('0').append(hex).toString();
            }
            hexString.append(hex.toUpperCase());
        }
        return hexString.toString();
    }

    public static boolean validPassword(String password, String passwordInDb)
            throws NoSuchAlgorithmException, UnsupportedEncodingException
    {
        byte[] pwdInDb = hexStringToByte(passwordInDb);

        byte[] salt = new byte[SALT_LENGTH.intValue()];

        System.arraycopy(pwdInDb, 0, salt, 0, SALT_LENGTH.intValue());

        MessageDigest md = MessageDigest.getInstance("MD5");

        md.update(salt);

        md.update(password.getBytes("UTF-8"));

        byte[] digest = md.digest();

        byte[] digestInDb = new byte[pwdInDb.length - SALT_LENGTH.intValue()];

        System.arraycopy(pwdInDb, SALT_LENGTH.intValue(), digestInDb, 0, digestInDb.length);

        return Arrays.equals(digest, digestInDb);
    }

    public static String getEncryptedPwd(String password)
            throws NoSuchAlgorithmException, UnsupportedEncodingException
    {
        byte[] pwd = null;

        SecureRandom random = new SecureRandom();

        byte[] salt = new byte[SALT_LENGTH.intValue()];

        random.nextBytes(salt);

        MessageDigest md = null;

        md = MessageDigest.getInstance("MD5");

        md.update(salt);

        md.update(password.getBytes("UTF-8"));

        byte[] digest = md.digest();

        pwd = new byte[digest.length + SALT_LENGTH.intValue()];

        System.arraycopy(salt, 0, pwd, 0, SALT_LENGTH.intValue());

        System.arraycopy(digest, 0, pwd, SALT_LENGTH.intValue(), digest.length);

        return byteToHexString(pwd);
    }

    public static String encryptMD5(byte[] data)
    {
        try
        {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(data);
            return byteToString(md5.digest()); } catch (Exception e) {
        }
        return "";
    }

    private static String byteToArrayString(byte bByte)
    {
        int iRet = bByte;
        if (iRet < 0) {
            iRet += 256;
        }
        int iD1 = iRet / 16;
        int iD2 = iRet % 16;
        return new StringBuilder().append(STR_DIGITS[iD1]).append(STR_DIGITS[iD2]).toString();
    }

    private static String byteToString(byte[] bByte)
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bByte.length; i++) {
            sb.append(byteToArrayString(bByte[i]));
        }
        return sb.toString();
    }

    public static String encryptMD5(String inputText)
    {
        return encrypt(inputText, "MD5");
    }

    private static String encrypt(String inputText, String algorithmName)
    {
        if ((inputText == null) || ("".equals(inputText.trim()))) {
        	 return "";
        }
        if ((algorithmName == null) || ("".equals(algorithmName.trim())))
            algorithmName = "md5";
        try
        {
            MessageDigest m = MessageDigest.getInstance(algorithmName);
            m.update(inputText.getBytes("UTF-8"));
            byte[] s = m.digest();
            return hex(s);
        } catch (NoSuchAlgorithmException e) {
            error("MD5加密异常:"+e);
            return null;
        } catch (UnsupportedEncodingException e) {
            error("MD5加密异常:{}"+e);
        }return null;
    }

    private static String hex(byte[] arr)
    {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < arr.length; i++) {
            sb.append(Integer.toHexString(arr[i] & 0xFF | 0x100).substring(1, 3));
        }

        return sb.toString();
    }

    public static String ecodeByMD5(String originstr)
    {
        String result = null;

        char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

        if (originstr != null)
        {
            try
            {
                MessageDigest md = MessageDigest.getInstance("MD5");

                byte[] source = originstr.getBytes("utf-8");

                md.update(source);

                byte[] tmp = md.digest();

                char[] str = new char[32];

                int i = 0; for (int j = 0; i < 16; i++)
            {
                byte b = tmp[i];

                str[(j++)] = hexDigits[(b >>> 4 & 0xF)];

                str[(j++)] = hexDigits[(b & 0xF)];
            }

                result = new String(str);
            }
            catch (NoSuchAlgorithmException e) {
                return null;
            } catch (UnsupportedEncodingException e) {
                return null;
            }
        }

        return result;
    }

    public static String encryptMd5(String originStr)
    {
        String result = null;
        if (null != originStr) {
            try {
                MessageDigest md = MessageDigest.getInstance("md5");
                byte[] mess = originStr.getBytes("utf-8");
                md.reset();
                byte[] hash = md.digest(mess);
                BASE64Encoder encoder = new BASE64Encoder();
                result = encoder.encode(hash);
            } catch (Exception e) {
                error("encryptMd5 error:"+e);
            }
        }
        return result;
    }
    public static void error(String msg) {
		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "\t: " + msg);
	}

    public static void main(String[] args) {

       String result= MD5Utils.encryptMD5("522627199205170415");
        System.out.println(result);
    }
}