package com.pets.service;

import com.pets.pojo.dto.*;
import com.pets.pojo.vo.CommonResponseVo;
import com.pets.pojo.vo.HomeOrderVO;
import com.pets.pojo.vo.OrderInfoDetailsCountVO;
import com.pets.pojo.vo.OrderVO;
import org.springframework.stereotype.Service;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.net.UnknownHostException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public interface PetOrderService {
    String insertOrder(PetOrderDTO petOrderDTO) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException;

    List<OrderVO> queryAllOrderByCustomerId(Integer order_type, String username, Integer orderPart, Integer num) throws UnknownHostException;

    List<String> queryAllOrderType();

    int updateExpiredOrders();

    CommonResponseVo queryAllOrder(PageRequestDto<PetOrderQueryDTO> pageRequestDto);

    OrderVO queryOneOrder(Long id) throws UnknownHostException;

    List<HomeOrderVO> homeOrderCount(String start, String end);

    List<OrderInfoDetailsCountVO> queryAllOrderInfoDetailsCount(String start, String end);

    List<OrderInfoDetailsCountVO> queryAllShoppingSale(String start, String end);

    String updateOrderStatus(OrderIdDTO orderIdDTO);
}
