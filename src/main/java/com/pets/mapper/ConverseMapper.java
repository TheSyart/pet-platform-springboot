package com.pets.mapper;

import com.pets.pojo.vo.ConverseVO;
import com.pets.webSocket.pojo.EmpWorkLoadVO;
import com.pets.webSocket.pojo.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ConverseMapper {
    List<ConverseVO> queryCustomerList(String searchName, String username);

    List<ConverseVO> queryEmpList(String searchName, String username);

    int insertOneMessage(Message message);

    List<Message> queryOnePersonMessage(String sender, String receiver, Integer converseNum);

    List<EmpWorkLoadVO> countEmpWorkLoad(@Param("empOnlineList") List<String> empOnlineList, String startTime, String endTime);
}
