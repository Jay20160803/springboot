package com.jk51.module.shiro.service;

import com.jk51.module.shiro.mapper.ModuleMapper;
import com.jk51.module.shiro.model.ModuleInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 版权所有(C) 2017 上海银路投资管理有限公司
 * 描述:
 * 作者: gaojie
 * 创建日期: 2018-01-22
 * 修改记录:
 */
@Service
public class ModuleService {

    @Autowired
    private ModuleMapper moduleMapper;


    /**
     * 获取角色模块
     *
     * @param userId
     * @return
     */
    public List<ModuleInfo> findModuleByUserId(int userId) {
        return moduleMapper.findModuleByUserId(userId);
    }
}
