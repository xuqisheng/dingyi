package com.zhidianfan.pig.yd.moduler.order.task;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.Table;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.TableArea;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.XmsBusiness;
import com.zhidianfan.pig.yd.moduler.common.service.ITableAreaService;
import com.zhidianfan.pig.yd.moduler.common.service.ITableService;
import com.zhidianfan.pig.yd.moduler.common.service.IXmsBusinessService;
import com.zhidianfan.pig.yd.moduler.order.bo.PosPcCodeBO;
import com.zhidianfan.pig.yd.moduler.order.bo.TablesBO;
import com.zhidianfan.pig.yd.moduler.order.service.XopService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author danda
 */
@Component
@ConditionalOnProperty(name = "yd.task", havingValue = "true")
public class TableAreaTask {
    /**
     * 桌位区域数据接口
     */
    @Autowired
    private ITableAreaService tableAreaService;
    /**
     * 西软接口数据接口
     */
    @Autowired
    private IXmsBusinessService xmsBusinessService;
    /**
     * 桌位数据接口
     */
    @Autowired
    private ITableService tableService;
    /**
     * 西软接口业务逻辑类
     */
    @Autowired
    private XopService xopService;
    private Map<String, List<String>> tableAreaList = Maps.newHashMap();

    @Scheduled(cron = "0 0 0 * * ?")
    public void update() {
        int currentPage = 1;
        int pageSize = 1000;
        while (true) {
            Page<XmsBusiness> page = new Page<>(currentPage, pageSize);
            Page<XmsBusiness> xmsBusinessPage = xmsBusinessService.selectPage(page);
            for (XmsBusiness xmsBusiness : xmsBusinessPage.getRecords()) {
                Integer businessId = xmsBusiness.getBusinessId();
                PosPcCodeBO posPcCodeBO = xopService.posPcCodes(businessId);
                if (!posPcCodeBO.isSuccess() && posPcCodeBO.getResults().size() == 0) {
                    return;
                }
                List<String> list = Lists.newArrayList();
                if (!StringUtils.isEmpty(xmsBusiness.getPccodes())) {
                    list = Arrays.asList(xmsBusiness.getPccodes().split(","));
                }
                List<String> pccodes = list;
                posPcCodeBO.getResults().forEach(pcCode -> {
                    if (pccodes.size() != 0 && !pccodes.contains(pcCode.get("pccode"))) {
                        return;
                    }
                    //更新桌位区域
                    updateTableArea(pcCode, businessId);
                });
            }
            if (!xmsBusinessPage.hasNext()) {
                break;
            }
            currentPage++;
        }

    }


    /**
     * 更新桌位区域
     *
     * @param pcCode     桌位区域信息
     * @param businessId 酒店id
     */
    private void updateTableArea(Map<String, String> pcCode, Integer businessId) {
        String tableAreaName = String.valueOf(pcCode.get("descript"));
        String code = String.valueOf(pcCode.get("pccode"));
        TablesBO tables = xopService.tables(businessId, code);
        if (tables.getResults() == null) {
            return;
        }
        if (!tables.isSuccess() && tables.getResults().size() == 0) {
            return;
        }
        Map<String, TableArea> tableAreaMap = Maps.newHashMap();
        tables.getResults().forEach(result -> {
            String regcode = result.get("regcode");
            String pccode = result.get("pccode");
            //不存在桌位区域添加区域
            TableArea tableArea = tableAreaService.selectOne(new EntityWrapper<TableArea>()
                    .eq("business_id", businessId)
                    .eq("area_code", regcode)
                    .eq("pccode", pccode));
            if (tableArea == null) {
                tableArea = tableAreaService.selectOne(new EntityWrapper<TableArea>()
                        .eq("business_id", businessId)
                        .eq("pccode", result.get("pccode")));
            }
            if (tableArea == null) {
                tableArea = new TableArea();
                //添加区域
                tableArea.setBusinessId(businessId);
                tableArea.setAreaCode(regcode + pccode);
                tableArea.setTableAreaName(tableAreaName);
                tableArea.setStatus("1");
                tableArea.setCreatedAt(new Date());
                tableArea.setUpdatedAt(new Date());
            }
            tableArea.setPccode(pccode);
            tableAreaService.insertOrUpdate(tableArea);
            tableAreaMap.put(regcode, tableArea);
            String tableNo = result.get("tableno");
            TableArea tablearea = tableAreaMap.get(regcode);
            Table table = tableService.selectOne(new EntityWrapper<Table>().eq("business_id", businessId).eq("table_code", tableNo));
            if (table == null) {
                //添加桌位
                table = new Table();
                table.setBusinessId(businessId);
                table.setTableCode(tableNo);
                table.setStatus("1");
                table.setMaxPeopleNum(result.get("cover"));
                table.setCreatedAt(new Date());
                table.setTableName(result.get("descript"));
                table.setUpdatedAt(new Date());
            }
            table.setAreaCode(tablearea.getAreaCode());
            table.setTableAreaId(tablearea.getId());
            tableService.insertOrUpdate(table);
        });
    }

}
