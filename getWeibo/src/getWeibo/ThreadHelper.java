package getWeibo;

import java.io.IOException;
import java.net.MalformedURLException;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;

public class ThreadHelper implements Runnable{
//�˹��������ڲ���ץȡ�߳�
	
	int totalTime;
	int breaktime;//���ü��ʱ��
	public static boolean flag;
	String u;
	String p;
	public ThreadHelper(String uu, String pp)
	{
		u = uu;
		p = pp;
		flag = true;
		totalTime = TimeHelper.ms2m(10);//�����߳�ִ��ʱ�䣬ĿǰΪ30����
		breaktime = TimeHelper.ms2m(2);
	}
	@SuppressWarnings("static-access")
	public void run()//ĿǰΪץȡ��ҳ
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
				//�߳�����10����
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}
