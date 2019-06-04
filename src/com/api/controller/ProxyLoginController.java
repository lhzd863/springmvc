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
import com.api.db.VerifyToken;
import com.api.proxy.UrlPostRequest;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.sf.json.JSONObject;

@Controller
public class ProxyLoginController {
	
	@RequestMapping(value="/login",method=RequestMethod.POST,produces="text/html;charset=UTF-8")
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
		
		String json = "{\"user\":\""+(String) m.get("user")+"\",\"passwd\":\""+(String) m.get("passwd")+"\"}";
		VerifyToken vup = new VerifyToken();
		String accesstoken = vup.verify((String) m.get("user"),(String) m.get("passwd"));

        String str="{\"accesstoken\":\""+accesstoken+"\"}";
//		System.out.println("["+str+"]");
		return "["+str+"]";
	}
}
