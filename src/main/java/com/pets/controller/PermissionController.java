package com.pets.controller;

import com.pets.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@Slf4j
@RequestMapping("")
@RestController
public class PermissionController {
    @Autowired
    private PermissionService permissionService;




}
