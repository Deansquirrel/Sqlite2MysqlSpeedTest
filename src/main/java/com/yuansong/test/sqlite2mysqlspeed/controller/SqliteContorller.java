package com.yuansong.test.sqlite2mysqlspeed.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yuansong.test.sqlite2mysqlspeed.service.SqliteService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags = "03 SqliteController")
@RequestMapping("/sqlite")
public class SqliteContorller {
	
	@Autowired
	private SqliteService sqliteService;
	
	@GetMapping("/init")
	@ApiOperation(value = "初始化", notes = "")
	public String initData(@RequestParam(name = "db") Integer dbCount, @RequestParam(name = "row") Integer rowCount) {
		if(dbCount == null || dbCount <= 0) dbCount = 1;
		if(rowCount == null || rowCount <= 100) rowCount = 100;
		try{
			this.sqliteService.clearSqlite();
			for(int i = 0; i < dbCount; i++) {
				this.sqliteService.createSqlite(rowCount);				
			}
		} catch(Exception e) {
			return e.getMessage();
		}
		return "init";
	}
}
