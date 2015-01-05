package com.syngenta.sylk.dbscripts;
import java.sql.ResultSet;

import org.apache.commons.lang.StringUtils;

import com.syngenta.sylk.libraries.CommonLibrary;
import com.syngenta.sylk.libraries.SyngentaException;
import com.syngenta.sylk.utils.ReturnStatementObject;

public class ExecuteQuery {

	public String executeQueryRequestingSingleColumn(String query) {
		query = StringUtils.deleteWhitespace(query);
		String returnString = null;
		DBHandler db = new DBHandler();
		ReturnStatementObject rtnObj = null;
		try {
			rtnObj = db.getResultSet(query);
			ResultSet rs = rtnObj.getResultSet();
			while (rs.next()) {
				returnString = rs.getString(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new SyngentaException("Exception when running query '"
					+ query + "'" + new CommonLibrary().getStackTrace(e));
		} finally {
			if (rtnObj != null) {
				db.exitSmoothly(rtnObj);
			}
		}

		return returnString;
	}
}
