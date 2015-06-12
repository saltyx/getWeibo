package getWeibo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.datatype.DatatypeConstants.Field;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/*
 * this class is to help read simple xml file
 * 
 * Author: saltyx
 * 
 * Time: 2015/6/12
 * 
 */
public class XmlHelper {

	public static File file;
 	public static NodeList _sqlinfo;
	public XmlHelper(String filename) throws ParserConfigurationException, SAXException, IOException
	{
		file = new File(filename);
		
//		System.out.println(file.exists());
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        
        DocumentBuilder builder = factory.newDocumentBuilder();
        
        Document doc = builder.parse(file);
        
        _sqlinfo = doc.getElementsByTagName("text");
        //text结点下所有文本
        
	}
	public ArrayList<String> getSqlConnInfo()
	{
		ArrayList<String> list = new ArrayList<String>();
		
		for (int i=0; i<_sqlinfo.getLength(); i++)
		{
			list.add(_sqlinfo.item(i).getFirstChild().getNodeValue());
		}
		return list;
	}
	public String getValueByTag(String tag)
	{
		String s = "";
		//to do
		
		
		return s;
	}
}
