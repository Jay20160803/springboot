package com.jk51.module.quartz.service;

import com.jk51.module.quartz.mapper.ScheduleMetaMapper;
import com.jk51.module.quartz.modle.ScheduleMeta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 版权所有(C) 2017 上海银路投资管理有限公司
 * 描述:
 * 作者: gaojie
 * 创建日期: 2018-01-18
 * 修改记录:
 */
@Service
public class ScheduleMetaService {

    @Autowired
    private ScheduleMetaMapper scheduleMetaMapper;

    public List<ScheduleMeta> queryAll(Integer enabled){
        return scheduleMetaMapper.queryAll(enabled);
    }

    public ScheduleMeta queryOne(Integer id){
        return scheduleMetaMapper.queryOne(id);
    }

    public void updateStatus(Integer id, Integer status){
        scheduleMetaMapper.updateStatus(id,status);
    }

}
