package com.konka.redis.writers;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import net.sf.json.JSONObject;

public class UpdateWriter extends DBWriter {
	private final static String SQL_STATEMENT = "CALL pro_insert_update(?, ?, ?, ?);"; 

	public UpdateWriter(DataSource dataSourcePool) {
		super(dataSourcePool, SQL_STATEMENT, "UpdateWriter");
	}

	@Override
	protected void setPrepareStatement(PreparedStatement ps, Info info,
			Object obj) throws SQLException {
		// TODO Auto-generated method stub
		JSONObject json = (JSONObject) obj;
		ps.setLong(1, info.getImei());
		ps.setString(2, json.getString("Version"));
		ps.setString(3, json.getString("InnerVersion"));
		ps.setLong(4, json.getLong("Time"));
		
	}

}
