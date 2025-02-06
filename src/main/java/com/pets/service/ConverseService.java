package com.pets.service;

import com.pets.pojo.vo.ConverseVO;
import com.pets.webSocket.pojo.EmpWorkLoadVO;
import com.pets.webSocket.pojo.Message;
import java.util.List;

public interface ConverseService {

    List<ConverseVO> queryConverseList(Integer type, String searchName, String username);

    String insertOneMessage(Message message);

    List<Message> queryOnePersonMessage(String sender, String receiver, Integer converseNum);

    List<EmpWorkLoadVO> countEmpWorkLoad(List<String> empOnlineList, String startTime, String endTime);

}
