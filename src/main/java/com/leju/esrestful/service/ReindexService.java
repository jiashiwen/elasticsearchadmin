package com.leju.esrestful.service;

public interface ReindexService {
	public String Reindex(String SourceName, String TargetName);
	public boolean ReindexByRelation(String source,String target,String relationship);

}
