package com.konka.redis.writers;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import net.sf.json.JSONObject;

public class MemoryWriter extends DBWriter{
	private final static String SQL_STATEMENT = "call pro_insert_memory(?, ?, ?, ?, ?, ?, ?);";

	public MemoryWriter(DataSource dataSourcePool) {
		super(dataSourcePool, SQL_STATEMENT, "MemoryWriter");
	}

	@Override
	protected void setPrepareStatement(PreparedStatement ps, Info info,
			Object obj) throws SQLException {
		// TODO Auto-generated method stub
		JSONObject json = (JSONObject)obj;
		ps.setLong(1, info.getImei());
		ps.setString(2, json.getString("Type"));
		ps.setDouble(3, json.getDouble("Size"));
		ps.setLong(4, json.getLong("Date"));
		ps.setInt(5, json.getInt("Count"));
		ps.setDouble(6, json.getDouble("Min"));
		ps.setDouble(7, json.getDouble("Max"));
	}

}
