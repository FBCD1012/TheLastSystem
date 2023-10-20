package Filter;


import Enitity.userInfo;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

//@WebFilter(urlPatterns = {"/forget"})
public class theForgetFilter implements Filter {
    //过滤操作
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest=(HttpServletRequest) request;
        HttpServletResponse httpResponse=(HttpServletResponse) response;
        String url=httpRequest.getRequestURL().toString();
        if (!(url.endsWith(".css")) && !(url.endsWith(".png")) && !url.endsWith(".html") && !url.endsWith(".woff2")) {
            HttpSession httpSession=httpRequest.getSession();
            //获取相关的http对应操作
            userInfo rCodeSession = (userInfo) httpSession.getAttribute("RCodeSession");
            if (rCodeSession == null && !url.endsWith("/forget") && !url.endsWith("/forgetContent") && !url.endsWith("/code")) {
                httpResponse.sendRedirect("/forget");
                return;
            }
        }
        chain.doFilter(request, response);
    }
}
