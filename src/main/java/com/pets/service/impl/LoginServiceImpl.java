package com.pets.service.impl;

import com.pets.pojo.dto.LoginDTO;
import com.pets.pojo.entity.Emp;
import com.pets.pojo.vo.LoginVO;
import com.pets.mapper.LoginMapper;
import com.pets.pojo.entity.Customer;
import com.pets.service.LoginService;
import com.pets.utils.base.BaseErrorException;
import com.pets.utils.base.JwtUtils;
import com.pets.utils.common.GetLocalIPAddress;
import com.pets.utils.phone.CheckSmsVerifyCode;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class LoginServiceImpl implements LoginService {

    @Autowired
    LoginMapper loginMapper;

    @Autowired
    PermissionImpl permissionImpl;

    @Autowired
    CheckSmsVerifyCode checkSmsVerifyCode;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    GetLocalIPAddress getLocalIPAddress;

    @Autowired
    private HttpServletRequest request; // 注入 HttpServletRequest

    @Override
    public LoginVO empLogin(LoginDTO loginDTO) {
        LoginVO loginVO = new LoginVO();
        Emp emp = loginMapper.empLogin(loginDTO);

        //判断账户状态
        backAccountStatus(emp.getStatus());

        // JWT令牌
        if (emp != null) {
            Map<String, Object> claims = new HashMap<>();
            claims.put("loginUsername", emp.getUsername());
            claims.put("loginPassword", emp.getPassword());
            claims.put("loginIdentity", "emp");
            String jwt = JwtUtils.generateJwt(claims);
            loginVO.setJwt(jwt);
            loginVO.setName(emp.getName());
            loginVO.setUsername(emp.getUsername());
            loginVO.setJob(Integer.valueOf(emp.getJob()));
            loginVO.setId(String.valueOf(emp.getId()));
            loginVO.setPermission(permissionImpl.queryPermissionByRole(Integer.valueOf(emp.getJob())));

            // 登录成功后记录 IP
            queryIp(loginDTO.getUsername(), 1, 0, 2);
            return loginVO;
        } else {
            // 如果不匹配，检查用户名是否存在
            int usernameExists = loginMapper.checkEmpExists(loginDTO.getUsername());

            if (usernameExists == 0) {
                throw new BaseErrorException("用户名不存在");
            } else {
                queryIp(loginDTO.getUsername(), 1, 1, 2); // 登录失败记录IP
                throw new BaseErrorException("密码错误");
            }
        }
    }

    //客户登录
    @Override
    public LoginVO customerLogin(LoginDTO loginDTO, String loginStatus) throws Exception {
        Customer customer = new Customer();
        LoginVO loginVO = new LoginVO();
        int login_type = 0;

        // 根据登录状态进行不同的处理
        if (loginStatus.equals("password")) {
            customer = loginMapper.loginByCustomerPassword(loginDTO);
            if (customer == null) {
                // 如果不匹配，检查用户名是否存在
                int usernameExists = loginMapper.checkCustomerExists(loginDTO.getUsername());
                if (usernameExists == 0) {
                    throw new BaseErrorException("用户名不存在");
                } else {
                    queryIp(loginDTO.getUsername(), 0, 1, login_type); // 登录失败记录IP
                    throw new BaseErrorException("密码错误");
                }
            }
        } else if (loginStatus.equals("phone")) {
            login_type = 1;
            boolean pass = checkSmsVerifyCode.checkSms(loginDTO.getPhoneNumber(), loginDTO.getCode());
            customer = loginMapper.getInformationByPhone(loginDTO.getPhoneNumber());

            if (customer == null) {
                throw new BaseErrorException("请重新登录!");
            }

            if (!pass) {
                queryIp(customer.getUsername(), 0, 1, login_type); // 登录失败记录IP
                throw new BaseErrorException("手机号登录失败!");
            }
        } else {
            throw new BaseErrorException("请重新登录!");
        }

        //判断账户状态
        backAccountStatus(customer.getStatus());

        // 生成JWT令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put("loginUsername", customer.getUsername());
        claims.put("loginPassword", customer.getPassword());
        claims.put("loginIdentity", "customer");
        String jwt = JwtUtils.generateJwt(claims);
        loginVO.setJwt(jwt);
        loginVO.setId(customer.getId());
        loginVO.setName(String.valueOf(customer.getName()));
        loginVO.setUsername(customer.getUsername());

        // 登录成功记录IP
        queryIp(customer.getUsername(), 0, 0, login_type);

        return loginVO;
    }

    public void backAccountStatus(Integer accountStatus){
        if (accountStatus == 1){
            throw new BaseErrorException("账户已被封禁，禁止登录!");
        }else if (accountStatus == 2){
            throw new BaseErrorException("账户已被删除，禁止登录!");
        }
    }

    //得到客户名字
    @Override
    public String customerName(String username) {
        return loginMapper.getCustomerName(username);
    }

    @Override
    public String getNameByJWT(String token) {
        if (StringUtils.isEmpty(token)){
            throw new BaseErrorException("无令牌!");
        }
        String username = (String) JwtUtils.parseJWT(token).get("loginUsername");
        System.out.println("该登录用户为" + username);
        return loginMapper.getEmpName(username);
    }

    // 查询登录的ip地址
    private void queryIp(String username, Integer type, Integer result, Integer login_type) {
        // 获取 IP 地址
        String ip = request.getHeader("X-Forwarded-For"); // 从代理头中获取
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP"); // 其他可能的代理头
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP"); // 其他可能的代理头
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr(); // 如果没有代理，直接获取远程地址
        }

        log.info("用户登录 IP 地址: {}", ip);

        // 调用查询 IP 归属地的服务
        String ipLocationResult = "无法查询归属地";
        try {
            ipLocationResult = getLocalIPAddress.getIpLocation(ip, username, type, result, login_type);
            log.info("用户 IP 地址归属地: {}", ipLocationResult);
        } catch (Exception e) {
            // 异常捕获日志
            log.error("查询 IP 归属地时发生错误: {}", e.getMessage());
        }

        // 记录查询的归属地结果
        log.info("最终 IP 地址归属地: {}", ipLocationResult);
    }

}
