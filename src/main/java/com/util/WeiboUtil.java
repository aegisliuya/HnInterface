package com.util;



import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by LiFei on 2017/11/21.
 */
public class WeiboUtil {
  public static String sub = null;
  public static String subp = null;
  public static String cookie = null;
  public static Logger log = Logger.getLogger(WeiboUtil.class);


  public static String initGetWeiboHtml(String url) throws UnsupportedEncodingException {
    ///visitor/visitor?entry=miniblog&a=enter&url=https%3A%2F%2Fweibo.com%2F6169203960%2FGo9Fm5DcV%3Frefer_flag%3D1001030103_%26type%3Dcomment&domain=.weibo.com&ua=php-sso_sdk_client-0.6.28&_rand=1530583144.744
    String url2 = "https://passport.weibo.com/visitor/visitor?entry=miniblog&a=enter&url=" + URLEncoder.encode(url, "utf-8") +
        "&domain=.weibo.com&ua=php-sso_sdk_client-0.6.28&_rand=" + new Date().getTime() / 1000 + "." + new Random(9999).nextInt();
    Map<String, String> headers = new HashMap<>();
    headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
    headers.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");
    headers.put("Host", "passport.weibo.com");
    HttpResponse httpResponse = HttpUtils.doGet(url2, headers);
    String p = HttpUtils.getStringFromResponse2(httpResponse, "gbk");

    String url6 = "https://passport.weibo.com/js/visitor/mini_original.js?v=20161116";
    headers.put("Referer", url2);
    httpResponse = HttpUtils.doGet(url6, headers);

    //获取tid
    String url3 = "https://passport.weibo.com/visitor/genvisitor";
    headers.put("Referer", url2);
    headers.put("Origin", "https://passport.weibo.com");
    headers.put("Host", "passport.weibo.com");
    Map<String, String> params = new HashMap<>();
    params.put("cb", "gen_callback");
//    params.put("fp","{\"os\":\"1\",\"browser\":\"Chrome58,0,3029,110\",\"fonts\":\"Aharoni,Algerian,Andalus,Angsana New,AngsanaUPC,Aparajita,Arabic Typesetting,Arial,Arial Black,Arial Narrow,Arial Unicode MS,Baskerville Old Face,Batang,BatangChe,Bauhaus 93,Bell MT,Berlin Sans FB,Berlin Sans FB Demi,Bernard MT Condensed,Bodoni MT Poster Compressed,Book Antiqua,Bookman Old Style,Bookshelf Symbol 7,Britannic Bold,Broadway,Browallia New,BrowalliaUPC,Brush Script MT,Calibri,Californian FB,Cambria,Cambria Math,Candara,Centaur,Century,Century Gothic,Chiller,Colonna MT,Comic Sans MS,Consolas,Constantia,Cooper Black,Corbel,Cordia New,CordiaUPC,Courier,Courier New,DaunPenh,David,DejaVu Sans Mono,DFKai-SB,DilleniaUPC,DokChampa,Dotum,DotumChe,Ebrima,Estrangelo Edessa,EucrosiaUPC,Euphemia,Fixedsys,Footlight MT Light,Franklin Gothic Medium,FrankRuehl,FreesiaUPC,Freestyle Script,Gabriola,Garamond,Gautami,Georgia,Gisha,Gulim,GulimChe,Gungsuh,GungsuhChe,Haettenschweiler,Harlow Solid Italic,Harrington,High Tower Text,Impact,Informal Roman,IrisUPC,Iskoola Pota,JasmineUPC,Jokerman,Juice ITC,Kalinga,Kartika,Khmer UI,KodchiangUPC,Kokila,Kristen ITC,Kunstler Script,Lao UI,Latha,Leelawadee,Levenim MT,LilyUPC,Lucida Bright,Lucida Calligraphy,Lucida Console,Lucida Fax,Lucida Handwriting,Lucida Sans Unicode,Magneto,Malgun Gothic,Mangal,Marlett,Matura MT Script Capitals,Meiryo,Meiryo UI,Microsoft Himalaya,Microsoft JhengHei,Microsoft New Tai Lue,Microsoft PhagsPa,Microsoft Sans Serif,Microsoft Tai Le,Microsoft Uighur,Microsoft Yi Baiti,MingLiU,MingLiU-ExtB,MingLiU_HKSCS,MingLiU_HKSCS-ExtB,Miriam,Miriam Fixed,Mistral,Modern,Modern No. 20,Mongolian Baiti,Monotype Corsiva,MoolBoran,MS Gothic,MS Mincho,MS Outlook,MS PGothic,MS PMincho,MS Reference Sans Serif,MS Reference Specialty,MS Sans Serif,MS Serif,MS UI Gothic,MV Boli,Narkisim,Niagara Engraved,Niagara Solid,Nyala,Old English Text MT,Onyx,Palatino Linotype,Parchment,Plantagenet Cherokee,Playbill,PMingLiU,PMingLiU-ExtB,Poor Richard,Raavi,Ravie,Rod,Roman,Sakkal Majalla,Script,Segoe Print,Segoe Script,Segoe UI,Segoe UI Light,Segoe UI Semibold,Segoe UI Symbol,Shonar Bangla,Showcard Gothic,Shruti,Simplified Arabic,Simplified Arabic Fixed,SimSun-ExtB,Small Fonts,Snap ITC,Stencil,Sylfaen,Symbol,System,Tahoma,TeamViewer12,Tempus Sans ITC,Terminal,Times New Roman,Traditional Arabic,Trebuchet MS,Tunga,Utsaah,Vani,Verdana,Vijaya,Viner Hand ITC,Vivaldi,Vladimir Script,Vrinda,Webdings,Wide Latin,Wingdings,Wingdings 2,Wingdings 3,仿宋,华文中宋,华文仿宋,华文宋体,华文彩云,华文新魏,华文楷体,华文琥珀,华文细黑,华文行楷,华文隶书,宋体,幼圆,微软雅黑,新宋体,方正兰亭超细黑简体,方正姚体,方正舒体,楷体,隶书,黑体\",\"screenInfo\":\"1536*864*24\",\"plugins\":\"Enables Widevine licenses for playback of HTML audio/video content. (version: 1.4.8.970)::widevinecdmadapter.dll::Widevine Content Decryption Module|Shockwave Flash 27.0 r0::pepflashplayer.dll::Shockwave Flash|::mhjfbmdgcfjbbpaeojofohoefgiehjai::Chrome PDF Viewer|::internal-nacl-plugin::Native Client|Portable Document Format::internal-pdf-viewer::Chrome PDF Viewer\"}");
    params.put("fp", "{\"os\":\"1\",\"browser\":\"Chrome67,0,3396,99\",\"fonts\":\"undefined\",\"screenInfo\":\"1920*1080*24\",\"plugins\":\"Portable Document Format::internal-pdf-viewer::Chrome PDF Plugin|::mhjfbmdgcfjbbpaeojofohoefgiehjai::Chrome PDF Viewer|::internal-nacl-plugin::Native Client\"}");
    httpResponse = HttpUtils.doPost(url3, headers, params);
    p = HttpUtils.getStringFromResponse2(httpResponse, "gbk");
    System.out.println(p);

    //获取tid，new_tid，confidence
    String tid = getStrFromStr(p, "tid");
//    tid = "cih86Zn75zMIq249uTNqPtfT1IpnRFbrXITq81uLCJw=";
    int new_tid = Boolean.valueOf(getStrFromStr(p, "new_tid")) ? 3 : 2;
    String confidence = autoGenericCode(getStrFromStr(p, "confidence"), 3);
    System.out.println("tid:" + tid + ",confidence:" + confidence + ",newTid:" + getStrFromStr(p, "new_tid"));

    //获取sub，subp
    String url4 = "https://passport.weibo.com/visitor/visitor?a=incarnate" + "&t=" + URLEncoder.encode(tid, "utf-8") + "&w=" + new_tid + "&c=" + confidence
        + "&gc=" + "&cb=cross_domain" + "&from=weibo" + "&_rand=" + Math.random();

    headers.remove("Origin");
    headers.put("Accept", "*/*");
    headers.put("Accept-Encoding", "gzip, deflate, br");
    headers.put("Referer", url2);
    headers.put("Host", "passport.weibo.com");
    headers.put("Cookie", "tid=" + tid + "__" + confidence);

    System.out.println(url4);
    httpResponse = HttpUtils.doGet(url4, headers);
    p = HttpUtils.getStringFromResponse2(httpResponse, "gbk");
    log.info(p);
    sub = getStrFromStr(p, "sub");
    subp = getStrFromStr(p, "subp");
    log.info("sub:" + sub + ",subp:" + subp);
    log.info("重新获取cookie");

    //路人登录
    String url5 = "https://passport.weibo.com/visitor/visitor?a=crossdomain"
        + "&s=" + sub
        + "&cb=return_back"
        + "&sp=" + subp
        + "&_rand=" + Math.random()
        + "&from=weibo";

    headers.put("Cookie", null);
    httpResponse = HttpUtils.doGet(url5, headers);
    p = HttpUtils.getStringFromResponse2(httpResponse, "gbk");
    System.out.println(p);

    //取正文详情

    headers.put("Host", "weibo.com");
    headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
    headers.put("Cookie", "SUB=" + sub + ";SUBP=" + subp);
    httpResponse = HttpUtils.doGet(url, headers);
    p = HttpUtils.getStringFromResponse2(httpResponse, "UTF-8", true);
    cookie = HttpUtils.setCookie2String(HttpUtils.getResponseCookies(httpResponse));
    return p;
  }

