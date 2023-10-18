package Servlet;

import Utils.TemplateUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.context.Context;

import java.io.IOException;

@WebServlet(urlPatterns = "/enroll")
public class enrollServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Context context=new Context();
        context.setVariable("enrollTips", "请输入账号以及密码以此来注册");
        context.setVariable("loginInfo", "注册账号");
        TemplateUtils.process("enroll.html", context, resp.getWriter());
        resp.sendRedirect("login");
    }
}
