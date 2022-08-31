package com.bt.controller; /**
 * @author 86155
 * @version v1.0
 * @project botao_xiaomi
 * @data 2022/8/29 20:06
 **/

import com.bt.utils.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@WebServlet(name = "PictureServlet", value = "/picture/*")
public class PictureServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        String filename=requestURI.substring(requestURI.lastIndexOf("/")+1);
        if(StringUtils.isEmpty(filename)){
            return ;
        }
        String BasePath=request.getServletContext().getRealPath("WEB-INF"+ File.separator+"images");
        FileInputStream fi= null;
        File file=new File(BasePath+ File.separator+filename);
        if (!file.exists()){
            return ;
        }
        try {
            fi = new FileInputStream(file);
            int len=0;
            byte[] bytes=new byte[1024];
            while((len=fi.read(bytes))!=0){
                response.getOutputStream().write(bytes,0,len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            fi.close();
        }

    }
}
