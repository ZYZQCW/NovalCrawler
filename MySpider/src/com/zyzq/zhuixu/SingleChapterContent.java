package com.zyzq.zhuixu;

import java.net.URL;
import java.net.URLConnection;

import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.nodes.TextNode;
import org.htmlparser.tags.Div;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

/**
 * 
 * @author zyzq
 * @intro  该类作用为测试提取单章内容
 */
public class SingleChapterContent {
	public static void main(String[] srgs) {
		//定义解析器
	    Parser parser = new Parser();
	    URLConnection connection = null;
	    
	    //定义赘婿在笔趣网的第X章地址
		final String adress = "http://www.biquge.tw/1_1287/794406.html";
		
		try {
			connection = new URL(adress).openConnection();
			//设置解析器的参数
			parser.setConnection(connection);
			parser.setEncoding("utf-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//抓取DIV标签
		TagNameFilter nameFilter = new TagNameFilter("div");
		//抓取含有属性id=content的标签
		HasAttributeFilter attributeFilter = new HasAttributeFilter("id","content");
		//与过滤器
		AndFilter andFilter = new AndFilter(nameFilter, attributeFilter);
		
        //执行抓取
        NodeList list = null;
		try {
			list = parser.extractAllNodesThatMatch(andFilter);
		} catch (ParserException e) {
			e.printStackTrace();
		}
        
		StringBuffer content = new StringBuffer();
        //遍历抓取到的DIV
        for (int i = 0; i < list.size (); i++)
        {
        	Div divTag = (Div)list.elementAt (i);
        	//遍历DIV标签的子结点
        	NodeList divContent = divTag.getChildren();
        	for(int j = 0; j < divContent.size (); j++){
        		Node node = divContent.elementAt(j);
        		//如果子结点属于文本结点
        		if(node instanceof TextNode){
        			TextNode text = (TextNode)node;
        			content.append(text.getText().trim().replaceAll("&nbsp;", "-"));
        		}
        		//子节点属于标签节点，<br/>
        		if(node instanceof TagNode){
        			content.append("\n");
        		}
        	}
        }
        System.out.println(content);
	}
}
