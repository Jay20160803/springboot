package com.jk51.module.quartz.manager;

import com.jk51.module.quartz.modle.ScheduleMeta;
import com.jk51.module.quartz.service.ScheduleMetaService;
import com.jk51.module.quartz.util.Constant;
import com.jk51.module.quartz.util.ScheduleUtils;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import static com.jk51.module.quartz.util.Constant.SCHEDULE_ENABLED;

/**
 * 版权所有(C) 2017 上海银路投资管理有限公司
 * 描述:
 * 作者: gaojie
 * 创建日期: 2018-01-18
 * 修改记录:
 */
@Service
public class ScheduleManager {

    private Logger logger = LoggerFactory.getLogger(ScheduleManager.class);

    @Autowired
    private SchedulerFactoryBean sfb;
    @Autowired
    private ScheduleMetaService service;

    private static final ConcurrentLinkedQueue<Integer> registers = new ConcurrentLinkedQueue<Integer>();

    @PostConstruct
    private void registerAll() throws SchedulerException {


        List<ScheduleMeta> metas = service.queryAll(SCHEDULE_ENABLED);
        for(ScheduleMeta s: metas){
            register(s);
        }

        logger.info("所有定时任务已注册完成.");
    }

    private void register(ScheduleMeta meta) throws SchedulerException{

        JobDetail jobDetail = ScheduleUtils.createJobDetail(meta);
        Trigger trigger = ScheduleUtils.createTrigger(meta);

        try {
            sfb.getScheduler().scheduleJob(jobDetail,trigger);

        } catch (SchedulerException e) {
            logger.error("定时任务[{},{}]注册失败",meta.getName(),meta.getCronExp());
        }

        registers.add(meta.getId());

        logger.info("定时任务[{},{}]注册完成.", meta.getName(), meta.getCronExp());

    }


    public void register(int id) throws SchedulerException {

        ScheduleMeta meta = service.queryOne(id);
        register(meta);
    }


    public void reload(int id)throws SchedulerException{

        ScheduleMeta meta = service.queryOne(id);

        if(meta==null){

            throw new SchedulerException("未找到ID为[" + id + "]的schedule-meta配置");
        }

        boolean exists = sfb.getScheduler().checkExists(ScheduleUtils.createJobKey(id));

        if(exists){
            destroyOne(id);
        }

        register(id);
        logger.info("重新加载定时任务[{}]操作完成", id);
    }

    public void restart() throws SchedulerException{

        if(sfb.getScheduler().isStarted()){

            for(Integer id:registers){
                destroyOne(id);
            }

            sfb.getScheduler().shutdown();
            sfb.getScheduler().start();
        }

        registerAll();
        logger.info("重启任务管理器操作完成.");
    }


    public void resume(int id) throws SchedulerException{

        boolean exists = sfb.getScheduler().checkExists(ScheduleUtils.createJobKey(id));

        if(!exists){
            throw new SchedulerException("未找到ID为[" + id + "]的schedule-meta配置");
        }

        TriggerKey triggerKey = ScheduleUtils.createTriggerKey(id);
        sfb.getScheduler().resumeTrigger(triggerKey);
        logger.info("恢复任务[{}]操作完成.", id);
    }

    public void resumeAll() throws SchedulerException{

        sfb.getScheduler().resumeAll();
        logger.info("恢复所有已注册的定时任务操作完成.");
    }

    /**
     * 暂停定时任务
     *
     * @param id 任务id
     * @throws SchedulerException 操作异常
     */
    public void pause(Integer id) throws SchedulerException {
        boolean exists = sfb.getScheduler().checkExists(ScheduleUtils.createJobKey(id));
        if (!exists) {
            throw new SchedulerException("未找到ID为[" + id + "]的schedule-meta配置");
        }
        TriggerKey triggerKey = ScheduleUtils.createTriggerKey(id);
        sfb.getScheduler().pauseTrigger(triggerKey);
        logger.info("暂停任务[{}]操作完成.", id);
    }

    public void pauseAll()throws SchedulerException{
        sfb.getScheduler().pauseAll();
        logger.info("暂停所有已注册的定时任务操作完成.");

    }


    public void destroyOne(int id) throws SchedulerException{

        JobKey jobKey = ScheduleUtils.createJobKey(id);
        sfb.getScheduler().deleteJob(jobKey);
        TriggerKey triggerKey = ScheduleUtils.createTriggerKey(id);
        sfb.getScheduler().unscheduleJob(triggerKey);
        registers.remove(id);
        logger.info("销毁定时任务[{}]操作完成.", id);
    }


    public Map<String,Object> getStatus() throws SchedulerException{

        Scheduler scheduler = sfb.getScheduler();

        List<ScheduleMeta> list = new ArrayList();

        for(int id:registers){

            TriggerKey key = ScheduleUtils.createTriggerKey(id);
            Object meta = scheduler.getTrigger(key).getJobDataMap().get(Constant.SCHEDULE_JDM_META_KEY);
            list.add((ScheduleMeta) meta);

        }

        Map<String,Object> result = new HashMap();
        result.put("manager_meta",scheduler.getMetaData());
        result.put("registers",list);
        result.put("total", ScheduleManager.registers.size());

        return result;
    }

    /**
     * 关闭定时任务管理器
     *
     * @throws SchedulerException 操作异常
     */
    @PreDestroy
    public void shutdown() throws SchedulerException {
        sfb.getScheduler().shutdown();
        logger.info("关闭任务管理器操作完成");
    }
}
