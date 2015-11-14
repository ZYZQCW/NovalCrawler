package com.zyzq.zhuixu.test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import com.zyzq.zhuixu.LinkStartFilter;
import com.zyzq.zhuixu.been.Chapter;
import com.zyzq.zhuixu.util.ChapterLinkAndXml;

public class TestChapterLinkAndXml {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//1.测试写入XML文件
//		try {
//			ChapterLinkAndXml.WriteToXml("D:/test1.xml", getNodelist());
//		} catch (ParserException e) {
//			e.printStackTrace();
//		}
		//2.测试从XML文件读取
		List<Chapter> links = ChapterLinkAndXml.FetchFromXml("D:/test1.xml");
		
        for (int i = 0; i < links.size (); i++)
        {
            Chapter chapter = (Chapter)links.get(i);
            System.out.print ("\"" + chapter.getName()+ "\" => ");
            System.out.println (chapter.getLink());
        }
	}
	
	public static NodeList getNodelist() throws ParserException{
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
        
        System.out.println("返回成功-------！！");
        return links;
	}

}
