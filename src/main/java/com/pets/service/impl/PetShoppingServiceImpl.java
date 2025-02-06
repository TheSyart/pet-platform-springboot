package com.pets.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pets.mapper.PetShoppingMapper;
import com.pets.pojo.dto.*;
import com.pets.pojo.entity.OrderInfo;
import com.pets.pojo.entity.PetShopping;
import com.pets.pojo.vo.*;
import com.pets.service.PetShoppingService;
import com.pets.utils.base.BaseErrorException;
import com.pets.utils.pic.HandlePic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class PetShoppingServiceImpl implements PetShoppingService {

    @Autowired
    PetShoppingMapper petShoppingMapper;

    @Autowired
    HandlePic handlePic;

    @Override
    public List<PetShoppingVO> noPageQueryAllShopping() throws UnknownHostException {
        List<PetShoppingVO> results = petShoppingMapper.noPageQueryAllShopping();
        List<String> type = queryAllShoppingType();

        for (PetShoppingVO result : results) {
            result.setImage(handlePic.fixPath(result.getImage()));

            int shoppingTypeId = result.getShoppingTypeId();
            if (shoppingTypeId >= 0 && shoppingTypeId < type.size()) {
                result.setCategory(type.get(shoppingTypeId));
            } else {
                // 处理超出范围的情况，例如设置默认值或日志记录
                result.setCategory("Unknown Category"); // 或其他适当的默认值
            }
        }
        return results;
    }

    @Override
    public List<String> queryAllShoppingType() {
        return petShoppingMapper.queryAllShoppingType();
    }

    @Override
    public List<ShoppingStockVO> queryShoppingStock(List<Long> shoppingIdList) {
        return petShoppingMapper.queryShoppingStock(shoppingIdList);
    }

    @Override
    public int updateShoppingStock(List<OrderInfo> orderInfoList, String type) {
        List<Long> shoppingIdList = new ArrayList<>();
        for (OrderInfo orderInfo : orderInfoList) {
            shoppingIdList.add(orderInfo.getShopping_id());
        }

        // 查询库存
        List<ShoppingStockVO> shoppingStockList = queryShoppingStock(shoppingIdList);

        // 检查库存并更新
        for (int i = 0; i < shoppingStockList.size(); i++) {
            ShoppingStockVO stockVO = shoppingStockList.get(i);
            OrderInfo orderInfo = orderInfoList.get(i);

            // 确保订单的商品ID与库存对应的商品ID匹配
            if (Objects.equals(stockVO.getId(), orderInfo.getShopping_id())) {
                int stock = stockVO.getStock();
                int orderQuantity = orderInfo.getQuantity();

                //买商品
                if (type.equals("insert")){
                    // 检查库存是否足够
                    if (stock == 0 || stock - orderQuantity < 0) {
                        throw new BaseErrorException("库存不足, 请刷新界面!");
                    } else {
                        // 更新库存，减少库存量
                        stockVO.setStock(stock - orderQuantity);
                    }

                //退商品
                }else if (type.equals("delete")){
                    // 更新库存，增加库存量
                    stockVO.setStock(stock + orderQuantity);
                }

            }
        }
        // 更新库存到数据库
        return petShoppingMapper.updateShoppingStock(shoppingStockList);
    }

    @Override
    public CommonResponseVo queryAllShopping(PageRequestDto<ShoppingQueryDTO> pageRequestDto) {
        PageHelper.startPage(pageRequestDto.getPagination().getPage(), pageRequestDto.getPagination().getSize());

        ShoppingQueryDTO shoppingQueryDTO = pageRequestDto.getConditions();
        CommonResponseVo commonResponseVo = new CommonResponseVo();

        List<ShoppingVO> shoppingVOS = petShoppingMapper.queryAllShopping(shoppingQueryDTO);
        PageInfo<ShoppingVO> pageInfo = new PageInfo<>(shoppingVOS);

        commonResponseVo.setList(pageInfo.getList());
        commonResponseVo.setTotal(pageInfo.getTotal());
        return commonResponseVo;
    }

    @Override
    public PetShopping queryOneShopping(Long id) throws UnknownHostException {
        PetShopping petShopping = petShoppingMapper.queryOneShopping(id);
        petShopping.setImage(handlePic.fixPath(petShopping.getImage()));
        return petShopping;
    }

    @Override
    public String insertNewShopping(PetShoppingCreateInfo petShoppingCreateInfo) {
        if (petShoppingMapper.insertNewShopping(petShoppingCreateInfo) != 1){
            throw new BaseErrorException("商品录入失败!");
        }
        return "商品录入成功!";
    }

    @Override
    public String updateShoppingStatus(UpdateStatusDTO updateStatusDTO) {
        if (petShoppingMapper.updateShoppingStatus(updateStatusDTO.getId(), updateStatusDTO.getStatus()) != 1){
            throw new BaseErrorException("商品状态操作失败!");
        }
        return "商品状态更改成功!";
    }

    @Override
    public String updateShopping(PetShopping petShopping) {
        if (petShoppingMapper.updateShopping(petShopping) != 1){
            throw new BaseErrorException("商品信息编辑失败!");
        }
        return "商品信息更改成功!";
    }

    @Override
    public String deleteShoppingImage(String id) {
        if(petShoppingMapper.deleteShoppingImage(id) != 1){
            throw new BaseErrorException("数据库同步删除照片失败!");
        }
        return null;
    }

}
