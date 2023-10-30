import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.junit.jupiter.api.Test;

import javax.swing.text.html.HTMLDocument;
import java.io.*;
import java.nio.charset.StandardCharsets;


public class MarkDownTest {
    @Test
   public void markDownTest() throws IOException {
        MutableDataSet options=new MutableDataSet();
        Parser parser=Parser.builder(options).build();
        HtmlRenderer HtmlRenderer= com.vladsch.flexmark.html.HtmlRenderer.builder(options).build();

        //参数是String 如何对参数进行操作？
        BufferedReader bufferedReader=new BufferedReader(new FileReader("E:\\fbcd2\\demo6\\reflection\\TheLastSystem\\src\\main\\resources\\Tomcat类加载机制.md"));
        String flags;
        StringBuilder stringBuilder=new StringBuilder();
        while ((flags=bufferedReader.readLine())!=null){
            stringBuilder.append(flags+"\n");
//            System.out.println(flags);
        }
//        System.out.println(stringBuilder);
        Node document=parser.parse(String.valueOf(stringBuilder));
        String html=HtmlRenderer.render(document);
        System.out.println(html);
   }
}
