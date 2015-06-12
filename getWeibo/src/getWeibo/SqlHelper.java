package getWeibo;

import java.awt.Point;
import java.io.IOException;
import java.sql.*;

import javax.naming.spi.DirStateFactory.Result;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;
//this class is to help you to use sqlserver 
/*
 * Author : saltyx
 * Time : 2015/6/12
 * 
 */
public class SqlHelper {
	public XmlHelper xmlhelper;
	Statement st;
	public SqlHelper()
	{
		try {
			xmlhelper = new XmlHelper("src/config.xml");
		} catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.exit(0);
		} catch (SAXException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//驱动
		String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		
		String info_db = xmlhelper.getSqlConnInfo().get(0);
		try
		{
			Class.forName(driver);
			
		}
		catch(ClassNotFoundException e)
		{
			System.out.println("NotFoundClass");
			System.exit(0);
		}
/*		System.out.println("yes");*/
		try
		{
			String u = xmlhelper.getSqlConnInfo().get(1);
			String pwd= xmlhelper.getSqlConnInfo().get(2);
			Connection conn = DriverManager.getConnection(info_db,u,pwd);
			st = conn.createStatement();
/*			ResultSet rs = st.executeQuery("select * from test");
			while (rs.next())
			{
				System.out.println(rs.getString("test"));
				System.out.println(rs.getInt("test1"));
			}
			System.out.println("yes...");*/
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			System.out.println("connection failed");
		}
	}
	public void excuteSql(String sql)
	{
		try {
			st.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("执行出错");
			e.printStackTrace();
		}
	}
	public ResultSet getInfo(String sql)
	{
		ResultSet rs = null;
		try {
			rs = st.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("sql语句有错");
			e.printStackTrace();
		}
		return rs;
	}
/*	public String getOneRec(String sql)
	{
		
	}
*/

}

