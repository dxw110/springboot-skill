package com.dxw.springbootsecurity.common.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
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
public class YzRole extends Model<YzRole> implements Serializable {

    private static final long serialVersionUID = 2096478509755241443L;

    /**
     * 角色id
     */
    @TableId
    private String id;

    /**
     * 父角色id
     */
    private String parentId;

    /**
     * 角色名称
     */
    private String roleName;


    /**
     * 角色描述
     */
    private String description;

    @TableField(fill = FieldFill.INSERT)
    private Long gmtCreate;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long gmtModified;

}
