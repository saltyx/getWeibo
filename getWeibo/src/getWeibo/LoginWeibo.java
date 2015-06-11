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
	public ArrayList GetInfo_follow() throws IOException, InterruptedException
	{
		List lists = new List();
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
		ArrayList<HtmlDivision> div_follow = (ArrayList<HtmlDivision>) page_follow.getByXPath("//div[@class=\"item-main txt-m mct-a txt-cut\"]");
		
		ArrayList<String> array = new ArrayList<String>();
		for(HtmlDivision div : div_follow)
		{
			array.add(div.asText());
		}
		return array;
	}
}
