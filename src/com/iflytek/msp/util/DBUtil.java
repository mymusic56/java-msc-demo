package com.iflytek.msp.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * 封装一个数据库的公共类
 * @author SuperStar
 *
 */
public class DBUtil {

	//声明获取连接和驱动所需要的参数
	private static String driver = null;
	private static String url = null;
	private static String user = null;
	private static String password = null;

	/**
	 * 保证在类加载的时候被执行，而且只执行一次
	 */
	static {
		try {
//			//读取配置文件信息 的两种方式：
			/*
			 * 第一种方式：
			 */
//			Properties properties = new Properties();
//			properties.load(DBUtil.class.getClassLoader().getResourceAsStream("com/bjsxt/config/dbconfig.properties"));
//			String database = properties.getProperty("database");
//			//获取配置文件信息
//			driver = properties.getProperty(database+"Driver");
//			url = properties.getProperty(database+"Url");
//			user = properties.getProperty(database+"User");
//			password = properties.getProperty(database+"Password");
			/*
			 * 第二种方式：
			 */
			ResourceBundle rb = ResourceBundle.getBundle("com.iflytek.msp.config.dbconfig");
			String database = rb.getString("database");
			driver = rb.getString(database+"Driver");
			url = rb.getString(database+"Url");
			user = rb.getString(database+"User");
			password = rb.getString(database+"Password");
			//加载驱动
			Class.forName(driver);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取连接
	 * @return
	 */
	public static Connection getConnection() {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			System.out.println("DBUtil.getConnection(获取连接失败，请检查参数)【" + url + "】【" + user + "】【" + password + "】");
			e.printStackTrace();
		}
		return connection;
	}

	/**
	 * 关闭连接
	 * @param connection
	 */
	public static void closeConnection(Connection connection) {
		try {
			//判断是否为空
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取请单对象（执行器）
	 * @param connection
	 * @return
	 */
	public static Statement getStatment(Connection connection) {
		//声明对象
		Statement statement = null;
		try {
			//获取对象
			statement = connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return statement;
	}

	/**
	 * 关闭清单对象
	 * @param statement
	 */
	public static void closeStatement(Statement statement) {
		try {
			if (statement != null) {
				statement.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取预处理清单对象
	 * @param connection
	 * @param sql
	 * @return
	 */
	public static PreparedStatement getPreparedStatement(Connection connection, String sql) {
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return preparedStatement;
	}

	/**
	 * 关闭结果集对象
	 * @param resultSet
	 */
	public static void closeResultSet(ResultSet resultSet) {
		try {
			if (resultSet != null) {
				resultSet.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 关闭所有
	 * @param connection
	 * @param statement
	 * @param resultSet
	 */
	public static void closeAll(Connection connection, Statement statement, ResultSet resultSet) {
		closeResultSet(resultSet);
		closeStatement(statement);
		closeConnection(connection);
	}
}
