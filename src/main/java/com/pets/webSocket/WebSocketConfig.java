package com.pets.webSocket;

import com.pets.service.ConverseService;
import com.pets.service.impl.ConverseServiceImpl;
import com.pets.service.impl.PetOrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final ConverseServiceImpl converseService;

    public WebSocketConfig(ConverseServiceImpl converseService) {
        this.converseService = converseService;
    }


    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new MyWebSocketHandler(converseService), "/ws")
                .setAllowedOrigins("*"); // 设置允许的跨域请求源
    }
}

