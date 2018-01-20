package com.jk51.module.quartz.util;

import com.jk51.module.quartz.job.SimpleHttpInvokeJob;
import com.jk51.module.quartz.modle.ScheduleMeta;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import static com.jk51.module.quartz.util.Constant.SCHEDULE_JDM_META_KEY;
import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;
import static org.quartz.core.jmx.JobDataMapSupport.newJobDataMap;

/**
 * 版权所有(C) 2017 上海银路投资管理有限公司
 * 描述:
 * 作者: gaojie
 * 创建日期: 2018-01-18
 * 修改记录:
 */
public class ScheduleUtils {


    private static Logger logger = LoggerFactory.getLogger(ScheduleUtils.class);

    public static final String TRIGGER_NAME_PREFIX = "T-";

    public static final String JOB_KEY_NAME_PREFIX = "J-";




    public static JobDetail createJobDetail(ScheduleMeta meta) {

        return newJob(SimpleHttpInvokeJob.class)
                .withIdentity(JOB_KEY_NAME_PREFIX + meta.getId(), Constant.SCHEDULE_DEFAULT_GROUP_NAME)
                .build();
    }

    public static Trigger createTrigger(ScheduleMeta meta){

        Trigger trigger = null;

        try{

             trigger =  newTrigger()
                    .withIdentity(TRIGGER_NAME_PREFIX+meta.getId(),Constant.SCHEDULE_DEFAULT_GROUP_NAME)
                    .withSchedule(cronSchedule(meta.getCronExp()))
                    .usingJobData(createJobDataMap(meta))
                    .build();

        }catch (Exception e){

            logger.error("创建Trigger失败,cron:{},报错信息:{}",meta.getCronExp(),e);
        }

        return trigger;

    }

    public static JobKey createJobKey(int id){

        return new JobKey(JOB_KEY_NAME_PREFIX+id,Constant.SCHEDULE_DEFAULT_GROUP_NAME);

    }


    public static TriggerKey createTriggerKey(int id){
        return new TriggerKey(TRIGGER_NAME_PREFIX+id,Constant.SCHEDULE_DEFAULT_GROUP_NAME);
    }




    private static JobDataMap createJobDataMap(ScheduleMeta meta){
        JobDataMap result = new JobDataMap();
        result.put(SCHEDULE_JDM_META_KEY,meta);
        return result;
    }
}
