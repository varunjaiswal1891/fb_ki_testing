package com.varun.fbproj.service;

import java.sql.*;
//Connects java page to Mysql database
public class DBAccess {

	Connection con=null;
	DBAccess()
	{
		con=null;
	}
	public boolean start()
	{
		try{
		Class.forName("com.mysql.jdbc.Driver");

		this.con=DriverManager.getConnection("jdbc:mysql://localhost:3306/facebook_group2","root","root");

		return true;
		}
		catch(Exception e)
		{
			System.out.println(e);
			return false;
		}
			
	}
	public boolean stop()
	{
		try{
			this.con.close();
			return true;
		}
		catch(Exception e)
		{
			System.out.println(e);
			return false;
		}
		
	}

	
}
