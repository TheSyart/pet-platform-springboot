package com.pets.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pets.pojo.dto.*;
import com.pets.pojo.entity.Dynamics;
import com.pets.pojo.entity.DynamicsLikePeople;
import com.pets.pojo.vo.CommonResponseVo;
import com.pets.pojo.vo.DynamicsVO;
import com.pets.mapper.DynamicsMapper;
import com.pets.service.DynamicsService;
import com.pets.utils.base.BaseErrorException;
import com.pets.utils.pic.HandlePic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.UnknownHostException;
import java.util.List;
@Service
@Transactional(rollbackFor = Exception.class)
public class DynamicsServiceImpl implements DynamicsService {

    @Autowired
    DynamicsMapper dynamicsMapper;

    @Autowired
    HandlePic handlePic;

    @Override
    public List<DynamicsVO> dynamicsList(int dynamicsNum, String username) throws UnknownHostException {
        List<DynamicsVO> postDynamics = dynamicsMapper.getDynamics(dynamicsNum, username);
        for (DynamicsVO postDynamic : postDynamics) {
            String path = postDynamic.getImage_path();

            // 检查 path动态照片 是否为 null
            if (path == null || path.isEmpty()) {
                postDynamic.setImage_path(null);
            } else {
                postDynamic.setImage_path(handlePic.fixPath(path));
            }

            // 同样对 image头像 进行处理
            String image = postDynamic.getImage();
            if (image != null) {
                postDynamic.setImage(handlePic.fixPath(image));
            }
        }

        System.out.println("发给android前端的动态的数量： " + postDynamics.size());
        return postDynamics;
    }

    @Override
    public String updateDynamics(String id, String username, Integer type) {
        //查看是否有历史点赞记录
        DynamicsLikePeople dynamicsLikePeople = dynamicsMapper.queryDynamicsHistory(id, username);

        //点赞
        if (type == 0){
            if((null == dynamicsLikePeople ?
                    dynamicsMapper.insertDynamicsLikePeople(id, username) :
                    dynamicsMapper.updateDynamicsLikePeople(id, username, 0)) != 1){
                throw new BaseErrorException("点赞失败!");
            }
        //取消点赞
        } else if (type == 1) {
            if(dynamicsMapper.updateDynamicsLikePeople(id, username, 1) != 1){
                throw new BaseErrorException("点赞取消失败!");
            }
        }

        return null;
    }

    @Override
    public String insertDynamics(DynamicsCreateInfo dynamicsCreateInfo) {
        //获得新动态的父级id
        dynamicsCreateInfo.setParent_id(dynamicsMapper.getCustomerIdByUsername(dynamicsCreateInfo.getUsername()));

        if (dynamicsMapper.insertDynamics(dynamicsCreateInfo) != 1){
            throw new BaseErrorException("动态发布失败!");
        }
        return null;
    }

    @Override
    public CommonResponseVo queryAllDynamics(PageRequestDto<DynamicsQueryDTO> pageRequestDto) {
        PageHelper.startPage(pageRequestDto.getPagination().getPage(), pageRequestDto.getPagination().getSize());

        DynamicsQueryDTO dynamicsQueryDTO = pageRequestDto.getConditions();
        CommonResponseVo commonResponseVo = new CommonResponseVo();

        List<DynamicsVO> dynamicsList = dynamicsMapper.queryAllDynamics(dynamicsQueryDTO);
        PageInfo<DynamicsVO> pageInfo = new PageInfo<>(dynamicsList);

        commonResponseVo.setList(pageInfo.getList());
        commonResponseVo.setTotal(pageInfo.getTotal());
        return commonResponseVo;
    }

    @Override
    public Dynamics queryOneDynamics(Long id) throws UnknownHostException {
        Dynamics dynamics = dynamicsMapper.queryOneDynamics(id);
        dynamics.setImage_path(handlePic.fixPath(dynamics.getImage_path()));
        return dynamics;
    }

    @Override
    public String updateDynamicsStatus(UpdateStatusDTO updateStatusDTO) {
        if (dynamicsMapper.updateDynamicsStatus(updateStatusDTO.getId(), updateStatusDTO.getStatus()) != 1){
            throw new BaseErrorException("动态状态操作失败!");
        }
        return "动态状态更改成功!";
    }
}
