package com.bt.controller; /**
 * @author 86155
 * @version v1.0
 * @project botao_xiaomi
 * @data 2022/8/24 11:05
 **/

import com.bt.entity.*;
import com.bt.service.AddressService;
import com.bt.service.CartService;
import com.bt.service.Impl.AddressServiceImpl;
import com.bt.service.Impl.CartServiceImpl;
import com.bt.service.Impl.OrderServiceImpl;
import com.bt.service.OrderService;
import com.bt.utils.RandomUtils;
import com.bt.utils.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@WebServlet(name = "OrderServlet", value = "/orderservlet")
public class OrderServlet extends BaseServlet {

//    订单预览
    public String getOrderView(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user= (User) request.getSession().getAttribute("user");
        if(user==null){
            return "redirect:/login.jsp";
        }
        try {
            CartService cartService=new CartServiceImpl();
            AddressService addressService=new AddressServiceImpl();
            List<Cart> cartList = cartService.findByUid(user.getId());
            List<Address> addressList = addressService.find(user.getId());
            if(cartList==null||cartList.size()==0){
                request.setAttribute("msg","购物车为空");
                return "/message.jsp";
            }
            request.setAttribute("cartList",cartList);
            request.setAttribute("addressList",addressList);

            return "/order.jsp";
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msg","结账失败");
            return "/message.jsp";
        }

    }
//    添加订单   删除购物车，
    public String addOrder(HttpServletRequest request, HttpServletResponse response){
        User user= (User) request.getSession().getAttribute("user");
        if(user==null){
            return "redirect:/login.jsp";
        }
//        1 获取数据
        String aid = request.getParameter("aid");
        if(StringUtils.isEmpty(aid)){
            request.setAttribute("msg","请选择收货地址");
            return "/message.jsp";
        }
//        2获取购物车信息
        CartService cartService=new CartServiceImpl();
        List<Cart> cartList = cartService.findByUid(user.getId());
        if(cartList==null||cartList.size()==0){
            request.setAttribute("msg","购物车不能为空");
            return "/message.jsp";
        }
//        3 创建一个订单对象
        String orderId= RandomUtils.createOrderCode();
        BigDecimal sum=new BigDecimal("0");
        for(Cart cart:cartList){
            sum=sum.add(cart.getMoney());
        }
        Order order=new Order(orderId,user.getId(),sum,"1",new Date(),Integer.parseInt(aid), null);
//        4创建业务对象
        try {
            OrderService orderService=new OrderServiceImpl();
            orderService.submitOrder(order,cartList);
            request.setAttribute("order",order);
            return "/orderSuccess.jsp";
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msg","订单提交失败");
            return "/message.jsp";
        }

    }

//    查询订单
    public String getOrderList(HttpServletRequest request,HttpServletResponse response){
        User user=(User) request.getSession().getAttribute("user");
        if(user==null){
            return  "redirect:/login.jsp";
        }
        String pageNum=request.getParameter("pageNum");
        String pageSize=request.getParameter("pageSize");
        int pn=1;
        int ps=10;
        try {
            if(!StringUtils.isEmpty(pageNum)){
                pn=Integer.parseInt(pageNum);
                if(pn<1){
                    pn=1;
                }
            }
            if(!StringUtils.isEmpty(pageSize)){
                ps=Integer.parseInt(pageSize);
                if(ps<1){
                    ps=10;
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        OrderService orderService=new OrderServiceImpl();

        try {
            PageBean<Order> pageBean=orderService.findBypage(user.getId(),pn,ps);
            request.setAttribute("pageBean",pageBean);
            return "/orderList.jsp";
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msg","订单查询失败");
            return "/message.jsp";
        }

    }
//    根据订单号查询订单详情
    public String getOrderDetail(HttpServletRequest request,HttpServletResponse response){
        User user=(User) request.getSession().getAttribute("user");
        if(user==null){
            return "redirect:/login.jsp";
        }
        String oid=request.getParameter("oid");
        if(StringUtils.isEmpty(oid)){
            request.setAttribute("msg","订单号不能为空");
            return "/message.jsp";
        }
        OrderService orderService=new OrderServiceImpl();
        try {
            Order order=orderService.findById(oid);
            if(order!=null){
              List<OrderDetail> orderDetailList= orderService.findDetail(oid);
              request.setAttribute("order",order);
              request.setAttribute("orderDetailList",orderDetailList);
              return "/orderDetail.jsp";
            }else {
                request.setAttribute("msg","订单不存在");
                return "/message.jsp";
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msg","订单查询失败");
            return "/message.jsp";
        }

    }
}
