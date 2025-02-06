package com.pets.controller;

import com.pets.pojo.dto.EncyclopediaCreateInfo;
import com.pets.pojo.dto.EncyclopediaQueryDTO;
import com.pets.pojo.dto.PageRequestDto;
import com.pets.pojo.dto.UpdateStatusDTO;
import com.pets.pojo.entity.Emp;
import com.pets.pojo.entity.PetEncyclopediaDetails;
import com.pets.service.EncyclopediaService;
import com.pets.utils.base.ResponseData;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.UnknownHostException;

@Slf4j
@CrossOrigin
@RequestMapping("/encyclopedia")
@RestController
public class EncyclopediaController {
    @Autowired
    EncyclopediaService encyclopediaService;

    //获取宠物百科的宠物全部分类
    @GetMapping("/allBreed")
    public ResponseData queryAllPetBreed() {
        return ResponseData.OK(encyclopediaService.queryAllPetBreed());
    }

    //获取宠物百科分类的图标及名称
    @GetMapping("/petSpecies")
    public ResponseData encyclopediaSpeciesSign() throws UnknownHostException {
        return ResponseData.OK(encyclopediaService.getEncyclopediaSpeciesSign());
    }

    //获取一个宠物分类下的所有宠物的三元素
    @GetMapping("/oneSpeciesDetails")
    public ResponseData oneSpeciesDetails(@RequestParam String id) throws UnknownHostException {
        return ResponseData.OK(encyclopediaService.oneSpeciesDetails(id));
    }

    //android获取一个具体宠物的所有信息
    @GetMapping("/onePetDetails")
    public ResponseData onePetDetails(@RequestParam String id) throws UnknownHostException {
        return ResponseData.OK(encyclopediaService.onePetDetails(id));
    }

    //web宠物百科单个查询
    @GetMapping( "/queryOneEncyclopedia")
    public ResponseData queryOneEncyclopedia(@RequestParam Long id) throws UnknownHostException {
        return ResponseData.OK(encyclopediaService.queryOneEncyclopedia(id));
    }

    //宠物百科分页查询
    @PostMapping("/pageQueryAllEncyclopedia")
    public ResponseData queryAllEncyclopedia(@RequestBody PageRequestDto<EncyclopediaQueryDTO> pageRequestDto){
        return ResponseData.OK(encyclopediaService.queryAllEncyclopedia(pageRequestDto));
    }

    //录入一个新宠物百科
    @PostMapping("/insertOneEncyclopedia")
    public ResponseData insertOneEncyclopedia(@RequestBody @Valid EncyclopediaCreateInfo encyclopediaCreateInfo){
        return ResponseData.OK(encyclopediaService.insertOneEncyclopedia(encyclopediaCreateInfo));
    }

    //更新宠物百科状态
    @PostMapping("/updateEncyclopediaStatus")
    public ResponseData updateEncyclopediaStatus(@RequestBody UpdateStatusDTO updateStatusDTO) {
        return ResponseData.OK(encyclopediaService.updateEncyclopediaStatus(updateStatusDTO));
    }

    //更新宠物百科的数据
    @PostMapping("/updateEncyclopedia")
    public ResponseData updateEncyclopedia(@RequestBody PetEncyclopediaDetails petEncyclopediaDetails) {
        return ResponseData.OK(encyclopediaService.updateEncyclopedia(petEncyclopediaDetails));
    }

    //删除宠物百科照片
    @GetMapping("/deleteEncyclopediaImage")
    public ResponseData deleteEncyclopediaImage(@RequestParam String id) {
        return ResponseData.OK(encyclopediaService.deleteEncyclopediaImage(id));
    }

}
