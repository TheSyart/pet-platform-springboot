package com.pets.controller;

import com.pets.pojo.dto.*;
import com.pets.pojo.entity.PetShopping;
import com.pets.pojo.vo.CommonResponseVo;
import com.pets.pojo.vo.HomeOrderVO;
import com.pets.pojo.vo.OrderInfoDetailsCountVO;
import com.pets.service.PetOrderService;
import com.pets.utils.EncryptionUtils.OrderNumberGenerator;
import com.pets.utils.EncryptionUtils.ParseOrderNumber;
import com.pets.utils.base.ResponseData;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.net.UnknownHostException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@RequestMapping("/petOrder")
public class PetOrderController {
    @Autowired
    PetOrderService petOrderService;

    @Autowired
    OrderNumberGenerator orderNumberGenerator;

    @Autowired
    ParseOrderNumber parseOrderNumber;

    //新增订单
    @PostMapping("/insertOrder")
    public ResponseData insertOrder(@RequestBody @Valid PetOrderDTO petOrderDTO) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        return ResponseData.OK(petOrderService.insertOrder(petOrderDTO));
    }

    //查询一个用户下所有订单
    @PostMapping("/queryAllOrderByCustomerId")
    public ResponseData queryAllOrderByCustomerId(
            @RequestBody OrderPartQueryDTO orderPartQueryDTO) throws UnknownHostException {
        return ResponseData.OK(
                petOrderService.queryAllOrderByCustomerId(
                        orderPartQueryDTO.getOrder_type(),
                        orderPartQueryDTO.getUsername(),
                        orderPartQueryDTO.getOrderPart(),
                        orderPartQueryDTO.getNum()
                )
        );
    }

    //查询所有订单类型
    @GetMapping("/queryAllOrderType")
    public ResponseData queryAllOrderType() {
        return ResponseData.OK(petOrderService.queryAllOrderType());
    }

    //分页查询订单表
    @PostMapping("/queryAllOrder")
    public ResponseData<CommonResponseVo> queryAllShopping(@RequestBody PageRequestDto<PetOrderQueryDTO> pageRequestDto) {
        return ResponseData.OK(petOrderService.queryAllOrder(pageRequestDto));
    }

    //订单单个查询
    @GetMapping("/queryOneOrder")
    public ResponseData<PetShopping> queryOneOrder(@RequestParam Long id) throws UnknownHostException {
        return ResponseData.OK(petOrderService.queryOneOrder(id));
    }

    //更新订单状态
    @PostMapping("/updateOrderStatus")
    public ResponseData updateOrderStatus(@RequestBody OrderIdDTO orderIdDTO) {
        return ResponseData.OK(petOrderService.updateOrderStatus(orderIdDTO));
    }

    //web首页展示今天一天销售数据
    @GetMapping("/homeOrderCount")
    public ResponseData<List<HomeOrderVO>> homeOrderCount(
            @RequestParam String start,
            @RequestParam String end
    ) {
        return ResponseData.OK(petOrderService.homeOrderCount(start, end));
    }

    //按订单类别查询所有商品及服务的销量
    @GetMapping("/queryAllOrderInfoDetailsCount")
    public ResponseData<List<OrderInfoDetailsCountVO>> queryAllOrderInfoDetailsCount(
            @RequestParam String start,
            @RequestParam String end
    ) {
        return ResponseData.OK(petOrderService.queryAllOrderInfoDetailsCount(start, end));
    }

    //查询所有商品的销量
    @GetMapping("/queryAllShoppingSale")
    public ResponseData<List<OrderInfoDetailsCountVO>> queryAllShoppingSale(
            @RequestParam String start,
            @RequestParam String end
    ) {
        return ResponseData.OK(petOrderService.queryAllShoppingSale(start, end));
    }
}