  public static String getWeiboHtml(String url) throws UnsupportedEncodingException {
    Map<String, String> headers = new HashMap<>();
    headers.put("Cookie", "SUB=" + sub + ";SUBP=" + subp + ";" + cookie);
    headers.put("Accept-Encoding", " deflate, sdch, br");
    headers.put("Upgrade-Insecure-Requests", 1 + "");
    headers.put("Accept-Language", "zh-CN,zh;q=0.8");
//    HttpResponse httpResponse = HttpUtils.doGet(url, headers);
    System.out.println("开始 HttpUtils.newDoGet(url, headers) 时间 是  --------  "+System.currentTimeMillis());
    HttpResponse httpResponse = HttpUtils.newDoGet(url, headers);
    System.out.println("执行完 HttpUtils.newDoGet(url, headers) 时间 是  --------  "+System.currentTimeMillis());
    Header[] h = httpResponse.getHeaders("Location");
    if (h != null && h.length > 0) {
      String Location = h[0].getValue();
      if (Location.startsWith("/?profile_ftype=1")) {
        log.info("链接" + url + "失效");
        return null;
      }
      String newUrl = "https://weibo.com" + Location;
      httpResponse = HttpUtils.newDoGet(newUrl, headers);
    }

    String p = HttpUtils.getStringFromResponse2(httpResponse, "UTF-8");
    if (p != null && p.contains("pl.content.homeFeed.index")) {
      return p;
    } else {
      System.out.println("开始 initGetWeiboHtml(url)时间 是  --------  "+System.currentTimeMillis());
      p = initGetWeiboHtml(url);
      System.out.println("执行完 initGetWeiboHtml(url)时间 是  --------  "+System.currentTimeMillis());
      log.info("重新获取网页源码链接:" + url);
      return p;
    }
//      if (p != null && p.contains("pl.content.homeFeed.index")) {
//        return p;
//      } else {
//        p = WeiboUtil.getWeiboHtml(url);
//        log.info("再次重新获取网页源码链接:" + url);
//        return p;
//      }
//    else if (p == null || p.length() < 300 || p.contains("Sina Visitor System") || (p.contains("请输入密码") && p.contains("请输入验证码"))) {
//      return initGetWeiboHtml(url);
//    } else if (!p.contains("pl.content.homeFeed.index")) {
//      String urlHtml = p.substring(p.indexOf("var url = \""), p.length());
//      String newUrl = urlHtml.substring(11, urlHtml.indexOf("\"")) + "?profile_ftype=1&is_all=1&page=1&count=15";
//      p = getWeiboHtml(newUrl);
//      return p;
//    } else {
//      return p;
//    }
  }

