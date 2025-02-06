package com.pets.controller;

import com.pets.pojo.dto.*;
import com.pets.pojo.entity.Customer;
import com.pets.pojo.entity.Emp;
import com.pets.pojo.entity.PageBean;
import com.pets.service.CustomerService;
import com.pets.utils.base.ResponseData;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.UnknownHostException;
import java.util.concurrent.ExecutionException;
@CrossOrigin
@Slf4j
@RequestMapping("/customer")
@RestController
public class CustomerController {
    @Autowired
    CustomerService customerService;

    //用户注册
    @PostMapping("/register")
    public ResponseData registerCustomer(@RequestBody @Validated CustomerCreateInfo customerCreateInfo) throws Exception {
        return ResponseData.OK(customerService.checkRegisterMessage(customerCreateInfo.getPhone(), customerCreateInfo.getCode(), customerCreateInfo.getGender(), customerCreateInfo.getBirth()));
    }

    //用户验证手机号
    @PostMapping("/sendSms")
    public ResponseData registerCustomer(@RequestBody SendSmsDTO sendSmsDTO) throws ExecutionException, InterruptedException {
        return ResponseData.OK(customerService.sendMessage(sendSmsDTO.getPhoneNumber(), sendSmsDTO.getSendType()));
    }

    //用户信息查询
    @GetMapping("queryOnePersonInformation")
    public ResponseData queryOnePersonInformation(@RequestParam String username) {
        return ResponseData.OK(customerService.queryOnePersonInformation(username));
    }

    //用户单个查询
    @GetMapping( "/queryOneCustomer")
    public ResponseData queryOneCustomer(@RequestParam Long id) throws UnknownHostException {
        return ResponseData.OK(customerService.queryOneCustomer(id));
    }

    //用户列表查询
    @PostMapping("/queryAllCustomer")
    public ResponseData queryAlCustomer(@RequestBody PageRequestDto<CustomerQueryDTO> pageRequestDto){
        return ResponseData.OK(customerService.queryAllCustomer(pageRequestDto));
    }

    //更新客户状态
    @PostMapping("/updateCustomerStatus")
    public ResponseData updateCustomerStatus(@RequestBody UpdateStatusDTO updateStatusDTO) {
        return ResponseData.OK(customerService.updateCustomerStatus(updateStatusDTO));
    }

    //更新客户的数据
    @PostMapping("/updateCustomer")
    public ResponseData updateCustomer(@RequestBody Customer customer) throws Exception {
        return ResponseData.OK(customerService.updateCustomer(customer));
    }

    //删除客户照片
    @GetMapping("/deleteCustomerImage")
    public ResponseData deleteCustomerImage(@RequestParam String id) {
        return ResponseData.OK(customerService.deleteCustomerImage(id));
    }
}
