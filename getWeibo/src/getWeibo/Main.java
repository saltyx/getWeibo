package getWeibo;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Scanner;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;

public class Main {

	public static void main(String[] args) throws FailingHttpStatusCodeException, MalformedURLException, IOException, InterruptedException {
		// TODO Auto-generated method stub
		Scanner scan = new Scanner(System.in);
		System.out.println(" ‰»Î’ ∫≈:\n");
		String u = scan.nextLine();
		System.out.println(" ‰»Î√‹¬Î:\n");
		String p = scan.nextLine();
		LoginWeibo test = new LoginWeibo(u,p);
		test.GetInfo_follow();
	}

}
