package com.bt.controller; /**
 * @author 86155
 * @version v1.0
 * @project botao_xiaomi
 * @data 2022/8/29 14:53
 **/

import com.alibaba.fastjson.JSON;
import com.bt.entity.Order;
import com.bt.entity.WeiXinResult;
import com.bt.service.Impl.OrderServiceImpl;
import com.bt.service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "PaySuccessServlet", value = "/paySuccess")
public class PaySuccessServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String result = request.getParameter("result");
        String oid=null;
        try {
            WeiXinResult weiXinResult = JSON.parseObject(result, WeiXinResult.class);
            if(weiXinResult.getResult().getResultCode().equals("SUCCESS")) {
                oid = weiXinResult.getResult().getOutTradeNo();
                OrderService orderService = new OrderServiceImpl();
                Order order = orderService.findById(oid);
                if (order!=null) {
                    order.setStatus("2");
                    orderService.update(order);
                    request.setAttribute("msg", oid+"支付成功了。。。。。。");
                } else {
                    request.setAttribute("msg",oid+"支付失败了。。。。。。");
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        request.getRequestDispatcher("/message.jsp").forward(request, response);
    }
}
