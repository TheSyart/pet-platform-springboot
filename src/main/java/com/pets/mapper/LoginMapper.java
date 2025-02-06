package com.pets.mapper;

import com.pets.pojo.dto.LoginDTO;
import com.pets.pojo.entity.Customer;
import com.pets.pojo.entity.Emp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface LoginMapper {
    //商户登录
    Emp empLogin(LoginDTO loginDTO);

    //客户登录
    Customer loginByCustomerPassword(LoginDTO loginDTO);

    //员工账户是否存在
    int checkEmpExists(String username);

    //客户账户是否存在
    int checkCustomerExists(String username);

    //客户名字
    String getCustomerName(String username);

    //员工名字
    String getEmpName(String username);

    Customer getInformationByPhone(String phone);

    int getEmpAccountStatus(String username);

    int getCustomerAccountStatus(String username);
}
