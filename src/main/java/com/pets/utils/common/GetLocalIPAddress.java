package com.pets.utils.common;

import com.pets.pojo.dto.IpLocationDTO;
import com.pets.service.IpRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Enumeration;
import java.util.Objects;

@Service
public class GetLocalIPAddress {

    @Autowired
    IpRecordService ipRecordService;

    private static final String API_URL = "http://ip-api.com/json/";
    public String getLocalIPAddress() {
        String ipAddress = "";
        try {
            // 获取本机的 InetAddress 对象
            InetAddress localHost = InetAddress.getLocalHost();
            // 获取本机的 IP 地址
            ipAddress = localHost.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return ipAddress;
    }

    //查询ip归属地
    public String getIpLocation(String ip, String username, Integer type, Integer result, Integer login_type) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            String url = API_URL + ip;

            // 请求API并获取IP归属地信息
            IpLocationDTO ipLocationDTO = restTemplate.getForObject(url, IpLocationDTO.class);

            // 检查API返回的IP信息是否为空或查询失败
            if (Objects.isNull(ipLocationDTO) || "fail".equals(ipLocationDTO.getStatus())) {
                // 如果API返回的IP信息为空或查询失败，插入失败记录
                IpLocationDTO errorIp = new IpLocationDTO();
                errorIp.setQuery(ip);
                errorIp.setCountry("无法查询归属地");
                errorIp.setMessage(ipLocationDTO != null ? ipLocationDTO.getMessage() : "未知错误");
                ipRecordService.ipInsert(errorIp, username, type, result, login_type);
                return "无法查询归属地";  // 返回查询失败信息
            } else {
                // 如果查询成功，插入成功记录
                ipRecordService.ipInsert(ipLocationDTO, username, type, result, login_type);
                return ipLocationDTO.toString();  // 返回查询结果
            }
        } catch (Exception e) {
            // 打印异常堆栈，记录异常日志
            e.printStackTrace();
            // 处理异常时插入失败记录
            IpLocationDTO errorIp = new IpLocationDTO();
            errorIp.setQuery(ip);
            errorIp.setCountry("无法查询归属地");
            errorIp.setMessage("异常: " + e.getMessage());
            ipRecordService.ipInsert(errorIp, username, type, result, login_type);
            return "无法查询归属地";  // 返回查询失败信息
        }
    }
}

