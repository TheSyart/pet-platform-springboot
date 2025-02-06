package com.pets.service;

import com.pets.pojo.dto.CustomerQueryDTO;
import com.pets.pojo.dto.PageRequestDto;
import com.pets.pojo.dto.UpdateStatusDTO;
import com.pets.pojo.entity.Customer;
import com.pets.pojo.vo.CommonResponseVo;
import org.springframework.stereotype.Service;

import java.net.UnknownHostException;
import java.util.concurrent.ExecutionException;

@Service
public interface CustomerService {
    void registerCustomer(String phone, Integer gender, String birth);

    String sendMessage(String phone, String sendType) throws ExecutionException, InterruptedException;

    String checkRegisterMessage(String phone, String code, Integer gender, String birth) throws Exception;

    CommonResponseVo queryAllCustomer(PageRequestDto<CustomerQueryDTO> pageRequestDto);

    Customer queryOneCustomer(Long id) throws UnknownHostException;

    String updateCustomerStatus(UpdateStatusDTO updateStatusDTO);

    String updateCustomer(Customer customer) throws Exception;

    String deleteCustomerImage(String id);

    Customer queryOnePersonInformation(String username);

}
