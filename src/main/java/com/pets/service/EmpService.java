package com.pets.service;

import com.pets.pojo.dto.*;
import com.pets.pojo.entity.Emp;
import com.pets.pojo.entity.PageBean;
import com.pets.pojo.vo.CommonResponseVo;

import java.net.UnknownHostException;
import java.util.List;


public interface EmpService {
    CommonResponseVo pageQueryAllEmp(PageRequestDto<EmpQueryDTO> pageRequestDto);

    String insertNewEmp(EmpCreateInfo empCreateInfo);

    Emp queryOneEmp(Long id) throws UnknownHostException;

    List<String> queryAllJob();

    List<Emp> queryAllEmpByJob(EmpQueryByJobDTO empQueryByJobDTO) throws UnknownHostException;

    String updateEmpStatus(UpdateStatusDTO updateStatusDTO);

    String updateEmp(Emp emp);

    String deleteEmpImage(String id);

    Boolean updatePassword(UpdatePasswordDTO updatePasswordDTO);
}
