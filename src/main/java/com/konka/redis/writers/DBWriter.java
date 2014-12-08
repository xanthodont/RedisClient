package com.konka.redis.writers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import javax.sql.DataSource;

import com.konka.redis.utils.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public abstract class DBWriter {
	/** 数据库操作语句 */
	protected final String sqlStatement;
	/** 数据库连接池 */
	protected DataSource dataSourcePool;
	/** 消息队列 */
	protected final BlockingQueue<Info> queue;
	/** 写入数据库线程 */
	protected Thread writerThread;
	/** 线程开关，线程是否完成 */
    volatile boolean done;
    /** 线程名称 */
    protected String threadName;
    protected int queueSize = 100;
	
	public DBWriter(DataSource dataSourcePool, String sqlStatement) {
		this.dataSourcePool = dataSourcePool;
		this.queue = new ArrayBlockingQueue<Info>(queueSize, true);
		this.sqlStatement = sqlStatement; 
		init();
	}	
	public DBWriter(DataSource dataSourcePool, String sqlStatement, String threadName) {
		this.threadName = threadName;
		this.dataSourcePool = dataSourcePool;
		this.sqlStatement = sqlStatement;
		this.queue = new ArrayBlockingQueue<Info>(queueSize, true);
		init();
	}
	public DBWriter(DataSource dataSourcePool, String sqlStatement, String threadName, int queueSize) {
		this.threadName = threadName;
		this.queueSize = queueSize;
		this.dataSourcePool = dataSourcePool;
		this.sqlStatement = sqlStatement;
		this.queue = new ArrayBlockingQueue<Info>(queueSize, true);
		init();
	}
	
	private void init() {
		done = false;
		writerThread = new Thread() {
			public void run() {
				// TODO Auto-generated method stub
				writeToDatabase(this);
			}
		};
		if (!StringUtil.isEmpty(threadName)) writerThread.setName(threadName);
		writerThread.setDaemon(true);
	} 
	
	/**
	 * 写入数据库
	 * @param thisThread
	 */
	public void writeToDatabase(Thread thisThread) {
		int count = 0;
		Connection jdbcConn = null;
		PreparedStatement ps = null;
		try {
			jdbcConn = dataSourcePool.getConnection();
			
			while(!done && (writerThread == thisThread)) {
				Info info = nextInfo();
				if (count == 0) 
					ps = jdbcConn.prepareStatement(sqlStatement);
				try {
					if (info != null) {
						JSONArray array = JSONArray.fromObject(info.getParams());
						for (int i = 0; i < array.size(); i++) {
							setPrepareStatement(ps, info, array.get(i));
							//System.out.printf(" -> addBantch ");
							ps.addBatch();
							count++;
						}
					} 
				} catch (Exception e) {
					e.printStackTrace();
					System.out.printf("json parse error!! Params:%s\n", info.getParams());
					continue;
				} finally {
					if (count > 500 || queue.isEmpty()) {
						System.out.printf(" -> executeBantch ");
						ps.executeBatch();
						count = 0;
						colsePreparedStatement(ps);
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.printf("appWriter exception!!");
			e.printStackTrace();
		} finally {
			closeConnection(jdbcConn);
		}
	}

	/**
	 * 配置预处理参数
	 * @param ps PrepareStatement预处理
	 * @param info info数据
	 * @param json params参数的Json对象
	 * @throws SQLException
	 */
	protected abstract void setPrepareStatement(PreparedStatement ps, Info info, Object obj) throws SQLException;

	
	public void putInfo(Info info) {
		// TODO Auto-generated method stub
		if (!done) {
			try {
				queue.put(info);
			} catch (InterruptedException ie) {
                ie.printStackTrace();
                return;
            }
            synchronized (queue) {
                queue.notifyAll();
            }
		}
	}
	
	private Info nextInfo() {
		// TODO Auto-generated method stub
		Info info = null;
        while (!done && (info = queue.poll()) == null) {
            try {
                synchronized (queue) {
                    queue.wait();
                }
            }
            catch (InterruptedException ie) {
                // Do nothing
            }
        }
        return info;
	}
	
	public void startup() {
		writerThread.start();
	}
	
	public void shutdown() {
		done = true;
        synchronized (queue) {
            queue.notifyAll();
        }
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
}
