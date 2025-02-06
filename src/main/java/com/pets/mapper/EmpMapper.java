package com.pets.mapper;

import com.pets.pojo.dto.EmpQueryDTO;
import com.pets.pojo.dto.UpdatePasswordDTO;
import com.pets.pojo.entity.Emp;
import com.pets.pojo.vo.EmpVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EmpMapper {
    Emp queryOneEmp(Long id);

    List<EmpVO> pageQueryAllEmp(EmpQueryDTO empQueryDTO);

    int insertNewEmp(Emp emp);

    List<String> queryAllJob();

    List<Emp> queryAllEmpByJob(@Param("job") Integer job);

    int checkAccountUniqueness(String username);

    int checkPhoneUniqueness(String phone);

    int updateEmpStatus(Long id, Integer status);

    int updateEmp(Emp emp);

    int deleteEmpImage(String id);

    int updatePassword(UpdatePasswordDTO updatePasswordDTO);
}
