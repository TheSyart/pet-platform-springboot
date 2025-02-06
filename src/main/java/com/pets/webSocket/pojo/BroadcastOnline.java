package com.pets.webSocket.pojo;

import lombok.Data;

@Data
public class BroadcastOnline {

    private String type;

    private String username;

    private Boolean online;

    private Integer onlineCount;
}
