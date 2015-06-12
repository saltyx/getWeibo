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
		return sec* 1000;
	}
	public static int ms2m(int minute)
	{
		return ms2s(minute*60);
	}
	public static int ms2h(int hour)
	{
		return (hour*60);
	}
}
