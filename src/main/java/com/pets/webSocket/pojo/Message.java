package com.pets.webSocket.pojo;

import lombok.Data;

@Data
public class Message {

    private String receiver;

    private Integer receiverType;

    private String sender;

    private Integer sendType;

    private String message;

    private String messageCover;

    private String messageLength;

    private Integer messageType;

}
