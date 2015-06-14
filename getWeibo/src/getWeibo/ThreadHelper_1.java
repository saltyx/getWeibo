package getWeibo;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;

public class ThreadHelper_1 implements Runnable{
	public String u;
	public String p;
	public static boolean flag;
	public ThreadHelper_1()
	{
		flag = true;
	}
	public ThreadHelper_1(String uu, String pp)
	{
		u = uu;
		p = pp;
		flag = true;
	}
	public void run()
	{
		LoginWeibo test = null;
		try {
			test = new LoginWeibo(u,p);
		} catch (FailingHttpStatusCodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
//			test.getWeiboInfo();
			try {
				test.getweiboinfo();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FailingHttpStatusCodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
