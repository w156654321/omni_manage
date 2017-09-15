package com.liudh.shiro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
public class UserController {

    @RequestMapping("list")
    public String list(){
        return "userList";
    }

}
