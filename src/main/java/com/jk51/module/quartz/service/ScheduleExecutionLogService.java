package com.jk51.module.quartz.service;

import com.jk51.module.quartz.mapper.ScheduleExecutionLogMapper;
import com.jk51.module.quartz.modle.ScheduleExecutionLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 版权所有(C) 2017 上海银路投资管理有限公司
 * 描述:
 * 作者: gaojie
 * 创建日期: 2018-01-18
 * 修改记录:
 */
@Service
public class ScheduleExecutionLogService {

    @Autowired
    private ScheduleExecutionLogMapper mapper;

    public void insert(ScheduleExecutionLog log){
        mapper.insert(log);
    }
}
