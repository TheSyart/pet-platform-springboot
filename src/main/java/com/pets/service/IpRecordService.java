package com.pets.service;

import com.pets.pojo.dto.IpLocationDTO;
import com.pets.pojo.dto.IpQueryDTO;
import com.pets.pojo.dto.PageRequestDto;
import com.pets.pojo.vo.CommonResponseVo;
import com.pets.pojo.vo.HomeLoginVO;

import java.util.List;

public interface IpRecordService {

    String ipInsert(IpLocationDTO ipLocationDTO, String username, Integer type, Integer result, Integer login_type);

    CommonResponseVo queryAllIpInfo(PageRequestDto<IpQueryDTO> pageRequestDto);

    List<HomeLoginVO> homeLoginCount(String startOfToday, String endOfToday);
}
