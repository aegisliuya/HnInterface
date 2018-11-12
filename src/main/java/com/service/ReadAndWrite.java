package com.service;

import com.config.ElasticsearchConfig;
import com.config.SystemConfig;
import com.engine.model.beans.es.Nature;
import com.engine.model.beans.es.OriginalData;
import com.engine.model.beans.es.SubInfo;
import com.engine.model.beans.persist.opinion.TrackProject;
import com.engine.model.beans.persist.opinion.case_detail;
import com.engine.model.beans.persist.opinion.case_report;
import com.engine.model.dao.repository.elasticsearch.SubInfoRepository;
import com.engine.model.dao.repository.jpa.base.opinion.Case_DetailDAO;
import com.engine.model.dao.repository.jpa.base.opinion.Case_ReportDAO;
import com.engine.model.dao.repository.jpa.base.opinion.TrackProjectDAO;
import com.util.DeleteDirectory;
import com.util.pingNet;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import wuhao.tools.hibernate.Inquiry;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.net.InetAddress;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.util.DeleteDirectory.delFolder;
/*
 * 根据网络状况，对请求数据进行相应的处理
 * Created by LiuYa on 2018/1/11.
 */


public class ReadAndWrite {
    private static Logger log = Logger.getLogger(ReadAndWrite.class);
    public static ApplicationContext contextES = null;
    public static void main(String[] args) throws IOException, DocumentException, ParseException {
        System.out.println("本机的IP = " + InetAddress.getLocalHost());
        DeleteDirectory deleteDirectory = new DeleteDirectory();
        pingNet netState = new pingNet();
        System.out.println(netState.isConnect());
        if(!netState.isConnect()){                  //连接外网时
            File dir = new File("D:\\test");
            /**
             * 获取目录下的所有文件和文件夹
             */
            String[] ids = dir.list();
            for (String id : ids) {
                String   newid=id.substring(0,id.length()-4);
                log.info("连接外网，获取到 D:\\test 目录下的文件"+newid+".xml");
                System.out.println(id.substring(0,id.length()-4));
                ReadAndWrite.insertTrackProject(newid);
                log.info(newid+".xml 已插入TrackProject表中");
            }
//                ReadAndWrite.queryES();
//                log.info("成功获取ES返回结果，写入本地舆情信息文件 ");
        }else{
                ReadAndWrite.writecase_detail();                 //服务器读取case_detail写入本地文件
            log.info("连接内网，成功读取案件信息表 case_detail写入本地xml文件");
            File dir1 = new File("D:\\testes");
            String[] ids = dir1.list();
            for (String id : ids) {
                String   newid=id.substring(0,id.length()-4);
                System.out.println(ids.length);
                ReadAndWrite.readesfile(newid);                              // 读取本地舆情文件写入mysql  case_report表
                log.info("成功读取本地舆情信息文件写入 case_report表");
            }
        }
    }
    /*
     *
        连接内网：读取数据库案件信息写入本地案件信息xml文件
     */
    public static  void  writecase_detail(){

        SystemConfig.configInit();
        Case_DetailDAO case_DetailDAO = SystemConfig.context.getBean(Case_DetailDAO.class);
//        case_detail casedetail =new case_detail();
        Inquiry inquiry = Inquiry.forClass(case_detail.class).addEq("ajlb",2).addEq("state",0);
        Inquiry inquirystate = Inquiry.forClass(case_detail.class).addEq("ajlb",2).addUpdate("state",1);
        List<case_detail> list = case_DetailDAO.find(inquiry);
        int list1 = case_DetailDAO.executeUpdate(inquirystate);
        System.out.println("更新数据"+list1);
        for(int i=0; i<list.size();i++) {
            String  caseInfoId1=list.get(i).getCaseId();
            String ah1 = list.get(i).getAjbt();
            String ajlb1 = list.get(i).getAjlb();
            String ajbt1 = list.get(i).getAjbt();
            String keyword1= list.get(i).getKeyword();
            String ay1 = list.get(i).getAy();
            String cbr1 = list.get(i).getCbr();
            String cbrfy1 = list.get(i).getCbfy();
            Date requestTime1 = list.get(i).getRequestTime();
            Date endTime1 = list.get(i).getEndTime();
            log.info("案件标题" + list.get(i).getAjbt());
  /*
   将服务器读取的数据写入到xml文件中，以id作为文件名称
 */
            String xmlString = "";
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            try {
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document document = builder.newDocument();
                document.setXmlStandalone(true);
                Element itemInfo = document.createElement("caseId");
                document.appendChild(itemInfo);
                Element caseInfoId = document.createElement("caseInfoId");
                caseInfoId.setTextContent(caseInfoId1);
                itemInfo.appendChild(caseInfoId);
                Element ah = document.createElement("ah");
                ah.setTextContent(ah1);
                itemInfo.appendChild(ah);
                Element ajlb = document.createElement("ajlb");
                ajlb.setTextContent(ajlb1);
                itemInfo.appendChild(ajlb);
                Element ajbt = document.createElement("ajbt");
                ajbt.setTextContent(ajbt1);
                itemInfo.appendChild(ajbt);
                Element keyword = document.createElement("keyword");
                keyword.setTextContent(keyword1);
                itemInfo.appendChild(keyword);
                Element ay = document.createElement("ay");
                ay.setTextContent(ay1);
                itemInfo.appendChild(ay);
                Element cbr = document.createElement("cbr");
                cbr.setTextContent(cbr1);
                itemInfo.appendChild(cbr);
                Element cbrfy = document.createElement("cbrfy");
                cbrfy.setTextContent(cbrfy1);
                itemInfo.appendChild(cbrfy);
                Element requestTime = document.createElement("requestTime");
                requestTime.setTextContent(String.valueOf(requestTime1));
                itemInfo.appendChild(requestTime);
                Element endTime = document.createElement("endTime");
                endTime.setTextContent(String.valueOf(endTime1));
                itemInfo.appendChild(endTime);
                TransformerFactory transFactory = TransformerFactory.newInstance();
                Transformer transformer = transFactory.newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                DOMSource domSource = new DOMSource(document);
                // xml transform String
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                transformer.transform(domSource, new StreamResult(bos));
                xmlString = bos.toString();

                File txt=new File("D:/test/"+caseInfoId1+".xml");
                if(!txt.exists()){
                    txt.createNewFile();
                }
                byte bytes[]=new byte[10];
                bytes=xmlString.getBytes();
                int b=bytes.length;   //是字节的长度，不是字符串的长度
                FileOutputStream fos=new FileOutputStream(txt);
                fos.write(bytes,0,b);
//                fos.write(bytes);
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /*
           连接内网： 读取本地舆情数据写入mysql数据库
     */
    public static  void   readesfile(String  id1) throws IOException, DocumentException, ParseException {
        SystemConfig.configInit();
        Case_ReportDAO case_reportDAO = SystemConfig.context.getBean(Case_ReportDAO.class);

        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("D:/testes/"+id1+".xml")),
                "UTF-8"));

        String buffer = null;

        StringBuffer xml = new StringBuffer();
        while ((buffer = br.readLine()) != null) {

            xml.append(buffer);
        }
        org.dom4j.Document document = null;
        SAXReader reader = new SAXReader();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(xml
                .toString().getBytes());
        InputStreamReader ir = new InputStreamReader(inputStream);
        //document已经获取到xml文件
        document = reader.read(ir);
        //        Document document = reader.read(new ByteArrayInputStream(xml.getBytes("utf-8")));//读取xml字符串，注意这里要转成输入流
        org.dom4j.Element root = document.getRootElement();//获取根元素
        String		subInfoId=root.element("id").getText();
        String		caseId=root.element("caseId").getText();
        String		title=root.element("ajbt").getText();
        String     author=root.element("author").getText();
        String     url=root.element("url").getText();
        String     postTime=root.element("postTime").getText();
        Integer    grade= Integer.valueOf(root.element("grade").getText());
        String     label=root.element("label").getText();
        String		 webName=root.element("webName").getText();
        String    infotypeId=root.element("infotypeId").getText();
        String      sitetypeId=root.element("sitetypeId").getText();
        String		summary=root.element("summary").getText();
        String     track_id=root.element("track_id").getText();
//        SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");
//        Date PostTime = sdf.parse(postTime);
        case_report case_report =new  case_report();
//        case_report.setSubInfoId(subInfoId);
        case_report.setCaseId(caseId);
        case_report.setGrade(grade);
        case_report.setInfotypeId(Integer.valueOf(infotypeId));
        case_report.setLabel(label);
        case_report.setPostTime(new Date(postTime));
        case_report.setSitetypeId(Integer.valueOf(sitetypeId));
        case_report.setSummary(summary);
        case_report.setTitle(title);
        case_report.getLabel(label);
        case_report.setWebName(webName);
        case_report.setAuthor(author);
        case_report.setUrl(url);
        case_report.setTrack_id(track_id);
        case_reportDAO.saveOrUpdate(case_report);
        log.info("成功保存数据");
        delFolder("D:\\testes");   //写入完成，删除文件

//        System.out.println(parentEventsList.get(0).getsiteTypeIds().toString());
    }
    /*
        连接外网：读取本地xml文件数据插入到TrackProject表中
     */
    public static void   insertTrackProject(String   newid) throws IOException, DocumentException, ParseException {
        SystemConfig.configInit();
        TrackProjectDAO trackProjectDAO = SystemConfig.context.getBean(TrackProjectDAO.class);
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("D:/test/"+newid+".xml")),
                "UTF-8"));

        String buffer = null;
        // 存放请求内容
        StringBuffer xml = new StringBuffer();
        while ((buffer = br.readLine()) != null) {
            // 在页面中显示读取到的请求参数
            xml.append(buffer);
        }
        org.dom4j.Document document = null;
        SAXReader reader = new SAXReader();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(xml
                .toString().getBytes());
        InputStreamReader ir = new InputStreamReader(inputStream);
        //document已经获取到xml文件
        document = reader.read(ir);
        //        Document document = reader.read(new ByteArrayInputStream(xml.getBytes("utf-8")));//读取xml字符串，注意这里要转成输入流
        org.dom4j.Element root = document.getRootElement();//获取根元素
        String caseInfoId = root.element("caseInfoId").getText();//获取当前元素名
        String ah = root.element("ah").getText();//获取当前元素名
        String ajlb = root.element("ajlb").getText();//获取当前元素名
        String ajbt = root.element("ajbt").getText();//获取当前元素名
        String cbr = root.element("cbr").getText();//获取当前元素名
        String keyword = root.element("keyword").getText();//获取当前元素名
        String ay = root.element("ay").getText();//获取当前元素名
