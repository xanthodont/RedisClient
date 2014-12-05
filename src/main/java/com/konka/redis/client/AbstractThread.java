package com.konka.redis.client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import net.sf.json.JSONObject;
import redis.clients.jedis.JedisPool;

public abstract class AbstractThread implements Runnable {
	/** jedis连接池 */
	protected JedisPool jedisPool;
	/** 数据库连接池 */
	protected DataSource dataSourcePool;
	/** 一次批处理量大小 */
	protected int batchSize = 10000;
	/** 当redis没有数据时，线程挂起的时间，单位是秒 */
	protected int sleepSeconds = 30;
	
	public AbstractThread(JedisPool jedisPool, DataSource dataSourcePool) {
		this.jedisPool = jedisPool;
		this.dataSourcePool = dataSourcePool;
	}
	
	protected double getJsonDouble(JSONObject json, String key) {
		if (json.has(key)) 
			return json.getDouble(key);
		else 
			return 0;
	}
	protected String getJsonString(JSONObject json, String key) {
		if (json.has(key)) 
			return json.getString(key);
		else 
			return null;
	}

	protected void closeConnection(Connection jdbcConn) {
		if (jdbcConn != null) {
			try {
				jdbcConn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	protected void colsePreparedStatement(PreparedStatement ps) {
		if (ps != null) {
			try {
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public abstract void run();
}
