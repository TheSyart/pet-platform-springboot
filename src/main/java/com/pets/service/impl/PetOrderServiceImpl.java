package com.pets.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pets.mapper.CustomerMapper;
import com.pets.mapper.PetOrderMapper;
import com.pets.mapper.PetServiceMapper;
import com.pets.mapper.PetShoppingMapper;
import com.pets.pojo.dto.*;
import com.pets.pojo.entity.Customer;
import com.pets.pojo.entity.OrderInfo;
import com.pets.pojo.entity.OrderRecord;
import com.pets.pojo.entity.OrderType;
import com.pets.pojo.vo.*;
import com.pets.service.PetOrderService;
import com.pets.service.PetShoppingService;
import com.pets.utils.EncryptionUtils.OrderNumberGenerator;
import com.pets.utils.EncryptionUtils.ParseOrderNumber;
import com.pets.utils.base.BaseErrorException;
import com.pets.utils.pic.HandlePic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.net.UnknownHostException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Transactional(rollbackFor = Exception.class)
public class PetOrderServiceImpl implements PetOrderService {
    @Autowired
    PetOrderMapper petOrderMapper;

    @Autowired
    CustomerMapper customerMapper;

    @Autowired
    OrderNumberGenerator orderNumberGenerator;

    @Autowired
    ParseOrderNumber parseOrderNumber;

    @Autowired
    PetShoppingService petShoppingService;

    @Autowired
    HandlePic handlePic;

    @Autowired
    PetShoppingMapper petShoppingMapper;

    @Autowired
    PetServiceMapper petServiceMapper;


    @Override
    public String insertOrder(PetOrderDTO petOrderDTO) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        Customer customer = new Customer();
        customer.setUsername(petOrderDTO.getUsername());
        customer = customerMapper.getCustomerInformation(customer);

        //查找该订单类型
        String orderFront = "";
        List<OrderType> orderTypes = petOrderMapper.queryAllOrderTypeAndFront();
        for (OrderType orderType : orderTypes) {
             if (Objects.equals(orderType.getOrder_type(), petOrderDTO.getOrder_type())) {
                 orderFront = orderType.getOrderFront();
             }
        }


        List<String> list = orderNumberGenerator.simpleGenerateOrderNumber(customer.getUsername(), orderFront);

        //生成订单编号与秘钥
        petOrderDTO.setCustomer_id(customer.getId());
        petOrderDTO.setOrder_id(list.get(1));
        petOrderDTO.setSecretKey(list.get(0));

        List<OrderInfo> orderInfoList = petOrderDTO.getOrderInfoList();
        for (OrderInfo orderInfo : orderInfoList) {
            orderInfo.setOrder_id(list.get(1));
        }

        //订单库存更新
        if (petOrderDTO.getOrder_type() == 0){
            if (petShoppingService.updateShoppingStock(orderInfoList, "insert") != orderInfoList.size()) {
                throw new BaseErrorException("商品订单录入失败");
            }
        }
        //订单内商品详情插入
        if (petOrderMapper.insertOrderInfo(orderInfoList) != orderInfoList.size()){
            throw new BaseErrorException("商品订单录入失败");
        }
        //订单插入
        if (petOrderMapper.insertOrder(petOrderDTO) != 1) {
            throw new BaseErrorException("商品订单录入失败");
        }

