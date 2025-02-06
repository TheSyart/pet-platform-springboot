package com.pets.service;

import com.pets.pojo.dto.PageRequestDto;
import com.pets.pojo.dto.PetShoppingCreateInfo;
import com.pets.pojo.dto.ShoppingQueryDTO;
import com.pets.pojo.dto.UpdateStatusDTO;
import com.pets.pojo.entity.OrderInfo;
import com.pets.pojo.entity.PetShopping;
import com.pets.pojo.vo.CommonResponseVo;
import com.pets.pojo.vo.PetShoppingVO;
import com.pets.pojo.vo.ShoppingStockVO;

import java.net.UnknownHostException;
import java.util.List;

public interface PetShoppingService {
    List<PetShoppingVO> noPageQueryAllShopping() throws UnknownHostException;

    List<String> queryAllShoppingType();

    List<ShoppingStockVO> queryShoppingStock(List<Long> shoppingIdList);

    int updateShoppingStock(List<OrderInfo> orderInfoList, String type);

    CommonResponseVo queryAllShopping(PageRequestDto<ShoppingQueryDTO> pageRequestDto);

    PetShopping queryOneShopping(Long id) throws UnknownHostException;

    String insertNewShopping(PetShoppingCreateInfo petShoppingCreateInfo);

    String updateShoppingStatus(UpdateStatusDTO updateStatusDTO);

    String updateShopping(PetShopping petShopping);

    String deleteShoppingImage(String id);
}

