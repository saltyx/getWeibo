package getWeibo;

import java.io.IOException;
import java.net.MalformedURLException;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;

public class ThreadHelper implements Runnable{
//�˹��������ڲ���ץȡ�߳�
	LoginWeibo test;
	int totalTime;
	public ThreadHelper(String u, String p)
	{
		totalTime = TimeHelper.ms2m(30);//�����߳�ִ��ʱ�䣬ĿǰΪ30����
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
	public void run()//ĿǰΪץȡ��ҳ
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
					//�߳�����8����
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
