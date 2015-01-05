package com.syngenta.sylk.dbscripts;

public class TestDBConnection {

	public static void main(String[] str) {

		String query = "select order_no from mv_mk_map"
				+ "where map_set_id = \'46192397\'";

		ExecuteQuery exec = new ExecuteQuery();
		String orderNo = exec.executeQueryRequestingSingleColumn(query);

		System.out.println(orderNo);

	}
}
