package com.yuansong.test.sqlite2mysqlspeed.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.yuansong.test.sqlite2mysqlspeed.repository.Do.RowVal;

@Repository
public class TabRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private String getInsertSql(String tabName) {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO ");
		sb.append(tabName);
		sb.append(" (id, val0, val1, val2, val3, val4, val5, val6, val7, val8, val9) VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");
		sb.append("ON DUPLICATE KEY UPDATE id = VALUES(id) ");
		sb.append(" , val0 = VALUES(val0)");
		sb.append(" , val1 = VALUES(val1)");
		sb.append(" , val2 = VALUES(val2)");
		sb.append(" , val3 = VALUES(val3)");
		sb.append(" , val4 = VALUES(val4)");
		sb.append(" , val5 = VALUES(val5)");
		sb.append(" , val6 = VALUES(val6)");
		sb.append(" , val7 = VALUES(val7)");
		sb.append(" , val8 = VALUES(val8)");
		sb.append(" , val9 = VALUES(val9);");
		return sb.toString();
	}
	
	private String getClearSql(String tabName) {
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM ");
		sb.append(tabName);
		sb.append(" ;");
		return sb.toString();
	}

	public void insert(String tabName, RowVal data) {
		if(this.jdbcTemplate == null) {
			return ;
		}
		this.jdbcTemplate.update(this.getInsertSql(tabName), 
				data.getId()
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
	
	public void clear(String tabName) {
		if(this.jdbcTemplate == null) {
			return ;
		}
		this.jdbcTemplate.update(this.getClearSql(tabName));
	}
}
