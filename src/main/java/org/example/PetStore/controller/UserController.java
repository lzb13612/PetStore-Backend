package org.example.PetStore.controller;

import cn.hutool.core.lang.Dict;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

import org.example.PetStore.service.UserService;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "用户操作", tags = {"用户操作 api"})
@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    UserService service;

    @ApiOperation(value = "智能合约 register 接口", notes = "智能合约 register 接口")
    @RequestMapping(value="register", method=RequestMethod.GET)
    public Dict register(@RequestParam("address") String address) {
        return service.register(address);
    }

    @ApiOperation(value = "智能合约 login 接口", notes = "智能合约 login 接口")
    @RequestMapping(value="login", method=RequestMethod.GET)
    public Dict login(@RequestParam("address") String address) {
        return service.login(address);
    }
}
