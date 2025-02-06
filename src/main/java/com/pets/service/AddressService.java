package com.pets.service;

import com.pets.pojo.dto.AddressUpdateDefaultDTO;
import com.pets.pojo.entity.Address;
import java.util.List;

public interface AddressService {

    List<Address> queryAddressByUsername(String username);

    String updateAddress(Address address);

    String updateDefaultAddress(AddressUpdateDefaultDTO addressUpdateDefaultDTO);
}
