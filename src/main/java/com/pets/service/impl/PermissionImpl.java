package com.pets.service.impl;

import com.pets.mapper.PermissionMapper;
import com.pets.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class PermissionImpl implements PermissionService {
    @Autowired
    PermissionMapper permissionMapper;
    @Override
    public List<String> queryPermissionByRole(Integer role) {
        return permissionMapper.queryPermissionByRole(role);
    }
}
