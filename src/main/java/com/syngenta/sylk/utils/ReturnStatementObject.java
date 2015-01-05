package com.syngenta.sylk.utils;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ReturnStatementObject {

	private PreparedStatement ps;
	private ResultSet rs;
	private int rsCount;
	private CallableStatement call;
	private Connection con;
	public PreparedStatement getPreparedStatement() {
		return this.ps;
	}
	public void setPreparedStatement(PreparedStatement ps) {
		this.ps = ps;
	}
	public ResultSet getResultSet() {
		return this.rs;
	}
	public void setResultSet(ResultSet rs) {
		this.rs = rs;
	}
	public int getRsCount() {
		return this.rsCount;
	}
	public void setRsCount(int rsCount) {
		this.rsCount = rsCount;
	}
	public CallableStatement getCall() {
		return this.call;
	}
	public void setCall(CallableStatement call) {
		this.call = call;
	}
	public void setConnection(Connection con) {
		this.con = con;
	}
	public Connection getConnection() {
		return this.con;
	}

}
