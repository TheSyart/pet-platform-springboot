package com.pets.controller;

import com.pets.pojo.dto.DynamicsCreateInfo;
import com.pets.pojo.dto.DynamicsQueryDTO;
import com.pets.pojo.dto.PageRequestDto;
import com.pets.pojo.dto.UpdateStatusDTO;
import com.pets.pojo.vo.DynamicsVO;
import com.pets.service.DynamicsService;
import com.pets.utils.base.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.UnknownHostException;
import java.util.List;
@RestController
@CrossOrigin
@RequestMapping("/dynamics")
public class DynamicsController {
    @Autowired
    DynamicsService dynamicsService;
    //获取动态信息
    @GetMapping( "/getAllInformation")
    public ResponseData dynamicsList(@RequestParam String dynamicsNum, @RequestParam String username) throws UnknownHostException {
        List<DynamicsVO> dynamics = dynamicsService.dynamicsList(Integer.parseInt(dynamicsNum), username);
        return ResponseData.OK(dynamics);
    }

    //更新点赞数量和点赞人
    @GetMapping("/updateNumAndPeople")
    public ResponseData updateDynamics(@RequestParam String id,
                                       @RequestParam String username,
                                       @RequestParam Integer type) {
        return ResponseData.OK(dynamicsService.updateDynamics(id, username, type));
    }

    //发布新动态
    @PostMapping("/insertDynamics")
    public ResponseData insertDynamics(@RequestBody DynamicsCreateInfo dynamicsCreateInfo) {
        return ResponseData.OK(dynamicsService.insertDynamics(dynamicsCreateInfo));
    }

    //动态单个查询
    @GetMapping( "/queryOneDynamics")
    public ResponseData queryOneDynamics(@RequestParam Long id) throws UnknownHostException {
        return ResponseData.OK(dynamicsService.queryOneDynamics(id));
    }

    //动态分页查询
    @PostMapping("/queryAllDynamics")
    public ResponseData queryAllDynamics(@RequestBody PageRequestDto<DynamicsQueryDTO> pageRequestDto){
        return ResponseData.OK(dynamicsService.queryAllDynamics(pageRequestDto));
    }

    //更新动态状态
    @PostMapping("/updateDynamicsStatus")
    public ResponseData updateDynamicsStatus(@RequestBody UpdateStatusDTO updateStatusDTO){
        return ResponseData.OK(dynamicsService.updateDynamicsStatus(updateStatusDTO));
    }
}
