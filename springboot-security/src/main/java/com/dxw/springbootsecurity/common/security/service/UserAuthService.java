package com.dxw.springbootsecurity.common.security.service;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dxw.springbootsecurity.common.entity.Users;
import com.dxw.springbootsecurity.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @Author: Dxw
 * @Date: 2020/6/1 11:02
 */
@Service
@AllArgsConstructor
public class UserAuthService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        //此处为了唯一，userId就是username
        Users user = userService.getOne(new LambdaQueryWrapper<Users>()
                .eq(Users::getId, userName));
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("用户不存在");
        }

        Set<String> dbAuthSet = new HashSet<>();
        // 获取权限
        dbAuthSet.addAll(userService.selectAuthorityByUserId(user.getId()));
        List<GrantedAuthority> authorityList =
                AuthorityUtils.createAuthorityList(dbAuthSet.toArray(new String[0]));
        return new User(user.getId(), user.getPassword(), authorityList);
    }
}
