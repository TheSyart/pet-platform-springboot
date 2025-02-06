package com.pets;

import com.pets.mapper.EmpMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpMybatisApplicationTests {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private EmpMapper empMapper;


    }




