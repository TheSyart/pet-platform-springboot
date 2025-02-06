package com.pets.controller;

import com.pets.pojo.dto.MyPetCreateDTO;
import com.pets.pojo.dto.MyPetQueryDTO;
import com.pets.pojo.dto.PageRequestDto;
import com.pets.pojo.vo.MyPetAndroidVO;
import com.pets.service.MyPetService;
import com.pets.utils.base.ResponseData;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.UnknownHostException;
import java.util.List;

@Slf4j
@CrossOrigin
@RequestMapping("/myPet")
@RestController
public class MyPetController {
    @Autowired
    MyPetService myPetService;

    //查询全部宠物
    @PostMapping("/getMyPet")
    public ResponseData MyPetGet(@RequestBody MyPetCreateDTO myPetCreateDTO) throws UnknownHostException {
        List<MyPetAndroidVO> results = myPetService.getMyPet(myPetCreateDTO.getUsername());
        return ResponseData.OK(results);
    }

    //查询全部宠物毛色
    @GetMapping( "/queryPetCoat")
    public ResponseData queryPetCoat(){
        return ResponseData.OK(myPetService.queryPetCoat());
    }

    //查询所有宠物详情信息
    @GetMapping( "/queryPetDetails")
    public ResponseData queryPetDetails(){
        return ResponseData.OK(myPetService.queryPetDetails());
    }

    //查询所有宠物服务预约时段
    @GetMapping( "/queryPetTimeslot")
    public ResponseData queryPetTimeslot(){
        return ResponseData.OK(myPetService.queryPetTimeslot());
    }

    //分页查询所有宠物
    @PostMapping("/queryAllPet")
    public ResponseData queryAllPet(@RequestBody PageRequestDto<MyPetQueryDTO> pageRequestDto){
        return ResponseData.OK(myPetService.queryAllPet(pageRequestDto));
    }

    //查询一个宠物
    @GetMapping( "/queryOnePet")
    public ResponseData queryOnePet(@RequestParam Integer id) throws UnknownHostException {
        return ResponseData.OK(myPetService.queryOnePet(id));
    }

    //录入新宠物
    @PostMapping("/insertMyPet")
    public ResponseData MyPetInsert(@RequestBody @Valid MyPetCreateDTO myPetCreateDTO) {
        return ResponseData.OK(myPetService.insertMyPet(myPetCreateDTO));
    }
}

