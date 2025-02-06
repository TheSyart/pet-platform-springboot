package com.pets.mapper;

import com.pets.pojo.dto.DynamicsCreateInfo;
import com.pets.pojo.dto.DynamicsQueryDTO;
import com.pets.pojo.entity.Dynamics;
import com.pets.pojo.entity.DynamicsLikePeople;
import com.pets.pojo.vo.DynamicsVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface DynamicsMapper {
    //查询动态
    List<DynamicsVO> getDynamics(int dynamicsNum, String username);

    //增加新动态
     int insertDynamics(DynamicsCreateInfo dynamicsCreateInfo);

     @Select("select id from pet_customer where username = #{username}")
     String getCustomerIdByUsername(String username);

    List<DynamicsVO> queryAllDynamics(DynamicsQueryDTO dynamicsQueryDTO);

    Dynamics queryOneDynamics(@Param("id") Long id);

    //查询该用户与该动态的历史记录
    DynamicsLikePeople queryDynamicsHistory(String id, String username);

    int insertDynamicsLikePeople(String dynamicsId, String username);

    int updateDynamicsLikePeople(String dynamicsId, String username, Integer status);

    int updateDynamicsStatus(Long id, Integer status);
}
