package com.bt.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * @author 86155
 * @version v1.0
 * @project botao_xiaomi
 * @data 2022/8/24 11:08
 * 通用的Servlet 包含所有的servlet通用的代码
 **/
public class BaseServlet  extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String methodName = request.getParameter("method");
//      利用反射
        try {
            Method method = this.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            if(method!= null){
               String url= (String) method.invoke(this, request, response);
               if(url!=null){
                   if(url.startsWith("redirect:")) {
                       url= url.substring(9);
                       response.sendRedirect(request.getContextPath() + url);
                   }else {
                       request.getRequestDispatcher(url).forward(request, response);
                   }
               }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
