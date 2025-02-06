package com.pets.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pets.mapper.CustomerMapper;
import com.pets.mapper.LoginMapper;
import com.pets.pojo.dto.CustomerQueryDTO;
import com.pets.pojo.dto.PageRequestDto;
import com.pets.pojo.dto.UpdateStatusDTO;
import com.pets.pojo.entity.Customer;
import com.pets.pojo.vo.CommonResponseVo;
import com.pets.pojo.vo.CustomerVO;
import com.pets.service.CustomerService;
import com.pets.utils.base.BaseErrorException;
import com.pets.utils.common.CommonUtils;
import com.pets.utils.phone.CheckSmsVerifyCode;
import com.pets.utils.phone.SendSmsVerifyCode;
import com.pets.utils.pic.HandlePic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
@Transactional(rollbackFor = Exception.class)
public class CustomerServiceImpl implements CustomerService {

    @Value("${phone.message.register}")
    private String registerCode;

    @Value("${phone.message.login}")
    private String loginCode;

    @Value("${phone.message.change}")
    private String changeCode;

    @Autowired
    CustomerMapper customerMapper;

    @Autowired
    HandlePic handlePic;

    @Autowired
    CommonUtils commonUtils;

    @Autowired
    CheckSmsVerifyCode checkSmsVerifyCode;

    @Autowired
    SendSmsVerifyCode sendSmsVerifyCode;

    @Override
    public void registerCustomer(String phone, Integer gender, String birth) {
        //注册用户
        Customer customer = new Customer();
        String name = "用户" + phone;
        String username = "";
        String image = "";

        //男账户
        if (gender == 0) {
            username = "M" + phone;
            image = "/server-resource/customer/default_avatar_male.jpg";

            //女账户
        } else if (gender == 1) {
            username = "F" + phone;
            image = "/server-resource/customer/default_avatar_female.jpg";

        }
        customer.setUsername(username);
        customer.setName(name);
        customer.setPhone(phone);
        customer.setGender(gender);
        customer.setImage(image);
        customer.setBirth(birth);
        customer.setStatus(0);

        if (customerMapper.registerNewAccount(customer) != 1) {
            throw new BaseErrorException("新员工创建失败");
        }


    }

    public String sendMessage(String phone, String sendType) throws ExecutionException, InterruptedException {
        if (commonUtils.checkPhoneNumber(phone)) {
            //注册验证码校验
            if (sendType.equals("register")) {
                //通过查询数据库，看该手机号是否已经注册过
                if (customerMapper.checkPhoneUnique(phone) != 0) {
                    return "手机号已注册！";
                } else {
                    //向注册手机号发送验证码
                    return sendSms(phone, registerCode);
                }

                //登录验证码校验
            } else if (sendType.equals("login")) {
                //通过查询数据库，看该手机号是否已经注册过
                if (customerMapper.checkPhoneUnique(phone) == 1) {
                    //向已有手机号发送验证码
                    return sendSms(phone, loginCode);
                } else {
                    return "该手机号未注册!";
                }

                // 换绑手机号
            } else if (sendType.equals("change")) {
                return sendSms(phone, changeCode);
            }
        } else {
            return "手机号不存在!";
        }

        // 默认返回值，用于处理其他情况
        return "无效的发送类型";
    }

    @Override
    public String checkRegisterMessage(String phone, String code, Integer gender, String birth) throws Exception {
        if (checkSms(phone, code)) {
            registerCustomer(phone, gender, birth);
            return "注册成功";
        } else {
            return "验证码核验失败";
        }
    }

    @Override
    public CommonResponseVo queryAllCustomer(PageRequestDto<CustomerQueryDTO> pageRequestDto) {
        PageHelper.startPage(pageRequestDto.getPagination().getPage(), pageRequestDto.getPagination().getSize());

        CustomerQueryDTO customerQueryDTO = pageRequestDto.getConditions();
        CommonResponseVo commonResponseVo = new CommonResponseVo();

        List<CustomerVO> customerList = customerMapper.queryAllCustomer(customerQueryDTO);
        PageInfo<CustomerVO> pageInfo = new PageInfo<>(customerList);

        commonResponseVo.setList(pageInfo.getList());
        commonResponseVo.setTotal(pageInfo.getTotal());
        return commonResponseVo;
    }

    @Override
    public Customer queryOneCustomer(Long id) throws UnknownHostException {
        Customer customer = customerMapper.queryOneCustomer(id);
        customer.setImage(handlePic.fixPath(customer.getImage()));
        return customer;
    }

    @Override
    public String updateCustomerStatus(UpdateStatusDTO updateStatusDTO) {
        if (customerMapper.updateCustomerStatus(updateStatusDTO.getId(), updateStatusDTO.getStatus()) != 1) {
            throw new BaseErrorException("客户状态操作失败!");
        }
        return "客户状态更改成功!";
    }

    @Override
    public String updateCustomer(Customer customer) throws Exception {

        //看电话是否与历史记录相同
        //如果不同查看是否重复
        Customer history = customerMapper.queryOneCustomer(Long.valueOf(customer.getId()));

        // 看发送的字段是否有手机号，并且是否与数据库中相同
        if (customer.getPhone() != null && !history.getPhone().equals(customer.getPhone())) {

            // 如果有其他账户绑定这个电话，进行对这个账号手机号的解绑
            if (!checkPhone(customer.getPhone())) {
                if (customerMapper.unbindPhone(customer.getPhone()) != 1){
                    throw new BaseErrorException("原账户解绑手机失败!");
                }
            }

            // 核验绑定手机验证码
            if(!checkSms(customer.getPhone(), customer.getCode())){
                throw new BaseErrorException("验证码错误!");
            }

        }

        if (customerMapper.updateCustomer(customer) != 1) {
            throw new BaseErrorException("客户工信息编辑失败!");
        }
        return "客户信息更改成功!";
    }

    @Override
    public String deleteCustomerImage(String id) {
        if (customerMapper.deleteCustomerImage(id) != 1) {
            throw new BaseErrorException("数据库同步删除照片失败!");
        }
        return null;
    }

    @Override
    public Customer queryOnePersonInformation(String username) {
        Customer customer = new Customer();
        customer.setUsername(username);
        Customer result = customerMapper.getCustomerInformation(customer);
        result.setImage(handlePic.fixPath(result.getImage()));
        return result;
    }

    // 发送短信
    public String sendSms(String phone, String smsCode) throws ExecutionException, InterruptedException {
        //向手机号发送验证码
        if (sendSmsVerifyCode.sendMessage(phone, smsCode)) {
            return "验证码成功发送";
        } else {
            return "验证码发送失败";
        }
    }


    // 核验短信
    public boolean checkSms(String phone, String code) throws Exception {
        return checkSmsVerifyCode.checkSms(phone, code);
    }

    // 查看电话是否有重复
    public boolean checkPhone(String phone) {
        return customerMapper.checkPhoneUnique(phone) == 0;
    }
}
