package com.api.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.api.db.UrlCode;
import com.api.db.VerifyToken;
import com.api.proxy.UrlPostRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class ProxyParameterDataWeatherController {
	
	@RequestMapping(value="/data/para",method=RequestMethod.POST,produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getDataJSON(@RequestParam (value="accesstoken", defaultValue="") String accesstoken,@RequestParam (value="method", defaultValue="") String method,@RequestParam (value="apid", defaultValue="") String apid) {
		String statustext = "fail";
		String statuscode = "500";
		String data = "";
		
		VerifyToken vt = new VerifyToken();
		String strm = vt.verifyAccessToken(accesstoken, apid);
		ObjectMapper mapper = new ObjectMapper();
		Map m=null;
		try {
			m = mapper.readValue(strm, Map.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(!m.get("status").equals("ok")) {
			return "[{\"statustext\":\""+statustext+"\",\"statuscode\":\""+statuscode+"\",\"data\":\""+data+"\"}]";
		}else {
			statustext = "ok";
			statuscode = "200";
		}
		
		UrlCode uc = new UrlCode();		
		try {
			data = UrlPostRequest.doPost(uc.getUrl(apid));
		} catch (Exception e) {
			e.printStackTrace();
		}

		String ret = "[{\"statustext\":\""+statustext+"\",\"statuscode\":\""+statuscode+"\",\"data\":"+data+"}]";
//		System.out.println(ret);
		return ret;
	}
}
