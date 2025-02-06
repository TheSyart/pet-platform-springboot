package com.pets.utils.common;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

@Service
public class GetAgeByBirth {
    public String transformBirth(String birth){
        // 将输入转换为 LocalDate
        LocalDate birthDate = LocalDate.parse(birth);
        LocalDate currentDate = LocalDate.now();

        // 计算年龄
        Period age = Period.between(birthDate, currentDate);

        // 判断年龄，如果小于一年则输出0岁
        int years = age.getYears();
        if (years < 1) {
            System.out.println("年龄: 0岁");
        } else {
            System.out.println("年龄: " + years + "岁");
        }
        return String.valueOf(years);
    }
}