  public static String getWeiboComment(String url) {
    Map<String, String> headers = new HashMap<>();
    headers.put("Cookie", "SUB=" + sub + ";SUBP=" + subp + ";" + cookie);
    headers.put("Accept-Encoding", " deflate, sdch, br");
    headers.put("Referer", url);
    headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36");
    headers.put("Accept", "text/html, application/xhtml+xml, */*");
    headers.put("Accept-Language", "zh-cn");
    headers.put("Host", "weibo.com");
    HttpResponse httpResponse = HttpUtils.doGet(url, headers);
    String p = HttpUtils.getStringFromResponse2(httpResponse, "utf-8");
    return p;
  }

  private static String getStrFromStr(String original, String name) {
    int start = -1;
    try {
      if ((start = original.indexOf("\"" + name + "\"")) > -1) {
        start += name.length() + 3;
        int end = original.indexOf(",", start + 1);
        if (end == -1) {
          end = original.indexOf("}", start + 1);
        }
        if (end > start) {
          return original.substring(start, end).replaceAll("\"", "");
        }
      }
    } catch (Exception e) {
      return null;
    }
    return "";
  }

  private static String autoGenericCode(String code, int num) {
    String result = "100";
    // 保留num的位数
    // 0 代表前面补充0
    // num 代表长度为4
    // d 代表参数为正数型
    try {
      result = String.format("%0" + num + "d", Integer.parseInt(code));
    } catch (NumberFormatException e) {
      result = "100";
    }

    return result;
  }


  public static void main(String[] args) throws UnsupportedEncodingException {
    String url = "https://weibo.com/ttarticle/p/show?id=2309404271221616313510";
    String text = WeiboUtil.initGetWeiboHtml(url);
    System.out.println(text);
    String text1 = WeiboUtil.getWeiboHtml(url);
    System.out.println(text1);
  }
}
