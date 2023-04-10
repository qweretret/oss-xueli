package com.ossjk.qlh.util;


import com.lowagie.text.pdf.BaseFont;
import com.ossjk.myUtil.ImgToBase64Util;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class PdfUtil {



    public static String createPDF(Object datas, String base64templateTxt,String rootPath,String pdfName)  {
        // 创建一个FreeMarker实例, 负责管理FreeMarker模板的Configuration实例
        Configuration cfg = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        StringTemplateLoader stringLoader = new StringTemplateLoader();

        stringLoader.putTemplate("zs-template", ImgToBase64Util.base64TexttoString(base64templateTxt) );

        cfg.setTemplateLoader (stringLoader);

        // 指定FreeMarker模板文件的位置
        ITextRenderer renderer = new ITextRenderer();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            // 设置 css中 的字体样式（暂时仅支持宋体和黑体） 必须，不然中文不显示
            renderer.getFontResolver().addFont("/static/font/simsun.ttc", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            // 设置模板的编码格式
            cfg.setEncoding(Locale.CHINA, "UTF-8");
            // 获取模板文件
            Template template = cfg.getTemplate("zs-template", "UTF-8");
            StringWriter writer = new StringWriter();

            // 将数据输出到html中
            Map<String, Object> data = new HashMap<>();

            Class clz = datas.getClass();
            Method[] mthds = clz.getMethods();
            if(mthds!=null){
                for (Method mtd:mthds) {
                    // getter   getXXXX
                   if( mtd.getName().indexOf("get") == 0 ){
                       //动态方法的调用
                       Object rslt= mtd.invoke(datas );
                       if( rslt!=null  ){
                           String fieldname= mtd.getName().substring(3);
                           Class rtnType =mtd.getReturnType();
                           String key = fieldname.substring(0,1).toLowerCase()+fieldname.substring(1);
                           if( rtnType.getName().equals("java.lang.Integer")   ){
                               data.put( key, (Integer)rslt);
                           }else if(rtnType.getName().equals("java.util.Date") ){
                               data.put( key, (Date)rslt);
                           }else{
                               data.put(key, rslt.toString() );
                           }
                       }
                   }
                }
            }

            template.process(data, writer);
            writer.flush();

            String html = writer.toString();
            // 把html代码传入渲染器中
            renderer.setDocumentFromString(html);

            // 设置模板中的图片路径 （这里的images在resources目录下） 模板中img标签src路径需要相对路径加图片名 如<img src="images/xh.jpg"/>
            String url = PdfUtil.class.getClassLoader().getResource("static/images").toURI().toString();
            renderer.getSharedContext().setBaseURL(url);
            renderer.layout();

            renderer.createPDF(out, false);
            renderer.finishPDF();
            out.flush();

            //写入文件
            File file1 = new File(rootPath);
            if (!file1.exists()) {
                file1.mkdirs();
            }
            String destUrl = rootPath +"/"+  pdfName;
            System.out.println( destUrl );
            File file2 = new File(destUrl);
            if( ("testZkz.pdf".equals(pdfName) ||"testZs.pdf".equals(pdfName)) && file2.exists()){
                file2.delete();
            }

            OutputStream  out2 = new FileOutputStream( destUrl  );
            out2.flush();
            out.writeTo(out2);
            out.flush();
            out.close();
            out.close();

            if(out2 != null){
                out2.close();
            }
            return destUrl;

        }catch (Exception ex){
            ex.printStackTrace();
        }

        return null;
    }

}