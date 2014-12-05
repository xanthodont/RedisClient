package com.konka.redis.client;

import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;


/**
 * Hello world!
 *
 */
public class App 
{
	private static Logger logger = Logger.getLogger(App.class);
	public static final String REDIS_REGISTER_KEY = RedisConstants.REQUEST_REGISTER;
	public static final String REDIS_INFO_KEY = RedisConstants.REQUEST_INFO;
	
	private static ApplicationContext ctx = new ClassPathXmlApplicationContext("app.xml");
	
    public static void main( String[] args ) {
        System.out.printf( "=================================\n");
        System.out.printf( "=          JedisClient          =\n");
        System.out.printf( "=================================\n");
        logger.info("Init Jedis Pool and Datasource Pool");
        JedisPool jedisPool = (JedisPool) ctx.getBean("jedisPool");
        DataSource dataSourcePool = (DataSource) ctx.getBean("dataSource");
        
        logger.info("Init RegisterThread and InfoThread, And then startThread.");
        Thread registerThread = new Thread(new RegisterThread(jedisPool, dataSourcePool), "RegisterThread");
        Thread infoThread = new Thread(new InfoThread(jedisPool, dataSourcePool), "InfoThread");
        registerThread.start();
        infoThread.start();
    }

}
