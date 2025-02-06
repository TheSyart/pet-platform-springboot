package com.pets.utils.EncryptionUtils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

@Component
public class ParseOrderNumber {
    @Value("${order.key}")
    private String key;
    private static int TYPE_SHIFT; // Caesar Cipher 位移量
    private static int USERNAME_SHIFT;
    private static int TIMESTAMP_SHIFT;
    private static int RANDOM_SHIFT;
    private static int RESULT_SHIFT;

    /**
     * 解析订单号，恢复其中的信息
     */
    public String simpleParse(String orderId, String parseKey) throws Exception {
        //解析秘钥
        String result = parseKey(parseKey);

        //获取凯撒密码位移量
        // Caesar Cipher 位移量
        TYPE_SHIFT = Character.getNumericValue(result.charAt(0));
        USERNAME_SHIFT = Character.getNumericValue(result.charAt(1));
        TIMESTAMP_SHIFT = Character.getNumericValue(result.charAt(2));
        RANDOM_SHIFT = Character.getNumericValue(result.charAt(3));
        RESULT_SHIFT = Character.getNumericValue(result.charAt(4));
        String num = decrypt(orderId, RESULT_SHIFT);

        int length = num.length();

        // 提取订单类型（前5个字符）
        String orderType = decrypt(num.substring(0, 5), TYPE_SHIFT);
        // 解密用户名部分（5到17字符）
        String username = decrypt(num.substring(5, 17), USERNAME_SHIFT);
        // 提取时间戳（从第17个字符开始到倒数第8个字符）
        String timeStamp = decrypt(num.substring(17, length - 8), TIMESTAMP_SHIFT);
        // 提取随机数（从倒数第7个字符到倒数第1个字符）
        String random = decrypt(num.substring(length - 7, length - 1), RANDOM_SHIFT);

        // 返回解析的订单信息
        return "订单类型：" + orderType + " 用户名：" + username + " 时间戳：" + timeStamp + " 随机数：" + random;
    }

    /**
     * 解密
     */
    private String decrypt(String input, int SHIFT) {
        StringBuilder decrypted = new StringBuilder();
        // 遍历每个字符进行解密
        for (char ch : input.toCharArray()) {
            if (Character.isLetter(ch)) {
                // 对字母进行凯撒解密
                char base = Character.isLowerCase(ch) ? 'a' : 'A';
                ch = (char) ((ch - base - SHIFT + 26) % 26 + base);
            } else if (Character.isDigit(ch)) {
                // 对数字进行解密
                ch = (char) ((ch - '0' - SHIFT + 10) % 10 + '0');
            }
            decrypted.append(ch);  // 将解密后的字符添加到结果中
        }
        return decrypted.toString();  // 返回解密后的字符串
    }

    /**
     * 解析秘钥
     */
    private String parseKey(String data) throws Exception {

        // 进行Base64解码
        byte[] decodedData = Base64.getDecoder().decode(data);

        // 使用相同的密钥来初始化AES解密器
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, keySpec);

        // 解密数据并返回
        byte[] decryptedData = cipher.doFinal(decodedData);
        System.out.println("结果：" + new String(decryptedData));
        return new String(decryptedData);

    }

}
