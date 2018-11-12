package com.util;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则匹配、替换工具类
 * @author Administrator
 *
 */
public class RegexUtil {
	private Logger log=Logger.getLogger(this.getClass());
	public static String click_replyPattern = "\\d+[/]\\d+";// 匹配如:444/33等点击率/回帖数信息
	public static String numPattern = "^\\d+(,\\d+)*$";// 匹配数字
	public static String numInStrPattern = "\\d+(,\\d+)*";// 匹配数字
	/**
	 * 正则判断是否匹配
	 * @param str
	 * @param strPattern
	 * @return
	 */
	public static boolean isMatcherRegex(String str, String strPattern){
		if(strPattern==null||strPattern.trim().equals("")){
			return false;
		}
//		System.out.println(str);
		str=str.trim();
		Pattern p = Pattern.compile(strPattern,Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(str);
		return m.find();
	}
	
	/**
	 * 返回正则匹配结果
	 * @param str
	 * @param strPattern
	 * @return
	 */
	public static String getMatcherRegex(String str, String strPattern) {
		Pattern p = Pattern.compile(strPattern,Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(str);
		if(m.find()){
			return m.group(0);
		}else{
			System.out.println("正则匹配失败："+strPattern);
			return null;
		}
	}
	/**
	 * 汉字关键字绝对匹配,如果完全匹配,返回true,不能完全匹配返回FALSE
	 * @param str
	 * @param strPattern
	 * @return
	 */
	public static boolean getCompleteMatcher(String str, String strPattern) {
		if(str==null||str.length()==0||strPattern==null||strPattern.length()==0){
			return false;
		}
		boolean checked = false;
		if(strPattern.indexOf(str)<0){
			return false;
		}else{
			return true;
		}
//		String[] sb=strPattern.split("\\|");
//		for(int i=0;i<sb.length;i++){
//			Pattern p = Pattern.compile(sb[i]);
//			Matcher m = p.matcher(str);
//			if(m.find()){
//				checked = true;
//				break;
//			}
//		}
//		return checked;
	}
	
	/**
	 * 获得正则组匹配的结果列表集合
	 * @param str
	 * @param strPattern
	 * @return
	 */
	public static List<String> getAllMatcherListRegex(String str, String strPattern){
		int matchNum=0;
		Pattern p = Pattern.compile(strPattern,Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(str);
		List<String> result=new ArrayList<String>();
		while(m.find()){
			matchNum++;
			result.add(m.group());
			str=str.substring(m.end());
			m=p.matcher(str);
		}
		return result;
	}
	
	public static List<String> getAllMatcherListRegexGroup1(String str,
			String strPattern,int gourp) {
        int matchNum=0;
		Pattern p = Pattern.compile(strPattern,Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(str);
		List<String> result=new ArrayList<String>();
		while(m.find()){
			matchNum++;
			result.add(m.group(gourp));
//			result.add(m.group(1));
//			str=str.substring(m.end(0));
//			m=p.matcher(str);
		}
		return result;
	}
	
//	/**
//	 * 获得正则组匹配的结果列表集合
//	 * @param str
//	 * @param strPattern
//	 * @return
//	 */
//	public static String[] getAllMatcherArrayRegex(String str, String strPattern){
//		int matchNum=0;
//		String[] results=new String[10];
//		Pattern p = Pattern.compile(strPattern,Pattern.CASE_INSENSITIVE);
//		Matcher m = p.matcher(str);
//		while(m.find()){
//			results[matchNum]=m.group();
//			matchNum++;
//			str=str.substring(m.end());
//			m=p.matcher(str);
//		}
//		return results;
//	}
	
	/**
	 * 获得正则的全部匹配结果
	 * @param plainText
	 * @param strPattern
	 * @return
	 */
	public static String getAllMatchRegex(String str, String strPattern){
		Pattern p = Pattern.compile(strPattern);
		Matcher m = p.matcher(str);
		String result="";
		while(m.find()){
			result=m.group(0);
		}
		return result;
	}
	
	/**
	 * 获得正则的第一个的组匹配结果
	 * @param plainText
	 * @param strPattern
	 * @return
	 */
	public static String getMatchGroupRegex(String str, String strPattern){
		Pattern p = Pattern.compile(strPattern,Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(str);
		String result="";
		if(m.find()){
			result = m.group(1);
		}
		return result;
	}
	
	/**
	 * 正则添加
	 * @param str
	 * @param strPattern
	 * @return
	 */
	public static String addRegex(String str, String strPattern, String strAppend){
		Pattern p = Pattern.compile(strPattern,Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(str);
		StringBuffer sb = new StringBuffer();
		
		while(m.find()){
			m.appendReplacement(sb, m.group()+strAppend);
		}
		m.appendTail(sb);
		return sb.toString();

	}
	
	
	/**
	 * 正则替换
	 * @param str
	 * @param strPattern
	 * @return
	 */
	public static String replaceRegex(String str, String strPattern){
		Pattern p = Pattern.compile(strPattern,Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(str);
		String result=null;
		if(m.find()){
			result=m.replaceAll("");
		}else{
			result=str;
		}
		return result;
	}
	
	
	/**
	 * @param nub 1.文字匹配 2.url匹配 3.正则匹配
	 * @param text
	 * @param newsName
	 * @param newsURL
	 * @return
	 */
	public static boolean make(int blockParserType,String text,String newsName,String newsURL){
		if(blockParserType == 1){
			return textMake(text,newsName);
		}else if(blockParserType == 2){
			return urlMake(text,newsURL);
		}else if(blockParserType == 3){
			return patternMake(text,newsName,newsURL);
		}else{
			return true;
		}
	}
	
	private static boolean textMake(String text,String newsName){
		Pattern p = Pattern.compile(text);
		Matcher m = p.matcher(newsName);
		return m.find();
	}
	
	private static boolean urlMake(String value,String newsURL){
		Pattern p = Pattern.compile(value);
		Matcher m = p.matcher(newsURL);
		return m.find();
	}
	
	private static boolean patternMake(String str,String newsName,String newsURL){
		Pattern p = Pattern.compile(str);
		Matcher m1 = p.matcher(newsName);
		Matcher m2 = p.matcher(newsURL);
		if(m1.find()){
			return true;
		}else{
			return m2.find();
		}
	}
	
	/***
	 * 根据数字正则，寻找数字
	 * **/
	public static String getNomalNumber(String input){
		Pattern p = Pattern.compile(numPattern);
		Matcher m=p.matcher(input);
		if(m.find()){
			return m.group(0);
		}else{
			return null;
		}
	}
	
	/***
	 * 匹配类似3332/111形式的点击回复字符窜
	 * **/
	public static String getReplyClick(String input){
		Pattern p = Pattern.compile(click_replyPattern);
		Matcher m=p.matcher(input);
		if(m.find()){
			return m.group(0);
		}else{
			return null;
		}
	}
	

	public static void main(String[] args)
	{

//		String content = "\\u627e\\u5230 500+ \\u6761\\u7ed3\\u679c<\\/p>\n<\\/div";
//		String regex = "\\u627e\\u5230 \\d+(\\+){0,1} \\u6761\\u7ed3\\u679c";
//		System.out.println(content);
//		System.out.println(RegexUtil.getMatcherRegex(content, regex));
		String content = "2013-06-17 04:22:10　来源:<http://epaper.xiancn.com/xawb/html/2013-06/17/content_214806.htm> 西安晚报(西安)　<#>有0人参与";
		String regex = "<http.*?>\\s*(.*?)<#>";
		Pattern p = Pattern.compile(regex,Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(content);
		if(m.find()){
			System.out.println(m.groupCount());
			System.out.println(m.group(0));
			System.out.println("@"+m.group(1));
		}
		System.out.println(RegexUtil.getMatchGroupRegex(content, regex));
	}
}