        //记录订单生成的日志
        insertOrderRecord(List.of(petOrderDTO.getOrder_id()), 0, customer.getId(), 0);
        return null;
    }


    @Override
    public List<OrderVO> queryAllOrderByCustomerId(Integer order_type, String username, Integer orderPart, Integer num) throws UnknownHostException {
        Customer customer = new Customer();
        customer.setUsername(username);
        customer = customerMapper.getCustomerInformation(customer);

        List<String> list = new ArrayList<>();

//        0.订单待确认 1.商品待配送 2.商品自取中
//        3.服务待服务 4.商品配送中 5.商品已到货
//        6.订单取消中 7.服务已超时 8.申请退货中
//        9.订单已退款 10.订单已完成

        //通过orderPart，判断是哪部分状态
        //orderPart = 0,查询全部
        //orderPart = 1,查询待收货
        //orderPart = 2,查询已取消
        //orderPart = 3,查询已完成
        //orderPart = 4,查询已超时
        List<Integer> orderStatusList = (
                orderPart == 0 ? List.of(0,1,2,3,4,5,6,7,8,9,10) :
                        orderPart == 1 ? List.of(0,1,2,3,4,5) :
                                orderPart == 2 ? List.of(6,8,9) :
                                        orderPart == 3 ? List.of(10) : List.of(7)
        );

        //获取用户订单集合
        List<OrderVO> orderVOS = petOrderMapper.queryAllOrderByCustomerId(customer.getId(), num, orderStatusList, order_type);
        for (OrderVO orderVO : orderVOS) {
            list.add(orderVO.getOrder_id());
        }

        //根据订单类型，判断使用哪种查询
        List<OrderInfo> orderInfoList = (order_type == 0 ?
                petOrderMapper.queryShoppingOrderInfo(list)
                : petOrderMapper.queryServiceOrderInfo(list));

        //补全照片路径
        for (OrderVO orderVO : orderVOS) {
            List<OrderInfo> infoList = new ArrayList<>();
            for (OrderInfo orderInfo : orderInfoList) {
                if (orderVO.getOrder_id().equals(orderInfo.getOrder_id())){
                    orderInfo.setImage(handlePic.fixPath(orderInfo.getImage()));
                    infoList.add(orderInfo);
                }
            }
            orderVO.setOrderInfoList(infoList);
        }
        return orderVOS;
    }

    @Override
    public List<String> queryAllOrderType() {
        return petOrderMapper.queryAllOrderType();
    }

    //订单超时的定时任务
    @Override
    public int updateExpiredOrders() {
        //查询符合条件的订单数量
        List<String> orderIdList = petOrderMapper.queryTimeoutOrder();

        if (orderIdList.isEmpty()){
            return 0;
        }

        int count = petOrderMapper.updateExpiredOrders(orderIdList);
        if (count != orderIdList.size()){
            throw new BaseErrorException("超时订单更新失败!");
        }

        //记录订单操作日志
        insertOrderRecord(orderIdList, 2, String.valueOf(0), 7 );
        return count;
    }

    @Override
    public CommonResponseVo queryAllOrder(PageRequestDto<PetOrderQueryDTO> pageRequestDto) {
        PageHelper.startPage(pageRequestDto.getPagination().getPage(), pageRequestDto.getPagination().getSize());

        PetOrderQueryDTO petOrderQueryDTO = pageRequestDto.getConditions();
        CommonResponseVo commonResponseVo = new CommonResponseVo();

        List<PetOrderVO> petOrderVOS = petOrderMapper.queryAllOrder(petOrderQueryDTO);
        PageInfo<PetOrderVO> pageInfo = new PageInfo<>(petOrderVOS);

        commonResponseVo.setList(pageInfo.getList());
        commonResponseVo.setTotal(pageInfo.getTotal());
        return commonResponseVo;
    }

    @Override
    public OrderVO queryOneOrder(Long id) throws UnknownHostException {
        OrderVO orderVO = petOrderMapper.queryOneOrder(id);

        //根据订单类型，判断使用哪种查询
        //获取该订单详情
        List<OrderInfo> orderInfoList = orderVO.getOrder_type() == 0 ?
                petOrderMapper.queryShoppingOrderInfo(List.of(orderVO.getOrder_id()))
                : petOrderMapper.queryServiceOrderInfo(List.of(orderVO.getOrder_id()));
        //补全路径
        for (OrderInfo info : orderInfoList) {
            info.setImage(handlePic.fixPath(info.getImage()));
        }
        orderVO.setOrderInfoList(orderInfoList);

        //获取订单日志
        List<OrderRecordVO> orderRecordList = petOrderMapper.queryOneOrderRecord(id);
        orderVO.setOrderRecordList(orderRecordList);
        return orderVO;
    }

    @Override
    public List<HomeOrderVO> homeOrderCount(String start, String end) {
        return petOrderMapper.homeOrderCount(start, end);
    }

    @Override
    public List<OrderInfoDetailsCountVO> queryAllOrderInfoDetailsCount(String start, String end) {
        List<OrderInfoDetailsCountVO> orderInfoDetailsCountVOS = new ArrayList<>();
        List<Integer> orderTypeList = petOrderMapper.queryAllOrderTypeId();

        for (int i = 0; i < orderTypeList.size(); i++) {

            Integer type = orderTypeList.get(i);
            List<Integer> typeIdList = new ArrayList<>();
            List<CommonOrderVO> commonOrderVOS = new ArrayList<>();
            OrderInfoDetailsCountVO detailsCountVO = new OrderInfoDetailsCountVO();

            if (type == 0){
                //宠物商城
                typeIdList = petShoppingMapper.queryAllShoppingTypeId();
                commonOrderVOS = petOrderMapper.queryAllOrderInfoDetailsCount(typeIdList, 0, start, end);
            } else if (type == 1) {
                //宠物服务
                typeIdList = petServiceMapper.allServiceTypeId();
                commonOrderVOS = petOrderMapper.queryAllOrderInfoDetailsCount(typeIdList, 1, start, end);
            } else {
                throw new BaseErrorException("奇怪的新类型：" + type + "!");
            }

            detailsCountVO.setOrderType(type);
            detailsCountVO.setOrderInfoList(commonOrderVOS);
            orderInfoDetailsCountVOS.add(detailsCountVO);
        }
        return orderInfoDetailsCountVOS;
    }

    @Override
    public List<OrderInfoDetailsCountVO> queryAllShoppingSale(String start, String end) {
        List<OrderInfoDetailsCountVO> orderInfoDetailsCountVOS = new ArrayList<>();
        List<Integer> orderTypeList = petOrderMapper.queryAllOrderTypeId();

        for (int i = 0; i < orderTypeList.size(); i++) {

            Integer type = orderTypeList.get(i);
            List<Integer> typeIdList = new ArrayList<>();
            List<CommonOrderVO> commonOrderVOS = new ArrayList<>();
            OrderInfoDetailsCountVO detailsCountVO = new OrderInfoDetailsCountVO();

            if (type == 0){
                //宠物商城
                commonOrderVOS = petOrderMapper.queryAllShoppingSale(0, start, end);
            } else if (type == 1) {
                //宠物服务
                commonOrderVOS = petOrderMapper.queryAllShoppingSale(1, start, end);
            } else {
                throw new BaseErrorException("奇怪的新类型：" + type + "!");
            }

            detailsCountVO.setOrderType(type);
            detailsCountVO.setOrderInfoList(commonOrderVOS);
            orderInfoDetailsCountVOS.add(detailsCountVO);
        }
        return orderInfoDetailsCountVOS;
    }


