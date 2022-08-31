package com.bt.controller;

import com.bt.entity.Goods;
import com.bt.entity.PageBean;
import com.bt.entity.User;
import com.bt.service.GoodsService;
import com.bt.service.Impl.GoodServiceImpl;
import com.bt.utils.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author 86155
 * @version v1.0
 * @project botao_xiaomi
 * @data 2022/8/25 10:02
 **/
@WebServlet(urlPatterns = "/goodsservlet")
@MultipartConfig(maxFileSize = 1024*1024*10)
public class GoodsServlet extends BaseServlet {
    public String getGoodsListByTypeId(HttpServletRequest request, HttpServletResponse response){
        String pageNum = request.getParameter("pageNum");
        String pageSize = request.getParameter("pageSize");
        int pn=1;
        int ps=8;
        try {
            if(!StringUtils.isEmpty(pageNum)){
                pn=Integer.parseInt(pageNum);
                if(pn<1) pn=1;
            }
            if(!StringUtils.isEmpty(pageSize)){
                ps=Integer.parseInt(pageSize);
                if(ps<1) ps=8;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
//       2 获取查询条件
        String typeId = request.getParameter("typeId");
        String name = request.getParameter("name");
//        拼接条件
        List<Object> params=new ArrayList<>();
        StringBuilder where = new StringBuilder();
        if(!StringUtils.isEmpty(typeId)){
            try {
                where.append(" and typeid=?");
                params.add(Integer.parseInt(typeId));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        if (!StringUtils.isEmpty(name)){
            where.append(" and name like ?");
            params.add("%"+name+"%");
        }
//        有条件 需要把最前面的and替换where
        if(where.length()>0){
            where.replace(0,4," where");
        }else {
            return "redirect:/index.jsp";
        }
        System.out.println(where.toString());
        System.out.println(params);
//     3创建业务对象
        GoodsService goodsService=new GoodServiceImpl();
        PageBean<Goods> pageBean=goodsService.findByPage(pn,ps,where.toString(),params);
//     4 放入域中
        request.setAttribute("typeid",typeId);
        request.setAttribute("name",name);
        request.setAttribute("pageBean",pageBean);
        System.out.println(pageBean.toString());
        return "goodsList.jsp";
    }
    public String getGoodsById(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String id = request.getParameter("id");
        if(StringUtils.isEmpty(id)){
            return "redirect:/index.jsp";
        }
//        创建业务对象
        GoodsService goodsService=new GoodServiceImpl();
//        调用方法
        try {
            Goods goods=goodsService.findById(Integer.parseInt(id));
            request.setAttribute("goods",goods);
            return "/goodsDetail.jsp";
        } catch (NumberFormatException e) {
            e.printStackTrace();
            request.setAttribute("msg","商品id不能为空");
            return "/message.jsp";
        }

    }
    public String addGoods(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        User user= (User) request.getSession().getAttribute("user");
        if (user==null){
            return "redirect:/login.jsp";
        }
        String name = request.getParameter("name");
        String typeId=request.getParameter("typeid");
        String pubdate = request.getParameter("pubdate");
        String price = request.getParameter("price");
        String star = request.getParameter("star");
        Part picture = request.getPart("picture");
        String intro = request.getParameter("intro");
//        检验
        if (StringUtils.isEmpty(name)){
            request.setAttribute("msg","姓名不能为空");
            return "/message.jsp";
        }

//        上传文件
        if(picture==null||picture.getSubmittedFileName().trim().equals("")){
            request.setAttribute("msg","图片不能为空");
            return "message.jsp";
        }
        String BasePath=request.getServletContext().getRealPath("WEB-INF"+ File.separator+"images");
        String newfilename;
        try {
            File file=new File(BasePath);
            if(!file.exists()){
                file.mkdirs();
            }
            String filename=picture.getSubmittedFileName();
            newfilename = UUID.randomUUID().toString().replace("-", "").substring(16)+filename;
            picture.write(BasePath+File.separator+newfilename);
        } catch (IOException e) {
            e.printStackTrace();
            request.setAttribute("msg","文件上传失败");
            return "message";
        }
//        封装
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        try {
            Goods goods=new Goods(0,name,sdf.parse(pubdate),newfilename,new BigDecimal(price),Integer.parseInt(star),intro,Integer.parseInt(typeId));
//            创建业务对象
            GoodsService goodsService=new GoodServiceImpl();
            goodsService.addGoods(goods);
            return "/goodsservlet?method=getGoodsList";
        } catch (ParseException e) {
            e.printStackTrace();
            request.setAttribute("msg","添加商品失败");
            return "message";
        }

    }
    public String getGoodsList(HttpServletRequest request,HttpServletResponse response){
        User admin= (User) request.getSession().getAttribute("admin");
        if(admin==null){
            return "redirect:/admin/login.jsp";
        }
//
        GoodsService goodsService=new GoodServiceImpl();
        try {
            List<Goods> goodsList=goodsService.findAll();

            request.getSession().setAttribute("goodsList",goodsList);
            return "redirect:/admin/showGoods.jsp";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/admin/showGoods.jsp";
        }


    }
}

