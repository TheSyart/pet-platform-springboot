package com.pets.service;

import com.pets.pojo.dto.*;
import com.pets.pojo.entity.PetService;
import com.pets.pojo.vo.CommonResponseVo;
import com.pets.pojo.vo.ServiceTypeVO;
import com.pets.pojo.vo.TimeSlotVO;

import java.net.UnknownHostException;
import java.util.List;

public interface PetServiceService {
    List<ServiceTypeVO> queryAllPetServiceType() throws UnknownHostException;

    List<PetService> queryPetServiceById(Long id) throws UnknownHostException;

    List<TimeSlotVO> queryTimeSlot(TimeSlotDTO timeSlotDTO);

    CommonResponseVo queryAllService(PageRequestDto<ServiceQueryDTO> pageRequestDto);

    PetService queryOneService(Long id) throws UnknownHostException;

    String insertNewService(PetServiceCreateInfo petServiceCreateInfo);

    List<String> allServiceType();

    String updateServiceStatus(UpdateStatusDTO updateStatusDTO);

    String updateService(PetService petService);

    String deleteServiceImage(String id);

}
