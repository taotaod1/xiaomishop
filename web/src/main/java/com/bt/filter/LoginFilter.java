package com.bt.filter; /**
 * @author 86155
 * @version v1.0
 * @project botao_xiaomi
 * @data 2022/8/24 17:14
 **/

import com.bt.entity.User;
import com.bt.service.Impl.UserServiceImpl;
import com.bt.service.UserService;
import com.bt.utils.Base64Utils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "LoginFilter",urlPatterns = "/index.jsp")
public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig config) throws ServletException {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req= (HttpServletRequest) request;
        HttpServletResponse res= (HttpServletResponse) response;
        User user = (User) req.getSession().getAttribute("user");
        if(user!=null){
            chain.doFilter(req,res);
            return ;
        }
        Cookie[] cookies = req.getCookies();
        for (Cookie cookie : cookies) {
            if("userinfo".equals(cookie.getName())){
                try {
                    String userinfo = cookie.getValue();
                    userinfo= Base64Utils.decode(userinfo);
                    String[] split = userinfo.split("#");
                    UserService userService=new UserServiceImpl();
                    User loginuser = userService.login(split[0], split[1]);
                    req.getSession().setAttribute("user",loginuser);
                    chain.doFilter(req,res);

                } catch (Exception e) {
                    e.printStackTrace();
                   cookie=new Cookie("userinfo","");
                   cookie.setMaxAge(0);
                   cookie.setPath(req.getContextPath());
                   res.addCookie(cookie);
                   chain.doFilter(req,res);
                }
            }
        }

    }
}
