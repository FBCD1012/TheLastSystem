package Servlet;

import Mapper.userInfoMapper;
import Utils.MybatisUtils;
import Utils.TemplateUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.ibatis.session.SqlSession;
import org.thymeleaf.context.Context;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Content-Type", "text/html;charset=UTF-8");
        //此处的操作实现，还是需要进行理解的，千万不能忘啊哥们！！！！
        Map<String, String[]> parameterMap = req.getParameterMap();
        if (parameterMap.containsKey("username") && parameterMap.containsKey("password")){
            String username = req.getParameter("username");
            String password = req.getParameter("password");
            if (username!=null && password != null){
                try (SqlSession session = MybatisUtils.SetAutoCommit(true)){
                    userInfoMapper mapper = session.getMapper(userInfoMapper.class);
                    int i = mapper.insertUserInfo(username, password);
                    if (i == 1) {
                        //TODO 到时候这里需要进行Session操作来进行连接登陆，免得用户多次登陆造成不必要的麻烦操作
                        //TODO 到时候这里直接进行跳转操作
                        //直接进行跳转操作
                        resp.sendRedirect("login");
                    }else {
                        resp.getWriter().write("<h1>用户注册失败</h1>");
                    }
                }
            }
        }else {
            resp.getWriter().write("<h1>表单参数不完整</h1>");
        }
    }
}
