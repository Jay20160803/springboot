package com.jk51.module.dynamic;

import com.jk51.configuration.dynamicDataSourceConfig.annotation.TargetDateSource;
import com.jk51.configuration.dynamicDataSourceConfig.util.DataSourceIdEnum;
import com.jk51.model.BTrades;
import com.jk51.module.dynamic.mapper.BTradesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 版权所有(C) 2017 上海银路投资管理有限公司
 * 描述:
 * 作者: gaojie
 * 创建日期: 2017-12-27
 * 修改记录:
 */
@Controller
@ResponseBody
@RequestMapping("dynamicData")
public class DynamicController {


    private Logger logger = LoggerFactory.getLogger(DynamicController.class);

    @Autowired
    private BTradesMapper bTradesMapper;

    @TargetDateSource(DataSourceIdEnum.MASTER)
    @RequestMapping("master")
    @Transactional
    public String getTrades(){

        bTradesMapper.updateBTradesBuyNick(1000021434522529650L,"aa");
        /*BTrades bTrades = bTradesMapper.getBTradesByPrimaryKey(1000021434522529650L);*/


        throw new RuntimeException("test Transactional");
        //return bTrades.getBuyerNick();
    }

    @TargetDateSource(DataSourceIdEnum.CUSTOMER)
    @RequestMapping("custom")
    public String getTradescustom(){

        BTrades bTrades = bTradesMapper.getBTradesByPrimaryKey(1000021434522529650L);
        return bTrades.getBuyerNick();
    }
}
