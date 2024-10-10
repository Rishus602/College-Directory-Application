package com.sahni.controller;

import com.sahni.Entity.User;
import com.sahni.service.UserRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserRegisterController {

    @Autowired
    private UserRegisterService userRegisterService;

    @PostMapping("/register")
    public String userAdminRegisterController(@RequestBody User request) {
        return userRegisterService.registerUser(request);
    }


}
