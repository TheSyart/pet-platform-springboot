package com.pets.controller;

import com.pets.pojo.dto.*;
import com.pets.pojo.entity.PetService;
import com.pets.pojo.entity.PetShopping;
import com.pets.pojo.vo.*;
import com.pets.service.PetServiceService;
import com.pets.utils.base.ResponseData;
import jakarta.validation.Valid;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.UnknownHostException;
import java.util.List;

@RestController
@RequestMapping("/petService")
public class PetServiceController {

    @Autowired
    PetServiceService petServiceService;

    //查询所有宠物服务类型及具体信息
    @PostMapping("/queryAllPetServiceType")
    public ResponseData<ServiceTypeVO> queryAddressByUsername() throws UnknownHostException {
        return ResponseData.OK(petServiceService.queryAllPetServiceType());
    }

    //查询所有宠物服务类型
    @GetMapping("/allServiceType")
    public ResponseData<List<String>> allServiceType() {
        return ResponseData.OK(petServiceService.allServiceType());
    }

    //根据宠物服务id查询该服务下所有项目
    @PostMapping("/queryPetServiceById")
    public ResponseData<PetService> queryPetServiceById(@RequestBody IdDTO idDTO) throws UnknownHostException {
        return ResponseData.OK(petServiceService.queryPetServiceById(idDTO.getId()));
    }

    //查询宠物服务的时段
    @PostMapping("/queryTimeSlot")
    public ResponseData<TimeSlotVO> queryTimeSlot(@RequestBody TimeSlotDTO timeSlotDTO) {
        return ResponseData.OK(petServiceService.queryTimeSlot(timeSlotDTO));
    }

    //分页查询宠物服务
    @PostMapping("/pageQueryAllService")
    public ResponseData<CommonResponseVo> queryAllService(@RequestBody PageRequestDto<ServiceQueryDTO> pageRequestDto) {
        return ResponseData.OK(petServiceService.queryAllService(pageRequestDto));
    }

    //宠物服务单个查询
    @GetMapping( "/queryOneService")
    public ResponseData<PetService> queryOneService(@RequestParam Long id) throws UnknownHostException {
        return ResponseData.OK(petServiceService.queryOneService(id));
    }

    //新宠物服务录入
    @PostMapping( "/insertNewService")
    public ResponseData<String> insertNewService(@RequestBody @Valid PetServiceCreateInfo petServiceCreateInfo){
        return ResponseData.OK(petServiceService.insertNewService(petServiceCreateInfo));
    }

    //更新宠物服务状态
    @PostMapping("/updateServiceStatus")
    public ResponseData updateServiceStatus(@RequestBody UpdateStatusDTO updateStatusDTO) {
        return ResponseData.OK(petServiceService.updateServiceStatus(updateStatusDTO));
    }

    //更新宠物服务的数据
    @PostMapping("/updateService")
    public ResponseData updateService(@RequestBody PetService petService) {
        return ResponseData.OK(petServiceService.updateService(petService));
    }

    //删除宠物服务照片
    @GetMapping("/deleteServiceImage")
    public ResponseData deleteServiceImage(@RequestParam String id) {
        return ResponseData.OK(petServiceService.deleteServiceImage(id));
    }
}
