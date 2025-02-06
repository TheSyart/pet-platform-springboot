package com.pets.utils.common;

import net.sourceforge.pinyin4j.PinyinHelper;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class TransformPinYin {

    public String transformPinYin(String chinese) {
        StringBuilder pinyinBuilder = new StringBuilder();

        for (char c : chinese.toCharArray()) {
            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(c);
            if (pinyinArray != null) {
                // 获取第一个拼音，并去掉声调数字
                String pinyinWithoutTone = pinyinArray[0].replaceAll("[0-9]", "");
                pinyinBuilder.append(pinyinWithoutTone);
            } else if (Character.isLetter(c)) {
                // 如果是字母，直接添加
                pinyinBuilder.append(c);
            } else {
                // 如果是其他符号，直接去除
                pinyinBuilder.append("");
            }
        }
        String result = pinyinBuilder.toString();

        // 检查拼音长度
        if (result.length() > 12) {
            StringBuilder initialsBuilder = new StringBuilder();
            for (char c : chinese.toCharArray()) {
                String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(c);
                if (pinyinArray != null) {
                    // 取拼音首字母并添加到 initialsBuilder
                    initialsBuilder.append(pinyinArray[0].charAt(0)); // 取拼音的第一个字母
                } else if (Character.isLetter(c)) {
                    initialsBuilder.append(c); // 如果是字母，直接添加
                }
                // 对于其他符号，不做处理
            }
            return initialsBuilder.toString(); // 返回首字母拼音
        }

        return result; // 返回完整拼音
    }

    // 姓名转化拼音首字母
    public String transformHeader(String chinese) {
        StringBuilder initialsBuilder = new StringBuilder();
        for (char c : chinese.toCharArray()) {
            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(c);
            if (pinyinArray != null) {
                // 取拼音首字母并添加到 initialsBuilder
                initialsBuilder.append(pinyinArray[0].charAt(0)); // 取拼音的第一个字母
            } else if (Character.isLetter(c)) {
                initialsBuilder.append(c); // 如果是字母，直接添加
            }
            // 对于其他符号，不做处理
        }
        return initialsBuilder.toString(); // 返回首字母拼音
    }

}

