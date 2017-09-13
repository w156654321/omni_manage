package com.liudh.shiro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/***
 *
 * 描    述：
 *
 * 创 建 者： liudh
 * 创建时间： 2017/6/1 14:16
 * 创建描述：
 *
 */
@Controller
@RequestMapping("role")
public class RoleController {

     @RequestMapping("list")
     public String roleList(){
         System.out.println("这是roleList...");
         return "roleList";
     }

}
