package com.pets.service;

import com.pets.pojo.dto.LoginDTO;
import com.pets.pojo.vo.LoginVO;
import org.springframework.stereotype.Service;

@Service
public interface LoginService {

    LoginVO empLogin(LoginDTO loginDTO);

    LoginVO customerLogin(LoginDTO loginDTO, String loginStatus) throws Exception;

    String customerName(String username);

    String getNameByJWT(String token);
}
