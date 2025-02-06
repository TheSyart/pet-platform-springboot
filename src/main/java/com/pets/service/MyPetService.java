package com.pets.service;

import com.pets.pojo.dto.MyPetCreateDTO;
import com.pets.pojo.dto.MyPetQueryDTO;
import com.pets.pojo.dto.PageRequestDto;
import com.pets.pojo.entity.PageBean;
import com.pets.pojo.vo.CommonResponseVo;
import com.pets.pojo.vo.MyPetAndroidVO;
import com.pets.pojo.vo.MyPetVO;
import org.springframework.stereotype.Service;

import java.net.UnknownHostException;
import java.util.List;

@Service
public interface MyPetService {
    List<MyPetAndroidVO> getMyPet(String username) throws UnknownHostException;

    String insertMyPet(MyPetCreateDTO myPetDTO);

    CommonResponseVo queryAllPet(PageRequestDto<MyPetQueryDTO> pageRequestDto);

    MyPetVO queryOnePet(Integer id) throws UnknownHostException;

    List<String> queryPetCoat();

    List<String> queryPetDetails();

    List<String> queryPetTimeslot();
}
