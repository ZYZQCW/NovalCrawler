package com.zyzq.zhuixu.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;

import com.zyzq.zuixu.been.Chapter;

public class ChapterLinkAndXml {
	/**
	 * 
	 * @param path	文件路径
	 * @param list	网页Link结点集合
	 */
	public static void WriteToXml(String path, NodeList list) {
        System.out.println("进入写入XML文件方法成功-------！！");
		Document document = DocumentHelper.createDocument();
		Element links = document.addElement("Links");
		Element link;
		Element name;
		Element address;
        for (int i = 0; i < list.size (); i++)
        {
            LinkTag linkTag = (LinkTag)list.elementAt (i);
            
    		link = links.addElement("link");
    		
    		name = link.addElement("name");
    		name.setText(linkTag.getLinkText ());
    		
    		address = link.addElement("address");
    		address.setText(linkTag.getLink ());
        }
        Writer fileWriter;
        XMLWriter xmlWriter = null;
		try {
			fileWriter = new FileWriter(path);
			xmlWriter = new XMLWriter(fileWriter);
			xmlWriter.write(document);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}finally{
			try {
				if(xmlWriter!=null){
					xmlWriter.close();
			}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
        System.out.println("写入成功-------！！");
	}

	/**
	 * 
	 * @param path XML文件路径
	 * @return	返回Chapter集合
	 */
	public static List<Chapter> FetchFromXml(String path) {
		System.out.println("进入读取XML文件方法成功-------！！");
		List<Chapter> list = new ArrayList<Chapter>();
		Chapter chapter;
		File inputXml = new File(path);
		SAXReader saxReader = new SAXReader();
		
		try {
			Document document = saxReader.read(inputXml);
			Element Links = document.getRootElement();
			for (Iterator<Element> linkIterator = Links.elementIterator(); linkIterator.hasNext();) {
				
				Element link = linkIterator.next();
				chapter = new Chapter();
				
				for (Iterator<Element> chapterIterator = link.elementIterator(); chapterIterator.hasNext();) {
					Element node = chapterIterator.next();
					
					if("name".equals(node.getName())){
						chapter.setName(node.getText());
					}
					
					if("address".equals(node.getName())){
						chapter.setLink(node.getText());
					}
				}
				list.add(chapter);
			}
		} catch (DocumentException e) {
			System.out.println(e.getMessage());
		}
		System.out.println("读取XML文件成功-------！！");
		return list;

	}
}

