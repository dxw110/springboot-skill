package com.dxw.springbootsecurity.common.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @Description
 * @Author Dxw
 * @Date 2020-06-03
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class YzRoleAuthority extends Model<YzRoleAuthority> implements Serializable {

    private static final long serialVersionUID = 8289361720256841442L;

    /**
     * 角色id
     */
    private String roleId;

    /**
     * 权限id
     */
    private String authorityId;

}
