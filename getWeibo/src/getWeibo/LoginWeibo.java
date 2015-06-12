package getWeibo;

import java.awt.List;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSection;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextArea;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLDivElement;

public class LoginWeibo {
	
	public static String url;
	public static long time = 2000;
	public static String user;
	public static String pwd;
//	public static HtmlPage page_login;
	WebClient wc;
	public LoginWeibo(String u, String p) throws FailingHttpStatusCodeException, MalformedURLException, IOException, InterruptedException
	{
		user = u;
		pwd = p;
//		loginPage();
	}
	private HtmlPage loginPage() throws FailingHttpStatusCodeException, MalformedURLException, IOException, InterruptedException
	{
		url = "https://passport.weibo.cn/signin/login?entry=mweibo&res=wel&wm=3349&r=http%3A%2F%2Fm.weibo.cn%2F";
		/*BrowserSetting*/
		wc = new WebClient(BrowserVersion.CHROME);
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
	    Thread.sleep(time);
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
			Thread.sleep(2000);
		//get textarea
			HtmlTextArea text_content = (HtmlTextArea) page_wr.getElementByName("content");
			text_content.focus();
			text_content.click();
			text_content.type(contents);
			wc.waitForBackgroundJavaScript(2000);
			
			HtmlAnchor button_send = page_wr.getAnchorByText("发送");
			HtmlPage tmp = button_send.click();
			Thread.sleep(2000);
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
		Thread.sleep(2000);
		//wc.waitForBackgroundJavaScript(1000);
		//等待js执行
		HtmlAnchor button_follow = page_me.getAnchorByHref("/page/tpl?containerid=1005051736530187_-_FOLLOWERS");
		HtmlPage page_follow = button_follow.click();
		Thread.sleep(2000);
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
	public ArrayList<INFO> getMainPageInfo() throws FailingHttpStatusCodeException, MalformedURLException, IOException, InterruptedException{
		
		ArrayList<INFO> array = new ArrayList<INFO>();
		HtmlPage page_main_init = loginPage();
		//click the main page
		HtmlAnchor button_mainpage = (HtmlAnchor) page_main_init.getByXPath("//a[@data-text=\"首页\"]").get(0);
		HtmlPage page_main = button_mainpage.click();
		//get the updated main page
		Thread.sleep(1000);
//		System.out.println(page_main.asXml());
		@SuppressWarnings("unchecked")
		ArrayList<HtmlDivision> list_div = (ArrayList<HtmlDivision>) page_main.getByXPath("//div[@class=\"card card9 line-around\"]");
		//get the division
		System.out.println(list_div.size());
		int index = 0;
		for(HtmlDivision div: list_div)
		{
			INFO info = new INFO();
			//获取昵称
			HtmlSpan span_text = (HtmlSpan) div.getByXPath("//a[@class=\"item-main txt-l mct-a txt-cut\"]/span").get(index);
			HtmlSection sec = (HtmlSection) div.getByXPath("//section[@class=\"weibo-detail\"]").get(index);
			HtmlSpan span_time = (HtmlSpan) div.getByXPath("//span[@class=\"time\"]").get(index);
			HtmlSpan span_from = (HtmlSpan) div.getByXPath("//span[@class=\"from\"]").get(index++);
			System.out.println(span_text.asText());
			System.out.println(sec.asText());
			String x = span_time.asText().substring(0, span_time.asText().length()-3);
			System.out.println(span_time.asText().substring(0, span_time.asText().length()-3));
			System.out.println(span_from.asText().substring(2, span_from.asText().length()));
			System.out.println(TimeHelper.subTime(x));
			
			String user = span_text.asText();
			String contents = sec.asText();
			String posttime = TimeHelper.subTime(x);
			String device = span_from.asText().substring(2, span_from.asText().length());
			String SQL = "insert into mainPage_WeiboInfo(username,contents,posttime,device) values('"+ user+"','"+contents + "','"+posttime+"','"+device+"')";
			SqlHelper sqlhelper = new SqlHelper();
			sqlhelper.excuteSql(SQL);
		}
		return array;
	}
	
	
}
