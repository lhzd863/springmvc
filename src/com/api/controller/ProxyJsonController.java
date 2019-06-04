package com.api.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
public class ProxyJsonController {
	
	@RequestMapping(value="/weather/post",method=RequestMethod.POST,produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getWeatherJSON(@RequestBody String jsonData) {

		ObjectMapper mapper = new ObjectMapper();
		Map m=null;
		try {
			m = mapper.readValue(jsonData, Map.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
//		System.out.println(m.get("username"));
		Map<String, Object> headers = new HashMap<>();
		headers.put("content-type", "application/json");
		
		HashMap<String,String> hm = new HashMap<String,String>();
		hm.put("exp", (String) m.get("exp"));
		hm.put("iat", (String) m.get("iat"));
		hm.put("key", (String) m.get("key"));
		
		String id = (String) m.get("apid");
		UrlCode uc = new UrlCode();
		
		String str="";
		try {
			str = UrlPostRequest.doPost(uc.getUrl(id));
		} catch (Exception e) {
			e.printStackTrace();
		}
//		System.out.println(str);
		return str;
	}
}
