package getWeibo;

import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class showPic extends JPanel {

	
	
	public static void show(String url) throws MalformedURLException
	{
		ImageIcon icon = new ImageIcon(new URL("http://weibo.cn/interface/f/ttt/captcha/show.php?cpt=2_2ff3771794fd9655"));
        JLabel label = new JLabel(icon);
 
        JFrame frame = new JFrame();
        frame.getContentPane().add(label);
 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(50, 50);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
	}
}
