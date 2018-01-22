package com.jk51.module.shiro.service;

import com.jk51.module.shiro.mapper.UserMapper;
import com.jk51.module.shiro.model.ModuleInfo;
import com.jk51.module.shiro.model.UserInfo;
import org.assertj.core.util.Lists;
import org.assertj.core.util.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * 版权所有(C) 2017 上海银路投资管理有限公司
 * 描述:
 * 作者: gaojie
 * 创建日期: 2018-01-22
 * 修改记录:
 */
@Service
public class UserService {


    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ModuleService moduleService;

    /**
     * 根据账号Account查询当前用户
     *
     * @param account
     * @return
     */
    public UserInfo findByAccount(String account) {
        return userMapper.findByAccount(account);
    }

    /**
     * 获取资源集合
     *
     * @param account
     * @return
     */
    public Set<String> findPermissions(String account) {
        Set<String> set = Sets.newHashSet();
        UserInfo user = findByAccount(account);
        List<ModuleInfo> modules = moduleService.findModuleByUserId(user.getId());

        for (ModuleInfo info : modules) {
            set.add(info.getModuleKey());
        }
        return set;
    }


    /**
     * 获取URL权限
     *
     * @param account
     * @return
     */
    public List<String> findPermissionUrl(String account) {
        List<String> list = Lists.newArrayList();
        UserInfo user = findByAccount(account);
        List<ModuleInfo> modules = moduleService.findModuleByUserId(user.getId());

        for (ModuleInfo info : modules) {
            if (info.getModuleType() == ModuleInfo.URL_TYPE) {
                list.add(info.getModulePath());
            }
        }
        return list;
    }
}
