package getWeibo;

import java.io.IOException;
import java.net.MalformedURLException;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;

public class ThreadHelper implements Runnable{
//此工具类用于不断抓取线程
	LoginWeibo test;
	int totalTime;
	public ThreadHelper(String u, String p)
	{
		totalTime = TimeHelper.ms2m(30);//设置线程执行时间，目前为30分钟
		try {
			test = new LoginWeibo(u,p);
		} catch (FailingHttpStatusCodeException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public void run()//目前为抓取主页
	{
		int nowTime=0;
		if(test!=null)
		{
			while(nowTime < totalTime)
			{
				nowTime += totalTime;
				try {
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
					Thread.sleep(TimeHelper.ms2m(2));
					//线程休眠8分钟
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
