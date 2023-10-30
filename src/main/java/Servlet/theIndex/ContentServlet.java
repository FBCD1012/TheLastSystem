package Servlet.theIndex;



import Enitity.noteInfo;
import Mapper.noteInfoMapper;
import Utils.MybatisUtils;
import Utils.TemplateUtils;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.apache.commons.io.IOUtils;
import org.apache.ibatis.session.SqlSession;
import org.thymeleaf.context.Context;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.List;
import java.util.Map;

@MultipartConfig
@WebServlet(urlPatterns = "/content")
public class ContentServlet extends HttpServlet{
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Content-Type","text/html;charset=UTF-8");
//        Cookie[] cookies = req.getCookies();
//        for (Cookie cookie:cookies) {
//            if (cookie.getName().equals("username")) {
//                System.out.println(cookie.getValue());
//            }
//        }
        /*
          面向外部建立的连接操作是非常优质且必要的
          */
        //会话如果不及时进行关闭的话还是会导致非密码注入问题
//        if (req.getSession()!=null) {
//            req.getSession().invalidate();
//
//        }
        try(SqlSession sqlSes= MybatisUtils.SetAutoCommit(true)) {
            noteInfoMapper mapper = sqlSes.getMapper(noteInfoMapper.class);
            List<noteInfo> noteInfoList= mapper.getNoteInfoList();
            System.out.println(noteInfoList);
        }
        Context context=new Context();
        TemplateUtils.process("content.html", context, resp.getWriter());
        req.getSession().invalidate();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Content-Type", "text/html;charset=UTF8");
        Map<String, String[]> parameterMap = req.getParameterMap();
        boolean NoteName=parameterMap.containsKey("NoteName");
        boolean NoteTime=parameterMap.containsKey("NoteTime");
        boolean author=parameterMap.containsKey("author");
        boolean isFull=NoteName && NoteTime &&author;
        if (isFull) {
            String noteName = req.getParameter("NoteName");
            //是否进行输入框的操作呢？
            //传输数据类型的理解操作
            Date dateTime= Date.valueOf(req.getParameter("NoteTime"));
            String Author=req.getParameter("author");
            //在前端保证前端传递的参数一定是存在且能够进行传输操作的，一定明晰
            try(SqlSession sqlSession= MybatisUtils.SetAutoCommit(true)) {
                noteInfoMapper mapper = sqlSession.getMapper(noteInfoMapper.class);
                Integer noteName1 = mapper.getNoteName(Author);
                if (noteName1 != null) {
                    int countInt = mapper.getCountInt();
                    //直接进行插入操作
                    int i = mapper.InsertNoteInfo(countInt + 1, noteName, dateTime, Author, noteName1);
                    if (i == 1) {
                        //上传文件的方式如何进行才是非常必要的
                        Part part=req.getPart("theUpdateLoadFile");
                        String pathName=part.getSubmittedFileName();
                        try (FileOutputStream fos = new FileOutputStream("E:\\fbcd2\\demo6\\reflection\\TheLastSystem\\src\\main\\webapp\\file\\"+pathName)){
                            IOUtils.copy(part.getInputStream(),fos);
                        }
                        resp.setHeader("Content-Type", "text/html;charset=UTF-8");
                        System.out.println("文件插入成功");
                        System.out.println("数据插入成功");
                        Context context=new Context();
                        TemplateUtils.process("content.html", context, resp.getWriter());
                        resp.getWriter().println("<script>window.alert('参数插入成功')</script>");
                    }else {
                        System.out.println("数据插入失败");
                        Context context=new Context();
                        TemplateUtils.process("content.html", context, resp.getWriter());
                        resp.getWriter().println("<script>window.alert('参数插入失败')</script>");
                    }
                }
            }
        }else {
            System.out.println("表单参数不完整");
        }
    }
}
