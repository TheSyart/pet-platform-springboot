package com.pets.controller;

import com.pets.pojo.dto.AddressUpdateDefaultDTO;
import com.pets.pojo.dto.UsernameForm;
import com.pets.pojo.entity.Address;
import com.pets.service.AddressService;
import com.pets.utils.base.ResponseData;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/petAddress")
public class AddressController {
    @Autowired
    AddressService addressService;

    //查询一个用户下所有地址
    @PostMapping("/queryAddressByUsername")
    public ResponseData<Address> queryAddressByUsername(@RequestBody UsernameForm username) {
        return ResponseData.OK(addressService.queryAddressByUsername(username.getUsername()));
    }

    //录入或更新一个地址
    @PostMapping("/updateAddress")
    public ResponseData<Address> updateAddress(@Valid @RequestBody Address address) {
        return ResponseData.OK(addressService.updateAddress(address));
    }

    //更新默认地址情况
    @PostMapping("/updateDefaultAddress")
    public ResponseData<Address> updateDefaultAddress(@RequestBody AddressUpdateDefaultDTO addressUpdateDefaultDTO) {
        return ResponseData.OK(addressService.updateDefaultAddress(addressUpdateDefaultDTO));
    }

}

