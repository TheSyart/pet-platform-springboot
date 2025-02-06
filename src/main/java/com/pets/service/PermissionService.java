package com.pets.service;

import java.util.List;

public interface PermissionService {
    List<String> queryPermissionByRole(Integer role);
}
