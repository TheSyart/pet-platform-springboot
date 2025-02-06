package com.pets.controller;

import com.pets.service.ConverseService;
import com.pets.utils.base.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.UnknownHostException;

@Slf4j
@RequestMapping("/converse")
@RestController
public class ConverseController {

    @Autowired
    ConverseService converseService;

    @GetMapping( "/queryConverseList")
    public ResponseData queryConverseList(@RequestParam Integer type, @RequestParam String searchName, @RequestParam String username) {
        return ResponseData.OK(converseService.queryConverseList(type, searchName, username));
    }

    @GetMapping( "/queryOnePersonMessage")
    public ResponseData queryOnePersonMessage(@RequestParam String sender, @RequestParam String receiver, @RequestParam Integer converseNum) {
        return ResponseData.OK(converseService.queryOnePersonMessage(sender, receiver, converseNum));
    }


}
