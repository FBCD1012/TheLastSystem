package Servlet;


import Enitity.userInfo;
import Mapper.userInfoMapper;
import Utils.MybatisUtils;
import Utils.TemplateUtils;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import lombok.SneakyThrows;
import org.apache.ibatis.session.SqlSession;
import org.thymeleaf.context.Context;

import java.io.IOException;
import java.util.Map;

@WebServlet(urlPatterns = "/login")
public class LoggingServlet extends HttpServlet {
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie [] cookies=req.getCookies();
        if (cookies != null) {
            String username=null;
            String password = null;
            //最好就是利用相关的传递参数来进行理解操作，如何空值其中的操作我觉得也是一个非常必要的理解
            for (Cookie cookie: cookies) {
                if (cookie.getName().equals("username")) username=  cookie.getValue();
                if (cookie.getName().equals("password")) password = cookie.getValue();
            }
            if (username!=null && password !=null){
                try (SqlSession sqlSession=MybatisUtils.SetAutoCommit(true)){
                    userInfoMapper mapper = sqlSession.getMapper(userInfoMapper.class);
                    userInfo theUserInfo = mapper.getTheUserInfo(username, password);
                    if (theUserInfo!=null){
                        HttpSession httpSession=req.getSession();
                        httpSession.setAttribute("userInfo", theUserInfo);
                        resp.sendRedirect("content");
                        return;
                    }
                }
            }
        }
        //        resp.setContentType("text/html;charset=UTF-8");
        Context context=new Context();
        //直接对相关的模板进行传递
        context.setVariable("loginTips", "请输入用户名以及密码以此来进行登陆");
        TemplateUtils.process("login.html", context, resp.getWriter());
        //如果cookie不是空的话，然后再进行详细操作，如何实现也是非常必要的！
        req.getRequestDispatcher("/").forward(req, resp);
    }
    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       resp.setHeader("Content-Type", "text/html;charset=UTF-8");
        Map<String, String[]> parameterMap = req.getParameterMap();
        String remember = req.getParameter("remember-me");
        if (parameterMap.containsKey("username") && parameterMap.containsKey("password")){
            //验证键的完整性？
            String username=req.getParameter("username");
            String password=req.getParameter("password");
            try (SqlSession sqlSession=MybatisUtils.SetAutoCommit(true)){
                userInfoMapper mapper = sqlSession.getMapper(userInfoMapper.class);
                userInfo theUserInfo = mapper.getTheUserInfo(username, password);
                if (theUserInfo != null) {
                    //实现cookie操作
                    if (remember!=null){
                        Cookie usernameCookie=new Cookie("username",username);
                        usernameCookie.setMaxAge(300);
                        Cookie passwordCookie=new Cookie("password",password);
                        passwordCookie.setMaxAge(300);
                        resp.addCookie(usernameCookie);
                        resp.addCookie(passwordCookie);
                    }
                    //获取session操作，建立会话进行连接理解，如何进行也是非常必要的
                    HttpSession httpSession=req.getSession();
                    httpSession.setAttribute("userInfo", theUserInfo);
                    resp.sendRedirect("content");
                }else {
                    //浏览器写入操作肯定是优先于弹窗写入操作的
                    //直接进行重定向操作，关于此处的实现，如何进行才是非常必要的
                    //TODO 关于此处逻辑的优化还是要进行一次的
                    resp.sendRedirect("login");
                }
            }
        }else {
            resp.getWriter().write("<h1>表单数据不完整</h1>");
        }
    }
}
