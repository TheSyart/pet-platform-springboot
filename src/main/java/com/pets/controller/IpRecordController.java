package com.pets.controller;

import com.pets.pojo.dto.IpQueryDTO;
import com.pets.pojo.dto.PageRequestDto;
import com.pets.pojo.vo.CommonResponseVo;
import com.pets.pojo.vo.HomeLoginVO;
import com.pets.service.IpRecordService;
import com.pets.utils.base.ResponseData;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/ip")
public class IpRecordController {

    @Autowired
    IpRecordService ipRecordService;

    @PostMapping("/queryAllIpInfo")
    public ResponseData<CommonResponseVo> encyclopediaSpeciesSign(@RequestBody PageRequestDto<IpQueryDTO> pageRequestDto) {
        return ResponseData.OK(ipRecordService.queryAllIpInfo(pageRequestDto));
    }

    //web首页展示今天一天登录数据
    @GetMapping("/homeLoginCount")
    public ResponseData<List<HomeLoginVO>> homeLoginCount(@RequestParam String start, @RequestParam String end) {
        return ResponseData.OK(ipRecordService.homeLoginCount(start, end));
    }

}
