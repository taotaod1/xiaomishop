package com.bt.controller; /**
 * @author 86155
 * @version v1.0
 * @project botao_xiaomi
 * @data 2022/8/24 10:55
 **/


import cn.dsna.util.images.ValidateCode;
import com.alibaba.fastjson.JSON;
import com.bt.entity.Address;
import com.bt.entity.CodeMsg;
import com.bt.entity.Result;
import com.bt.entity.User;
import com.bt.service.AddressService;
import com.bt.service.Impl.AddressServiceImpl;
import com.bt.service.Impl.UserServiceImpl;
import com.bt.service.UserService;
import com.bt.utils.Base64Utils;
import com.bt.utils.RandomUtils;
import com.bt.utils.StringUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "UserServlet", value = "/userservlet")
public class UserServlet extends BaseServlet {

    public String register(HttpServletRequest request, HttpServletResponse response){
//    1 接受请求参数
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String repassword = request.getParameter("repassword");
        String email = request.getParameter("email");
        String gender = request.getParameter("gender");
//        2 校验
        if(StringUtils.isEmpty(username)){
            request.setAttribute("registerMsg","用户名不能为空");
            return "/register.jsp";
        }
        if (StringUtils.isEmpty(password)){
            request.setAttribute("registerMsg","密码不能为空");
            return "/register.jsp";
        }
        if (!password.equals(repassword)){
            request.setAttribute("registerMsg","确认密码不能为空");
            return "/register.jsp";
        }
        if (StringUtils.isEmpty(email)){
            request.setAttribute("registerMsg","邮箱不能为空");
            return "/register.jsp";
        }
        if(StringUtils.isEmpty(gender)){
            request.setAttribute("registerMsg","性别不能为空");
            return "/register.jsp";
        }
//        2.2 格式校验
        String reg="^\\w+@\\w+(\\.\\w+)+$";
        if(!email.matches(reg)){
            request.setAttribute("registerMsg","邮箱格式不正确");
            return "/register.jsp";
        }

//        创建业务对象，调用业务方法
        UserService userService = new UserServiceImpl();

        User user=new User(0,username,password,email, gender, 0,1,RandomUtils.createCode());
        try {
            userService.regist(user);
            return "redirect:/registerSuccess.jsp";
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("registerMsg","注册失败");
            return "/register.jsp";
        }

    }
    public String login(HttpServletRequest request,HttpServletResponse response){
        String username=request.getParameter("username");
        String password=request.getParameter("password");
        String vcode=request.getParameter("vcode");
//        校验
        if(StringUtils.isEmpty(username)){
            request.setAttribute("msg","用户名不能为空");
            return  "/login.jsp";
        }
        if(StringUtils.isEmpty(password)){
            request.setAttribute("msg","密码不能为空");
            return  "/login.jsp";
        }
        if(StringUtils.isEmpty(vcode)){
            request.setAttribute("msg","验证码不能为空");
            return  "/login.jsp";
        }
//        验证码校验
        String serverCode = (String) request.getSession().getAttribute("servercode");
        if(!vcode.equals(serverCode)){
            request.setAttribute("msg","验证码不正确");
            return  "/login.jsp";
        }
//       创建业务对象
        UserService userService = new UserServiceImpl();
        try {
            User user = userService.login(username, password);
            request.getSession().setAttribute("user",user);
            String auto = request.getParameter("auto");
            if (auto!=null){
                String userinfo = Base64Utils.encode(username + "#" + password);
                Cookie cookie = new Cookie("userinfo",userinfo);
                cookie.setMaxAge(60*60*24*14);
                cookie.setPath(request.getContextPath());
                response.addCookie(cookie);
            }
            return "redirect:/index.jsp";
        } catch (Exception e) {
            e.printStackTrace();
            return  "/login.jsp";
        }
    }
    public  String  checkUserName(HttpServletRequest request,HttpServletResponse response) {
        String username = request.getParameter("username");
        if (StringUtils.isEmpty(username)) {
              return  null;
        }
            UserService userService = new UserServiceImpl();
            User user = userService.findByUsername(username);
            if (user != null) {
                try {
                    response.getWriter().write("1");
                    return null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
       return null;
    }
    public String code(HttpServletRequest request,HttpServletResponse response)  {
        ValidateCode validateCode=new ValidateCode(100,35,4,20);
        request.getSession().setAttribute("servercode",validateCode.getCode());
        try {
            validateCode.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public String checkCode(HttpServletRequest request,HttpServletResponse response){
        String vcode = request.getParameter("code");
        String serverCode = (String) request.getSession().getAttribute("servercode");
        if(vcode.equals(serverCode)){
            try {
                response.getWriter().write("0");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    public String adminlogin(HttpServletRequest request,HttpServletResponse response){
        String username = request.getParameter("username");
        String password=request.getParameter("password");
        if(StringUtils.isEmpty(username)){
            request.setAttribute("msg","请输入用户名");
            return "/message.jsp";
        }
        if(StringUtils.isEmpty(username)){
            request.setAttribute("msg","请输入密码");
            return "/message.jsp";
        }
//        创建业务对象
        UserService userService=new UserServiceImpl();
        try {
            User user = userService.login(username, password);
            if(user==null){
                request.setAttribute("msg","用户不存在");
                return "/message.jsp";
            }
            if(user.getRole()!=0){
                request.setAttribute("msg","非管理员禁止登录");
                return "/message.jsp";
            }
            request.getSession().setAttribute("admin",user);
            return "redirect:/admin/admin.jsp";
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msg","登录失败");
            return "/message.jsp";
        }

    }

//    退出功能
    public String logOut(HttpServletRequest request,HttpServletResponse response){
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        session.invalidate();
//        cookie删除
       Cookie cookie=new Cookie("userinfo","");
       cookie.setPath(request.getContextPath());
       cookie.setMaxAge(0);
       response.addCookie(cookie);
        return "redirect:/login.jsp";
    }
//    ==============地址管理========================
    public String getAddress(HttpServletRequest request,HttpServletResponse response){
        User user = (User) request.getSession().getAttribute("user");
        if(user==null){
            return "redirect:/login.jsp";
        }
//  创建业务对象
        AddressService addressService = new AddressServiceImpl();
        try {
            List<Address> addList=addressService.find(user.getId());
//        放入域中
            request.setAttribute("addList",addList);
//      转发
            return "/self_info.jsp";
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msg","地址查询失败");
            return "message.jso";
        }
    }
    public String addAddress(HttpServletRequest request,HttpServletResponse response){
        User user = (User) request.getSession().getAttribute("user");
        if(user==null){
            return "redirect:/login.jsp";
        }
        response.setContentType("application/json;charset=utf-8");
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String detail = request.getParameter("detail");
//        校验
        if(StringUtils.isEmpty(name)){
            try {
                Result result=Result.error(CodeMsg.NAME_NOT_EXIST);
                response.getWriter().write(JSON.toJSONString(result));
//                response.getWriter().write("请输入收货人姓名");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
//            request.setAttribute("msg","姓名不能为空");
//            return "/self_info.jsp";
        }
        if(StringUtils.isEmpty(phone)){
            try {
                Result result=Result.error(CodeMsg.PHONE_NOT_EXIST);
                response.getWriter().write(JSON.toJSONString(result));
//                response.getWriter().write("请输入收货人电话");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
//            request.setAttribute("msg","手机号不能为空");
//            return "/self_info.jsp";
        }
        if(StringUtils.isEmpty(detail)){
            try {
                Result result=Result.error(CodeMsg.ADDRESS_NOT_EXIST);
                response.getWriter().write(JSON.toJSONString(result));
//                response.getWriter().write("请输入收货人详细地址");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
//            request.setAttribute("msg","详细地址不能为空");
//            return "/self_info.jsp";
        }
//        创建业务对象
        AddressService addressService = new AddressServiceImpl();
        try {
            Address address=new Address(0,detail,name,phone,user.getId(),0);
            addressService.add(address);
            Result result=Result.success(null);
            response.getWriter().write(JSON.toJSONString(result));
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msg","地址添加失败");
            return "/message.jsp";
        }
    }
    public String deleteAddress(HttpServletRequest request,HttpServletResponse response){
        User user = (User) request.getSession().getAttribute("user");
        if(user==null){
            return "redirect:/login.jsp";
        }
        String id = request.getParameter("id");
        if(StringUtils.isEmpty(id)){
            return null;
        }
        AddressService addressService = new AddressServiceImpl();
        try {
            addressService.delete(Integer.parseInt(id));

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return "redirect:/userservlet?method=getAddress";
    }
    public String updateAddress(HttpServletRequest request,HttpServletResponse response){
        User user = (User) request.getSession().getAttribute("user");
        if(user==null){
            return "redirect:/login.jsp";
        }
        String id = request.getParameter("id");
        String level = request.getParameter("level");
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String detail = request.getParameter("detail");
//        校验
        if(StringUtils.isEmpty(id)){
            return "redirect:/userservlet?method=getAddress";
        }
        if (StringUtils.isEmpty(level)){
            return "redirect:/userservlet?method=getAddress";
        }
        if (StringUtils.isEmpty(name)){
            return "redirect:/userservlet?method=getAddress";
        }
        if (StringUtils.isEmpty(phone)){
            return "redirect:/userservlet?method=getAddress";
        }
        if (StringUtils.isEmpty(detail)){
            return "redirect:/userservlet?method=getAddress";
        }
//        创建业务对象
        AddressService addressService = new AddressServiceImpl();
        try {
            Address address=new Address(Integer.parseInt(id),detail,name,phone,user.getId(),Integer.parseInt(level));
            addressService.update(address);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return "redirect:/userservlet?method=getAddress";
    }
    public String defaultAddress(HttpServletRequest request,HttpServletResponse response){
        User user = (User) request.getSession().getAttribute("user");
        if(user==null){
            return "redirect:/login.jsp";
        }
        String id = request.getParameter("id");
        if(StringUtils.isEmpty(id)){
            return "redirect:/userservlet?method=getAddress";
        }
        AddressService addressService = new AddressServiceImpl();
        try {
            addressService.updateLevel(Integer.parseInt(id),user.getId());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return "redirect:/userservlet?method=getAddress";
    }
}
