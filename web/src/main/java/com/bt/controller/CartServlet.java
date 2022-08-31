package com.bt.controller;

import com.bt.entity.Cart;
import com.bt.entity.Goods;
import com.bt.entity.User;
import com.bt.service.CartService;
import com.bt.service.GoodsService;
import com.bt.service.Impl.CartServiceImpl;
import com.bt.service.Impl.GoodServiceImpl;
import com.bt.utils.StringUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author 86155
 * @version v1.0
 * @project botao_xiaomi
 * @data 2022/8/25 15:16
 **/
@WebServlet(urlPatterns = "/cartservlet")
public class CartServlet extends BaseServlet {
   public String addCart(HttpServletRequest request, HttpServletResponse response) throws Exception {
      User user = (User) request.getSession().getAttribute("user");
      if(user==null){
         return "redirect:/login.jsp";
      }
//      2获取参数
      String goodsid = request.getParameter("goodsId");
      String number = request.getParameter("number");
      int num=1;
      if(StringUtils.isEmpty(goodsid)){
         request.setAttribute("msg","请选择商品");
         return "/jsp/msg.jsp";
      }
      if(!StringUtils.isEmpty(number)){
            num=Integer.parseInt(number);
            if (num>10){
               num=1;
            }
      }
//      3创建业务对象
      CartService cartService = new CartServiceImpl();
//     3.1 先 查 询 购 物 项
      try {
         GoodsService goodsService=new GoodServiceImpl();
         Goods goods = goodsService.findById(Integer.parseInt(goodsid));
         if(goods==null) {
            request.setAttribute("msg","商品不存在");
            return "/message.jsp";
         }
         Cart cart=cartService.findCard(user.getId(),Integer.parseInt(goodsid));
         if(cart==null){
            cart=new Cart(user.getId(),Integer.parseInt(goodsid),num,goods.getPrice().multiply(new BigDecimal(num)));
            cartService.add(cart);
         }else {
            cart.setNum(cart.getNum()+num);
            cart.setMoney(goods.getPrice().multiply(new BigDecimal(cart.getNum())));
            cartService.update(cart);
         }
         return "redirect:/cartSuccess.jsp";
      } catch (NumberFormatException e) {
         e.printStackTrace();
         request.setAttribute("msg","商品不存在");
         return "/message.jsp";
      }
   }
//   根据用户id查询购物车
   public String getCart(HttpServletRequest request, HttpServletResponse response) throws Exception {
      User user = (User) request.getSession().getAttribute("user");
       if(user==null){
            return "redirect:/login.jsp";
       }
//       创建业务对象
      try {
         CartService cartService=new CartServiceImpl();
         List<Cart> cartList= cartService.findByUid(user.getId());
         System.out.println(cartList);
//      3放入域中
         request.setAttribute("cartlist",cartList);
         return "/cart.jsp";
      } catch (Exception e) {
         e.printStackTrace();
            request.setAttribute("msg","查询失败");
            return "/message.jsp";
      }
   }

//   修改购物车信息
   public String addCartAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
      User user = (User) request.getSession().getAttribute("user");
      if(user==null){
         return "redirect:/login.jsp";
      }
      String goodsId = request.getParameter("goodsId");
      String number = request.getParameter("number");
      int num=0;
      if(!StringUtils.isEmpty(number)){
         num=Integer.parseInt(number);
      }
//      2创建业务对象
      CartService cartService = new CartServiceImpl();
      GoodsService goodsService=new GoodServiceImpl();
//      增加 减少 删除
      if(num==1||num==-1) {
         Cart cart = cartService.findCard(user.getId(), Integer.parseInt(goodsId));
         Goods goods = goodsService.findById(Integer.parseInt(goodsId));
         if(cart!=null&&goods!=null){
            cart.setNum(cart.getNum()+num);
            cart.setMoney(goods.getPrice().multiply(new BigDecimal(cart.getNum())));
            cartService.update(cart);
         }
      } else if(num==0){
             cartService.delete(user.getId(),Integer.parseInt(goodsId));
      }


      return "";
      }
      public String clearCartAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
         User user = (User) request.getSession().getAttribute("user");
         if(user==null){
            return "redirect:/login.jsp";
         }
//        创建业务对象
         CartService cartService = new CartServiceImpl();
         cartService.delete(user.getId());
         return "";
      }
}

