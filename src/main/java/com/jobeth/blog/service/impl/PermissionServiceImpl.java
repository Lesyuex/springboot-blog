package com.jobeth.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jobeth.blog.dto.PermissionDTO;
import com.jobeth.blog.mapper.PermissionMapper;
import com.jobeth.blog.po.Permission;
import com.jobeth.blog.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Jobeth
 * @since 2020-06-30
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {
    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public List<Permission> listByPermissionDTO(PermissionDTO dto) {
        return permissionMapper.listByPermissionDTO(dto);
    }
}
