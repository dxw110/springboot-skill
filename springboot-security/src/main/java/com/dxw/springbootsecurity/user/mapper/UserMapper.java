package com.dxw.springbootsecurity.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dxw.springbootsecurity.common.entity.Users;
import com.dxw.springbootsecurity.common.entity.YzRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author DCool
 * date 2020-07-03
 */
public interface UserMapper extends BaseMapper<Users> {

    /**
     * 查询权限合计
     * @param id
     * @return
     */
    List<String> selectAuthorityByUserId(@Param("id") String id);

    /**
     * 根据角色名称查询角色
     * @param roleName
     * @return
     */
    YzRole selectRoleByRoleName(@Param("roleName") String roleName);

}
