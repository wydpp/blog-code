package com.dpp;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

public class HttpClientTest {

    public static void main(String[] args) throws Exception {
        String url = "http://127.0.0.1:8081/query/common/test";
        Map<String, String> header = new HashMap<>();
        header.put("\n", "123");
        String result = sendGetRequest(url, "GET", header);
        System.out.println(result);
    }

    public static String sendGetRequest(String urlParam, String requestType, Map<String, String> header) {

        URLConnection con = null;

        BufferedReader buffer = null;
        StringBuffer resultBuffer = null;

        try {
            URL url = new URL(urlParam);
            //得到连接对象
            con = (HttpURLConnection) url.openConnection();
            //设置请求类型
            //con.setRequestMethod(requestType);
            //设置请求需要返回的数据类型和字符集类型
            con.setRequestProperty("Content-Type", "application/json;charset=GBK");
            for (Map.Entry<String, String> entry : header.entrySet()) {
                con.setRequestProperty(entry.getKey(), entry.getValue());
            }
            //允许写出
            con.setDoOutput(true);
            //允许读入
            con.setDoInput(true);
            //不使用缓存
            con.setUseCaches(false);
            //得到响应流
            InputStream inputStream = con.getInputStream();
            //将响应流转换成字符串
            resultBuffer = new StringBuffer();
            String line;
            buffer = new BufferedReader(new InputStreamReader(inputStream, "GBK"));
            while ((line = buffer.readLine()) != null) {
                resultBuffer.append(line);
            }
            return resultBuffer.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


}
