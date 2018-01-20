package com.jk51.module.quartz.controller;


import com.jk51.module.quartz.manager.ScheduleManager;
import com.jk51.module.quartz.util.Constant;
import com.jk51.module.util.JacksonUtils;
import net.minidev.json.JSONObject;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 版权所有(C) 2017 上海银路投资管理有限公司
 * 描述: Schedule管理控制台
 * 作者: wangzhengfei
 * 创建日期: 2017-02-23
 * 修改记录:
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    public static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private ScheduleManager manager;

    @RequestMapping("/restart")
    public String restart(){
        try {
            manager.restart();
            return Constant.RESPONSE_SUCCESS;
        } catch (SchedulerException e) {
            return ExceptionUtils.getRootCauseMessage(e);
        }
    }

    @RequestMapping("/pause")
    public String pause(@RequestParam("id") Integer id){
        try {
            manager.pause(id);
            return Constant.RESPONSE_SUCCESS;
        } catch (SchedulerException e) {
            return ExceptionUtils.getRootCauseMessage(e);
        }
    }

    @RequestMapping("/pauseAll")
    public String pauseAll(){
        try {
            manager.pauseAll();
            return Constant.RESPONSE_SUCCESS;
        } catch (SchedulerException e) {
            return ExceptionUtils.getRootCauseMessage(e);
        }
    }

    @RequestMapping("/resume")
    public String resume(@RequestParam("id") Integer id){
        try {
            manager.resume(id);
            return Constant.RESPONSE_SUCCESS;
        } catch (SchedulerException e) {
            return ExceptionUtils.getRootCauseMessage(e);
        }
    }

    @RequestMapping("/resumeAll")
    public String resumeAll(){
        try {
            manager.resumeAll();
            return Constant.RESPONSE_SUCCESS;
        } catch (SchedulerException e) {
            return ExceptionUtils.getRootCauseMessage(e);
        }
    }

    @RequestMapping("/register")
    public String register(@RequestParam("id") Integer id){
        try {
            manager.register(id);
            return Constant.RESPONSE_SUCCESS;
        } catch (SchedulerException e) {
            return ExceptionUtils.getRootCauseMessage(e);
        }
    }

    @RequestMapping("/reload")
    public String reload(@RequestParam("id") Integer id){
        try {
            manager.reload(id);
            return Constant.RESPONSE_SUCCESS;
        } catch (SchedulerException e) {
            return ExceptionUtils.getRootCauseMessage(e);
        }
    }

    @RequestMapping("/shutdown")
    public String shutdown(){
        try {
            manager.shutdown();
            return Constant.RESPONSE_SUCCESS;
        } catch (SchedulerException e) {
            return ExceptionUtils.getRootCauseMessage(e);
        }
    }

    @RequestMapping("/destroy")
    public String destroy(@RequestParam("id") Integer id){
        try {
            manager.destroyOne(id);
            return Constant.RESPONSE_SUCCESS;
        } catch (SchedulerException e) {
            return ExceptionUtils.getRootCauseMessage(e);
        }
    }

    @RequestMapping("/stat")
    public String stat(){
        try {
            Map<String,Object> stats = manager.getStatus();
            logger.info("Current Schedule Manager stats:{}",stats);
            return JacksonUtils.mapToJson(stats);
        } catch (SchedulerException e) {
            return ExceptionUtils.getRootCauseMessage(e);
        }
    }


}
