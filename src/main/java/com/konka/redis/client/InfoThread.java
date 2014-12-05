package com.konka.redis.client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.konka.redis.utils.StringUtil;
import com.konka.redis.writers.AppsWriter;
import com.konka.redis.writers.DBWriter;
import com.konka.redis.writers.Info;
import com.konka.redis.writers.LocationWriter;
import com.konka.redis.writers.MemoryWriter;
import com.konka.redis.writers.ShutdownWriter;
import com.konka.redis.writers.UpdateWriter;

import net.sf.json.JSONObject;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class InfoThread extends AbstractThread {
	private static Logger logger = Logger.getLogger(InfoThread.class);
	private String[] infoTypes = {"Apps", "Men", "Shutdown", "Update", "Location"};
	private Map<String, DBWriter> dbWriters;
	
	static {
		
	}

	public InfoThread(JedisPool jedisPool, DataSource dataSourcePool) {
		super(jedisPool, dataSourcePool);
		// TODO Auto-generated constructor stub
		this.init();
	}
	
	private void init() {
		dbWriters = new HashMap<String, DBWriter>();
		dbWriters.put("Apps", new AppsWriter(super.dataSourcePool));
		dbWriters.put("Men", new MemoryWriter(super.dataSourcePool));
		dbWriters.put("Shutdown", new ShutdownWriter(super.dataSourcePool));
		dbWriters.put("Update", new UpdateWriter(super.dataSourcePool));
		dbWriters.put("Location", new LocationWriter(dataSourcePool));
		for (String jsonKey : infoTypes) {
			dbWriters.get(jsonKey).startup();
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			Jedis jedis = jedisPool.getResource();
			if (jedis.llen(App.REDIS_INFO_KEY) == 0) { // 无数据
				// wait 线程等待
				jedisPool.returnResource(jedis);
				System.out.printf("=== InfoThread: jedis length is 0 , Thread sleep %d s!\n", sleepSeconds);
				try {
					Thread.sleep(sleepSeconds*1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				continue;
			}
			
			try {
				System.out.printf("=== InfoThread === \n start batch ");		
				/** 一次批处理 */
				for (int i = 0; i < batchSize; i++) {
					/** 读取redis数据 */
					String params = jedis.lpop(App.REDIS_INFO_KEY);
					if (StringUtil.isEmpty(params)) {
						System.out.printf("-> jedis pop finish ");
						break;
					}
					logger.info("Params:"+params);
					JSONObject obj = JSONObject.fromObject(params);
					String imei = obj.getString("Imei");
					for (String jsonKey : infoTypes) {
						if (obj.has(jsonKey)) {
							String jsonParam = obj.getString(jsonKey);
							if (!StringUtil.isEmpty(jsonParam)) {
								Info info = new Info(imei, jsonKey, jsonParam);
								dbWriters.get(jsonKey).putInfo(info);
							}
						}
					}
				}
				
			} catch (Exception e) {
				logger.error("Info thread catch exception", e);
			} finally {
				/** 释放资源 */
				jedisPool.returnResource(jedis);
				System.out.printf("-> release resources \n");
			}
		}
	}

}
