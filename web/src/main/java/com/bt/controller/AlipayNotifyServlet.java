package com.bt.controller; /**
 * @author 86155
 * @version v1.0
 * @project botao_xiaomi
 * @data 2022/8/29 11:01
 **/

import com.bt.alipay.AlipayConfig;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;

import com.bt.entity.Order;
import com.bt.service.Impl.OrderServiceImpl;
import com.bt.service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "AlipayNotifyServlet", value = "/alipaynotify")
public class AlipayNotifyServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("trade_status").equals("TRADE_SUCCESS")) {
            System.out.println("=========支付宝异步回调========");
            Map<String, String> params = new HashMap<>();
            Map<String, String[]> requestParams = request.getParameterMap();
            for (String name : requestParams.keySet()) {
                params.put(name, request.getParameter(name));
                // System.out.println(name + " = " + request.getParameter(name));
            }

            String outTradeNo = params.get("out_trade_no");
            String gmtPayment = params.get("gmt_payment");
            String alipayTradeNo = params.get("trade_no");

            String sign = params.get("sign");
            String content = AlipaySignature.getSignCheckContentV1(params);
            try {
                boolean checkSignature = AlipaySignature.rsa256CheckContent(content, sign, AlipayConfig.alipayPublicKey, "UTF-8"); // 验证签名
                // 支付宝验签
                if (checkSignature) {
                    // 验签通过
                    System.out.println("交易名称: " + params.get("subject"));
                    System.out.println("交易状态: " + params.get("trade_status"));
                    System.out.println("支付宝交易凭证号: " + params.get("trade_no"));
                    System.out.println("商户订单号: " + params.get("out_trade_no"));
                    System.out.println("交易金额: " + params.get("total_amount"));
                    System.out.println("买家在支付宝唯一id: " + params.get("buyer_id"));
                    System.out.println("买家付款时间: " + params.get("gmt_payment"));
                    System.out.println("买家付款金额: " + params.get("buyer_pay_amount"));
                    // 查询订单
                    OrderService orderService=new OrderServiceImpl();
                    Order order=orderService.findById(outTradeNo);
                    if (order != null) {
                        //修改订单
                        order.setStatus("2");
                        orderService.update(order);
                    }
                }
            } catch (AlipayApiException e) {
                e.printStackTrace();
            }
        }
    }
}
