package com.liudh.shiro.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 类型转换功能类
 *
 * @author ZhangQiang
 * @version 2008-3-5 19:06:52
 */
@SuppressWarnings("unchecked")
public class ConvertUtils
{
    /**
     * 输入流转字节数组
     *
     * @param inputStream InputStream
     * @return byte[]
     * @throws IOException
     */
    public static byte[] inputStream2byte(InputStream inputStream) throws IOException
    {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int n = 0;
        while (-1 != (n = inputStream.read(buffer)))
        {
            outputStream.write(buffer, 0, n);
        }
        return outputStream.toByteArray();
    }

    /**
     * 字节数组转十六进制
     *
     * @param bytes byte[]
     * @return String
     */
    public static String bytes2Hex(byte[] bytes)
    {
        String des = "";
        String tmp;
        for (byte bt : bytes)
        {
            tmp = (Integer.toHexString(bt & 0xFF));
            if (tmp.length() == 1)
            {
                des += "0";
            }
            des += tmp;
        }
        return des;
    }

    /**
     * 十六进制转字节数组
     *
     * @param source 源十六进制字符串
     * @return byte[]
     */
    public static byte[] hex2Bytes(String source)
    {
        byte[] sourceBytes = new byte[source.length() / 2];
        for (int i = 0; i < sourceBytes.length; i++)
        {
            sourceBytes[i] = (byte) Integer.parseInt(source.substring(i * 2, i * 2 + 2), 16);
        }
        return sourceBytes;
    }

    /**
     * 转换IP地址为长整形数字
     *
     * @param ip IP地址
     * @return 返回值
     */
    public static long ipToLong(String ip)
    {
        String[] ips = ip.split(".");
        return 16777216 * Long.parseLong(ips[0]) + 65536 * Long.parseLong(ips[1]) +
                256 * Long.parseLong(ips[2]) + Long.parseLong(ips[3]);
    }

    /**
     * 转换长整型数字为IP地址
     *
     * @param ipLong 长整型数字
     * @return 返回值
     */
    public static String longToIp(long ipLong)
    {
        long mask[] = {0x000000FF, 0x0000FF00, 0x00FF0000, 0xFF000000};
        long num = 0;
        StringBuilder ipInfo = new StringBuilder();
        for (int i = 0; i < 4; i++)
        {
            num = (ipLong & mask[i]) >> (i * 8);
            if (i > 0) ipInfo.insert(0, ".");
            ipInfo.insert(0, Long.toString(num, 10));
        }
        return ipInfo.toString();
    }
}
