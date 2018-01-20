package com.jk51.module.quartz.mapper;

import com.jk51.module.quartz.modle.ScheduleMeta;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 版权所有(C) 2017 上海银路投资管理有限公司
 * 描述:
 * 作者: gaojie
 * 创建日期: 2018-01-18
 * 修改记录:
 */
@Mapper
public interface ScheduleMetaMapper {


    List<ScheduleMeta> queryAll(Integer enabled);

    ScheduleMeta queryOne(Integer id);

    void updateStatus(@Param("id") Integer id, @Param("status") Integer status);
}
