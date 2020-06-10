package com.yuansong.test.sqlite2mysqlspeed.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.alibaba.druid.pool.DruidDataSource;
import com.yuansong.test.sqlite2mysqlspeed.repository.Do.RowVal;
import com.yuansong.test.sqlite2mysqlspeed.repository.Do.rowMapper.RowValRowMapper;
import com.yuansong.tools.common.CommonTool;

@Service
public class SqliteService {
	
	@Autowired
	private TempDataService tempDataService;
	
	/**
	 * 清除当前sqlite文件
	 * @throws FileNotFoundException
	 * @throws Exception
	 */
	public void clearSqlite() throws FileNotFoundException , Exception {
		String path = ResourceUtils.getURL("").getPath() + File.separator + "data";
		File dataFolder = new File(path);
		if(!dataFolder.exists()) {
			return;
		}
		for(File file : dataFolder.listFiles()) {
			if(file.isFile()) {
				if(!file.delete()) {
					throw new Exception("del file [" + file.getName() + "] failed.");
				}				
			}
		}
	}
	
	/**
	 * 创建sqlite并添加测试数据
	 * @param c
	 * @throws FileNotFoundException
	 */
	public void createSqlite(int c) throws FileNotFoundException {
		String path = ResourceUtils.getURL("").getPath() + File.separator + "data";
		File dataFolder = new File(path);
		if(!dataFolder.exists()) {
			dataFolder.mkdirs();
		}
		String dbName = this.getDbName();
		JdbcTemplate jdbcTemplate = this.getJdbcTemplate(dbName, path + File.separator + dbName);
		this.dbInit(jdbcTemplate);
		this.dataInit(jdbcTemplate, c);
		DruidDataSource ds = (DruidDataSource)jdbcTemplate.getDataSource();
		ds.close();
	}
	
	/**
	 * 获取sqlite数据
	 * @param jdbcTemplate
	 * @return
	 */
	public Map<String, List<RowVal>> getSqliteData()  throws FileNotFoundException {
		Map<String, List<RowVal>> map = new HashMap<String, List<RowVal>>();
		String path = ResourceUtils.getURL("").getPath() + File.separator + "data";
		File dataFolder = new File(path);
		if(!dataFolder.exists()) {
			return map;
		}
		for(File file : dataFolder.listFiles()) {
			if(!file.isFile()) continue;
			String fileName = file.getName();
			if(!fileName.endsWith(".db")) continue;
			String key = file.getName().replace(".db", "");
			JdbcTemplate jdbcTemplate = this.getJdbcTemplate(key , file.getPath());
			List<RowVal> list = jdbcTemplate.query("select id,val0,val1,val2,val3,val4,val5,val6,val7,val8,val9 from tab;", 
					new RowValRowMapper());
			map.put(key, list);
			DruidDataSource ds = (DruidDataSource)jdbcTemplate.getDataSource();
			ds.close();
		}

		return map;
	}
	
	private void dbInit(JdbcTemplate jdbcTemplate) {
		jdbcTemplate.update(this.getDbInitSql());
	}
	
	private void dataInit(JdbcTemplate jdbcTemplate, int c) {
		jdbcTemplate.update(this.getClearSql("tab"));
		List<RowVal> list = tempDataService.getTestDataList(c);
		for(RowVal data : list) {
			jdbcTemplate.update(this.getInsertSql("tab"),data.getId()
				, data.getVal0()
				, data.getVal1()
				, data.getVal2()
				, data.getVal3()
				, data.getVal4()
				, data.getVal5()
				, data.getVal6()
				, data.getVal7()
				, data.getVal8()
				, data.getVal9());
		}
	}
	
	private JdbcTemplate getJdbcTemplate(String connName, String fileUrl) {
		return new JdbcTemplate(this.getDs(connName, fileUrl));
	}
	
	private DataSource getDs(String connName, String fileUrl) {		
		DruidDataSource ds = new DruidDataSource();
		ds.setName(connName);
		ds.setUrl("jdbc:sqlite:" + fileUrl);

		ds.setMinIdle(0);
		ds.setInitialSize(1);
		ds.setMaxActive(30);
		ds.setMaxWait(10000);
		ds.setQueryTimeout(3600);
		ds.setValidationQuery("SELECT 'X'");
		ds.setTimeBetweenEvictionRunsMillis(60000);
		ds.setMinEvictableIdleTimeMillis(30000);
		ds.setTimeBetweenConnectErrorMillis(5 * 60 * 1000);
		
		return ds;
	}
	
	private String getDbInitSql() {
		StringBuilder sb = new StringBuilder();
		sb.append("DROP TABLE IF EXISTS 'tab';");
		sb.append("CREATE TABLE 'tab' ('id' TEXT(36) NOT NULL, ");
		sb.append("'val0' TEXT(36), ");
		sb.append("'val1' TEXT(36), ");
		sb.append("'val2' TEXT(36), ");
		sb.append("'val3' TEXT(36), ");
		sb.append("'val4' TEXT(36), ");
		sb.append("'val5' TEXT(36), ");
		sb.append("'val6' TEXT(36), ");
		sb.append("'val7' TEXT(36), ");
		sb.append("'val8' TEXT(36), ");
		sb.append("'val9' TEXT(36), ");
		sb.append("PRIMARY KEY ('id'));");
		return sb.toString();
	}
	
	private String getInsertSql(String tabName) {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO ");
		sb.append(tabName);
		sb.append(" (id, val0, val1, val2, val3, val4, val5, val6, val7, val8, val9) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
		return sb.toString();
	}
	
	private String getClearSql(String tabName) {
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM ");
		sb.append(tabName);
		sb.append(" ;");
		return sb.toString();
	}
	
	private String getDbName() {
		CommonTool ct = new CommonTool();
		return ct.UUID().replace("-", "") + ".db";
	}
}
