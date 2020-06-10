package com.yuansong.test.sqlite2mysqlspeed.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yuansong.test.sqlite2mysqlspeed.service.TabService;
import com.yuansong.test.sqlite2mysqlspeed.service.TempDataService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags = "02 MysqlController")
@RequestMapping("/mysql")
public class MysqlController {

	@Autowired
	private TabService tabService;
	
	@Autowired
	private TempDataService tempDataService;
		
	@GetMapping("/init")
	@ApiOperation(value = "初始化", notes = "")
	public String initData(@RequestParam(name="c") Integer c) {
		if(c == null || c < 100) {
			c = 100;
		}
		tabService.addList("tab0", tempDataService.getTestDataList(c));
		tabService.addList("tab1", tempDataService.getTestDataList(c));
		tabService.addList("tab2", tempDataService.getTestDataList(c));
		tabService.addList("tab3", tempDataService.getTestDataList(c));
		tabService.addList("tab4", tempDataService.getTestDataList(c));
		tabService.addList("tab5", tempDataService.getTestDataList(c));
		tabService.addList("tab6", tempDataService.getTestDataList(c));
		tabService.addList("tab7", tempDataService.getTestDataList(c));
		tabService.addList("tab8", tempDataService.getTestDataList(c));
		tabService.addList("tab9", tempDataService.getTestDataList(c));
		return "init";
	}
}
