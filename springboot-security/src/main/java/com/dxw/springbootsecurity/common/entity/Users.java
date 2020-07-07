package com.dxw.springbootsecurity.common.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户表
 * @author DCool
 * date 2020-07-03
 */
@Data
public class Users implements Serializable {

    @TableId
    private String id;

    private String username;
    private String password;

}
