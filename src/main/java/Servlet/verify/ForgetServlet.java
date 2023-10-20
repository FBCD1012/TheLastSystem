package Servlet.verify;


import Enitity.userInfo;
import Mapper.userInfoMapper;
import Utils.MailUtils;
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
import java.util.Objects;


@WebServlet(urlPatterns = "/forget")
public class ForgetServlet extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            String rCode = null;
            String username=null;
            for (Cookie cookie: cookies) {
                //从前端获取的cookie不一定是可信的，所以一定需要一个前后端的确认操作，这样才是专业且稳定的
                if (cookie.getName().equals("rCode")) rCode=cookie.getValue();
                if (cookie.getName().equals("username")) username=cookie.getValue();
            }
            //初步确认
            if (rCode !=null && username != null){
                try (SqlSession sqlSession=MybatisUtils.SetAutoCommit(true)){
                    userInfoMapper mapper = sqlSession.getMapper(userInfoMapper.class);
                    //进阶确认其中的操作
                    userInfo userName = mapper.getUserName(username);
                    //完整确认之后才能进行条件的操作
                    if (userName != null) {
                        HttpSession httpSession=req.getSession();
                        httpSession.setAttribute("RCodeSession",userName);
                        resp.sendRedirect("forgetContent");
                        return;
                    }
                }
            }
        }
        Context context=new Context();
        context.setVariable("forgetPassWord", "忘记密码");
        TemplateUtils.process("forgetPage.html",context,resp.getWriter());
        req.getRequestDispatcher("/").forward(req, resp);
    }
    //传递的Post方法操作，如何实现非常必要
    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Content-Type", "text/html;charset=UTF-8");
        //单纯的参数验证请求操作就可以了
        Map<String, String[]> parameterMap = req.getParameterMap();
        //获取其中的键值对操作
        if (parameterMap.containsKey("username") && parameterMap.containsKey("CodeNum")) {
            String username=req.getParameter("username");
            String CodeNum=req.getParameter("CodeNum");
            //todo 利用中间servletContext对参数进行理解！
            String rCode = (String) req.getServletContext().getAttribute("RCode");
            boolean isEqual=CodeNum.equals(rCode);
            try (SqlSession sqlSession=MybatisUtils.SetAutoCommit(true)){
                userInfoMapper mapper = sqlSession.getMapper(userInfoMapper.class);
                userInfo  userName = mapper.getUserName(username);
                // todo 验证条件进行理解了
                if ( userName != null && isEqual) {
                    //设置cookie缓存操作
                    Cookie rCodeCookie=new Cookie("rCode",rCode);
                    //设置cookie的生命周期，单位是s操作
                    rCodeCookie.setMaxAge(30);
                    Cookie usernameCookie=new Cookie("username",username);
                    usernameCookie.setMaxAge(30);
                    resp.addCookie(rCodeCookie);
                    resp.addCookie(usernameCookie);
                    //后续实现session操作，其实你能发现其中相关的逻辑应用还是相当不一样的
                    HttpSession httpSession=req.getSession();
                    httpSession.setAttribute("RCodeSession",userName);
                    resp.sendRedirect("forgetContent");
                }else {
                    resp.sendRedirect("forgetContent");
                }
            }
        }else {
            //todo 这里可以进行一次页面刷新，也可以直接抛出异常
            resp.getWriter().write("<h1>表单数据不完整</h1>");
        }
        resp.getWriter().write("<h1>没有任何参数输入/或者参数输入错误</h1>");
        System.out.println("找回操作实现");
    }
}
