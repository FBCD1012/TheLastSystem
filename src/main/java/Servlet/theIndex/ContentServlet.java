package Servlet.theIndex;



import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;

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
        req.getSession().invalidate();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
