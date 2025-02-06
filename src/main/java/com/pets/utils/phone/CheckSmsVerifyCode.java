package com.pets.utils.phone;

import com.aliyun.auth.credentials.Credential;
import com.aliyun.auth.credentials.provider.StaticCredentialProvider;
import com.aliyun.sdk.service.dypnsapi20170525.models.*;
import com.aliyun.sdk.service.dypnsapi20170525.*;
import com.google.gson.Gson;
import darabonba.core.client.ClientOverrideConfiguration;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.concurrent.CompletableFuture;

/**
 * 核验验证码
 * */
@Component
public class CheckSmsVerifyCode {
    private Boolean result = false;

    @Value("${aliyun.accessKeyId}")
    private String accessKeyId;

    @Value("${aliyun.accessKeySecret}")
    private String accessKeySecret;
    public Boolean checkSms(String phoneNumber, String code) throws Exception {

        StaticCredentialProvider provider = StaticCredentialProvider.create(Credential.builder()
                .accessKeyId(accessKeyId)
                .accessKeySecret(accessKeySecret)
                .build());


        // Region ID
        // Endpoint 请参考 https://api.aliyun.com/product/Dypnsapi
        //.setConnectTimeout(Duration.ofSeconds(30))


        try (AsyncClient client = AsyncClient.builder()
                .region("cn-hangzhou") // Region ID
                .credentialsProvider(provider)
                .overrideConfiguration(
                        ClientOverrideConfiguration.create()
                                // Endpoint 请参考 https://api.aliyun.com/product/Dypnsapi
                                .setEndpointOverride("dypnsapi.aliyuncs.com")
                        //.setConnectTimeout(Duration.ofSeconds(30))
                )
                .build()) {
            CheckSmsVerifyCodeRequest checkSmsVerifyCodeRequest = CheckSmsVerifyCodeRequest.builder()
                    .phoneNumber(phoneNumber)
                    .verifyCode(code)
                    .build();
            CompletableFuture<CheckSmsVerifyCodeResponse> response = client.checkSmsVerifyCode(checkSmsVerifyCodeRequest);

            CheckSmsVerifyCodeResponse resp = response.get();
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

