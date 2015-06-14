package getWeibo;

import java.awt.List;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

import javax.swing.JLabel;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.CookieManager;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlImage;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSection;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTextArea;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLDivElement;
import com.gargoylesoftware.htmlunit.javascript.host.html.Image;

public class LoginWeibo {
	
	public static String url;
	public static long time = 2000;
	public static String user;
	public static String pwd;
	public String weibo = "http://m.weibo.cn";
	public boolean flag;
	public int count;
	public int key;
//	public static HtmlPage page_login;
	WebClient wc;
	public LoginWeibo(String u, String p) throws FailingHttpStatusCodeException, MalformedURLException, IOException, InterruptedException
	{
		key = 1;
		flag = true;
		user = u;
		pwd = p;
		count = 0;
//		loginPage();
	}
	private HtmlPage loginPage() throws FailingHttpStatusCodeException, MalformedURLException, IOException, InterruptedException
	{
		url = "https://passport.weibo.cn/signin/login?entry=mweibo&res=wel&wm=3349&r=http%3A%2F%2Fm.weibo.cn%2F";
		/*BrowserSetting*/
		wc = new WebClient(BrowserVersion.FIREFOX_38);
		wc.getOptions().setCssEnabled(false);
	    wc.getOptions().setThrowExceptionOnScriptError(false);   
	    wc.setAjaxController(new NicelyResynchronizingAjaxController());  
	    
	    HtmlPage page_login = wc.getPage(url);
	    HtmlTextInput u = (HtmlTextInput) page_login.getElementById("loginName");
	    HtmlPasswordInput p = (HtmlPasswordInput) page_login.getElementById("loginPassword");
	    
	    u.type(user);
	    p.type(pwd);
	    
	    HtmlAnchor button_ok = (HtmlAnchor) page_login.getElementById("loginAction");
	    
	    HtmlPage page_logined = button_ok.click();
	    Thread.currentThread().sleep(1000);
//	    page_login = page_logined;
	    System.out.println("登录成功");
	    return page_logined;
	}
	public void PostWeiBo(String contents)
	{
		try {
			HtmlPage page_init = loginPage();
			if (page_init == null)
				return;
			HtmlAnchor button_wr = page_init.getAnchorByHref("/mblog");
			HtmlPage page_wr = button_wr.click();
			Thread.currentThread().sleep(1000);
		//get textarea
			HtmlTextArea text_content = (HtmlTextArea) page_wr.getElementByName("content");
			text_content.focus();
			text_content.click();
			text_content.type(contents);
			wc.waitForBackgroundJavaScript(2000);
			
			HtmlAnchor button_send = page_wr.getAnchorByText("发送");
			HtmlPage tmp = button_send.click();
			Thread.currentThread().sleep(1000);
			System.out.println("发送成功");
			
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
	public ArrayList<String> GetInfo_follow() throws IOException, InterruptedException
	{
		HtmlAnchor button_me = (HtmlAnchor) loginPage().getByXPath("//a[@data-action=\"profile\"]").get(0);
		HtmlPage page_me = button_me.click();
		Thread.currentThread().sleep(1000);
		//wc.waitForBackgroundJavaScript(1000);
		//等待js执行
		HtmlAnchor button_follow = page_me.getAnchorByHref("/page/tpl?containerid=1005051736530187_-_FOLLOWERS");
		HtmlPage page_follow = button_follow.click();
		Thread.currentThread().sleep(1000);
		//获得关注页
//		HtmlDivision button_more = (HtmlDivision) page_follow.getByXPath("//div[@class=\"loading\"]").get(0);
//		HtmlPage page_more = button_more.click();
//		wc.waitForBackgroundJavaScript(2000);
//		System.out.println(page_more.asText());
		@SuppressWarnings("unchecked")
		ArrayList<HtmlDivision> div_follow = (ArrayList<HtmlDivision>) page_follow.getByXPath("//div[@class=\"item-main txt-m mct-a txt-cut\"]");
		
		ArrayList<String> array = new ArrayList<String>();
		for(HtmlDivision div : div_follow)
		{
			array.add(div.asText());
		}
		return array;
		
	}
	public void getMainPageInfo() throws FailingHttpStatusCodeException, MalformedURLException, IOException, InterruptedException{
		
//		ArrayList<INFO> array = new ArrayList<INFO>();
		HtmlPage page_main_init = loginPage();
		//click the main page
		HtmlAnchor button_mainpage = (HtmlAnchor) page_main_init.getByXPath("//a[@data-text=\"首页\"]").get(0);
		HtmlPage page_main = button_mainpage.click();
		//get the updated main page
		Thread.currentThread().sleep(1000);
//		System.out.println(page_main.asXml());
		@SuppressWarnings("unchecked")
		ArrayList<HtmlDivision> list_div = (ArrayList<HtmlDivision>) page_main.getByXPath("//div[@class=\"card card9 line-around\"]");
		//get the division
		System.out.println(list_div.size());
		int index = 0;
		for(int i=0 ;i <list_div.size();i++)
		{
//			HtmlDivision div = list_div.get(i);
//			System.out.println(div.asText());
//			INFO info = new INFO();
			//获取昵称
			if(i >= (list_div.get(i).getByXPath("//a[@class=\"item-main txt-l mct-a txt-cut\"]/span")).size() ||
					i>=list_div.get(i).getByXPath("//section[@class=\"weibo-detail\"]").size() ||
					i>= list_div.get(i).getByXPath("//span[@class=\"from\"]").size())
				break;
			HtmlSpan span_text = (HtmlSpan) (list_div.get(i).getByXPath("//a[@class=\"item-main txt-l mct-a txt-cut\"]/span")).get(i);
			
			HtmlSection sec = (HtmlSection) list_div.get(i).getByXPath("//section[@class=\"weibo-detail\"]").get(i);
			HtmlSpan span_time = (HtmlSpan) list_div.get(i).getByXPath("//span[@class=\"time\"]").get(i);
			HtmlSpan span_from = (HtmlSpan) list_div.get(i).getByXPath("//span[@class=\"from\"]").get(i);
//			System.out.println(span_text.asXml());
//		System.out.println(sec.asText());
			String x = span_time.asText().substring(0, span_time.asText().length()-3);
/*			System.out.println(span_time.asText().substring(0, span_time.asText().length()-3));
			System.out.println(span_from.asText().substring(2, span_from.asText().length()));
			System.out.println(TimeHelper.subTime(x));
			*/
			String user = span_text.asText();
			String contents = sec.asText();
			String posttime = TimeHelper.subTime(x);
			String device = span_from.asText().substring(2, span_from.asText().length());//break;
			String SQL = "insert into mainPage_WeiboInfo(username,contents,posttime,device) values('"+ user+"','"+contents + "','"+posttime+"','"+device+"')";
			SqlHelper sqlhelper = new SqlHelper();
			sqlhelper.excuteSql(SQL);
		}
		index = 0;
		//return array;
	}
	
	//从关注入手爬去数据，设计url队列，层次爬去，可能有重复，将会调用sqlhelper执行去重操作
	public void getWeiboInfo() throws FailingHttpStatusCodeException, MalformedURLException, IOException, InterruptedException
	{
		HtmlPage page_login = loginPage();wc.waitForBackgroundJavaScript(4000);//Thread.sleep(4000);//
		
		//获取'我'的页面
		HtmlAnchor button_me = (HtmlAnchor) page_login.getByXPath("//a[@data-text=\"我\"]").get(0);
		
		HtmlPage page_me = button_me.click();
		Thread.sleep(2000);
//		System.out.println(page_me.asXml());
		HtmlDivision div_fol = (HtmlDivision) page_me.getByXPath("//div[@class=\"mct-a txt-s txt-bottom\"]").get(1);
		HtmlPage page_fol = div_fol.click();
		Thread.sleep(2000);
		
//		System.out.println(page_fol.asXml());
		
		Queue<String> queue = new LinkedList<String>();
		queue.clear();
//		System.out.println(page_fol.asXml());
		ArrayList<HtmlAnchor> all = (ArrayList<HtmlAnchor>) page_fol.getByXPath("//a[@class=\"mod-media size-m\"]");
		System.out.println(all.size());
		if (all.size() == 0)
			Thread.currentThread().stop();
		for(HtmlAnchor div_tmp : all)
		{
			String Url = div_tmp.getAttribute("href");
			Url = "http://m.weibo.cn" + Url;
			queue.offer(Url);
		}
		//先把粉丝url 入队列，之后爬去自身的所有微博数据
		String Url;
		while((Url = queue.poll())!=null)
		{
			HtmlPage page_m = getPage(Url);
			//获取一个关注页
			//之后获取关注的关注页
			HtmlAnchor a = (HtmlAnchor) page_m.getByXPath("//a[@class=\"box-col line-separate\"]").get(2);
			HtmlPage page_sub = a.click();
			Thread.currentThread().sleep(1000);
//			System.out.println(page_sub.asText());
//			break;
			ArrayList<HtmlAnchor> list = (ArrayList<HtmlAnchor>) page_sub.getByXPath("//a[@class=\"mod-media size-m\"]");
			
			for(HtmlAnchor l : list)
			{
				String tmp = l.getAttribute("href");
				tmp = weibo+tmp;
				queue.offer(tmp);
//				System.out.println(tmp);
				
			}
			//之后要获取 page_m的前十个微博
			HtmlAnchor a1 = (HtmlAnchor) page_m.getByXPath("//a[@class=\"box-col line-separate\"]").get(1);
			HtmlPage page_weibo = a1.click();
			Thread.currentThread().sleep(1000);
			
			
			
			/*****************************************************************/
			@SuppressWarnings("unchecked")
			ArrayList<HtmlDivision> list_div = (ArrayList<HtmlDivision>) page_weibo.getByXPath("//div[@class=\"card card9 line-around\"]");
			//get the division
			System.out.println(list_div.size());
			if(list_div.size() == 0)
			{
				System.out.println(page_weibo.asText());
			}
			int index = 0;
			for(int i=0 ;i <list_div.size();i++)
			{
				//获取昵称
				if(i >= (list_div.get(i).getByXPath("//a[@class=\"item-main txt-l mct-a txt-cut\"]/span")).size() ||
						i>=list_div.get(i).getByXPath("//section[@class=\"weibo-detail\"]").size() ||
						i>= list_div.get(i).getByXPath("//span[@class=\"from\"]").size())
					break;
				HtmlSpan span_text = (HtmlSpan) (list_div.get(i).getByXPath("//a[@class=\"item-main txt-l mct-a txt-cut\"]/span")).get(i);
				
				HtmlSection sec = (HtmlSection) list_div.get(i).getByXPath("//section[@class=\"weibo-detail\"]").get(i);
				HtmlSpan span_time = (HtmlSpan) list_div.get(i).getByXPath("//span[@class=\"time\"]").get(i);
				HtmlSpan span_from = (HtmlSpan) list_div.get(i).getByXPath("//span[@class=\"from\"]").get(i);
//				System.out.println(span_text.asXml());
//				System.out.println(sec.asText());
				
/*				System.out.println(span_time.asText().substring(0, span_time.asText().length()-3));
				System.out.println(span_from.asText().substring(2, span_from.asText().length()));
				System.out.println(TimeHelper.subTime(x));
*/				//需要对时间特殊处理
				//如果span_time字符串前两位为’今天‘，则处理’今天‘，如果字符串长度为11或者更大直接处理
				String cur = span_time.asText();

//				String x = span_time.asText().substring(0, span_time.asText().length()-3);
				String posttime = TimeHelper.dealTime(cur);
				String user = span_text.asText();
				String contents = sec.asText();				
				String device = span_from.asText().substring(2, span_from.asText().length());
//				System.out.println(user);
//				System.out.println(posttime);
//				System.out.println(device);
//				System.out.println(contents);
//				break;
				String SQL = "insert into mainPage_WeiboInfo(username,contents,posttime,device) values('"+ user+"','"+contents + "','"+posttime+"','"+device+"')";
				SqlHelper sqlhelper = new SqlHelper();
				sqlhelper.excuteSql(SQL);
			}
			/*********************************/
//			break;
		}
	}
	//另一个微博手机入口
	public HtmlPage loginweibo() throws FailingHttpStatusCodeException, MalformedURLException, IOException, InterruptedException
	{
		String ip = "203.208.41.153";
		int port = 443;
		wc = new WebClient(BrowserVersion.CHROME);
		wc.getOptions().setCssEnabled(false);
	    wc.getOptions().setThrowExceptionOnScriptError(false);   
	    wc.setAjaxController(new NicelyResynchronizingAjaxController());  
	    wc.getCookieManager().setCookiesEnabled(false);
	    wc.getOptions().setJavaScriptEnabled(true);
	    url = "http://login.weibo.cn/login/?ns=1&revalid=2&backURL=http%3A%2F%2Fweibo.cn%2F&backTitle=%CE%A2%B2%A9&vt=";
	    System.out.println(wc.getBrowserVersion()+"\n"+wc.getOptions().getProxyConfig().getProxyHost()+":"+wc.getOptions().getProxyConfig().getProxyPort());
	    URL link = new URL(url);
	    WebRequest request = new WebRequest(link);
//	    request.setAdditionalHeader("User-Agent", "Mozilla/5.0 (Windows NT 5.1; rv:6.0.2) Gecko/20100101 Firefox/6.0.2");
//	    wc.addRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 5.1; rv:6.0.2) Gecko/20100101 Firefox/6.0.2");
//	    request.setCharset("UTF-8");
//	    request.setProxyHost("202.120.121.49");
//	    request.setProxyPort(8080);
		HtmlPage init = wc.getPage(url);
//		System.out.println(init.asXml());
		HtmlTextInput in_u = init.getElementByName("mobile");
		HtmlPasswordInput in_p = (HtmlPasswordInput) init.getElementsByTagName("input").get(1);
		
		in_u.type(user);
		in_p.type(pwd);
		
		HtmlSubmitInput button_ok = init.getElementByName("submit");
		
		HtmlPage res = button_ok.click();
		Thread.sleep(1000);
//		System.out.println(res.asText());
		return res;
	}
//	@SuppressWarnings("static-access")
	public void getweiboinfo() throws FailingHttpStatusCodeException, MalformedURLException, IOException, InterruptedException, SQLException
	{
		HtmlPage loginpage = loginweibo();
//		System.out.println(loginpage.asXml());
/*		if (loginpage.getByXPath("//div[@class=\"tip2\"]/a[2]").size() == 0)
		{
			//登录失败
			//尝试输入验证码
			//获取验证码
			System.out.println(loginpage.asXml());
			String image = loginpage.getElementsByTagName("img").get(0).getAttribute("src");
//			loginpage = wc.getPage(image);
//			wc.download(arg0, arg1, arg2, arg3, arg4);
			System.out.println(image);
			JLabel label=new JLabel("验证码");
			label.setText("<html>验证码<img src='"+image+"' /><html>");
			
			Scanner in = new Scanner(System.in);
			String input = in.next();
			HtmlPasswordInput p = (HtmlPasswordInput) loginpage.getElementsByTagName("input").get(1);
			p.focus();p.click();
			p.type(pwd);
			HtmlTextInput code = loginpage.getElementByName("code");
			code.type(input);
			HtmlSubmitInput button_submit = loginpage.getElementByName("submit");
			loginpage = button_submit.click();
			Thread.sleep(2000);
			System.out.println(loginpage.asText()+"\n");
		}*/
		HtmlAnchor list = (HtmlAnchor) loginpage.getByXPath("//div[@class=\"tip2\"]/a[2]").get(0);
		//HtmlAnchor follow = 
		HtmlPage concern = list.click();
		Thread.sleep(1000);
		
		
		Queue<String> queue = new LinkedList<String>();
		ArrayList<DomElement> table = (ArrayList<DomElement>) concern.getByXPath("//table");
		int i = 1;
		for(DomElement d : table )
		{
			String index = String.valueOf(i++);
			HtmlAnchor a = (HtmlAnchor) d.getByXPath("//table["+index+"]/tbody/tr/td/a").get(0);
//			System.out.println( a.getAttribute("href") );
			queue.offer(a.getAttribute("href"));
		}//装入我的关注url
		
		String tmp_url = null;
		while( (tmp_url = queue.poll())!=null && flag)
		{
			HtmlPage nowpage = wc.getPage(tmp_url);
//			System.out.println(nowpage.asText());
			HtmlAnchor follow = (HtmlAnchor) nowpage.getByXPath("//div[@class=\"tip2\"]/a").get(0);
			HtmlPage followpage = follow.click();
			Thread.sleep(2000);
			
			ArrayList<DomElement> ta = (ArrayList<DomElement>) followpage.getByXPath("//table");
			int ii = 1;
			for(DomElement d : ta )
			{
				String index = String.valueOf(ii++);
				HtmlAnchor a = (HtmlAnchor) d.getByXPath("//table["+index+"]/tbody/tr/td/a").get(0);
//				System.out.println( a.getAttribute("href") );
				queue.offer(a.getAttribute("href"));
			}//装入我的关注url
			//url装入完毕  下一步 提取原创微博
			ArrayList<DomElement> divs = (ArrayList<DomElement>) nowpage.getByXPath("//div[@class=\"c\"]");
			int j=1;
			HtmlSpan users = (HtmlSpan) nowpage.getByXPath("//span[@class=\"ctt\"]").get(0); 
			for(DomElement div : divs)
			{
				String index = String.valueOf(j++);

				if (div.getByXPath("//div[@class=\"c\"]["+index+"]/div/span[@class=\"ct\"]").size() != 0
						&& div.getByXPath("//div[@class=\"c\"]["+index+"]/div").size() !=0) 
				{
					HtmlDivision text = (HtmlDivision) div.getByXPath("//div[@class=\"c\"]["+index+"]/div").get(0);

					HtmlSpan info2 = (HtmlSpan) div.getByXPath("//div[@class=\"c\"]["+index+"]/div/span[@class=\"ct\"]").get(0);
					String []info = info2.asText().split(" ");
					String[] user = users.asText().split(" ");//包含性别和位置,目前取昵称

					handlesql(user[0], text.asText(), info2.asText());//break;
				}
			}
//			break;
		}
		
	}
	
	
	public void handlesql(String user,String contents, String other) throws InterruptedException, SQLException
	{
		String [] info = other.split("来自");
		String device = "";
		if (info.length == 1)
		{
			device = "微博 weibo.com";
		}
		else  device = info[1];
		String date = info[0];
		String posttime = "";
		if (date.indexOf("分钟前") != -1 || date.indexOf("今天") != -1)
		{
			posttime = TimeHelper.dealTime(date);
		}
		else if (date.indexOf("日") != -1 || date.indexOf("月")!=-1)
		{
			date = date.replace('月', '-');
			date = date.replace('日', ' ');
//			System.out.println(date);
			posttime = TimeHelper.dealTime(date);
		}
		else posttime = TimeHelper.dealTime(date);
		
//		System.out.println(device+"\n"+posttime);
		Thread.sleep(20);
		String SQL = "insert into mainPage_WeiboInfo(username,contents,posttime,device) values('"+ user+"','"+contents + "','"+posttime+"','"+device+"')";
		SqlHelper sqlhelper = new SqlHelper();
		sqlhelper.excuteSql(SQL);
//		System.out.println(user+"\n"+info[0]);
		if (count >= 500*key)
		{
			key++;
			sqlhelper.RemoveTheSameRec("mainPage_WeiboInfo");
		}
		if (count >= 20000)
			flag = false;
		count ++;
		
	}
	
	
	public HtmlPage getPage(String url) throws InterruptedException, FailingHttpStatusCodeException, IOException
	{
		//HtmlPage res=null;
		HtmlPage res = wc.getPage(url);
		
		return res;
	}
}
