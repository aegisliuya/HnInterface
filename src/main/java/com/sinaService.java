package com;


import com.engine.model.beans.persist.opinion.yiNet;
import com.util.RegexUtil;
import com.web.Response;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/*
 * 对 WeiboService 请求做相应
 * Created by LiuYa on 2018/8/30.处理
 */

@RestController
public class sinaService {
  private static Log log = LogFactory.getLog(com.controller.SinaService.class);
  private static Map<String, String> headers = new HashMap<>();
  @ResponseBody
  @RequestMapping(value = "sina/service", method = RequestMethod.GET)
  public Object getWebInfo(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException {
//    response.setContentType("text/javascript json");
    response.setContentType("application/json; charset=utf-8");
//    Set<yiNet>  returnSet = new HashSet<>();;
    String url = request.getParameter("url");
    Integer  page= Integer.valueOf(request.getParameter("currentpage"));
    Integer SourceId= Integer.valueOf(request.getParameter("sourceid"));




//    String newsid = url.contains("doc-i") ? "comos-"+url.substring(url.indexOf("doc-i")+5,url.lastIndexOf(".")) : "comos-"+url.substring(url.indexOf("detail-i")+5,url.lastIndexOf("."));
////    String   newsid="comos-"+url.substring(url.indexOf("doc-i")+5,url.lastIndexOf("."));
//    System.out.println("newsid："+newsid);
////     int  commmNum  = Integer.parseInt(getSinaNum( newsid, url, page, SourceId  ));
//    String channel =  getSinaChneel(newsid,url,1);
//    String body = getSinaSource(  newsid,   url,  page , channel);

    String htmlCode = getHtml(url);
    String channelRegex = "channel: '([a-z]+)',";
    String newsIdRegex = "newsid: '([\\w-]+)',";
    String channel = RegexUtil.getMatchGroupRegex(htmlCode, channelRegex);
    String newsid = RegexUtil.getMatchGroupRegex(htmlCode, newsIdRegex);
    String commentUrl = "http://comment5.news.sina.com.cn/page/info?version=1&format=js&channel=" + channel + "&newsid=" + newsid;
    String body = getHtml(commentUrl).replace("var data=","");
    if(body.contains("Catch exception")){
      return new Response("400", "FAILD");
    }else  {
      String commentnum ="";
      if (body.startsWith("{")&&body.contains("count")) {
        JSONObject  object = new JSONObject(body);
        JSONObject result = object.getJSONObject("result");
        JSONObject count = result.getJSONObject("count");
        commentnum = count.get("show").toString();
        System.out.println("总评论数："+commentnum);
      }
      List<yiNet> linkList= com.controller.SinaService.getSinaList(newsid,url,page,SourceId,channel,body);
      return new Response("200", "OK",page,1, linkList);
    }
  }

  static  String  getSinaNum(String newsid,String  articleurl,Integer page,Integer SourceId  ,String channel) throws IOException {
    JSONObject object;
    String body = getSinaSource(  newsid,   articleurl,  page , channel);
    String commentnum ="";
    if (body.startsWith("{")&&body.contains("count")) {
      object = new JSONObject(body);
      JSONObject result = object.getJSONObject("result");
      JSONObject count = result.getJSONObject("count");
      commentnum = count.get("show").toString();
    }

    return  commentnum;
  }


  static  List<yiNet>  getSinaList(String newsid, String  articleurl, Integer page, Integer SourceId , String  channel , String  body ) throws IOException, ParseException {
    List<yiNet> linkList = new ArrayList<>();
    JSONObject object;
    JSONArray searchResult;
    if (body.startsWith("{")) {
      object = new JSONObject(body);
      JSONObject result = object.getJSONObject("result");
      JSONObject count = result.getJSONObject("count");
      Object commentnum = count.get("show");
      JSONArray cmntlist = result.getJSONArray("cmntlist");
//        searchResult = (JSONArray) cmntlist.get("cmntlist");
      for (int ii = 0; ii < cmntlist.length(); ii++) {
        yiNet yinet = new yiNet();
        JSONObject data = (JSONObject) cmntlist.get(ii);
        Object content =  data.get("content");
        Object nick =  data.get("nick");
        System.out.println("----------- " + data.get("against"));
        String against = data.get("against").toString();
        Integer newagainst = Integer.valueOf(against);
        Object mid =  data.get("mid");
        String vote =  data.get("vote").toString();
        yinet.setAuthor(nick.toString().trim());
        yinet.setCommentId(mid.toString().trim());
        yinet.setUrl(articleurl);
        yinet.setNewsId(newsid);
        SimpleDateFormat  format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String d = format.format(newagainst);
        Date  date=format.parse(d);
        yinet.setReleaseTime(date);
        yinet.setSourceId(SourceId);
        yinet.setParams("");
        yinet.setContent(content.toString().trim());
        yinet.setVote(Integer.valueOf(vote) );
        linkList.add(yinet);
      }
    }

    return  linkList;
  }

  static  String getSinaSource(String newsid,String  articleurl,Integer page,String   channel )throws IOException {
    String url = "http://comment5.news.sina.com.cn/page/info?version=1&format=js&channel="+channel+"&newsid="+newsid+"&group=0&compress=0&ie=gbk&oe=gbk&page="+page+"&page_size="+page*20+"&jsvar=loader_1538277850336_64346616";
    //获取请求连接
    Integer timeout = 50000;
    Connection con = Jsoup.connect(url).timeout(timeout);
    //遍历生成参数
    con.ignoreContentType(true);
    String body = con.get().text();
    body=body.substring(body.indexOf("result")-2);
    System.out.println("----------- " + body);
    return body;
  }


  static  String getSinaChneel(String newsid,String  articleurl,Integer page)throws IOException {
    String url = articleurl;
    Integer timeout = 50000;
    Connection con = Jsoup.connect(url).timeout(timeout);
    con.ignoreContentType(true);
    String body = con.get().toString();
    body=body.substring(body.indexOf("channel:")-2,body.indexOf("channel:")+17);
    String  channel= body.substring(body.indexOf(":")+3,body.lastIndexOf(",")-1);
    return channel;
  }









  public static void main(String[] args) throws IOException {

    test84();

  }


  private static void test84() {
    String url = "https://finance.sina.com.cn/chanjing/gsnews/2018-10-11/doc-ihmhafiq7827820.shtml";
    String htmlCode = getHtml(url);
    String channelRegex = "channel: '([a-z]+)',";
    String newsIdRegex = "newsid: '([\\w-]+)',";
    String channel = RegexUtil.getMatchGroupRegex(htmlCode, channelRegex);
    String newsId = RegexUtil.getMatchGroupRegex(htmlCode, newsIdRegex);
    String commentUrl = "http://comment5.news.sina.com.cn/page/info?version=1&format=js&channel=" + channel + "&newsid=" + newsId;
    String commentHtml = getHtml(commentUrl);
    analysisHtml(commentHtml);
  }

  private static String getHtml(String url) {
    CloseableHttpClient client = HttpClientBuilder.create().build();
    HttpGet get = new HttpGet(url);
    String html = null;
    try {
      HttpResponse response = client.execute(get);
      html = EntityUtils.toString(response.getEntity());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return html;
  }

  private static void analysisHtml(String commentHtml) {
    commentHtml = commentHtml.substring(9, commentHtml.length());
//      System.out.println(commentHtml);
    JSONObject jsonObject = new JSONObject(commentHtml);
    JSONObject resultObject = jsonObject.getJSONObject("result");
//      JSONArray replydictArray=resultObject.getJSONArray("replydict");
//      for(int i=0;i<replydictArray.size();i++){
//        JSONObject dataObject=replydictArray.getJSONObject(i);
//        System.out.println();
//      }
    JSONObject replydictObject = resultObject.getJSONObject("replydict");
//      System.out.println(replydictObject);

    //最新
    Iterator keys = replydictObject.keys();
    while (keys.hasNext()) {
      String key = String.valueOf(keys.next());
      JSONArray dataArray = replydictObject.getJSONArray(key);
      for (int i = 0; i < dataArray.length(); i++) {
        JSONObject dataObject = dataArray.getJSONObject(i);
        System.out.println(dataObject.get("mid"));
        System.out.println(dataObject.get("uid"));
        System.out.println(dataObject.get("content"));
        System.out.println(dataObject.get("nick"));
        System.out.println(dataObject.get("newsid"));
        System.out.println(dataObject.get("against"));
        System.out.println(dataObject.get("time"));
        System.out.println();
      }

    }
    //最热
    JSONArray cmntArray = resultObject.getJSONArray("cmntlist");
    for (int i = 0; i < cmntArray.length(); i++) {
      JSONObject dataObject = cmntArray.getJSONObject(i);
      System.out.println(dataObject.get("mid"));
      System.out.println(dataObject.get("uid"));
      System.out.println(dataObject.get("content"));
      System.out.println(dataObject.get("nick"));
      System.out.println(dataObject.get("newsid"));
      System.out.println(dataObject.get("against"));
      System.out.println(dataObject.get("time"));
      System.out.println();
    }
  }


  public static String getMatchGroupRegex(String str, String strPattern) {
    Pattern p = Pattern.compile(strPattern, Pattern.CASE_INSENSITIVE);
    Matcher m = p.matcher(str);
    String result = "";
    if (m.find()) {
      result = m.group(1);
    }
    return result;
  }

  public static Date getPostDate(String timeText) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
    SimpleDateFormat toDaySdf = new SimpleDateFormat("yyyy年MM月dd日");
    try {
      if (timeText.contains("-")) {
        SimpleDateFormat lsdStrFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date strD = lsdStrFormat.parse(timeText);
        return strD;
      } else if (timeText.contains("天前")) {
        Long timer = Long.valueOf(timeText.replace("天前", ""));
        return new Date(System.currentTimeMillis() - timer * 1000 * 60 * 60L);
      } else if (timeText.contains("小时前")) {
        Long timer = Long.valueOf(timeText.replace("小时前", ""));
        return new Date(System.currentTimeMillis() - timer * 1000 * 60 * 60L);
      } else if (timeText.contains("分钟前")) {
        Long timer = Long.valueOf(timeText.replace("分钟前", ""));
        return new Date(System.currentTimeMillis() - timer * 1000 * 60L);
      } else if (timeText.contains("秒前")) {
        Long timer = Long.valueOf(timeText.replace("秒前", ""));
        return new Date(System.currentTimeMillis() - timer * 1000L);
      } else {
        System.out.println("解析发布时间有问题 " + timeText);
        return new Date();
      }


    } catch (Exception e) {
      e.printStackTrace();
    }
    return new Date();
  }

  private  static void setDefaultInfo() {
    headers.clear();
    headers.put("Host", "www.thepaper.cn");
    headers.put("Connection", "keep-alive");
    headers.put("Cache-Control", " max-age=0");
    headers.put("Upgrade-Insecure-Requests", " 1");
    headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
    headers.put("Accept-Encoding", "gzip, deflate, br");
    headers.put("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8");
    headers.put("User-Agent", " Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3423.2 Safari/537.36");
    headers.put("Cookie", " route=ac205598b1fccbab08a64956374e0f11; JSESSIONID=8338F7764DABA05FC2D17051136268FD; uuid=12672244-6e56-4591-8fa8-0bb244211613; SERVERID=srv-omp-ali-portal11_80; UM_distinctid=165efd9abaa3b9-09ff7d575202ed-794d2643-1fa400-165efd9abab877; Hm_lvt_94a1e06bbce219d29285cee2e37d1d26=1536801574,1537327345; jwplayer.captionLabel=Off; CNZZDATA1261102524=436792607-1537322338-null%7C1538210072; Hm_lpvt_94a1e06bbce219d29285cee2e37d1d26=1538212296; __ads_session=lBeOmKAmKwnu7vo0ZgA=");
  }

//  public Object getyiNetInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
////    response.setContentType("text/javascript json");
//    response.setContentType("application/json; charset=utf-8");
//    String url = request.getParameter("url");
//    Integer  page= Integer.valueOf(request.getParameter("page"));
//    String   newsid=url.substring(url.lastIndexOf("/")+1,url.lastIndexOf("."));
//    String newStr=  getyiNetSource( newsid, url,page);
//    JSONObject obj = new  JSONObject(newStr.trim());
//    Object newListSize = obj.get("newListSize");
//    if(Integer.valueOf(newListSize.toString())/30<page){
//      return new Response("400", "faild");
//    }else{
//      List<yiNet> linkList=yiNetService.getyiNetContent(newsid,url,page);
//      return new Response("200", "OK",page,Integer.valueOf(newListSize.toString()), linkList);
////        return new Response("200", "OK" ,null);
//    }
//
//  }

}

