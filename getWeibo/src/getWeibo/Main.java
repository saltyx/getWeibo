package getWeibo;


import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;

public class Main {

	public static void main(String[] args) throws FailingHttpStatusCodeException, MalformedURLException, IOException, InterruptedException, SQLException {
		// TODO Auto-generated method stub
		SqlHelper sqlhelper = new SqlHelper();
		Statement st = sqlhelper.getst();
		
		ArrayList<String> in1 = sqlhelper.getp("select * from main_user");
		ArrayList<String> in2 = sqlhelper.getu("select * from main_user");
		int tmp_index = 1;
		;
		ThreadHelper_1 [] thread = new ThreadHelper_1[in1.size()];
//		ArrayList<ThreadHelper_1> thread = new ArrayList<ThreadHelper_1>(in1.size());
		while (in1.size() == in2.size() &&  in1.size() >= tmp_index)
		{
			
			String u = in2.get(tmp_index-1);
			String p = in1.get(tmp_index-1);
			
//			System.out.println(u+"\n"+p);
			//System.out.println(u);
			thread[tmp_index-1] = new ThreadHelper_1(u,p);
			thread[tmp_index-1].u = u;
			thread[tmp_index-1].p = p;
			Thread thread_mainpage = new Thread(thread[tmp_index-1]);
			thread_mainpage.start();
			
			System.out.println("≈¿≥Ê"+tmp_index+"∫≈");
			thread_mainpage.join();
			sqlhelper.RemoveTheSameRec("mainPage_WeiboInfo");
			tmp_index++;
		}
		sqlhelper.RemoveTheSameRec("mainPage_WeiboInfo");
//			System.out.println(TimeHelper.dealTime("2015-06-14 15:45 "));
		
	}

}
