package com.api.proxy;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

public class UrlPostRequest {

	public static String doPost(String url) throws Exception {
		StringBuffer json = new StringBuffer();
        try {
            //实例一个url和URLConnection
            URL oracle = new URL(url);
            //打开链接
            URLConnection yc = oracle.openConnection();
            //输入流作参数传进InputStreamReader并用BufferedReader接受
            BufferedReader in = new BufferedReader(new InputStreamReader(
                                        yc.getInputStream()));
            String inputLine = null;
            //一直读到空，并设置流编码是UTF8
            while ( (inputLine = in.readLine()) != null) {
                json.append(new String(inputLine.getBytes(),"utf-8"));
            }
            //记得关闭连接
            in.close();
        } catch (Exception e)  {
            e.printStackTrace();
        }
        return json.toString();
	}
}
