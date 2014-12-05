package com.konka.redis.writers;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import net.sf.json.JSONObject;

public class AppsWriter extends DBWriter{
	private static Logger logger = Logger.getLogger(AppsWriter.class);
	private final static String SQL_STATEMENT = "CALL pro_insert_apps(?, ?, ?, ?, ?);"; 
    
    public AppsWriter(DataSource dataSourcePool) {
    	super(dataSourcePool, SQL_STATEMENT, "AppWriter");
    }

    @Override
    protected void setPrepareStatement(PreparedStatement ps, Info info, Object obj) throws SQLException {
    	JSONObject json = (JSONObject) obj;
		ps.setLong(1, info.getImei());
		ps.setLong(2, System.currentTimeMillis());
		ps.setString(3, json.getString("PackageName"));
		ps.setString(4, json.getString("AppName"));
		ps.setInt(5, json.getInt("Times"));
		logger.debug(json.getString("AppName"));
	}
}
