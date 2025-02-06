package com.pets.mapper;

import com.pets.pojo.entity.Address;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AddressMapper {
    List<Address> queryAddressByUsername(String id);

    int insertNewAddress(Address address);

    int updateDefaultAddressOne(Long id, Long customer_id);

    int updateAddress(Address address);

    int updateDefaultAddressZero(Long id, Long customer_id);
}

