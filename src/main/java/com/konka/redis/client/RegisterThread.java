package com.konka.redis.client;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.konka.redis.utils.StringUtil;
import com.konka.redis.writers.DBWriter;
import com.konka.redis.writers.Info;
import com.konka.redis.writers.LocationWriter;
import com.mysql.fabric.xmlrpc.base.Array;

import net.sf.json.JSONObject;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RegisterThread implements Runnable{
	private static Logger logger = Logger.getLogger(RegisterThread.class);
	/** jedis连接池 */
	private JedisPool jedisPool;
	/** 一次批处理量大小 */
	private int batchSize = 1000;
	/** 数据库连接池 */
	private DataSource dataSourcePool;
	private int sleepSeconds = 30;
	private static final String SQL_STATEMENT = "call pro_insert_account(?, ?, ?, ?, ?, ?, ?, ?, ?);";

	
	public RegisterThread(JedisPool jedisPool, DataSource dataSourcePool) {
		this.jedisPool = jedisPool;
		this.dataSourcePool = dataSourcePool;
		logger.info("Construct RegisterThread, initial and start the LocationWriter");
	}

	public void run() {
		logger.info("== RegisterThread: begin to start !!!");
		while (true) {
			Jedis jedis = jedisPool.getResource();
			if (jedis.llen(RedisConstants.REQUEST_REGISTER) == 0) { // 无数据
				// wait 线程等待
				jedisPool.returnResource(jedis);
				logger.info(String.format("=== RegisterThread: jedis length is 0 , Thread sleep %d s!\n", sleepSeconds));
				try {
					Thread.sleep(sleepSeconds*1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					logger.error("RegisterThread catch exception", e);
				}
				continue;
			} 
			Connection jdbcConn = null;
			PreparedStatement ps = null;
			try {
				jdbcConn = dataSourcePool.getConnection();
				ps = jdbcConn.prepareStatement(SQL_STATEMENT);
				logger.info("=== RegisterThread === initial jdbcConnection and PrepareStatement, then start batch ");		
				/** 一次批处理 */
				for (int i = 0; i < batchSize; i++) {
					/** 读取redis数据 */
					String params = jedis.lpop(RedisConstants.REQUEST_REGISTER);
					if (StringUtil.isEmpty(params)) {
						logger.info("-> jedis pop finish ");
						break;
					}
					JSONObject obj = JSONObject.fromObject(params);
					// 设置参数
					long imei = obj.getLong("Imei");
					Double lng = getJsonDouble(obj, "Longitude");
					Double lat = getJsonDouble(obj, "Latitude");
					if (lng != null && lat != null) {
						jedis.rpush(App.REDIS_INFO_KEY, String.format("{\"Imei\":\"%d\", \"Location\":[{\"Lng\":%f, \"Lat\":%f}]}", imei, lng, lat));
					}
					ps.setLong(1, imei);
					ps.setString(2, obj.getString("Sn"));
					ps.setString(3, obj.getString("PhoneType"));
					ps.setString(4, obj.getString("MobileVersion"));
					ps.setString(5, obj.getString("InternalVersion"));
					ps.setLong(6, System.currentTimeMillis());
					ps.setLong(7, System.currentTimeMillis());
					ps.setDouble(8, lng);
					ps.setDouble(9, lat);
					// 添加批处理
					ps.addBatch();
				}
				// 执行批处理
				ps.executeBatch();
				
				logger.info("-> executeBatch ");
			} catch (Exception e) {
				logger.error("RegisterThread execute batch exception", e);
			} finally {
				/** 释放资源 */
				colsePreparedStatement(ps);
				closeConnection(jdbcConn);
				jedisPool.returnResource(jedis);
				logger.info("Release resources");
			}
		}
	}
	
	
	private Double getJsonDouble(JSONObject json, String key) {
		if (json.has(key)) 
			return json.getDouble(key);
		else 
			return null;
	}
	private String getJsonString(JSONObject json, String key) {
		if (json.has(key)) 
			return json.getString(key);
		else 
			return null;
	}

	private void closeConnection(Connection jdbcConn) {
		if (jdbcConn != null) {
			try {
				jdbcConn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void colsePreparedStatement(PreparedStatement ps) {
		if (ps != null) {
			try {
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
