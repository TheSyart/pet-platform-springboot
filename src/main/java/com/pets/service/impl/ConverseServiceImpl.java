package com.pets.service.impl;

import com.github.pagehelper.util.StringUtil;
import com.pets.mapper.ConverseMapper;
import com.pets.pojo.vo.ConverseVO;
import com.pets.service.ConverseService;
import com.pets.utils.base.BaseErrorException;
import com.pets.utils.pic.HandlePic;
import com.pets.webSocket.pojo.EmpWorkLoadVO;
import com.pets.webSocket.pojo.Message;
import com.pets.webSocket.MyWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class ConverseServiceImpl implements ConverseService {

    @Autowired
    ConverseMapper converseMapper;

    @Autowired
    HandlePic handlePic;

    @Override
    public List<ConverseVO> queryConverseList(Integer type, String searchName, String username){

        List<ConverseVO> results = new ArrayList<>();

        List<String> onlineList = MyWebSocketHandler.getConnectedUsers();

        System.out.println(onlineList);
        if (type == 0){
            // 客户列表
            results = converseMapper.queryCustomerList(searchName, username);
        }else if (type == 1){
            // 员工列表
            results = converseMapper.queryEmpList(searchName, username);
        }

        for (ConverseVO result : results) {
            result.setImage(handlePic.fixPath(result.getImage()));
            result.setOnline(onlineList.contains(result.getUsername()));
        }
        return results;
    }

    @Override
    public String insertOneMessage(Message message) {
        if (converseMapper.insertOneMessage(message) != 1){
            throw new BaseErrorException("消息录入失败!");
        }
        return "消息录入成功!";
    }

    @Override
    public List<Message> queryOnePersonMessage(String sender, String receiver, Integer converseNum) {
        List<Message> results = converseMapper.queryOnePersonMessage(sender, receiver, converseNum);
        for (Message result : results) {
            //如果不是文本消息,路径要补全
            if (result.getMessageType() != 0){
                result.setMessage(handlePic.fixPath(result.getMessage()));
                if (!StringUtil.isEmpty(result.getMessageCover()) && !result.getMessageCover().equals("null")){
                    result.setMessageCover(handlePic.fixPath(result.getMessageCover()));
                }
            }
        }
        return results;
    }

    @Override
    public List<EmpWorkLoadVO> countEmpWorkLoad(List<String> empOnlineList, String startTime, String endTime) {
        return converseMapper.countEmpWorkLoad(empOnlineList, startTime, endTime);
    }
}
