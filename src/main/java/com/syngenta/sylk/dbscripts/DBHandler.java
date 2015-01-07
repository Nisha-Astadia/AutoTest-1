package com.syngenta.sylk.dbscripts;
import java.sql.Connection;
import java.sql.SQLException;

import com.syngenta.sylk.utils.DBGateKeeper;
import com.syngenta.sylk.utils.ReturnStatementObject;

/**
 * @author Nisha Pillai
 * 
 */
public class DBHandler {

	private Connection con = null;
	public DBHandler() {
	}
	protected ReturnStatementObject getResultSet(String query)
			throws SQLException {

		ReturnStatementObject obj = null;
		try {
			this.con = DBGateKeeper.getInstance().getConnection();
			obj = new ReturnStatementObject();
			obj.setConnection(this.con);
			obj.setPreparedStatement(this.con.prepareStatement(query.toString()));
			obj.setResultSet(obj.getPreparedStatement().executeQuery());

		} catch (SQLException e) {
			throw e;
		}

		return obj;
	}

	protected void exitSmoothly(ReturnStatementObject obj) {
		if (obj != null) {
			try {
				if (obj.getPreparedStatement() != null) {
					obj.getPreparedStatement().close();
				}
			} catch (SQLException e) { /*
										 * lets do nothing for now... see if
										 * needs to bubble up
										 */
			}
			try {
				if (obj.getResultSet() != null) {
					obj.getResultSet().close();
				}
			} catch (SQLException e) { /*
										 * lets do nothing for now... see if
										 * needs to bubble up
										 */
			}

			try {
				if (obj.getConnection() != null) {
					obj.getConnection().close();
				}
			} catch (SQLException e) { /*
										 * lets do nothing for now... see if
										 * needs to bubble up
										 */
			}
		}
	}

}
