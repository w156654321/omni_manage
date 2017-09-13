package com.liudh.shiro.controller;

import com.liudh.shiro.pojo.UPermission;
import com.liudh.shiro.service.TestService;
import com.liudh.shiro.util.MdUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.SimpleHash;
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

    @Autowired
    private TestService testService;

    @RequestMapping("/")
    public String index(){
//        UPermission permission = testService.insertTest();
        System.out.println("111");
        return "login";
    }

    @RequestMapping("toLogin")
    public String toLogin(){
//        UPermission permission = testService.insertTest();
        return "login";
    }
    @RequestMapping(value = "login",method = RequestMethod.POST)
    public String login(HttpServletRequest request, Model model) throws Exception {

        String username = request.getParameter("username");
        String pwd = request.getParameter("password");
        String captcha = request.getParameter("captcha");
        captcha = "1234";
        UsernamePasswordToken token = new UsernamePasswordToken(username, pwd, captcha);
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.login(token);

        return "redirect:/user/main.do";
    }

    @RequestMapping("main")
    public String main(){
        return "main";
    }

    @RequestMapping("403")
    public String error403(){
//        UPermission permission = testService.insertTest();
        System.out.println("111");
        return "403";
    }

}
