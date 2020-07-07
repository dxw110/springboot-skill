package com.dxw.springbootsecurity.common.security.handler;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.PatternMatchUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;

/**
 * @Author: Lao Cat
 * @Date: 2020/5/14 10:01
 */
@Slf4j
@Component("pms")
public class CustomPermissionEvaluator {

    /**
     * 判断用户是否拥有 权限  xxx:xxx
     *
     * @Author: Dxw
     * @Date: 2020/6/1 11:02
     */
    public boolean check(String permission) {
        if (StrUtil.isBlank(permission)) {
            return false;
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (null == authentication) {
            return false;
        }
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        return authorities
                .stream()
                .map(GrantedAuthority::getAuthority)
                .filter(StringUtils::hasText)
                .anyMatch(x -> PatternMatchUtils.simpleMatch(permission, x));
    }

}


