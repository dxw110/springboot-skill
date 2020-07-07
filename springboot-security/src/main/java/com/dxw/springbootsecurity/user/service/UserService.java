package com.dxw.springbootsecurity.user.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dxw.springbootsecurity.common.entity.Users;
import com.dxw.springbootsecurity.user.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author DCool
 * date 2020-07-03
 */
@Service
@AllArgsConstructor
public class UserService extends ServiceImpl<UserMapper, Users> {

    private final UserMapper userMapper;

    /**
     * 查询权限合计
     *
     * @author DCool
     * @date: 2020/7/3 17:15
     */
    public List<String> selectAuthorityByUserId(String id) {
        return userMapper.selectAuthorityByUserId(id);
    }

}
