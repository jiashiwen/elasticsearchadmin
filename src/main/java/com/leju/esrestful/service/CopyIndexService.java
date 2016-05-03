package com.leju.esrestful.service;

public interface CopyIndexService {
	public  Boolean CopyIndexMetadata(String source,String target);
	public  Boolean CopyIndex(String source,String target);
	public Boolean CopyIndexByQueryDsl(String source, String target, String DSL);
}
