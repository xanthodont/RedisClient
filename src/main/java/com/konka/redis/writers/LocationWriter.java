package com.konka.redis.writers;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.konka.redis.utils.GoogleMapUtil;
import com.konka.redis.utils.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class LocationWriter extends DBWriter{
	private static Logger logger = Logger.getLogger(LocationWriter.class);
	private final static String SQL_STATEMENT = "call pro_update_location(?, ?, ?, ?, ?, ?);"; 

	public LocationWriter(DataSource dataSourcePool) {
		// TODO Auto-generated constructor stub
		super(dataSourcePool, SQL_STATEMENT, "LocationWriter", 20000);
	}

	@Override
	protected void setPrepareStatement(PreparedStatement ps, Info info,
			Object obj) throws SQLException {
		// TODO Auto-generated method stub
		JSONObject json = (JSONObject) obj;
		double lng = json.getDouble("Lng");
		double lat = json.getDouble("Lat");
		String address = "";
		String country = "";
		String[] rs = GoogleMapUtil.googleGeocode(lng, lat);
		address = rs[0];
		country = rs[1];
		ps.setLong(1, info.getImei());
		ps.setDouble(2, lng);
		ps.setDouble(3, lat);
		ps.setString(4, address);
		ps.setString(5, country);
		ps.setLong(6, System.currentTimeMillis());
	}
}
