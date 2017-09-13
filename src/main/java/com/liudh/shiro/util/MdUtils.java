package com.liudh.shiro.util;

import java.security.MessageDigest;

/**
 * MessageDigest算法
 *
 * @author ZhangQiang
 * @version 2009-2-24 17:32:39
 */
public class MdUtils
{
    public static String md5(String data) throws Exception
    {
        byte[] btInput = data.getBytes("utf-8");
        return md(btInput, "MD5");
    }

    public static String sha1(String data) throws Exception
    {
        byte[] btInput = data.getBytes("utf-8");
        return md(btInput, "SHA-1");
    }

    public static String sha256(String data) throws Exception
    {
        byte[] btInput = data.getBytes("utf-8");
        return md(btInput, "SHA-256");
    }

    public static String md(byte[] data, String alg) throws Exception
    {
        // 获得MD5摘要算法的 MessageDigest 对象
        MessageDigest mdInst = MessageDigest.getInstance(alg);
        // 使用指定的字节更新摘要
        mdInst.update(data);
        // 获得密文
        byte[] md = mdInst.digest();
        // 把密文转换成十六进制的字符串形式
        return ConvertUtils.bytes2Hex(md);
    }

    public static void main(String[] args) throws Exception
    {
//        System.out.println(MdUtils.md(FileUtils.readFileToByteArray(new File("c:/pri.pem")),"MD5"));
//        System.out.println(MdUtils.md(FileUtils.readFileToByteArray(new File("c:/pub.pem")),"MD5"));
//        System.out.println(MdUtils.sha1("ll"));
//        System.out.println(MdUtils.sha256("ll"));
        System.out.println(md5("1"));
    }
}
