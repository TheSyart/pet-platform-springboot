package com.pets.service.impl;

import com.github.pagehelper.util.StringUtil;
import com.pets.mapper.AddressMapper;
import com.pets.mapper.CustomerMapper;
import com.pets.pojo.dto.AddressUpdateDefaultDTO;
import com.pets.pojo.entity.Address;
import com.pets.pojo.entity.Customer;
import com.pets.service.AddressService;
import com.pets.utils.base.BaseErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class AddressServiceImpl implements AddressService {
    @Autowired
    AddressMapper addressMapper;

    @Autowired
    CustomerMapper customerMapper;


    @Override
    public List<Address> queryAddressByUsername(String username) {
        Customer customer = new Customer();
        customer.setUsername(username);
        customer = customerMapper.getCustomerInformation(customer);

        return addressMapper.queryAddressByUsername(customer.getId());
    }

    @Override
    public String updateAddress(Address address) {

        Customer customer = new Customer();
        customer.setUsername(address.getUsername());
        customer = customerMapper.getCustomerInformation(customer);
        if (customer == null){
            throw new BaseErrorException("没有该客户");
        }
        address.setCustomer_id(Long.valueOf(customer.getId()));


        if (StringUtil.isEmpty(address.getId())){
            //录入新地址

            // 将该用户默认地址清空
            int affectedRows = addressMapper.updateDefaultAddressOne(null, Long.valueOf(customer.getId()));

            // 检查返回值是否为 0 或 1
            if (affectedRows != 0 && affectedRows != 1) {
                throw new BaseErrorException("默认地址清空失败，返回值异常：" + affectedRows);
            }


            if(addressMapper.insertNewAddress(address) != 1){
                throw new BaseErrorException("地址录入失败");
            }
        }else {
            // 将该用户默认地址清空
            int affectedRows = addressMapper.updateDefaultAddressOne(null, Long.valueOf(customer.getId()));
            // 检查返回值是否为 0 或 1
            if (affectedRows != 0 && affectedRows != 1) {
                throw new BaseErrorException("默认地址清空失败，返回值异常：" + affectedRows);
            }

            //更新地址
            if(addressMapper.updateAddress(address) != 1){
                throw new BaseErrorException("地址更新失败");
            }
        }

        return null;
    }

    @Override
    public String updateDefaultAddress(AddressUpdateDefaultDTO addressUpdateDefaultDTO) {
        //取消老地址
        if (addressUpdateDefaultDTO.getOldId() != null){
            if (addressMapper.updateDefaultAddressOne(addressUpdateDefaultDTO.getOldId(), null) != 1){
                throw new BaseErrorException("取消默认地址出错");
            }
        }
        //设置新地址
        if (addressUpdateDefaultDTO.getNewId() != null){
            if (addressMapper.updateDefaultAddressZero(addressUpdateDefaultDTO.getNewId(), null) != 1){
                throw new BaseErrorException("设置新默认地址出错");
            }
        }
        return null;
    }
}
