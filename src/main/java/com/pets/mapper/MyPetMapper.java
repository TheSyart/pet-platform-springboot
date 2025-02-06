package com.pets.mapper;

import com.pets.pojo.dto.MyPetCreateDTO;
import com.pets.pojo.dto.MyPetQueryDTO;
import com.pets.pojo.vo.MyPetAndroidVO;
import com.pets.pojo.vo.MyPetVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface MyPetMapper {

    List<MyPetAndroidVO> getAllMyPetInformation(String parent_id);

    int insertNewMyPet(MyPetCreateDTO myPetDTO);

    List<MyPetVO> queryAllPet(MyPetQueryDTO myPetQueryDTO);

    MyPetVO queryOnePet(Integer id);

    List<String> queryPetCoat();

    List<String> queryPetDetails();

    List<String> queryPetTimeslot();
}
