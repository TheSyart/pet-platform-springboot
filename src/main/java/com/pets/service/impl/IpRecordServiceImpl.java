package com.pets.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pets.mapper.IpRecordMapper;
import com.pets.pojo.dto.IpLocationDTO;
import com.pets.pojo.dto.IpQueryDTO;
import com.pets.pojo.dto.PageRequestDto;
import com.pets.pojo.entity.IpLocation;
import com.pets.pojo.vo.CommonResponseVo;
import com.pets.pojo.vo.HomeLoginVO;
import com.pets.service.IpRecordService;
import com.pets.utils.base.BaseErrorException;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class IpRecordServiceImpl implements IpRecordService {
    @Autowired
    IpRecordMapper ipRecordMapper;

    @Override
    public String ipInsert(IpLocationDTO ipLocationDTO, String username, Integer type, Integer result,Integer login_type) {
        IpLocation ipLocation = new IpLocation();
        ipLocation.setIp(ipLocationDTO.getQuery());
        ipLocation.setMessage(ipLocationDTO.getMessage());
        ipLocation.setAccount(username);
        ipLocation.setAccountType(type);
        ipLocation.setCountry(ipLocationDTO.getCountry());
        ipLocation.setRegion(ipLocationDTO.getRegion());
        ipLocation.setCity(ipLocationDTO.getCity());
        ipLocation.setLat(ipLocationDTO.getLat());
        ipLocation.setLon(ipLocationDTO.getLon());
        ipLocation.setIsp(ipLocationDTO.getIsp());
        ipLocation.setResult(result);
        ipLocation.setLogin_type(login_type);


        if (ipRecordMapper.ipInsert(ipLocation) != 1){
            throw new BaseErrorException("ip信息录入失败!");
        }
        return null;
    }

    @Override
    public CommonResponseVo queryAllIpInfo(PageRequestDto<IpQueryDTO> pageRequestDto) {
        PageHelper.startPage(pageRequestDto.getPagination().getPage(), pageRequestDto.getPagination().getSize());

        IpQueryDTO ipQueryDTO = pageRequestDto.getConditions();
        CommonResponseVo commonResponseVo = new CommonResponseVo();

        List<IpLocation> ipLocations = ipRecordMapper.queryAllIpInfo(ipQueryDTO);
        PageInfo<IpLocation> pageInfo = new PageInfo<>(ipLocations);

        commonResponseVo.setList(pageInfo.getList());
        commonResponseVo.setTotal(pageInfo.getTotal());
        return commonResponseVo;
    }

    @Override
    public List<HomeLoginVO> homeLoginCount(String startOfToday, String endOfToday) {
        return ipRecordMapper.homeLoginCount(startOfToday, endOfToday);
    }
}
