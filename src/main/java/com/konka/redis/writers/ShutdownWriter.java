package com.konka.redis.writers;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import net.sf.json.JSONObject;

public class ShutdownWriter extends DBWriter{
	private static final String SQL_STATEMENT = "call pro_insert_shutdown(?, ?, ?)";

	public ShutdownWriter(DataSource dataSourcePool) {
		super(dataSourcePool, SQL_STATEMENT, "ShutdownWriter");
	}

	@Override
	protected void setPrepareStatement(PreparedStatement ps, Info info, Object obj) throws SQLException {
		// TODO Auto-generated method stub
		Long time = (Long) obj;
		ps.setLong(1, info.getImei());
		ps.setLong(2, time);
		ps.setLong(3, System.currentTimeMillis());
	}

}
