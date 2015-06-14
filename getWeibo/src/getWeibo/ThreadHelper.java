package getWeibo;

import java.io.IOException;
import java.net.MalformedURLException;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;

public class ThreadHelper implements Runnable{
//此工具类用于不断抓取线程
	
	int totalTime;
	int breaktime;//设置间隔时间
	public static boolean flag;
	String u;
	String p;
	public ThreadHelper(String uu, String pp)
	{
		u = uu;
		p = pp;
		flag = true;
		totalTime = TimeHelper.ms2m(10);//设置线程执行时间，目前为30分钟
		breaktime = TimeHelper.ms2m(2);
	}
	@SuppressWarnings("static-access")
	public void run()//目前为抓取主页
	{
		int nowTime=0;
		while(nowTime < totalTime && flag)
		{
			
			nowTime += breaktime;
			
			try {
				LoginWeibo test = new LoginWeibo(u,p);
				test.getMainPageInfo();
			} catch (FailingHttpStatusCodeException e) {
				e.printStackTrace();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			try {
				Thread.currentThread().sleep(breaktime);
				//线程休眠10分钟
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}
