package com.pets.controller;

import com.pets.pojo.dto.PageRequestDto;
import com.pets.pojo.dto.PetShoppingCreateInfo;
import com.pets.pojo.dto.ShoppingQueryDTO;
import com.pets.pojo.dto.UpdateStatusDTO;
import com.pets.pojo.entity.Emp;
import com.pets.pojo.entity.PetShopping;
import com.pets.pojo.vo.CommonResponseVo;
import com.pets.service.PetShoppingService;
import com.pets.utils.base.ResponseData;
import com.pets.pojo.vo.PetShoppingVO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.UnknownHostException;
import java.util.List;

@RestController
@RequestMapping("/petShopping")
public class PetShoppingController {

    @Autowired
    private PetShoppingService petShoppingService;

    //不分页查询商品表，用于存储商品的具体信息列表
    @PostMapping("/noPageQueryAllShopping")
    public ResponseData<List<PetShoppingVO>> noPageQueryAllShopping() throws UnknownHostException {
        return ResponseData.OK(petShoppingService.noPageQueryAllShopping());
    }

    //查询商品所有类型
    @GetMapping("/allType")
    public ResponseData<List<String>> queryAllShoppingType() {
        return ResponseData.OK(petShoppingService.queryAllShoppingType());
    }
    
    //分页查询商品表，用于存储商品的具体信息列表
    @PostMapping("/pageQueryAllShopping")
    public ResponseData<CommonResponseVo> queryAllShopping(@RequestBody PageRequestDto<ShoppingQueryDTO> pageRequestDto) {
        return ResponseData.OK(petShoppingService.queryAllShopping(pageRequestDto));
    }

    //商品单个查询
    @GetMapping( "/queryOneShopping")
    public ResponseData<PetShopping> queryOneShopping(@RequestParam Long id) throws UnknownHostException {
        return ResponseData.OK(petShoppingService.queryOneShopping(id));
    }

    //新商品录入
    @PostMapping( "/insertNewShopping")
    public ResponseData<String> insertNewShopping(@RequestBody @Valid PetShoppingCreateInfo petShoppingCreateInfo){
        return ResponseData.OK(petShoppingService.insertNewShopping(petShoppingCreateInfo));
    }

    //更新商品状态
    @PostMapping("/updateShoppingStatus")
    public ResponseData updateShoppingStatus(@RequestBody UpdateStatusDTO updateStatusDTO) {
        return ResponseData.OK(petShoppingService.updateShoppingStatus(updateStatusDTO));
    }

    //更新商品的数据
    @PostMapping("/updateShopping")
    public ResponseData updateShopping(@RequestBody PetShopping petShopping) {
        return ResponseData.OK(petShoppingService.updateShopping(petShopping));
    }

    //删除商品照片
    @GetMapping("/deleteShoppingImage")
    public ResponseData deleteShoppingImage(@RequestParam String id) {
        return ResponseData.OK(petShoppingService.deleteShoppingImage(id));
    }
}
