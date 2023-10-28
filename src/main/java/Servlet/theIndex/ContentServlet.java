package Servlet.theIndex;



import Mapper.noteInfoMapper;
import Utils.MybatisUtils;
import Utils.TemplateUtils;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.ibatis.session.SqlSession;
import org.thymeleaf.context.Context;


import java.io.IOException;
import java.sql.Date;
import java.util.Map;

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
                    if (i==1){
                        resp.getWriter().write("<script>window.alert('文本插入成功')</script>");
                    }
                }else {
                    resp.getWriter().write("<script>window.alert('不存在这样的作者')</script>");
                }
            }
        }else {
            resp.getWriter().write("<script>window.alert('表单参数错误')</script>");
        }
    }
}
