package com.pets.controller;

import com.pets.pojo.dto.LoginDTO;
import com.pets.service.LoginService;
import com.pets.utils.base.ResponseData;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin
@RequestMapping("/login")
@RestController
public class LoginController {
    @Autowired
    LoginService loginService;

    @PostMapping("/empLogin")
    public ResponseData empLogin(@RequestBody @Valid LoginDTO loginDTO){
        log.info("校验员工登录");
        return ResponseData.OK(loginService.empLogin(loginDTO));
    }


    @PostMapping("/customerLogin")
    public ResponseData customerLogin(@RequestBody LoginDTO loginDTO,
                                @RequestParam String loginStatus) throws Exception {
        log.info("校验客户登录");
        return ResponseData.OK(loginService.customerLogin(loginDTO, loginStatus));

    }

    @PostMapping("/getCustomerName")
    public ResponseData getCustomerName(@RequestBody LoginDTO loginDTO) {
    return ResponseData.OK(loginService.customerName(loginDTO.getUsername()));
    }

    @PostMapping("/hello")
    public ResponseData getNameByJWT(@RequestHeader("token") String token) {
        return ResponseData.OK(loginService.getNameByJWT(token));
    }
}



