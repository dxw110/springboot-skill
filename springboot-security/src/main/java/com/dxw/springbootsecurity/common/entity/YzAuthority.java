package com.dxw.springbootsecurity.common.entity;

import com.baomidou.mybatisplus.annotation.TableId;
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
public class YzAuthority extends Model<YzAuthority> implements Serializable {

    private static final long serialVersionUID = 5518340814574083709L;

    /**
     * 权限id
     */
    @TableId
    private String id;

    /**
     * 权限名称
     */
    private String authorityName;

    /**
     * 权限描述
     */
    private String description;

}
