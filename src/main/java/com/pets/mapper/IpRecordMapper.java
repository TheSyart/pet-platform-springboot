package com.pets.mapper;



import com.pets.pojo.dto.IpQueryDTO;
import com.pets.pojo.entity.IpLocation;
import com.pets.pojo.vo.HomeLoginVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface IpRecordMapper {
    int ipInsert(IpLocation ipLocation);

    List<IpLocation> queryAllIpInfo(IpQueryDTO ipQueryDTO);

    List<HomeLoginVO> homeLoginCount(
            @Param("startOfToday") String startOfToday,
            @Param("endOfToday") String endOfToday);
}
