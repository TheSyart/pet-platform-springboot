package com.pets.webSocket.pojo;

import lombok.Data;

@Data
public class AssignCustomerServer {

    private String type;

    private Boolean result;

    private String reason;

    private String username;

}