//        0.订单待确认 1.商品待配送 2.商品自取中
//        3.服务待服务 4.商品配送中 5.商品已到货
//        6.订单取消中 7.服务已超时 8.申请退货中
//        9.订单已退款 10.订单已完成
    @Override
    public String updateOrderStatus(OrderIdDTO orderIdDTO) {
        //进行各种状态的判断
        Integer orderStatus = orderIdDTO.getOrderStatus() == null ? null : orderIdDTO.getOrderStatus();
        Integer status = orderIdDTO.getStatus() == null ? null : orderIdDTO.getStatus();

        if (orderStatus != null && orderStatus == 9){
            removeOneOrder(orderIdDTO.getOrder_id());
        }

        if (petOrderMapper.updateOrderStatus(String.valueOf(orderIdDTO.getOrder_id()), orderStatus, status) != 1){
            throw new BaseErrorException("订单状态更新失败!");
        }

        //记录订单状态更新的日志
        if (orderStatus != null){
            insertOrderRecord(List.of(orderIdDTO.getOrder_id()), orderIdDTO.getOperatorType(), orderIdDTO.getId(), orderIdDTO.getOrderStatus());
        } else if (status != null) {
            //客户删除历史订单，记录状态为11
            insertOrderRecord(List.of(orderIdDTO.getOrder_id()), orderIdDTO.getOperatorType(), orderIdDTO.getId(), 11);
        }else {
            throw new BaseErrorException("订单日志出现逃逸!");
        }

        return "订单状态更新成功!";
    }

    public String removeOneOrder(String order_id) {
        List<String> list = new ArrayList<>();
        list.add(order_id);
        //根据订单编号查询订单类型
        int type = petOrderMapper.queryOrderTypeByOrderId(order_id);

        //根据订单编号查询订单状态
        int orderStatus = petOrderMapper.queryOrderStatusByOrderId(order_id);

        //订单状态
        //如果为 6.订单取消中 8.申请退货中
        //订单可进行取消，退款操作
        if (orderStatus != 6 && orderStatus != 8){
            throw new BaseErrorException("订单"+ order_id + "当前状态" + orderStatus + "不可取消!");
        }

        //根据订单类型，判断使用哪种查询
        List<OrderInfo> orderInfoList = (type == 0 ?
                petOrderMapper.queryShoppingOrderInfo(list)
                : petOrderMapper.queryServiceOrderInfo(list));

        //订单库存更新
        if (type == 0){
            System.out.println("Order Info List: " + orderInfoList);
            if (petShoppingService.updateShoppingStock(orderInfoList, "delete") != orderInfoList.size()) {
                throw new BaseErrorException("订单取消失败!");
            }
        }

//        if (petOrderMapper.updateOrderStatus(order_id, 1, null) != 1){
//            throw new BaseErrorException("订单取消失败!");
//        }
        return null;
    }

    public void insertOrderRecord(List<String> orderIdList, Integer operatorType, String operatorId, Integer operationContent){
        List<OrderRecord> orderRecordList = new ArrayList<>();
        for (String orderId : orderIdList) {
            OrderRecord orderRecord = new OrderRecord();
            orderRecord.setOrderId(orderId);
            orderRecord.setOperatorType(operatorType);
            orderRecord.setOperatorId(operatorId);
            orderRecord.setOperationContent(operationContent);
            orderRecordList.add(orderRecord);
        }
        if (petOrderMapper.insertOrderRecord(orderRecordList) != orderIdList.size()) {
            throw new BaseErrorException("订单日志录入失败!");
        }
    }

}
