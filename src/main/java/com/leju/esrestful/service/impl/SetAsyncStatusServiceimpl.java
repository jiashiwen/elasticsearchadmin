package com.leju.esrestful.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.leju.com.entities.StatusEntity;
import com.leju.esrestful.configuration.AsyncStatusConfiguration;
import com.leju.esrestful.service.SetAsyncStatusService;

@Service
public class SetAsyncStatusServiceimpl implements SetAsyncStatusService{

	@Autowired
	private AsyncStatusConfiguration as;
	
	@Override
	@Async
	public void SetAsyncStatus(String source,String target) {
		// TODO Auto-generated method stub
		//开始运行时，执行插入运行状态
		StatusEntity se = new StatusEntity();
		se.setSource(source);
		se.setTarget(target);
		se.setStatus(as.getExecuting());
		as.addStatusmap(se.getTarget(), se);
		
		//sleep模拟执行过程
		try {
			Thread.sleep(15000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//运行介绍，执行插入成功状态
		se.setSource(source);
		se.setTarget(target);
		se.setStatus(as.getSuccess());
		as.addStatusmap(se.getTarget(), se);
	}

}
