package com.pets.mapper;

import com.pets.pojo.dto.FeedingSkillCreateInfo;
import com.pets.pojo.dto.FeedingSkillQueryDTO;
import com.pets.pojo.entity.FeedingSkill;
import com.pets.pojo.entity.PetEncyclopediaDetails;
import com.pets.pojo.vo.FeedingSkillVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FeedingSkillMapper {
    List<FeedingSkill> queryFeedingSkill(Integer skillNum);

    List<FeedingSkillVO> pageQueryAllFeeding(FeedingSkillQueryDTO feedingSkillQueryDTO);

    FeedingSkill queryOneFeeding(Long id);

    int insertOneFeeding(FeedingSkillCreateInfo feedingSkillCreateInfo);

    int updateFeedingStatus(Long id, Integer status);

    int updateFeeding(FeedingSkill updateDTO);

    int deleteFeedingImage(String id);
}
