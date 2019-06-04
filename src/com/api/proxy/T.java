package com.api.proxy;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.api.db.UrlCode;
import com.api.db.VerifyToken;
import com.fasterxml.jackson.databind.ObjectMapper;


public class T {

	public static void main(String[] args) {
          T t= new T();
          t.testaudio();
		
	}
	
	public void testhttp() {

		String json = "{\"exp\":\"1595482650719371100\",\"iat\":\"lhzd863\"}";
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost("http://192.168.1.189:6781/api/token");
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-Type", "application/json");
		StringEntity entity = new StringEntity(json, "UTF-8");
		httpPost.setEntity(entity);
		CloseableHttpResponse response = null;
		try {
			response = httpclient.execute(httpPost);
			StatusLine status = response.getStatusLine();
			int state = status.getStatusCode();
//			if (state == HttpStatus.SC_OK) {
//				HttpEntity responseEntity = response.getEntity();
//				String jsonString = EntityUtils.toString(responseEntity);
//				System.out.println(jsonString);
//			}
			HttpEntity responseEntity = response.getEntity();
			String jsonString = EntityUtils.toString(responseEntity);
			System.out.println(jsonString);
			System.out.println(state);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
//		
//        System.out.println(str);
	}
	
	public void testVerifyPasswd() {
		VerifyToken vup = new VerifyToken();
		System.out.println(vup.verify("lhzd863","lhzd863"));
	}
	
	public void testW() {
		String statustext = "fail";
		String statuscode = "500";
		String data = "";
		
		VerifyToken vt = new VerifyToken();
		String strm = vt.verifyAccessToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOiIxOTE1MjU1NDQ3Nzg3NzcyNjg1IiwiaWF0IjoiMTU1NTI1NTQ0Nzc4Nzc3MjY4NSIsInVzciI6ImxoemQ4NjMifQ.Q8-WaeYoYCf6ojkBo-qZvrCg3AClFfqg16YT6ri3fGg", "00001");

		ObjectMapper mapper = new ObjectMapper();
		Map m=null;
		try {
			m = mapper.readValue(strm, Map.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(!m.get("status").equals("ok")) {
//			return "[{\"statustext\":\""+statustext+"\",\"statuscode\":\""+statuscode+"\",\"data\":\""+data+"\"}]";
		}else {
			statustext = "ok";
			statuscode = "200";
		}
		
		UrlCode uc = new UrlCode();		
		try {
			data = UrlPostRequest.doPost(uc.getUrl("00001"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Map m1=null;
		ObjectMapper mapper1 = new ObjectMapper();
		data = data.replaceAll("\\[", "").replaceAll("\\]", "");
		try {
			m1 = mapper1.readValue(data, Map.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String data1 = "{\"city\":\""+m1.get("city")+"\",\"cityid\":\""+m1.get("cityid")+"\",\"tmp\":\""+m1.get("tmp")+"\",\"time\":\""+m1.get("time")+"\"}";
		System.out.println(data);
		String ret = "[{\"statustext\":\""+statustext+"\",\"statuscode\":\""+statuscode+"\",\"data\":["+data1+"]}]";
		System.out.println(ret);
//		return ret;
	}
	
	public void testaudio() {
		String statustext = "fail";
		int statuscode = 500;
		String data = "";
		HttpGet httpGet = null;
		HttpClient client = HttpClients.createDefault();
		
		UrlCode uc = new UrlCode();		
		try {
			httpGet = new HttpGet();
			System.out.println(uc.getUrl("u00001")+"ldh");
			httpGet.setURI(new URI(uc.getUrl("u00001")+"ldh"));
			HttpResponse httpResponse = client.execute(httpGet);
			HttpEntity entity = httpResponse.getEntity();
			byte[] body = EntityUtils.toByteArray(entity);
			StatusLine sL = httpResponse.getStatusLine();
			statuscode = sL.getStatusCode();
			if (statuscode == 200) {
				data = new String(body, "utf-8");
				entity.consumeContent();
				statustext = "ok";
			} else {
				statustext = "fail";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		String ret = "[{\"statustext\":\""+statustext+"\",\"statuscode\":\""+statuscode+"\",\"data\":"+data+"}]";
		System.out.println(ret);
	}

}
