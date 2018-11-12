package com.util;

import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.*;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

/**
 * @author zc
 * http操作相关的类
 */
public class HttpUtils {

  public static HttpResponse newDoGet(String url, Map<String, String> headers) {
    HttpClient client = createHttpClient();
    client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 60000);
//		client.getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);
    client.getParams().setParameter(ClientPNames.HANDLE_REDIRECTS, false);
    HttpGet getMethod = new HttpGet(url);
    HttpResponse response = null;
    try {
      if (headers != null && headers.keySet().size() > 0) {
        for (String key : headers.keySet()) {
          getMethod.addHeader(key, headers.get(key));
        }
      }
      response = client.execute(getMethod);
    } catch (ClientProtocolException e) {
      e.printStackTrace();
    } catch (IOException e) {
      String msg = e.getMessage();
      if (msg.contains("Truncated chunk")) {
        System.out.println(e.getMessage() + " 数据获取不完整。需要重新获取。");
      } else {
        System.out.println(e.getMessage() + " 连接 被拒绝。需要降低爬取频率。");
      }
    } catch (Exception e) {
    }
    return response;
  }

  /*
   * params :
   * url:  地址
   * headers：请求头部信息
   * return : httpresponse响应
   */
  public static HttpResponse doGet(String url, Map<String, String> headers) {
    HttpClient client = createHttpClient();
    HttpGet getMethod = new HttpGet(url);
    HttpResponse response = null;
    try {
      if (headers != null && headers.keySet().size() > 0) {
        for (String key : headers.keySet()) {
          getMethod.addHeader(key, headers.get(key));
        }
      }
      response = client.execute(getMethod);
    } catch (ClientProtocolException e) {
      e.printStackTrace();
    } catch (IOException e) {
      String msg = e.getMessage();
      if (msg.contains("Truncated chunk")) {
        System.out.println(e.getMessage() + " 数据获取不完整。需要重新获取。");
      } else {
        System.out.println(e.getMessage() + " 连接 被拒绝。需要降低爬取频率。");
      }
    } catch (Exception e) {
    }
    return response;
  }

  //需ssl证书认证的方法
  public static HttpResponse doGetForSSL(String url, Map<String, String> headers) {
    HttpClient client = createHttpClient();
    client = wrapClient(client);
    HttpGet getMethod = new HttpGet(url);
    HttpResponse response = null;
    try {
      if (headers != null && headers.keySet().size() > 0) {
        for (String key : headers.keySet()) {
          getMethod.addHeader(key, headers.get(key));
        }
      }
      response = client.execute(getMethod);
    } catch (ClientProtocolException e) {
      e.printStackTrace();
    } catch (IOException e) {
      String msg = e.getMessage();
      if (msg.contains("Truncated chunk")) {
        System.out.println(e.getMessage() + " 数据获取不完整。需要重新获取。");
      } else {
        System.out.println(e.getMessage() + " 连接 被拒绝。需要降低爬取频率。");
      }
    } catch (Exception e) {
    }
    return response;
  }

  //以下是wrapClient方法

  /**
   * 获取可信任https链接，以避免不受信任证书出现peer not authenticated异常
   *
   * @param base
   * @return
   */
  public static HttpClient wrapClient(HttpClient base) {
    try {
      SSLContext ctx = SSLContext.getInstance("TLS");
      X509TrustManager tm = new X509TrustManager() {
        public void checkClientTrusted(X509Certificate[] xcs, String string) {
        }

        public void checkServerTrusted(X509Certificate[] xcs, String string) {
        }

        public X509Certificate[] getAcceptedIssuers() {
          return null;
        }
      };
      ctx.init(null, new TrustManager[]{tm}, null);
      SSLSocketFactory ssf = new SSLSocketFactory(ctx);
      ssf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
      ClientConnectionManager ccm = base.getConnectionManager();
      SchemeRegistry sr = ccm.getSchemeRegistry();
      sr.register(new Scheme("https", ssf, 443));
      return new DefaultHttpClient(ccm, base.getParams());
    } catch (Exception ex) {
      ex.printStackTrace();
      return null;
    }
  }

  //上传附件
  public static HttpResponse doPostWithImg(String url, Map<String, String> headers, Map<String, Object> params) {
    HttpClient client = createHttpClient();
    HttpPost postMethod = new HttpPost(url);
    HttpResponse response = null;
    try {
      if (headers != null && headers.keySet().size() > 0) {
        for (String key : headers.keySet()) {
          postMethod.addHeader(key, headers.get(key));
        }
      }
      List<NameValuePair> p = null;
//				Part[] parts = new Part[10];
      MultipartEntity entity = new MultipartEntity();
//				InputStream in = new ByteArrayInputStream(new byte[2]);
//				postMethod.setRequestBody(in);
      if (params != null && params.keySet().size() > 0) {
        for (String key : params.keySet()) {
          if (key.equals("IMG")) {
            FileBody fileBody = new FileBody(new File(params.get(key).toString()));
            entity.addPart("IMG", fileBody);
          } else {
            StringBody stringBody = new StringBody(params.get(key).toString());
            entity.addPart(key, stringBody);
          }
        }
      }
//				if(p!=null)
//					postMethod.setEntity(new UrlEncodedFormEntity(p,HTTP.UTF_8));
      postMethod.setEntity(entity);

      response = client.execute(postMethod);
    } catch (ClientProtocolException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return response;
  }

  public static HttpResponse doGet302(String url, Map<String, String> headers) {
    HttpClient client = createHttpClient();
    HttpGet getMethod = new HttpGet(url);
    HttpResponse response = null;
    try {
      if (headers != null && headers.keySet().size() > 0) {
        for (String key : headers.keySet()) {
          getMethod.addHeader(key, headers.get(key));
        }
      }
      HttpParams httpParams = client.getParams();
      httpParams.setParameter(ClientPNames.HANDLE_REDIRECTS, true);
      response = client.execute(getMethod);
    } catch (ClientProtocolException e) {
      e.printStackTrace();
    } catch (IOException e) {
      String msg = e.getMessage();
      if (msg.contains("Truncated chunk")) {
        System.out.println(e.getMessage() + " 数据获取不完整。需要重新获取。");
      } else {
        System.out.println(e.getMessage() + " 连接 被拒绝。需要降低爬取频率。");
      }
    } catch (Exception e) {
    }
    return response;
  }

  /*
   * params :
   * url:  地址
   * headers：请求头部信息
   * params：post的请求数据
   * return : httpresponse响应
   */

  public static HttpResponse doPost(String url, Map<String, String> headers, Map<String, String> params) {
    HttpClient client = createHttpClient();
    HttpPost postMethod = new HttpPost(url);
    HttpResponse response = null;
    try {
      if (headers != null && headers.keySet().size() > 0) {
        for (String key : headers.keySet()) {
          postMethod.addHeader(key, headers.get(key));
        }
      }
      List<NameValuePair> p = null;
      if (params != null && params.keySet().size() > 0) {
        p = new ArrayList<NameValuePair>();
        for (String key : params.keySet()) {
          p.add(new BasicNameValuePair(key, params.get(key)));
        }
      }
      postMethod.getParams().setParameter("http.protocol.cookie-policy", CookiePolicy.BROWSER_COMPATIBILITY);
      if (p != null)
        postMethod.setEntity(new UrlEncodedFormEntity(p, HTTP.UTF_8));
      response = client.execute(postMethod);
    } catch (ClientProtocolException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return response;
  }

  public static HttpResponse doPost(String url, Map<String, String> headers, Map<String, String> params, String charset) {
    HttpClient client = createHttpClient();
    HttpPost postMethod = new HttpPost(url);
    HttpResponse response = null;
    try {
      if (headers != null && headers.keySet().size() > 0) {
        for (String key : headers.keySet()) {
          postMethod.addHeader(key, headers.get(key));
        }
      }
      List<NameValuePair> p = null;
      if (params != null && params.keySet().size() > 0) {
        p = new ArrayList<NameValuePair>();
        for (String key : params.keySet()) {
          p.add(new BasicNameValuePair(key, params.get(key)));
        }
      }
      if (p != null)
//				postMethod.setEntity(new UrlEncodedFormEntity(p,HTTP.UTF_8));
        postMethod.setEntity(new UrlEncodedFormEntity(p, charset));
      response = client.execute(postMethod);
    } catch (ClientProtocolException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return response;
  }

  public static HttpResponse doPostForSSL(String url, Map<String, String> headers, Map<String, String> params) {
    HttpClient client = createHttpClient();
    client = wrapClient(client);
    HttpPost postMethod = new HttpPost(url);
    HttpResponse response = null;
    try {
      if (headers != null && headers.keySet().size() > 0) {
        for (String key : headers.keySet()) {
          postMethod.addHeader(key, headers.get(key));
        }
      }
      List<NameValuePair> p = null;
      if (params != null && params.keySet().size() > 0) {
        p = new ArrayList<NameValuePair>();
        for (String key : params.keySet()) {
          p.add(new BasicNameValuePair(key, params.get(key)));
        }
      }
      postMethod.getParams().setParameter("http.protocol.cookie-policy", CookiePolicy.BROWSER_COMPATIBILITY);
      if (p != null)
        postMethod.setEntity(new UrlEncodedFormEntity(p, HTTP.UTF_8));
      response = client.execute(postMethod);
    } catch (ClientProtocolException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return response;
  }

  /*
   * params :
   * httpresponse
   * return : 响应的头部信息
   */

  public static List<Header> getReponseHeaders(HttpResponse response) {
    List<Header> headers = null;
    Header[] hds = response.getAllHeaders();
    if (hds != null && hds.length > 0) {
      headers = new ArrayList<Header>();
      for (int i = 0; i < hds.length; i++) {
        headers.add(hds[i]);
      }
    }
    return headers;
  }

  /*
   * params :
   * headers:头部信息
   * request：请求
   */
  public static void setHeaders(Map<String, String> headers, HttpUriRequest request) {
    if (headers != null && headers.keySet().size() > 0) {
      for (String key : headers.keySet()) {
        request.addHeader(key, headers.get(key));
      }
    }
  }

  /*
   * params :
   * httpresponse
   * return : 响应的cookies值
   */

  public static List<Cookie> getResponseCookies(HttpResponse response) {
    List<Cookie> cookies = null;
    Header[] hds = response.getAllHeaders();
    if (hds != null && hds.length > 0) {
      for (int i = 0; i < hds.length; i++) {
        if (hds[i].getName().equalsIgnoreCase("Set-Cookie")) {
          if (cookies == null) {
            cookies = new ArrayList<Cookie>();
          }
          String cookiestring[] = hds[i].getValue().split(";");
          String ss[] = cookiestring[0].split("=", 2);
          String cookiename = ss[0];
          String cookievalue = ss[1];
          Cookie cookie = new BasicClientCookie(cookiename, cookievalue);
          cookies.add(cookie);
        }
      }
    }
    return cookies;
  }

  /*
   * params :
   * cookies数组
   * return : cookies数组组成的字符串
   */
  public static String setCookie2String(List<Cookie> cookies) {
    StringBuilder builder = null;
    if (cookies != null && cookies.size() > 0) {
      builder = new StringBuilder();
      for (int j = 0; j < cookies.size(); j++) {
        Cookie c = cookies.get(j);
        builder.append(c.getName() + "=" + c.getValue());
        if (j != cookies.size() - 1)
          builder.append("; ");
      }
      return builder.toString();
    }
    return null;
  }

  /*
   * 从响应中得到输入流
   */
  public static InputStream getInputStreamFromResponse(HttpResponse response) {
    if (response == null) {
      return null;
    }
    HttpEntity entity = response.getEntity();
    InputStream in = null;
    try {
      in = entity.getContent();
    } catch (IllegalStateException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return in;
  }

  /*
   * 从响应中得到字符串
   */
  public static String getStringFromResponse(HttpResponse response) {
    if (response == null) {
      return null;
    }
    InputStream in = getInputStreamFromResponse(response);
    String responseText = "";
    if (in != null) {
      responseText = Utils.getStringFromStream(in);
    }
    return responseText;
  }

  public static String getStringFromResponse2(HttpResponse response, String charset) {
    InputStream in = null;
    String responseText = "";
    if (response == null) {
      return null;
    }
    try {
      in = getInputStreamFromResponse(response);
      if (in != null) {
        responseText = Utils.getStringFromStream2(in, charset);
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (in != null) {
          in.close();
        }
      } catch (IOException e) {
      }
    }


    return responseText;
  }

  public static String getStringFromResponse2(HttpResponse response, String format, boolean isGZIP) {
    if (response == null) {
      try {
        EntityUtils.consume(response.getEntity());
      } catch (IOException e) {
        e.printStackTrace();
      }
      return null;
    }
    String responseText = "";
    InputStream in = getInputStreamFromResponse(response);
    if (in == null) return responseText;
    if (!isGZIP) {
      responseText = Utils.getStringFromStream2(in, format);
      try {
        in.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
      return responseText;
    }
    String encode;
    try {
      encode = response.getEntity().getContentEncoding().getValue();
    } catch (Exception e) {
      return null;
    }

    if (encode.equals("gzip")) {
      try {
        StringBuilder buffer = new StringBuilder();
        String str;
        GZIPInputStream gzip = new GZIPInputStream(in);
        InputStreamReader isr = new InputStreamReader(gzip, format);
        BufferedReader br = new BufferedReader(isr);
        while ((str = br.readLine()) != null) {
          buffer.append(str);
          buffer.append("\n");
        }
        responseText = buffer.toString();
        in.close();
        gzip.close();
        isr.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return responseText;
  }

  /**
   * 创建支持多线程并发连接的HTTPCLIENT
   */
  private final static HttpClient createHttpClient() {
    HttpParams params = new BasicHttpParams();
    HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
    HttpProtocolParams.setContentCharset(params, "UTF-8");

    ThreadSafeClientConnManager clientmanager = new ThreadSafeClientConnManager();
    clientmanager.setMaxTotal(20);
    HttpClient client = new DefaultHttpClient(clientmanager, params);
    return client;
  }


}
