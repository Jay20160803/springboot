package com.jk51.module.quartz.job;


import com.jk51.module.quartz.modle.ScheduleExecutionLog;
import com.jk51.module.quartz.modle.ScheduleMeta;
import com.jk51.module.quartz.service.ScheduleExecutionLogService;
import com.jk51.module.quartz.util.ApplicationContextUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


import java.util.Date;

import static com.jk51.module.quartz.util.Constant.SCHEDULE_JDM_META_KEY;
import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

/**
 * 版权所有(C) 2017 上海银路投资管理有限公司
 * 描述:
 * 作者: gaojie
 * 创建日期: 2018-01-18
 * 修改记录:
 */

public class SimpleHttpInvokeJob implements Job {



    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {


        ScheduleMeta meta = (ScheduleMeta) context.getTrigger().getJobDataMap().get(SCHEDULE_JDM_META_KEY);

        System.out.println("执行 SimpleHttpInvoke job,"+meta);

        insertScheduleExecutionLog(meta.getId(),meta.toString(),new Date());
    }



    private void insertScheduleExecutionLog(int metaId,String logStr,Date startTime){

        ScheduleExecutionLogService service = ApplicationContextUtil.getContext().getBean(ScheduleExecutionLogService.class);

        ScheduleExecutionLog log = new ScheduleExecutionLog();
        log.setScheduleId(metaId);
        log.setLog(logStr);
        log.setStatus(1);
        log.setStartTime(startTime);

        service.insert(log);
    }


}
