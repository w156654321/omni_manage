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
public class IndexController {

    @RequestMapping("/")
    public String index(){
        return "login";
    }

    @RequestMapping("/500")
    public String error500(){
        return "500";
    }
    @RequestMapping("/404")
    public String error404(){
        return "404";
    }
}
