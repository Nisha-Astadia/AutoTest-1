package com.syngenta.sylk.utils;

import java.sql.Connection;
import java.sql.SQLException;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;
import com.syngenta.sylk.libraries.CommonLibrary;
import com.syngenta.sylk.libraries.Environment;
import com.syngenta.sylk.libraries.SyngentaException;

public class DBGateKeeper {

	private BoneCP connectionPool;
	private final static DBGateKeeper instance = new DBGateKeeper();

	public void shudownPool() {
		this.connectionPool.shutdown();
	}

	private DBGateKeeper() {
		this.loadThePoolUp();
	}

	public Connection getConnection() throws SQLException {
		return this.connectionPool.getConnection();

	}

	public static DBGateKeeper getInstance() {
		return instance;
	}

	private void loadThePoolUp() {

		try {
			Class.forName(this.getdBDriver()); // load the DB driver
			BoneCPConfig config = new BoneCPConfig(); // create a new
			// configuration object
			config.setJdbcUrl(this.getdBUrl()); // set the JDBC url
			config.setUsername(this.getdBUser()); // set the username
			config.setPassword(this.getdBPwd()); // set the password
			config.setPartitionCount(1);
			config.setMinConnectionsPerPartition(2);
			config.setMaxConnectionsPerPartition(20);
			config.setAcquireIncrement(2);
			this.connectionPool = new BoneCP(config); // setup the connection
														// pool
		} catch (Exception e) {
			throw new SyngentaException(new CommonLibrary().getStackTrace(e));
		}
	}

	private String getdBPwd() {
		return Environment.DB_PWD;
	}

	private String getdBUser() {
		return Environment.DB_USER;
	}

	private String getdBUrl() {
		return Environment.DB_URL;
	}

	public String getdBDriver() {
		return Environment.DB_DRIVER;
	}

}
