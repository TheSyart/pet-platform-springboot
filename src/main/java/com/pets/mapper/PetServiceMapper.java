package com.pets.mapper;

import com.pets.pojo.dto.PetServiceCreateInfo;
import com.pets.pojo.dto.ServiceQueryDTO;
import com.pets.pojo.dto.TimeSlotQueryPeopleDTO;
import com.pets.pojo.entity.PetService;
import com.pets.pojo.vo.PetServiceV0;
import com.pets.pojo.vo.ServiceTypeVO;
import com.pets.pojo.vo.TimeSlotVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PetServiceMapper {
    List<ServiceTypeVO> queryAllPetServiceType();

    List<PetService> queryPetServiceById(Long id);

    List<TimeSlotVO> queryTimeSlot();

    List<Integer> queryTimeSlotPeople(
            @Param("timeSlotQueryPeopleDTOS") List<TimeSlotQueryPeopleDTO> timeSlotQueryPeopleDTOS,
            @Param("doctorId") Integer doctorId);

    List<PetServiceV0> queryAllService(ServiceQueryDTO serviceQueryDTO);

    //获取宠物服务所有类型name
    List<String> allServiceType();

    //获取宠物服务所有类型id
    List<Integer> allServiceTypeId();

    PetService queryOneService(Long id);

    int insertNewService(PetServiceCreateInfo petServiceCreateInfo);

    int updateServiceStatus(Long id, Integer status);

    int updateService(PetService petService);

    int deleteServiceImage(String id);
}