//        String cbfy = root.element("cbfy").getText();//获取当前元素名
        //        Element mclist = root.element("dsrlist");//获取当前元素名
        String requestTime = root.element("requestTime").getStringValue();//获取当前元素名
        String endTime = root.element("endTime").getText();//获取当前元素名
        SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");
        Date requestime = sdf.parse(requestTime);
        Date endtime = sdf.parse(endTime);
        String    newkey="("+keyword.trim().replace("|",")||(").replace(" ",")&&(").replace("  ",")&&(")+")";
        String    newkeygrop=keyword.trim().replace("|",", ").replace(" ","").replace("  ","");
        String[] newkeygrop1 = newkeygrop.split(",");
        Integer siteTypeIds[] = {0, 1, 4, 18, 20, 21, 22, 23, 5, 8, 28, 29, 30, 32, 33, 34, 35, 37, 12, 13, 14, 24, 25, 15, 16, 17, 2, 19, 26, 27, 36};
        Integer infoTypeIds[] = {2, 3, 4, 5, 6, 7, 8, 9, 10};
//        String KeywordsGroups[] = {"原告李志", "被告邱国", "通海水域人身损害"};
        Date DT = new Date(2017 - 3 - 12);
        TrackProject trackProject = new TrackProject();
//        trackProject.setId(100000);
        trackProject.setCaseId(caseInfoId);
        trackProject.setSiteTypeIds(siteTypeIds);
        trackProject.setName(ajbt);
        trackProject.setKeywordsGroups(newkeygrop1);
        trackProject.setCreateTime(requestime);
        trackProject.setStartTime(requestime);
        trackProject.setEndTime(endtime);
        trackProject.setInfoTypeIds(infoTypeIds);
        trackProject.setKeywords(newkey);
        trackProject.setEventsId(5846);
        trackProject.setClientId(182);
        trackProject.setCrawlerGradeId(2);
        trackProject.setNoCrawl(0);
        trackProject.setType(3);
        trackProject.setEventsNature(0);
        trackProject.setEnable(1);
        trackProjectDAO.saveOrUpdate(trackProject);
        log.info("成功保存trackProject表数据- - - - -");
        delFolder("D:\\test");   //删除文件
         log.info("获取到trackProjectId为"+trackProject.getId()+"开始查询ES数据库-----");
        String   newtrackProjectID=  String.valueOf(trackProject.getId());
         ReadAndWrite.queryES(newtrackProjectID,caseInfoId);
         log.info("成功获取ES返回结果，已写入本地舆情信息文件 ");
    }

    /*
        连接外网：读取ES数据写入本地文件
     */
    public static void  queryES(String  trackProjectId ,String  caseInfoId ) {
        SystemConfig.configInit();
        Case_ReportDAO case_reportDAO = SystemConfig.context.getBean(Case_ReportDAO.class);
        case_detail case_detail  =new case_detail();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -365);
        SubInfo SubInfo = new SubInfo();
        OriginalData originalData=new   OriginalData();
        if (contextES == null) {
            contextES = new AnnotationConfigApplicationContext(ElasticsearchConfig.class);
        }
        SubInfoRepository subInfoRepository = contextES.getBean(SubInfoRepository.class);
        QueryBuilder news_builder = QueryBuilders.boolQuery().must(QueryBuilders.termQuery("trackProjectIds", "836618"))
                .must(QueryBuilders.rangeQuery("crawlerTime").gt(cal.getTimeInMillis()).lt(System.currentTimeMillis()))
                .must(QueryBuilders.existsQuery("smsSendRecord"));
        NativeSearchQuery news_query = new NativeSearchQuery(news_builder);
        news_query.setPageable(new PageRequest(0, 5));
        Page<SubInfo> news_subInfo = subInfoRepository.search(news_query);
        log.info("获取到结果"+news_subInfo.getTotalElements());
        Iterator it = news_subInfo.iterator();
        while (it.hasNext()) {
            SubInfo= (com.engine.model.beans.es.SubInfo) it.next();
//            originalData= (com.engine.model.beans.es.OriginalData) it.next();
            String  related=SubInfo.getNature().toString();
            if(!related.equals("UNSIGNED")) {
                String		id1=SubInfo.getId();
                String		caseId1=caseInfoId;
                String		title1=SubInfo.getOriginalData().getTitle();
                String     author1=SubInfo.getAuthor();
                String     url1=SubInfo.getOriginalData().getUrl();
                Date       postTime1=SubInfo.getPostTime();
                Integer     grade1=SubInfo.getWarningGrade();
                Nature    label1=SubInfo.getNature();
                String		 webName1=SubInfo.getOriginalData().getWebName();
                Integer     infotypeId1=SubInfo.getInfoTypeId();
                Integer     sitetypeId1=SubInfo.getSiteTypeId();
                String		summary1=SubInfo.getOriginalData().getSummary();
                Set<Integer> trackProjectIds1 =SubInfo.getTrackProjectIds();
                log.info("获取到的trackProjectIds"+trackProjectIds1);
//                case_report case_report =new  case_report();
//                case_report.setCaseId(caseId1);
//                case_report.setGrade(grade1);
//                case_report.setInfotypeId(infotypeId1);
////             case_report.setLabel(label);
//                case_report.setPostTime(postTime1);
//                case_report.setSitetypeId(sitetypeId1);
//                case_report.setSummary(summary1);
//                case_report.setTitle(title1);
//                case_report.setWebName(webName1);
//                case_report.setAuthor(author1);
//                case_report.getUrl(url1);
//                case_reportDAO.saveOrUpdate(case_report);

                String xmlString = "";
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                try {
                    DocumentBuilder builder = factory.newDocumentBuilder();
                    Document document = builder.newDocument();
                    document.setXmlStandalone(true);
                    Element itemInfo = document.createElement("CaseReport");
                    document.appendChild(itemInfo);
                    Element id = document.createElement("id");
                    id.setTextContent(id1);
                    itemInfo.appendChild(id);
                    Element caseId = document.createElement("caseId");
                    caseId.setTextContent(caseId1);
                    itemInfo.appendChild(caseId);
                    Element title = document.createElement("ajbt");
                    title.setTextContent(title1);
                    itemInfo.appendChild(title);
                    Element url = document.createElement("url");
                    url.setTextContent(url1);
                    itemInfo.appendChild(url);
                    Element postTime = document.createElement("postTime");
                    postTime.setTextContent(String.valueOf(postTime1));
                    itemInfo.appendChild(postTime);
                    Element grade = document.createElement("grade");
                    grade.setTextContent(String.valueOf(grade1));
                    itemInfo.appendChild(grade);
                    Element label = document.createElement("label");
                    label.setTextContent(String.valueOf(label1));
                    itemInfo.appendChild(label);

                    Element author = document.createElement("author");
                    author.setTextContent(author1);
                    itemInfo.appendChild(author);
                    Element webName = document.createElement("webName");
                    webName.setTextContent(webName1);
                    itemInfo.appendChild(webName);

                    Element infotypeId = document.createElement("infotypeId");
                    infotypeId.setTextContent(String.valueOf(infotypeId1));
                    itemInfo.appendChild(infotypeId);

                    Element sitetypeId = document.createElement("sitetypeId");
                    sitetypeId.setTextContent(String.valueOf(sitetypeId1));
                    itemInfo.appendChild(sitetypeId);
                    Element track_id = document.createElement("track_id");
                    track_id.setTextContent(String.valueOf(trackProjectIds1).substring(1,String.valueOf(trackProjectIds1).length()-1));
                    itemInfo.appendChild(track_id);

                    Element summary = document.createElement("summary");
                    summary.setTextContent(summary1);
                    itemInfo.appendChild(summary);
                    TransformerFactory transFactory = TransformerFactory.newInstance();
                    Transformer transformer = transFactory.newTransformer();
                    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                    DOMSource domSource = new DOMSource(document);
                    // xml transform String
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    transformer.transform(domSource, new StreamResult(bos));
                    xmlString = bos.toString();
                     log.info("将ES获取的结果集写入到本地xml文件，SubInfo 的id作为文件名称");
                    File txt=new File("D:/testes/"+id1+".xml");
                    if(!txt.exists()){
                        txt.createNewFile();
                    }
                    byte bytes[]=new byte[10];
                    bytes=xmlString.getBytes();
                    int b=bytes.length;   //是字节的长度，不是字符串的长度
                    FileOutputStream fos=new FileOutputStream(txt);
                    fos.write(bytes,0,b);
//                    fos.write(bytes);
                    fos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            log.info("成功保存数据");
        }
    }

}
