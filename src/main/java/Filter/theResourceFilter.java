package Filter;


import Enitity.userInfo;
import Mapper.userInfoMapper;
import Utils.MybatisUtils;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.ibatis.session.SqlSession;

import java.io.IOException;
import java.util.Map;

//此处出现的路径操作还是需要进行注意了，学过的很多知识确实容易忘记，记得去复盘才行
@WebFilter(urlPatterns = "/*")
public class theResourceFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //转换Servlet对象来进行操作
        HttpServletRequest httpServletRequest=(HttpServletRequest) request;
        HttpServletResponse httpServletResponse=(HttpServletResponse) response;
        String requestURL = httpServletRequest.getRequestURL().toString();
        if (!requestURL.endsWith(".html")
                && !requestURL.endsWith(".css")
                && !requestURL.endsWith(".png")
                && !requestURL.endsWith(".woff2")
                && !requestURL.endsWith(".gif")
                && !requestURL.endsWith(".jpg")
                && !requestURL.endsWith(".js")
        && !requestURL.endsWith(".ico")) {
            HttpSession httpSession=httpServletRequest.getSession();
            userInfo userInfo = (Enitity.userInfo) httpSession.getAttribute("userInfo");
            if (userInfo == null && ! requestURL.endsWith("/login")
                    && !requestURL.endsWith("/enroll") && !requestURL.endsWith("/forget") && !requestURL.endsWith("/code")
                    && !requestURL.endsWith("/forgetContent") && !requestURL.endsWith("/userNameTestServlet")
                    && !requestURL.endsWith("/content") && !requestURL.endsWith("/innerContent") && !requestURL.endsWith("/theMarkdown")
            ) {
                httpServletResponse.sendRedirect("login");
                //几处return还是需要注意的
                return;
            }
        }
        //始终还是要进行原型对象的传递操作
        chain.doFilter(request, response);
    }
}
