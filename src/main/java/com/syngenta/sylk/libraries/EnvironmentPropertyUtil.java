package com.syngenta.sylk.libraries;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

/**
 * @author Nisha Pillai
 * 
 */
public class EnvironmentPropertyUtil {

	private static EnvironmentPropertyUtil singleton = new EnvironmentPropertyUtil();

	private String environment;
	private String url;
	private String defaultPageWaitTime;

	private String browser;

	private String dbPassword;

	private String dbUser;

	private String dbUrl;

	private String dbDriver;

	private EnvironmentPropertyUtil() {
		this.determineEnvironment();
		this.getPropertySettings();
		this.getAutomationParams();
	}

	private void getAutomationParams() {
		String propertyFile = "/automation_param.properties";
		InputStream inputStream = EnvironmentPropertyUtil.class
				.getResourceAsStream(propertyFile);
		Properties props = new Properties();
		try {
			props.load(inputStream);
			this.readAutomationParams(props);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	private void readAutomationParams(Properties props) {
		this.browser = props.getProperty("browser");
	}

	private void getPropertySettings() {
		String propertyFile = String.format("/%s-environment.properties",
				this.environment);
		InputStream inputStream = EnvironmentPropertyUtil.class
				.getResourceAsStream(propertyFile);
		Properties props = new Properties();
		try {
			props.load(inputStream);
			this.readPropertyFile(props);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	private void readPropertyFile(Properties props) {
		this.url = props.getProperty("app.url");
		this.defaultPageWaitTime = props
				.getProperty("default.page.wait.time.ms");
		this.dbDriver = props.getProperty("dbdriver");
		this.dbUrl = props.getProperty("dburl");
		this.dbPassword = props.getProperty("dbpassword");
		this.dbUser = props.getProperty("dbuser");
	}

	private void determineEnvironment() {

		System.setProperty("env", "dev");
		String setting = System.getProperty("env");
		this.environment = StringUtils.isEmpty(setting) ? "dev" : setting;
	}

	public String getUrl() {
		return this.url;
	}

	public long getDefaultWaitTime() {
		return Long.parseLong(this.defaultPageWaitTime);
	}

	public static EnvironmentPropertyUtil getInstance() {
		return singleton;
	}

	public String getBrowser() {
		return this.browser;
	}

	public String getDBPassword() {
		return this.dbPassword;
	}

	public String getDBUser() {
		return this.dbUser;
	}

	public String geDBUrl() {
		return this.dbUrl;
	}

	public String getDBDriver() {
		return this.dbDriver;
	}

}
