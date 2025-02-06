package com.pets.utils.phone;

import org.json.JSONObject;
import com.aliyun.auth.credentials.Credential;
import com.aliyun.auth.credentials.provider.StaticCredentialProvider;
import com.aliyun.sdk.service.dypnsapi20170525.models.*;
import com.aliyun.sdk.service.dypnsapi20170525.*;
import com.google.gson.Gson;
import darabonba.core.client.ClientOverrideConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * 向用户手机号发送验证码
 * */
@Component
public class SendSmsVerifyCode {
    private Boolean result = false;

    @Value("${aliyun.accessKeyId}")
    private String accessKeyId;

    @Value("${aliyun.accessKeySecret}")
    private String accessKeySecret;

    public Boolean sendMessage(String phoneNumber, String templateCode) throws ExecutionException, InterruptedException {
        StaticCredentialProvider provider = StaticCredentialProvider.create(Credential.builder()
                .accessKeyId(accessKeyId)
                .accessKeySecret(accessKeySecret)
                .build());

        try{AsyncClient client = AsyncClient.builder()
                .region("cn-hangzhou") // Region ID
                .credentialsProvider(provider)
                .overrideConfiguration(
                        ClientOverrideConfiguration.create()
                                .setEndpointOverride("dypnsapi.aliyuncs.com")
                )
                .build();

        SendSmsVerifyCodeRequest sendSmsVerifyCodeRequest = SendSmsVerifyCodeRequest.builder()
                .phoneNumber(phoneNumber)
                .signName("宠物护理平台")
                .templateCode(templateCode)
                .templateParam("{\"code\":\"##code##\"}")
                .duplicatePolicy(1L)//核验规则，当有效时间内对同场景内的同号码重复发送验证码时，旧验证码如何处理。
                                    // 1：覆盖处理（默认），即旧验证码会失效掉。
                                    // 2：保留，即多个验证码都是在有效期内都可以校验通过。

                .validTime(300L)    //验证码有效时长，单位秒，默认为 300 秒。
                                    // 注意 该字段类型为 Long，在序列化/反序列化的过程中可能导致精度丢失，请注意数值不得大于 9007199254740991。

                .interval(60L)      //时间间隔，单位：秒。即多久间隔可以发送一次验证码，用于频控，默认 60 秒。
                .build();

        CompletableFuture<SendSmsVerifyCodeResponse> response = client.sendSmsVerifyCode(sendSmsVerifyCodeRequest);
        SendSmsVerifyCodeResponse resp = response.get();
        String responseString = new Gson().toJson(resp);

        System.out.println(" 原始数据： " + responseString);
        // 解析 JSON
        JSONObject jsonObject = new JSONObject(responseString);
        result = jsonObject.getJSONObject("body").getBoolean("success");
     } catch (Exception e) {
        // 捕获异常并打印错误信息
        System.err.println("验证码验证失败: " + e.getMessage());
        // 在这里可以根据需要进行额外的错误处理
    }
        return result;
    }

}

