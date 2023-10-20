package Servlet.verify;

import Enitity.userInfo;
import Utils.TemplateUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;


@WebServlet(urlPatterns = "/forgetContent")
public class forgetContentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取其中的CodeSession操作理解
        resp.setHeader("Content-Type","text/html;charset=UTF-8");
        //TODO content操作理解，如何实现其中的内容操作
        userInfo rCodeSession = (userInfo) req.getSession().getAttribute("RCodeSession");
        if (rCodeSession == null) {
            resp.sendRedirect("forget");
            return;
        }
        resp.getWriter().write("<h1>this is the forgetContent</h1>");
        req.getSession().invalidate();
    }
}
