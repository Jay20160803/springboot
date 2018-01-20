package com.jk51.module.dynamic.mapper;

import com.jk51.model.BTrades;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 版权所有(C) 2017 上海银路投资管理有限公司
 * 描述:
 * 作者: gaojie
 * 创建日期: 2017-12-27
 * 修改记录:
 */
@Mapper
public interface BTradesMapper {

    BTrades getBTradesByPrimaryKey(long trades_id);

    int updateBTradesBuyNick(@Param("trades_id")long  trades_id,@Param("buyer_nick") String buyer_nick);
}
