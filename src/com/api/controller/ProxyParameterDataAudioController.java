package com.api.controller;

import java.io.IOException;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
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
public class ProxyParameterDataAudioController {
	
	@RequestMapping(value="/para/audio",method=RequestMethod.POST,produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getDataJSON(@RequestParam (value="accesstoken", defaultValue="") String accesstoken,@RequestParam (value="para", defaultValue="") String para,@RequestParam (value="keyid", defaultValue="") String keyid,@RequestParam (value="apid", defaultValue="") String apid) {
		
		String statustext = "fail";
		String statuscode = "500";
		String data = "";
		VerifyToken vt = new VerifyToken();
		String strm = vt.verifyAccessToken(accesstoken, keyid);
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
		String json = "{\"audiotype\":\""+para+"\"}";

		HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(uc.getUrl(apid));
        
        try {
			StringEntity s = new StringEntity(json);
			s.setContentEncoding("UTF-8");
			s.setContentType("application/json");
			post.setEntity(s);
			HttpResponse res = client.execute(post);
			System.out.println(res.getStatusLine().getStatusCode());
			HttpEntity entity = res.getEntity();
            data = EntityUtils.toString(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		

		String ret = "[{\"statustext\":\""+statustext+"\",\"statuscode\":\""+statuscode+"\",\"data\":"+data+"}]";
//		System.out.println(ret);
		return ret;
	}
}
