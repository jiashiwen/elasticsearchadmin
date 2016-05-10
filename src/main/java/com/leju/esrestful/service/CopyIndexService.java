package com.leju.esrestful.service;

import java.net.UnknownHostException;

public interface CopyIndexService {
	public  Boolean CopyIndexMetadata(String source,String target);
	public  Boolean CopyIndex(String source,String target) ;
	public  void CopyIndexByQueryDsl(String source,String target,String DSL);
}
