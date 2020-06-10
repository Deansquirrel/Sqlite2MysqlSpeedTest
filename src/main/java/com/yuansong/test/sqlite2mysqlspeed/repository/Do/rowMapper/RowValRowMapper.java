package com.yuansong.test.sqlite2mysqlspeed.repository.Do.rowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.yuansong.test.sqlite2mysqlspeed.repository.Do.RowVal;

public class RowValRowMapper implements RowMapper<RowVal> {

	@Override
	public RowVal mapRow(ResultSet rs, int rowNum) throws SQLException {
		RowVal d = new RowVal();
		d.setId(rs.getString("id"));
		d.setVal0(rs.getString("val0"));
		d.setVal1(rs.getString("val1"));
		d.setVal2(rs.getString("val2"));
		d.setVal3(rs.getString("val3"));
		d.setVal4(rs.getString("val4"));
		d.setVal5(rs.getString("val5"));
		d.setVal6(rs.getString("val6"));
		d.setVal7(rs.getString("val7"));
		d.setVal8(rs.getString("val8"));
		d.setVal9(rs.getString("val9"));
		return d;
	}

}
