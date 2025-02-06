package com.pets.service;

import com.pets.pojo.dto.DynamicsCreateInfo;
import com.pets.pojo.dto.DynamicsQueryDTO;
import com.pets.pojo.dto.PageRequestDto;
import com.pets.pojo.dto.UpdateStatusDTO;
import com.pets.pojo.entity.Dynamics;
import com.pets.pojo.vo.CommonResponseVo;
import com.pets.pojo.vo.DynamicsVO;

import java.net.UnknownHostException;
import java.util.List;

public interface DynamicsService {
    List<DynamicsVO> dynamicsList(int dynamicsNum, String username) throws UnknownHostException;

    String updateDynamics(String id, String username, Integer type);

    String insertDynamics(DynamicsCreateInfo dynamicsCreateInfo);

    CommonResponseVo queryAllDynamics(PageRequestDto<DynamicsQueryDTO> pageRequestDto);

    Dynamics queryOneDynamics(Long id) throws UnknownHostException;

    String updateDynamicsStatus(UpdateStatusDTO updateStatusDTO);
}
