package com.dcool.springbootactivity.activity.mapper;


import com.dcool.springbootactivity.activity.entity.ActHiProcinst;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author DCool
 * date 2020-06-05
 */
@Mapper
public interface ActHiProcinstMapper {

    /**
     * 获取PROC_INST_ID_
     *
     * @param procInstId
     * @return
     */
    ActHiProcinst getBusinessKey(@Param("procInstId") String procInstId);
}
