package com.api.db;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;

import com.fasterxml.jackson.databind.ObjectMapper;


public class VerifyToken {

	public UrlCodeParameter ur ;
    public List ret() {
		SqlSession session = new DatasourceMybatisSqlSession("ds-meta.xml").getSqlSession();
		List datalst = null;
		try {
			datalst = session.getMapper(UrlCodeMapper.class).getPasswd(ur);
		} catch (Exception e) {
			session.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return datalst;
	}
    
    public String verify(String user,String passwd) {
    	String userid = "";
    	String pwd = "";
    	String accesstoken = "";
		try {
			ur = new UrlCodeParameter();
			ur.setUser(user);
			List list = ret();
			UserBean ucb = (UserBean)list.get(0);
			userid = ucb.getUserid();
			pwd = ucb.getPasswd();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(passwd!=null&&passwd.length()>0&&pwd.equals(passwd)) {
			ObjectMapper mapper = new ObjectMapper();
			Map m=null;
			try {
				String token = accessToken(userid,user);	
				m = mapper.readValue(token, Map.class);
				accesstoken = (String)((Map) m.get("Data")).get("accesstoken");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return accesstoken;
		}
		return "";
	}
    
    public String accessToken(String userid,String username) {
        UrlCode uc = new UrlCode();
        long curns = System.currentTimeMillis();
        long exp = curns + 24*3600;
		String json = "{\"period\":\""+24+"\",\"key\":\""+userid+"\",\"usr\":\""+username+"\"}";
		HttpClient client = HttpClientBuilder.create().build();
//		System.out.println(uc.getUrl("00002")+json);
        HttpPost post = new HttpPost(uc.getUrl("00002"));
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
//        System.out.println(str);
        return str;
    }
    
    public String verifyAccessToken(String accesstoken,String key) {
        UrlCode uc = new UrlCode();
        long curns = System.currentTimeMillis();
        long exp = curns + 24*3600;
		String json = "{\"accesstoken\":\""+accesstoken+"\",\"key\":\""+key+"\"}";

		HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(uc.getUrl("00003"));
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
//        System.out.println(str);
        return str;
    }
}
