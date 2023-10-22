package Servlet.Testing;

import Enitity.userInfo;
import Mapper.userInfoMapper;
import Service.ServiceImpl.userServiceImpl;
import Service.userService;
import Utils.MybatisUtils;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.apache.ibatis.session.SqlSession;

import java.io.IOException;

import java.util.Map;

@WebServlet(value = "/userNameTestServlet")
public class userNameTestServlet extends HttpServlet {
    private static userService userService;
    @Override
    public void init(ServletConfig config) throws ServletException {
        userService=new userServiceImpl();
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //传递参数进入相关servlet,后端对前端密码进行校验操作，这里本来我想使用前端来进行操作的，无奈我前端太菜了，所以我只能进行后端校验的操作
        resp.setHeader("Content-Type","text/html;charset=UTF-8");
        Map<String, String[]> parameterMap = req.getParameterMap();
        if (parameterMap.containsKey("username") && parameterMap.containsKey("password")) {
            String username = req.getParameter("username");
            //对用户名进行校验操作
            boolean elements = userService.isElements(username);
            String password = req.getParameter("password");
            if (elements) {
                try(SqlSession session = MybatisUtils.SetAutoCommit(true)) {
                    userInfoMapper mapper = session.getMapper(userInfoMapper.class);
                    int i = mapper.setNewlyPassword(password, username);
                    userInfo theUserInfo = mapper.getTheUserInfo(username, password);
                    if (i!=0 && theUserInfo!=null){
                        Cookie userNameCookie=new Cookie("username",username);
                        userNameCookie.setMaxAge(300);
                        Cookie passwordCookie=new Cookie("password",password);
                        passwordCookie.setMaxAge(300);
                        resp.addCookie(userNameCookie);
                        resp.addCookie(passwordCookie);
                        HttpSession httpSession= req.getSession();
                        httpSession.setAttribute("userInfo",theUserInfo);
                        resp.sendRedirect("content");
                    }
                }
            }else {
                resp.getWriter().write("<script>window.alert('用户名称参数错误')</script>");
            }
        }
    }
}
