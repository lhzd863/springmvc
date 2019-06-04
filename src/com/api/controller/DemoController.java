package com.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class DemoController {

	@RequestMapping(value="/api/get")
	@ResponseBody
	public String getShopInJSON(@RequestParam ("access_token") String token) {

		String str = "[{\"user\":\"lhzd863\",\"password\":\"abcd123\",\"token\":\""+token+"\"}]";
		System.out.println(str);
		return str;
	}
}
