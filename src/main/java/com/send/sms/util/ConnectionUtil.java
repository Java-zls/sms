package com.send.sms.util;

import org.springframework.stereotype.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class ConnectionUtil {
    /**
     * 使用HttpURLConnection方式连接
     * @param soapurl
     * @param soapXML
     * @return
     * @throws IOException
     */
    public String urlConnectionUtil(String soapurl, String soapXML) throws IOException {
        // 第一步：创建服务地址
        // http://172.25.37.31:8080/PeopleSoftService/getPerson.wsdl
        URL url = new URL(soapurl);
        // 第二步：打开一个通向服务地址的连接
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        // 第三步：设置参数
        // 3.1发送方式设置：POST必须大写
        connection.setRequestMethod("POST");
        // 3.2设置数据格式：content-type
        connection.setRequestProperty("content-type", "text/xml;charset=utf-8");
        // 3.3设置输入输出，因为默认新创建的connection没有读写权限，
        connection.setDoInput(true);
        connection.setDoOutput(true);
        // 第四步：组织SOAP数据，发送请求
        // 将信息以流的方式发送出去
        OutputStream os = connection.getOutputStream();
        os.write(soapXML.getBytes());
        StringBuilder sb = new StringBuilder();
        // 第五步：接收服务端响应，打印
        int responseCode = connection.getResponseCode();
        if (200 == responseCode) {// 表示服务端响应成功
            // 获取当前连接请求返回的数据流
            InputStream is = connection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);

            String temp = null;
            while (null != (temp = br.readLine())) {
                sb.append(temp);
            }
            is.close();
            isr.close();
            br.close();
        }
        os.close();
        return sb.toString();
    }

    /**
     * 生成SmsID
     *
     * @return
     */
    public static String getSmsID() {
        String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String num = (Math.random() * 900000) + 100000 + "";
        String smsId = (format + num).replaceAll("-", "").replaceAll(" ", "").replaceAll(":", "").substring(0, 20);
        //System.out.println("smsId = " + smsId);
        return smsId;
    }

    public String getXML(String[] phoneNum, String content) {
        /*String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "<SMS type=\"send\">"+
                "<Message SmsID=\"20130308094612321123\" SendNum=\"100\" RecvNum=\"17631089214\" Content=\"发送短信内容1\"/>" +
                "</SMS>";
        return xml;*/
        String xml1 = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<SMS type=\"send\">\n" + "";
        String xml2 = null;
        List<String> list = new ArrayList<>();
        for (int i = 0; i < phoneNum.length; i++) {
            xml2 = '"' +"<Message " +
                    "SmsID=\"smsId\" " +
                    "SendNum=\"100\" " +
                    "RecvNum=\"phoneNum\" " +
                    "Content=\"content\"/>\n" +'"' ;
            xml2 = xml2.replaceAll("phoneNum", phoneNum[i]).replaceAll("content", content).replaceAll("smsId", getSmsID());
            list.add(xml2);
        }
        String xml3 = "</SMS>";
        String xml = xml1 + list + xml3;
        return xml;
    }
}
