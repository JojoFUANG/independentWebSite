package com.jojo.independentwebsite.utils;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {

    public static String str2MD5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {

        str += "@fdki11234568#fjen";
        byte[] digest = null;
        MessageDigest md5 = MessageDigest.getInstance("md5");
        digest = md5.digest(str.getBytes("utf-8"));

        //16表示转换为16进制
        String md5str = new BigInteger(1,digest).toString(16);
        return md5str;
    }

    public static boolean checkPassword(String psw,String pswInDB) throws Exception {
        return str2MD5(psw).equals(pswInDB);
    }

}
