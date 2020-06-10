package com.yuansong.test.sqlite2mysqlspeed.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuansong.test.sqlite2mysqlspeed.repository.TabRepository;
import com.yuansong.test.sqlite2mysqlspeed.repository.Do.RowVal;

@Service
public class TabService {
	
	@Autowired
	private TabRepository tabRepository;
	
	@Transactional
	public void addList(String tabName, List<RowVal> list) {
//		this.tabRepository.clear(tabName);
		for(RowVal data : list) {
			this.tabRepository.insert(tabName, data);
		}
	}
}
