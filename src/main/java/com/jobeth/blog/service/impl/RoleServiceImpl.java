package com.jobeth.blog.service.impl;

import com.jobeth.blog.common.enums.ResultEnum;
import com.jobeth.blog.common.exception.ServerException;
import com.jobeth.blog.common.utils.JacksonUtil;
import com.jobeth.blog.dto.RoleDTO;
import com.jobeth.blog.po.Role;
import com.jobeth.blog.mapper.RoleMapper;
import com.jobeth.blog.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author Jobeth
 * @since 2020-06-30
 */
@Service
@Slf4j
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Override
    public List<Role> listAll(RoleDTO roleDTO) {
        return baseMapper.listAll(roleDTO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateRole(RoleDTO roleDTO) {
        System.out.println(JacksonUtil.objectToJson(roleDTO));
        Role role = new Role();
        BeanUtils.copyProperties(roleDTO, role);
        baseMapper.updateById(role);
        baseMapper.deleteRoleMenuAndPermByRoleId(roleDTO.getId());
        if (roleDTO.getMenuIdList() != null) {
            for (Long id : roleDTO.getMenuIdList()) {
                baseMapper.insertRoleMenu(roleDTO.getId(), id);
            }
        }
        if (roleDTO.getPermIdList() != null) {
            for (Long id : roleDTO.getPermIdList()) {
                baseMapper.insertRoleMenu(roleDTO.getId(), id);
            }
        }
    }
}
