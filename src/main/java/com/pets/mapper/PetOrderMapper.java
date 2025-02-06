package com.pets.mapper;


import com.pets.pojo.dto.PetOrderDTO;
import com.pets.pojo.dto.PetOrderQueryDTO;
import com.pets.pojo.entity.OrderInfo;
import com.pets.pojo.entity.OrderRecord;
import com.pets.pojo.entity.OrderType;
import com.pets.pojo.vo.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PetOrderMapper {
    int insertOrder(PetOrderDTO petOrderDTO);
    int insertOrderInfo(@Param("orderInfoList") List<OrderInfo> orderInfoList);
    List<OrderVO> queryAllOrderByCustomerId(@Param("customerId") String customerId,
                                            @Param("num") Integer num,
                                            @Param("orderStatusList") List<Integer> orderStatusList,
                                            @Param("order_type") Integer order_type);
    List<OrderInfo> queryShoppingOrderInfo(@Param("list") List<String> list);

    List<OrderInfo> queryServiceOrderInfo(@Param("list") List<String> list);

    //生成订单编号时使用
    List<OrderType> queryAllOrderTypeAndFront();

    //返回所有订单类型description
    List<String> queryAllOrderType();

    //返回所有订单类型id
    List<Integer> queryAllOrderTypeId();

    List<PetOrderVO> queryAllOrder(PetOrderQueryDTO petOrderQueryDTO);

    OrderVO queryOneOrder(Long id);

    int updateOrderStatus(@Param("order_id") String order_id,
                          @Param("orderStatus") Integer orderStatus,
                          @Param("status") Integer status);

    int queryOrderTypeByOrderId(String orderId);

    int queryOrderStatusByOrderId(String orderId);

    List<HomeOrderVO> homeOrderCount(@Param("start") String start,
                                     @Param("end") String end);

    List<CommonOrderVO> queryAllOrderInfoDetailsCount(@Param("categoryIds") List<Integer> categoryIds,
                                                      @Param("type") Integer type,
                                                      @Param("start") String start,
                                                      @Param("end") String end);

    List<CommonOrderVO> queryAllShoppingSale(@Param("type") Integer type,
                                             @Param("start") String start,
                                             @Param("end") String end);
    //定时任务:检查订单是否超时
    int updateExpiredOrders(@Param("orderIdList") List<String> orderIdList);

    int insertOrderRecord(@Param("orderRecords") List<OrderRecord> orderRecords);

    List<OrderRecordVO> queryOneOrderRecord(Long id);

    List<String> queryTimeoutOrder();
}
