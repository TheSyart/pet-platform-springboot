package com.pets.mapper;

import com.pets.pojo.dto.CustomerQueryDTO;
import com.pets.pojo.entity.Customer;
import com.pets.pojo.vo.CustomerVO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface CustomerMapper {
    //检查手机号是否唯一
    int checkPhoneUnique(String phone);

    int registerNewAccount(Customer customer);

    Customer getCustomerInformation(Customer customer);

    List<CustomerVO> queryAllCustomer(CustomerQueryDTO customerQueryDTO);

    Customer queryOneCustomer(Long id);

    int updateCustomerStatus(Long id, Integer status);

    int updateCustomer(Customer customer);

    int deleteCustomerImage(String id);

    int unbindPhone(String phone);
}
