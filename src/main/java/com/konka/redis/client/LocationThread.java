package com.konka.redis.client;

import javax.sql.DataSource;

import redis.clients.jedis.JedisPool;

public class LocationThread  extends AbstractThread{

	public LocationThread(JedisPool jedisPool, DataSource dataSourcePool) {
		super(jedisPool, dataSourcePool);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
