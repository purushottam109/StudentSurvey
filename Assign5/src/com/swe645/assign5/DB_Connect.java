package com.swe645.assign5;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB_Connect
{
	private Connection conn = null;

	public DB_Connect(String db_name)
	{
		try
		{

			final String DB_DRIVER_CLASS = "com.mysql.jdbc.Driver";
			Class.forName(DB_DRIVER_CLASS).newInstance();
			//final String DB_URL = properties.getProperty("dbURLLit").trim()+db_name;
			final String DB_URL = "swe645.c6pdc37boo3g.us-east-1.rds.amazonaws.com:3306/StudentDB";
			final String DB_UNAME = "psharma";
			final String DB_PWD = "psharma91";
			conn = DriverManager.getConnection(DB_URL, DB_UNAME, DB_PWD);
		}
		catch (SQLException se)
		{
			System.out.println("An error occured while trying to connect to the DB: ");
			se.printStackTrace();
		}
		catch (ClassNotFoundException cnfe)
		{
			System.out.println("Class was not found: ");
			cnfe.printStackTrace();
		}
		catch (IllegalAccessException iae)
		{
			iae.printStackTrace();
		}
		catch (InstantiationException ie)
		{
			ie.printStackTrace();
		}
	}

	public Connection getConnect()
	{
		return conn;
	}

	public void closeConnect() throws SQLException
	{
		conn.close();
	}


}
