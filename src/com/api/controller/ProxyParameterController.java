package com.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.api.db.UrlCode;
import com.api.proxy.UrlPostRequest;

@Controller
public class ProxyParameterController {
	
	@RequestMapping(value="/data/weather/para1",method=RequestMethod.POST,produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getWeatherJSON(@RequestParam (value="access_token", defaultValue="") String token,@RequestParam (value="method", defaultValue="") String method,@RequestParam (value="apid", defaultValue="") String apid) {

		String str = "[{\"user\":\"lhzd863\",\"password\":\"abcd123\",\"token\":\""+token+"\",\"method\":\""+method+"\"}]";
		UrlCode uc = new UrlCode();
		
		try {
			str = UrlPostRequest.doPost(uc.getUrl(apid));
		} catch (Exception e) {
			e.printStackTrace();
		}
//        System.out.println(str);
		return str;
	}
}
