package com.pets.utils.EncryptionUtils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class OrderNumberGenerator {
    @Value("${order.key}")
    private String key;
    // 默认随机数长度
    private static final int DEFAULT_RANDOM_LENGTH = 6;
    private static int TYPE_SHIFT; // Caesar Cipher 位移量
    private static int USERNAME_SHIFT;
    private static int TIMESTAMP_SHIFT;
    private static int RANDOM_SHIFT;
    private static int RESULT_SHIFT;

    /**
     * 生成订单号
     */
    public List<String> simpleGenerateOrderNumber(String username, String orderType) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {

        // 生成订单号，使用默认的随机数长度

        System.out.println(key);
        List<String> list = new ArrayList<>();
        list.add(generateKey());
        list.add(generateOrderNumber(DEFAULT_RANDOM_LENGTH, username, orderType));
        return list;
    }

    /**
     * 生成带指定随机数长度的订单号
     */
    private String generateOrderNumber(int randomLength, String username, String orderType) {
        //加密订单类型
        orderType = encrypt(orderType, TYPE_SHIFT);

        // 获取当前时间的时间戳（格式：yyyyMMddHHmmssSSS）
        String timestamp =  encrypt(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()), TIMESTAMP_SHIFT);

        // 生成指定长度的随机数
        String randomDigits = encrypt(generateRandomDigits(randomLength), RANDOM_SHIFT);

        // 将 username 使用加密处理，确保其唯一性和安全性
        String usernameTamp = encrypt(username, USERNAME_SHIFT);

        // 拼接订单类型、处理后的用户名、时间戳和随机数作为订单号
        return encrypt(orderType + usernameTamp + timestamp + randomDigits, RESULT_SHIFT);
    }

    /**
     * 生成指定长度的随机数字符串
     */
    private String generateRandomDigits(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        // 生成指定长度的随机数
        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10));  // 生成0-9的随机整数
        }

        return sb.toString();
    }

    /**
     * 加密
     */
    private String encrypt(String input, int SHIFT) {
        StringBuilder encrypted = new StringBuilder();
        // 遍历用户名中的每个字符
        for (char ch : input.toCharArray()) {
            if (Character.isLetter(ch)) {
                // 对字母进行凯撒加密，保持字母的性质
                char base = Character.isLowerCase(ch) ? 'a' : 'A';
                ch = (char) ((ch - base + SHIFT) % 26 + base);
            } else if (Character.isDigit(ch)) {
                // 对数字进行加密，确保结果仍然是数字
                ch = (char) ((ch - '0' + SHIFT) % 10 + '0');
            }
            encrypted.append(ch);  // 将加密后的字符添加到结果中
        }
        return encrypted.toString();  // 返回加密后的用户名
    }


    /**
     * 生成秘钥
     */
    private String generateKey() throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException {
        Random random = new Random();
        StringBuilder body = new StringBuilder();

        TYPE_SHIFT = random.nextInt(10);
        USERNAME_SHIFT = random.nextInt(10);
        TIMESTAMP_SHIFT = random.nextInt(10);
        RANDOM_SHIFT = random.nextInt(10);
        RESULT_SHIFT = random.nextInt(10);
        // 生成密钥字符串，包含每个部分的位移量
        body.append(TYPE_SHIFT)
           .append(USERNAME_SHIFT)
           .append(TIMESTAMP_SHIFT)
           .append(RANDOM_SHIFT)
           .append(RESULT_SHIFT);

        System.out.println("原始加密规则：" + body.toString());

        // 创建AES密钥规范
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);

        // 加密数据
        byte[] encrypted = cipher.doFinal((body.toString()).getBytes());

        // 返回加密后的Base64字符串
        return Base64.getEncoder().encodeToString(encrypted);
    }

}
