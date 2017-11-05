package com.liudh.shiro.util;

import com.csvreader.CsvWriter;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/***
 *
 *
 * 描    述：
 *
 * 创 建 者： liudh
 * 创建时间： 2017/11/3 17:57
 * 创建描述：
 *
 * 修 改 者：  
 * 修改时间： 
 * 修改描述： 
 *
 * 审 核 者：
 * 审核时间：
 * 审核描述：
 *
 */
public class CsvUtil {


    public static void getInfo(HttpServletResponse response){
        try {
            String[] headers = {"姓名","年龄","编号","性别"};
            // 创建CSV写对象
            List<String[]> ls=new ArrayList<String[]>();
            for (int i = 0; i <100; i++) {
                String[] str = new String[headers.length];
                str[0] = "小帅";
                str[1] = "12";
                str[2] = "100";
                str[3] = "男";
                ls.add(str);
            }
            //写入临时文件
            File tempFile = File.createTempFile("vehicle", ".csv");
            CsvWriter csvWriter = new CsvWriter(tempFile.getCanonicalPath(),',', Charset.forName("UTF-8"));
            // 写表头
            long s= System.currentTimeMillis();
            System.err.println();
            csvWriter.writeRecord(headers);
            for (String[] l : ls) {
                csvWriter.write(l[0]);
                csvWriter.write(l[1]);
                csvWriter.write(l[2]);
                csvWriter.write(l[3]);
                csvWriter.endRecord();
            }
            csvWriter.close();
            long e=System.currentTimeMillis();

            System.err.println(e-s);

            /**
             * 写入csv结束，写出流
             */
            byte[] buffer = null;
            File fileLoad = new File(tempFile.getCanonicalPath());
            FileInputStream fis = new FileInputStream(fileLoad);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
            System.out.println(buffer);

//            OutputStream out = response.getOutputStream();
//            byte[] b = new byte[1024];
//            File fileLoad = new File(tempFile.getCanonicalPath());
//            response.reset();
//            response.setContentType("application/csv");
//            response.setHeader("content-disposition", "attachment; filename=vehicleModel.csv");
//            long fileLength = fileLoad.length();
//            String length1 = String.valueOf(fileLength);
//            response.setHeader("Content_Length", length1);
//            java.io.FileInputStream in = new java.io.FileInputStream(fileLoad);
//            int n;
//            while ((n = in.read(b)) != -1) {
//                out.write(b, 0, n); //每次写入out1024字节
//            }
//            in.close();
//            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
