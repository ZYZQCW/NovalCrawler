package com.zyzq.zhuixu;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.tags.LinkTag;

public class LinkStartFilter implements NodeFilter {

	private static final long serialVersionUID = -3689704206995968035L;

	//过滤字符串参数
	protected String mPattern;

	//标识符，用于判断是否需要大小写
	protected boolean mCaseSensitive;

	//单参构造函数，不严格要求大小写
	public LinkStartFilter(String pattern) {
		this(pattern, false);
	}

	//双参构造函数，需要指定是否判断大小写
	public LinkStartFilter(String pattern, boolean caseSensitive) {
		mPattern = pattern;
		mCaseSensitive = caseSensitive;
	}

	//实现接口NodeFilter中的方法
	public boolean accept(Node node) {
		boolean ret;

		ret = false;
		if (LinkTag.class.isAssignableFrom(node.getClass())) {
			String link = ((LinkTag) node).getLink();
			if (mCaseSensitive) {
				if (link.startsWith(mPattern))
					ret = true;
			} else {
				if (link.toUpperCase().startsWith(mPattern.toUpperCase()))
					ret = true;
			}
		}

		return (ret);
	}
}
