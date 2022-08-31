package com.bt.controller;

import com.alibaba.fastjson.JSON;
import com.bt.entity.GoodsType;
import com.bt.service.GoodsTypeService;
import com.bt.service.Impl.GoodsTypeServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author 86155
 * @version v1.0
 * @project botao_xiaomi
 * @data 2022/8/25 9:06
 **/
@WebServlet(urlPatterns = "/goodstypeservlet")
public class GoodsTypeServlet extends BaseServlet{
    public  String goodstypelist(HttpServletRequest request, HttpServletResponse response) throws Exception {
 String json= (String) this.getServletContext().getAttribute("goodstype");
   if(json==null) {
       //        创建业务对象
       GoodsTypeService goodsTypeService = new GoodsTypeServiceImpl();
//        调用方法
       List<GoodsType> goodsTypes = goodsTypeService.findBylevel(1);
//       转成json字符串
       json = JSON.toJSONString(goodsTypes);
//       存入application域中
       this.getServletContext().setAttribute("goodstype", json);
   }
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
        return null;
    }

}
