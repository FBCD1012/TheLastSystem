package Servlet.verify;

import Enitity.userInfo;
import Mapper.userInfoMapper;
import Service.ServiceImpl.userServiceImpl;
import Service.userService;
import Utils.MybatisUtils;
import Utils.TemplateUtils;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.ibatis.session.SqlSession;
import org.thymeleaf.context.Context;


import java.io.IOException;
import java.util.Map;


//此处的延展逻辑操作还是需要后续来进行理解操作的，如何实现其中的操作也是非常必要的
@WebServlet(urlPatterns = "/forgetContent")
public class forgetContentServlet extends HttpServlet {
    private static userService userService;
    @Override
    public void init(ServletConfig config) throws ServletException {
        //接口实现的方法理解操作也会是非常必要的操作之一啊
        userService=new userServiceImpl();
        super.init(config);
    }

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
        Context context=new Context();
        //获取浏览器的写入对象操作
        context.setVariable("loginInfo", "更改你的密码");
        context.setVariable("enrollTips", "请按规范进行输入");
        //如何实现其中的操作呢？
        TemplateUtils.process("forgetContent.html",context,resp.getWriter());
        req.getSession().invalidate();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Content-Type", "text/html;charset=UTF-8");
        //TODO 实现用户名称check
        Map<String, String[]> parameterMap = req.getParameterMap();
        if (parameterMap.containsKey("username") && parameterMap.containsKey("password")) {
            String username=req.getParameter("username");
            String password=req.getParameter("password");
            //检查用户名称是否在数据库中进行存放，使用一个service进行操作理解
            boolean elements = userService.isElements(username);
            if (elements) {
                try (SqlSession sqlSession=MybatisUtils.SetAutoCommit(true)){
                    userInfoMapper mapper = sqlSession.getMapper(userInfoMapper.class);
                    int i = mapper.setNewlyPassword(password, username);
                    if (i!=0) {
                        userInfo userName = mapper.getUserName(username);
                        //直接跳转到主页面进行操作
                        HttpSession httpSession= req.getSession();
                        httpSession.setAttribute("userInfo",userName);
                        resp.sendRedirect("content");
                    }
                }
            }else {
                resp.sendRedirect("forgetContentServlet");
            }
        }
        //TODO 实现密码的修改 update
        //TODO 修改完成之后传入到核心的内容页面操作
        resp.getWriter().write("<h1>用户提交操作实现了</h1>");
    }
}
