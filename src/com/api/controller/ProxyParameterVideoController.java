package com.api.controller;

import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.api.db.UrlCode;

@Controller
public class ProxyParameterVideoController {
	
	@RequestMapping(value="/video",method=RequestMethod.POST,produces="text/html;charset=UTF-8")
	@ResponseBody
	public String videoJSON(@RequestParam (value="appid", defaultValue="") String appid,@RequestParam (value="videocd", defaultValue="") String videocd) {
		String statustext = "fail";
		int statuscode = 500;
		String data = "";
		HttpGet httpGet = null;
		HttpClient client = HttpClients.createDefault();
		
		UrlCode uc = new UrlCode();		
		try {
			httpGet = new HttpGet();
			httpGet.setURI(new URI(uc.getUrl(appid)+videocd));
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
//		System.out.println(ret);
		return ret;
	}
}
