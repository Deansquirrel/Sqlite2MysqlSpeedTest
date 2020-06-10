package com.yuansong.test.sqlite2mysqlspeed.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.yuansong.test.sqlite2mysqlspeed.repository.Do.RowVal;
import com.yuansong.tools.common.CommonTool;

@Service
public class TempDataService {
	
	public RowVal getTestData() {
		CommonTool ct = new CommonTool();
		RowVal d = new RowVal();
		d.setId(ct.UUID());
		d.setVal0(ct.UUID());
		d.setVal1(ct.UUID());
		d.setVal2(ct.UUID());
		d.setVal3(ct.UUID());
		d.setVal4(ct.UUID());
		d.setVal5(ct.UUID());
		d.setVal6(ct.UUID());
		d.setVal7(ct.UUID());
		d.setVal8(ct.UUID());
		d.setVal9(ct.UUID());
		return d;
	}
	
	public List<RowVal> getTestDataList(int c){
		List<RowVal> list = new ArrayList<RowVal>();
		if(c > 0) {
			for(int i = 0; i < c; i++) {
				list.add(this.getTestData());
			}
		}
		return list;
	}

}
