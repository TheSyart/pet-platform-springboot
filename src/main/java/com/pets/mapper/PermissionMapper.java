package com.pets.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PermissionMapper {
    List<String> queryPermissionByRole(Integer role);
}
