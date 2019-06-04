package com.api.db;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class DatasourceMybatisSqlSession {
	
	public static SqlSession sqlSession=null;
	public static SqlSessionFactory sqlSessionFactory=null;
	public DatasourceMybatisSqlSession(String resource){
		
		Reader reader=null;
		try {
			reader = Resources.getResourceAsReader(resource);
		} catch (IOException e) {
			e.printStackTrace();
		}
		sqlSessionFactory=new SqlSessionFactoryBuilder().build(reader);
	}
	public  SqlSession getSqlSession(){
		sqlSession=sqlSessionFactory.openSession();
		return sqlSession;
	}
	public  SqlSession getSqlSession(Class cla){
		sqlSessionFactory.getConfiguration().addMapper(cla);
		sqlSession=sqlSessionFactory.openSession();
		return sqlSession;
	}
}
