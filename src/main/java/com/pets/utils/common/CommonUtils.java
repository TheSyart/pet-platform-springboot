package com.pets.utils.common;

import org.springframework.stereotype.Component;

@Component
public class CommonUtils {
    public String convertMillisToTimeFormat(long seconds) {
//        long seconds = milliseconds / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;

        seconds %= 60;
        minutes %= 60;

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    public boolean checkPhoneNumber(String phone) {
        // 正则表达式匹配中国大陆手机号：11位，以1开头，第二位为3-9
        String regex = "^1[3-9]\\d{9}$";
        return phone.matches(regex);
    }
}
