package com.pets.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pets.mapper.FeedingSkillMapper;
import com.pets.pojo.dto.*;
import com.pets.pojo.entity.FeedingSkill;
import com.pets.pojo.entity.PetEncyclopediaDetails;
import com.pets.pojo.vo.CommonResponseVo;
import com.pets.pojo.vo.EncyclopediaVO;
import com.pets.pojo.vo.FeedingSkillVO;
import com.pets.service.FeedingSkillService;
import com.pets.utils.base.BaseErrorException;
import com.pets.utils.pic.HandlePic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.UnknownHostException;
import java.util.List;
@Service
@Transactional(rollbackFor = Exception.class)
public class FeedingSkillServiceImpl implements FeedingSkillService {

    @Autowired
    FeedingSkillMapper feedingSkillMapper;

    @Autowired
    HandlePic handlePic;
    @Override
    public List<FeedingSkill> queryFeedingSkill(Integer skillNum) throws UnknownHostException {
        List<FeedingSkill> results = feedingSkillMapper.queryFeedingSkill(skillNum);
        for (FeedingSkill result : results) {
            result.setImage(handlePic.fixPath(result.getImage()));
        }
        return results;
    }

    @Override
    public CommonResponseVo pageQueryAllFeeding(PageRequestDto<FeedingSkillQueryDTO> pageRequestDto) {
        PageHelper.startPage(pageRequestDto.getPagination().getPage(), pageRequestDto.getPagination().getSize());

        FeedingSkillQueryDTO feedingSkillQueryDTO = pageRequestDto.getConditions();
        CommonResponseVo commonResponseVo = new CommonResponseVo();

        List<FeedingSkillVO> feedingSkillVOS = feedingSkillMapper.pageQueryAllFeeding(feedingSkillQueryDTO);

        PageInfo<FeedingSkillVO> pageInfo = new PageInfo<>(feedingSkillVOS);

        commonResponseVo.setList(pageInfo.getList());
        commonResponseVo.setTotal(pageInfo.getTotal());
        return commonResponseVo;
    }

    @Override
    public FeedingSkill queryOneFeeding(Long id) throws UnknownHostException {
        FeedingSkill result = feedingSkillMapper.queryOneFeeding(id);
        result.setImage(handlePic.fixPath(result.getImage()));
        return result;
    }

    @Override
    public String insertOneFeeding(FeedingSkillCreateInfo feedingSkillCreateInfo) {
        if (feedingSkillMapper.insertOneFeeding(feedingSkillCreateInfo) != 1){
            throw new BaseErrorException("新宠物喂养技巧录入失败!");
        }
        return "新宠物喂养技巧录入成功!";
    }

    @Override
    public String updateFeedingStatus(UpdateStatusDTO updateStatusDTO) {
        if (feedingSkillMapper.updateFeedingStatus(updateStatusDTO.getId(), updateStatusDTO.getStatus()) != 1){
            throw new BaseErrorException("宠物喂养技巧状态操作失败!");
        }
        return "宠物喂养技巧状态更改成功!";
    }

    @Override
    public String updateFeeding(FeedingSkill updateDTO) {
        if (feedingSkillMapper.updateFeeding(updateDTO) != 1){
            throw new BaseErrorException("宠物喂养技巧信息编辑失败!");
        }
        return "宠物喂养技巧信息更改成功!";
    }

    @Override
    public String deleteFeedingImage(String id) {
        if(feedingSkillMapper.deleteFeedingImage(id) != 1){
            throw new BaseErrorException("数据库同步删除照片失败!");
        }
        return null;
    }
}
