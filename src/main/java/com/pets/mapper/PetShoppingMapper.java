package com.pets.mapper;


import com.pets.pojo.dto.PetShoppingCreateInfo;
import com.pets.pojo.dto.ShoppingQueryDTO;
import com.pets.pojo.entity.OrderInfo;
import com.pets.pojo.entity.PetShopping;
import com.pets.pojo.vo.PetShoppingVO;
import com.pets.pojo.vo.ShoppingStockVO;
import com.pets.pojo.vo.ShoppingVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PetShoppingMapper {
    List<PetShoppingVO> noPageQueryAllShopping();

    //查询宠物商城所有类型name
    List<String> queryAllShoppingType();

    //查询宠物商城所有类型id
    List<Integer> queryAllShoppingTypeId();

    List<ShoppingStockVO> queryShoppingStock(@Param("shoppingIdList") List<Long> shoppingIdList);

    int updateShoppingStock(List<ShoppingStockVO> shoppingStockVOS);

    List<ShoppingVO> queryAllShopping(ShoppingQueryDTO shoppingQueryDTO);

    PetShopping queryOneShopping(Long id);

    int insertNewShopping(PetShoppingCreateInfo petShoppingCreateInfo);

    int updateShoppingStatus(Long id, Integer status);

    int updateShopping(PetShopping petShopping);

    int deleteShoppingImage(String id);
}

