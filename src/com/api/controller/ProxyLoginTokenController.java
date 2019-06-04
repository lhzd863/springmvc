package com.api.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.api.db.UrlCode;
import com.api.proxy.UrlPostRequest;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.sf.json.JSONObject;

@Controller
public class ProxyLoginTokenController {
	
	@RequestMapping(value="/login/token",method=RequestMethod.POST,produces="text/html;charset=UTF-8")
	@ResponseBody
	public String tokenJSON(@RequestBody String jsonData) {

		ObjectMapper mapper = new ObjectMapper();
		Map m=null;
		try {
			m = mapper.readValue(jsonData, Map.class);
		} catch (IOException e) {
			e.printStackTrace();
		}

		String id = (String) m.get("apid");
		UrlCode uc = new UrlCode();
		
		String json = "{\"period\":\""+(String) m.get("period")+"\",\"key\":\""+(String) m.get("key")+"\",\"usr\":\""+(String) m.get("usr")+"\"}";
		HttpClient client = HttpClientBuilder.create().build();
//		System.out.println(uc.getUrl(id));
        HttpPost post = new HttpPost(uc.getUrl(id));
        String str="";
        
        try {
			StringEntity s = new StringEntity(json);
			s.setContentEncoding("UTF-8");
			s.setContentType("application/json");
			post.setEntity(s);
			HttpResponse res = client.execute(post);

			System.out.println(res.getStatusLine().getStatusCode());
			HttpEntity entity = res.getEntity();
            str = EntityUtils.toString(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
        ObjectMapper mapper1 = new ObjectMapper();
		Map m1=null;
		try {
			m1 = mapper1.readValue(str, Map.class);
			String accesstoken = (String)((Map) m1.get("Data")).get("accesstoken");
			str="{\"accesstoken\":\""+accesstoken+"\"}";
		} catch (IOException e) {
			e.printStackTrace();
		}
//		System.out.println("["+str+"]");
		return "["+str+"]";
	}
}
