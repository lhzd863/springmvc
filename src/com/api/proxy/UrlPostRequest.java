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
            //ʵ��һ��url��URLConnection
            URL oracle = new URL(url);
            //������
            URLConnection yc = oracle.openConnection();
            //����������������InputStreamReader����BufferedReader����
            BufferedReader in = new BufferedReader(new InputStreamReader(
                                        yc.getInputStream()));
            String inputLine = null;
            //һֱ�����գ���������������UTF8
            while ( (inputLine = in.readLine()) != null) {
                json.append(new String(inputLine.getBytes(),"utf-8"));
            }
            //�ǵùر�����
            in.close();
        } catch (Exception e)  {
            e.printStackTrace();
        }
        return json.toString();
	}
}
