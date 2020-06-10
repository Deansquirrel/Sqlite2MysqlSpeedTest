package com.yuansong.test.sqlite2mysqlspeed.controller;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.yuansong.test.sqlite2mysqlspeed.controller.message.BaseMessage;
import com.yuansong.test.sqlite2mysqlspeed.controller.message.TimeRecord;
import com.yuansong.test.sqlite2mysqlspeed.repository.Do.RowVal;
import com.yuansong.test.sqlite2mysqlspeed.service.SqliteService;
import com.yuansong.test.sqlite2mysqlspeed.service.TabService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags = "01 RootController")
@RequestMapping("/")
public class RootController {
	
//	private static final Logger logger = LoggerFactory.getLogger(RootController.class);
	
	@Autowired
	private SqliteService sqliteService;
	
	@Autowired
	private TabService tabService;
	
	/**
	 * 测试路径
	 * @return
	 */
	@GetMapping("/")
	@ApiOperation(value = "测试路径", notes = "")
	public String root() {
		return "root";
	}
	
	@GetMapping("/test")
	@ApiOperation(value = "测试", notes = "")
	public String test() {
		
		long begTime = System.currentTimeMillis();
		
		Map<String, List<RowVal>> map;
		
		try {
			map = sqliteService.getSqliteData();
		} catch (FileNotFoundException e) {
			return e.getMessage();
		}
		
		Map<String, Integer> recordsInfo = new HashMap<String, Integer>();
		for(String k : map.keySet()) {
			recordsInfo.put(k, map.get(k).size());
			tabService.addList("tab", map.get(k));
		}
		
		long endTime = System.currentTimeMillis();
		
		TimeRecord timeRecord = new TimeRecord();
		timeRecord.setTotal(endTime - begTime);
//		timeRecord.setRecords_info(recordsInfo);
		
		BaseMessage<TimeRecord> rep = new BaseMessage<TimeRecord>();
		rep.setCode(0);
		rep.setMessage("success");
		rep.setData(timeRecord);
		
		Gson gson = new Gson();
		return gson.toJson(rep);
	}
}
