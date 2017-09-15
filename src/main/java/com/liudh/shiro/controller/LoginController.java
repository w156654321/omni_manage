package com.liudh.shiro.controller;

import com.liudh.shiro.service.TestService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/***
 *
 * 描    述：
 *
 * 创 建 者： liudh
 * 创建时间： 2017/5/25 17:21
 * 创建描述：
 *
 */
@Controller
@RequestMapping("user")
public class LoginController {

    @RequestMapping(value = "login",method = RequestMethod.POST)
    public String login(HttpServletRequest request, Model model) throws Exception {

        String username = request.getParameter("username");
        String pwd = request.getParameter("password");
        String captcha = request.getParameter("captcha");
        captcha = "1234";
        UsernamePasswordToken token = new UsernamePasswordToken(username, pwd, captcha);
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.login(token);
        return "index";
    }

    @RequestMapping("403")
    public String error403(){
        return "403";
    }

    @RequestMapping("list")
    public String list(){
        System.out.println("111");
        return "list";
    }

    @RequestMapping("edit")
    public String edit(){
        System.out.println("111");
        return "edit";
    }
}
