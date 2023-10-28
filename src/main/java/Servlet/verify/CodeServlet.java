package Servlet.verify;


import Service.ServiceImpl.userServiceImpl;
import Service.userService;
import Utils.MailUtils;
import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

import java.io.IOException;
import java.util.Map;
import java.util.Random;

@WebServlet(urlPatterns = "/code")
public class CodeServlet extends HttpServlet {
    private static userService userService;
    @Override
    public void init(ServletConfig config) throws ServletException {
        userService=new userServiceImpl();
        super.init(config);
    }

    //原谅我复用操作，这种操作显然是不太符合软件工程操作的
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Random random=new Random();
        //随机数还是要保证其中的位数一定要完整实现
        int RCode=random.nextInt(9999);
        String RCodeString = null;
        if (RCode > 999 ) {
            RCodeString=String.valueOf(RCode);
            System.out.println("验证码为："+RCodeString);
        }
        ServletContext servletContext = req.getServletContext();
        servletContext.setAttribute("RCode", RCodeString);
        System.out.println("验证码请求收到");
    }
}
