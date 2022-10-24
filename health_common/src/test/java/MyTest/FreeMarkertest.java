package MyTest;

import freemarker.ext.beans.IteratorModel;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lihongxing
 * @version 1.0
 * @ClassName FreeMarkertest
 * @date 2022/8/23 09:19
 */

public class FreeMarkertest {
    public static void main(String[] args) throws Exception {
//        创建配置类
        Configuration configuration = new Configuration(Configuration.getVersion());
//        找到类模版所在目录
        configuration.setDirectoryForTemplateLoading(new File("/Users/lihongyuan/Desktop/post"));
//        设置字符集
        configuration.setDefaultEncoding("utf-8");
//        加载模版
        Template template = configuration.getTemplate("test.ftl");
//        创建数据模型

        List<Map> list=new ArrayList<>();
        Map map1=new HashMap();
        map1.put("","");

        Map map = new HashMap<>();
        map.put("name","李李李");

        FileWriter writer = new FileWriter(new File("/Users/lihongyuan/Desktop/post/test.html"));
        template.process(map,writer);
        writer.close();


    }
}
