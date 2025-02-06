package com.pets.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pets.pojo.dto.MyPetCreateDTO;
import com.pets.pojo.dto.MyPetQueryDTO;
import com.pets.pojo.dto.PageRequestDto;
import com.pets.pojo.vo.CommonResponseVo;
import com.pets.pojo.vo.MyPetAndroidVO;
import com.pets.pojo.vo.MyPetVO;
import com.pets.mapper.CustomerMapper;
import com.pets.mapper.MyPetMapper;
import com.pets.pojo.entity.Customer;
import com.pets.service.MyPetService;
import com.pets.utils.base.BaseErrorException;
import com.pets.utils.common.GetAgeByBirth;
import com.pets.utils.pic.HandlePic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.UnknownHostException;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class MyPetServiceImpl implements MyPetService {

    @Autowired
    MyPetMapper myPetMapper;

    @Autowired
    CustomerMapper customerMapper;

    @Autowired
    GetAgeByBirth getAgeByBirth;

    @Autowired
    HandlePic handlePic;

    @Autowired
    EncyclopediaServiceImpl encyclopediaService;

    @Override
    public List<MyPetAndroidVO> getMyPet(String username) throws UnknownHostException {
        Customer customer = new Customer();
        customer.setUsername(username);
        List<MyPetAndroidVO> results = myPetMapper.getAllMyPetInformation(customerMapper.getCustomerInformation(customer).getId());

        List<String> petBreed = encyclopediaService.queryAllPetBreed();
        List<String> petCoat = queryPetCoat();
        List<String> petDetails = queryPetDetails();

        for (MyPetAndroidVO result : results) {
            result.setImage(handlePic.fixPath(result.getImage()));
            result.setPet_birth(getAgeByBirth.transformBirth(result.getPet_birth()));
            result.setPet_breed(petBreed.get(Integer.parseInt(result.getPet_breed())));
            result.setPet_sex((result.getPet_sex().equals("0") ? "雄性" : "雌性"));
            result.setPet_coat(petCoat.get(Integer.parseInt(result.getPet_coat())));
            result.setPet_details(petDetails.get(Integer.parseInt(result.getPet_details())));
        }
        return results;
    }

    @Override
    public String insertMyPet(MyPetCreateDTO myPetDTO) {
        //获取父级id
        Customer customer = new Customer();
        customer.setUsername(myPetDTO.getUsername());
        myPetDTO.setParent_id(customerMapper.getCustomerInformation(customer).getId());

        if (myPetMapper.insertNewMyPet(myPetDTO) != 1) {
            throw new BaseErrorException("宠物添加失败!");
        }
        return null;
    }

    @Override
    public CommonResponseVo queryAllPet(PageRequestDto<MyPetQueryDTO> pageRequestDto) {
        PageHelper.startPage(pageRequestDto.getPagination().getPage(), pageRequestDto.getPagination().getSize());

        MyPetQueryDTO myPetQueryDTO = pageRequestDto.getConditions();
        CommonResponseVo commonResponseVo = new CommonResponseVo();

        List<MyPetVO> petList = myPetMapper.queryAllPet(myPetQueryDTO);

        PageInfo<MyPetVO> pageInfo = new PageInfo<>(petList);

        commonResponseVo.setList(pageInfo.getList());
        commonResponseVo.setTotal(pageInfo.getTotal());
        return commonResponseVo;
    }

    @Override
    public MyPetVO queryOnePet(Integer id) throws UnknownHostException {
        MyPetVO myPetVO = myPetMapper.queryOnePet(id);
        myPetVO.setImage(handlePic.fixPath(myPetVO.getImage()));
        return myPetVO;
    }

    @Override
    public List<String> queryPetCoat() {
        return myPetMapper.queryPetCoat();
    }

    @Override
    public List<String> queryPetDetails() {
        return myPetMapper.queryPetDetails();
    }

    @Override
    public List<String> queryPetTimeslot() {
        return myPetMapper.queryPetTimeslot();
    }
}