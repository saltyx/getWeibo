package getWeibo;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;

public class Main {

	public static void main(String[] args) throws FailingHttpStatusCodeException, MalformedURLException, IOException, InterruptedException, SQLException {
		// TODO Auto-generated method stub
		SqlHelper sqlhelper = new SqlHelper();
		ResultSet rs = sqlhelper.getInfo("select * from main_user");
		if (rs.next())
		{
			String u = rs.getString("username");
			String p = rs.getString("password");
			//System.out.println(u);
			ThreadHelper threadHelper = new ThreadHelper(u,p);
			Thread thread_mainpage = new Thread(threadHelper);
			thread_mainpage.start();
		}
	}

}
