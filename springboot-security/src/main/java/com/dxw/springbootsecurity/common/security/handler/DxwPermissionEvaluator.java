package com.dxw.springbootsecurity.common.security.handler;

import com.alibaba.druid.util.StringUtils;
import com.dxw.springbootsecurity.common.entity.YzRole;
import com.dxw.springbootsecurity.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author: Dxw
 * @Date: 2020/6/1 11:02
 */
@Component
public class DxwPermissionEvaluator implements PermissionEvaluator {

    @Autowired
    private UserMapper userMapper;


    @Override
    public boolean hasPermission(Authentication authentication, Object customizeRole, Object customizePermission) {
        User user = (User) authentication.getPrincipal();

        Collection<GrantedAuthority> authorities = user.getAuthorities();
        List<GrantedAuthority> grantedAuthorities = authorities
                .stream()
                .filter(a -> StringUtils.equals(a.getAuthority(), customizeRole.toString())).collect(Collectors.toList());

        for (GrantedAuthority authority : grantedAuthorities) {
            String roleName = authority.getAuthority();

            YzRole yzRole = userMapper.selectRoleByRoleName(roleName);
            //获取角色
            String permissions = null;
            if (Objects.nonNull(yzRole)) {
                permissions = yzRole.getRoleName();
            }
            if (permissions.contains(customizePermission.toString())) {
                return true;
            }

        }
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable serializable, String s, Object o) {
        return false;
    }
}
