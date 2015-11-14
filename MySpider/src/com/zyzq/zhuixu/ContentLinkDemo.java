package com.zyzq.zhuixu;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

/*
* 过滤小说目录链接的示例代码
* 
* 
*/
public class ContentLinkDemo {
	
    public static void main (String[] args) throws ParserException
    {
    	//定义解析器
        Parser parser = new Parser();
        URLConnection connection = null;
        
        //定义赘婿在笔趣网的目录地址
		final String adress = "http://www.biquge.tw/1_1287/";
		
		try {
			connection = new URL(adress).openConnection();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//设置解析器的参数
        parser.setConnection(connection);
        parser.setEncoding("utf-8");
        
        //定义过滤器一：抓取链接标签
        NodeFilter filter = new NodeClassFilter (LinkTag.class);
        //定义过滤器二：抓取标签开头为XXX的标签
        LinkStartFilter startFilter = new LinkStartFilter(adress);
        //定义过滤器二：逻辑与过滤器
        AndFilter andFilter = new AndFilter(filter, startFilter);
        
        //执行抓取
        NodeList links = parser.extractAllNodesThatMatch(andFilter);
        
        //遍历抓取结果
        for (int i = 0; i < links.size (); i++)
        {
            LinkTag linkTag = (LinkTag)links.elementAt (i);
            System.out.print ("\"" + linkTag.getLinkText () + "\" => ");
            System.out.println (linkTag.getLink ());
        }
    }
}
