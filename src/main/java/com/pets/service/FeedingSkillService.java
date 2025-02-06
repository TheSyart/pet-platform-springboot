package com.pets.service;

import com.pets.pojo.dto.FeedingSkillCreateInfo;
import com.pets.pojo.dto.FeedingSkillQueryDTO;
import com.pets.pojo.dto.PageRequestDto;
import com.pets.pojo.dto.UpdateStatusDTO;
import com.pets.pojo.entity.FeedingSkill;
import com.pets.pojo.vo.CommonResponseVo;
import org.springframework.stereotype.Service;

import java.net.UnknownHostException;
import java.util.List;
@Service
public interface FeedingSkillService {
    List<FeedingSkill> queryFeedingSkill(Integer skillNum) throws UnknownHostException;

    CommonResponseVo pageQueryAllFeeding(PageRequestDto<FeedingSkillQueryDTO> pageRequestDto);

    FeedingSkill queryOneFeeding(Long id) throws UnknownHostException;

    String insertOneFeeding(FeedingSkillCreateInfo feedingSkillCreateInfo);

    String updateFeedingStatus(UpdateStatusDTO updateStatusDTO);

    String updateFeeding(FeedingSkill updateDTO);

    String deleteFeedingImage(String id);
}
