package com.pets.controller;


import com.pets.pojo.dto.*;
import com.pets.pojo.entity.FeedingSkill;
import com.pets.pojo.entity.PetEncyclopediaDetails;
import com.pets.service.FeedingSkillService;
import com.pets.utils.base.ResponseData;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.UnknownHostException;
import java.util.List;

@Slf4j
@CrossOrigin
@RequestMapping("/feedingSkill")
@RestController
public class FeedingSkillController {

    @Autowired
    FeedingSkillService feedingSkillService;

    //获取宠物喂养技巧
    @GetMapping("/queryFeedingSkill")
    public ResponseData<List<FeedingSkill>> queryFeedingSkill(@RequestParam Integer skillNum) throws UnknownHostException {
        return ResponseData.OK(feedingSkillService.queryFeedingSkill(skillNum));
    }

    //宠物喂养技巧分页查询
    @PostMapping("/pageQueryAllFeeding")
    public ResponseData queryAllEncyclopedia(@RequestBody PageRequestDto<FeedingSkillQueryDTO> pageRequestDto){
        return ResponseData.OK(feedingSkillService.pageQueryAllFeeding(pageRequestDto));
    }

    //宠物喂养技巧单个查询
    @GetMapping( "/queryOneFeeding")
    public ResponseData queryOneFeeding(@RequestParam Long id) throws UnknownHostException {
        return ResponseData.OK(feedingSkillService.queryOneFeeding(id));
    }

    //录入一个新宠物喂养技巧
    @PostMapping("/insertOneFeeding")
    public ResponseData insertOneEncyclopedia(@RequestBody @Valid FeedingSkillCreateInfo feedingSkillCreateInfo){
        return ResponseData.OK(feedingSkillService.insertOneFeeding(feedingSkillCreateInfo));
    }

    //更新宠物喂养技巧状态
    @PostMapping("/updateFeedingStatus")
    public ResponseData updateEncyclopediaStatus(@RequestBody UpdateStatusDTO updateStatusDTO) {
        return ResponseData.OK(feedingSkillService.updateFeedingStatus(updateStatusDTO));
    }

    //更新宠物喂养技巧的数据
    @PostMapping("/updateFeeding")
    public ResponseData updateEncyclopedia(@RequestBody FeedingSkill updateDTO) {
        return ResponseData.OK(feedingSkillService.updateFeeding(updateDTO));
    }

    //删除宠物喂养技巧照片
    @GetMapping("/deleteFeedingImage")
    public ResponseData deleteEncyclopediaImage(@RequestParam String id) {
        return ResponseData.OK(feedingSkillService.deleteFeedingImage(id));
    }

}
