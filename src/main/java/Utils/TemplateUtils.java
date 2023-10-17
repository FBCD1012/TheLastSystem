package Utils;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.io.Writer;

public class TemplateUtils {
    private static TemplateEngine templateEngine;
    static {
        templateEngine=new  TemplateEngine();
        ClassLoaderTemplateResolver classLoaderTemplateResolver=new ClassLoaderTemplateResolver();
        //中文乱码的问题并不是请i求书写或者是静态页面的问题，主要还是模板引擎的问题，实现其中的字符集统一就行了
        classLoaderTemplateResolver.setCharacterEncoding("UTF-8");
        templateEngine.setTemplateResolver(classLoaderTemplateResolver);
    }
    public static void process(String string, Context context, Writer writer){
        templateEngine.process(string,context ,writer );
    }
}
