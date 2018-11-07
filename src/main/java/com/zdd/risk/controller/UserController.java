package com.zdd.risk.controller;

import java.util.ArrayList;
import java.util.List;

import com.zdd.risk.bean.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController {

    @GetMapping("/getString")
    public String getString() {
        return "Hello 张三";
    }

    //@GetMapping("/queryUserById")
    @RequestMapping(value = "/queryUserById",method=RequestMethod.GET)
    public User queryUserById() {
        User user = new User();
        user.setId("123456");
        user.setName("张三");
        user.setAge(12);
        return user;
    }

    @GetMapping("/queryUserList")
    public List<User> queryUserList() {
        List<User> list = new ArrayList<>();

        User user = new User();
        user.setId("123456");
        user.setName("张三");
        user.setAge(12);

        User user2 = new User();
        user2.setId("789");
        user2.setName("李四");
        user2.setAge(22);

        list.add(user);
        list.add(user2);
        return list;
    }

}
