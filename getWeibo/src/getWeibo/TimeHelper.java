package getWeibo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;



public class TimeHelper {
	
	public static Date getNowTime()
	{
		Date date = new Date();
		DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String time=format.format(date);
		return date;
	}
	public static String dealTime(String time)
	{
		String res="";
		Date date = new Date();
		if (time.indexOf("今") != -1 || time.indexOf("天") != -1)
		{
			String tmp = time.substring(time.length()-5, time.length());
			//时分
			
			DateFormat format=new SimpleDateFormat("yyyy-MM-dd");
			String t=format.format(date);
			res = tmp+" "+t;
		}
		else if (time.indexOf("分")!=-1 || time.indexOf("钟")!=-1)
		{
			if (time.indexOf(" ") == -1)
				time+=" ";
			else {
				String []info = time.split(" ");
				time = info[0] + " ";
			}
			String x = time.substring(0, time.length()-4);
			res = subTime(x);
		}
		else if (time.length() >= 10)
		{
			//直接处理类型
			//若是跨年的类型直接返回
			if (time.substring(0, 4).indexOf("-") == -1)
			{
				//time = time.substring(0, time.length()-4);
				String[] info = time.split(":");
				res = info[0].split(" ")[1] + ":" + info[1] +" "+info[0].split(" ")[0];
			}
			else 
			{
				//需要处理类型
				String tmp = time.substring(time.length()-5, time.length());
				String tmp1 = time.substring(0, 5);
				DateFormat format=new SimpleDateFormat("yyyy");
				String t=format.format(date);
				res = tmp+" "+t+"-"+tmp1;
			}
		}
		return res;
	}
	
	
	
	public static String subTime(String x)
	{
		//返回x分钟前的时间
		try
		{
			Date date = getNowTime();
			int m = Integer.parseInt(x);
			Calendar now = Calendar.getInstance();
			now.setTime(date);
			now.add(Calendar.MINUTE, -m);
			Date date_f = now.getTime();
			DateFormat format=new SimpleDateFormat("HH:mm yyyy-MM-dd");
			
			return format.format(date_f);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("时间出错");
			return "0000";
		}
	}
	public static int ms2s(int sec)
	{
		return sec*1000;
	}
	public static int ms2m(int minute)
	{
		return ms2s(minute*60);
	}
	public static int ms2h(int hour)
	{
		return ms2m(hour*60);
	}
}
