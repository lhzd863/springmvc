package com.api.db;

import java.util.List;

public interface UrlCodeMapper {

	public List getUrl(UrlCodeParameter up);
	public List getPasswd(UrlCodeParameter up);
}
