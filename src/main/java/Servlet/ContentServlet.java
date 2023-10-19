package Servlet;


import Enitity.userInfo;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = "/content")
public class ContentServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Content-Type","text/html;charset=UTF-8");
//        Cookie[] cookies = req.getCookies();
//        for (Cookie cookie:cookies) {
//            if (cookie.getName().equals("username")) {
//                System.out.println(cookie.getValue());
//            }
//        }
        resp.getWriter().write("用户登录成功");
        //会话如果不及时进行关闭的话还是会导致非密码注入问题
        req.getSession().invalidate();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
