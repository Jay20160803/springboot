package com.jk51.module.shiro.mapper;

import com.jk51.module.shiro.model.ModuleInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


import java.util.List;

@Mapper
public interface ModuleMapper {

	/**
	 * 获取该人的权限模块
	 * 
	 * @param userId
	 * @return
	 */
	@Select(value = "select e.id,e.id,e.module_name moduleName,e.module_path modulePath,e.module_type moduleType,e.module_key moduleKey,e.create_time createTime from t_user_role b left join t_role c on b.role_id=c.id left join t_role_module d on c.id=d.role_id left join t_module e on d.module_id=e.id where b.user_id=#{userId}")
	List<ModuleInfo> findModuleByUserId(int userId);
}
