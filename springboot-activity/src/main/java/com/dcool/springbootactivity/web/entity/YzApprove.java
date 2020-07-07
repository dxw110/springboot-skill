package com.dcool.springbootactivity.web.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @Description 审批表(根据实际页面需求来创建)
 * @Author Dxw
 * @Date 2020-06-15
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class YzApprove extends Model<YzApprove> implements Serializable {

    private static final long serialVersionUID = 8509579511043658280L;

    @TableId
    private String id;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Long gmtCreate;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Long gmtModified;

    /**
     * 模板id
     */
    private String templateId;

    /**
     * 审批内容json
     */
    private String approve;

    /**
     * 图片
     */
    private String img;

    /**
     * 附件
     */
    private String annex;

    /**
     * 是否通过聊天发送给审批人  0-否 1-是
     */
    private String isChatSend;

    /**
     * 1-审核中 2-驳回，结束 3-已完成   4-草稿
     */
    private String isApprovalCompleted;

    /**
     * 流程id
     */
    private String proId;

    /**
     * 发起人id
     */
    private String sponsorId;

    /**
     * 通用 id
     */
    private String basicId;

    /**
     * 节点组
     */
    private String nodeIds;
}
