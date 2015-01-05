package com.syngenta.sylk.libraries;

public interface Environment {

	public static long DEFAULT_WAIT_TIME = EnvironmentPropertyUtil
			.getInstance().getDefaultWaitTime();
	public static String PRODUCT_URL = EnvironmentPropertyUtil.getInstance()
			.getUrl();
	public static String browser = EnvironmentPropertyUtil.getInstance()
			.getBrowser();
	public static String DB_PWD = EnvironmentPropertyUtil.getInstance()
			.getDBPassword();
	public static String DB_USER = EnvironmentPropertyUtil.getInstance()
			.getDBUser();
	public static String DB_URL = EnvironmentPropertyUtil.getInstance()
			.geDBUrl();
	public static String DB_DRIVER = EnvironmentPropertyUtil.getInstance()
			.getDBDriver();
}
