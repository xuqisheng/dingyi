package com.zhidianfan.pig.ydmonitor.task;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.zhidianfan.pig.common.constant.SuccessTip;
import com.zhidianfan.pig.common.dto.WxBatchPushDTO;
import com.zhidianfan.pig.common.dto.WxSinglePushDTO;
import com.zhidianfan.pig.ydmonitor.dao.entity.MonitorConfig;
import com.zhidianfan.pig.ydmonitor.dao.entity.MonitorLog;
import com.zhidianfan.pig.ydmonitor.dao.entity.MonitorServer;
import com.zhidianfan.pig.ydmonitor.dao.service.IMonitorConfigService;
import com.zhidianfan.pig.ydmonitor.dao.service.IMonitorLogService;
import com.zhidianfan.pig.ydmonitor.dao.service.IMonitorServerService;
import com.zhidianfan.pig.ydmonitor.feign.WxPushFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.*;

/**
 * 监控ECS中的服务状态
 *
 * @Author sherry
 * @Description
 * @Date Create in 2018/11/7
 * @Modified By:
 */
@RestController
@Slf4j
public class EcsMacController {
    /**
     * 监听的服务信息数据接口
     */
    @Autowired
    private IMonitorServerService serverService;
    /**
     * 微信推送远程调用
     */
    @Autowired
    private WxPushFeign wxPushFeign;
    /**
     * 配置服务接口
     */
    @Autowired
    private IMonitorConfigService configService;
    /**
     * 本地日志服务接口
     */
    @Autowired
    private IMonitorLogService logService;

    @GetMapping("/service/status")
    public SuccessTip service() {
        List<MonitorServer> servers = serverService.selectList(new EntityWrapper<MonitorServer>().eq("is_enable", 1));
        if (servers == null || servers.size() == 0) {
            return new SuccessTip();
        }
        List<MonitorConfig> configs = configService.selectList(null);
        Map<String, Object> configMap = Maps.newHashMap();
        configs.forEach(config -> {
            if (configMap.containsKey(config.getConfigName())) {
                if (configMap.get(config.getConfigName()) instanceof String) {
                    HashSet<Object> value = Sets.newHashSet();
                    value.add(config.getConfigValue());
                    value.add(config.getConfigValue());
                    configMap.put(config.getConfigName(), value);
                } else if (configMap.get(config.getConfigName()) instanceof Set) {
                    Set value = (Set) configMap.get(config.getConfigName());
                    value.add(config.getConfigValue());
                    configMap.put(config.getConfigName(), value);
                }
            } else {
                configMap.put(config.getConfigName(), config.getConfigValue());
            }
        });
        servers.stream().parallel().forEach((server) -> {
            String address = server.getAddress();
            int port = server.getPort();
            Socket socket = new Socket();
            boolean isSendMessage = false;
            try {
                socket.connect(new InetSocketAddress(address, port));
            } catch (IOException e) {
                log.error(e.getMessage());
                ResponseEntity entity = null;
                if (configMap.get("openid") instanceof String) {
                    String openid = (String) configMap.get("openid");
                    String templateId = (String) configMap.get("templateId");
                    WxSinglePushDTO singlePushDTO = new WxSinglePushDTO();
                    singlePushDTO.setOpenId(openid);
                    singlePushDTO.setTemplateId(templateId);
                    Map<String, Object> params = Maps.newHashMap();
                    Map<String,String> addressMap = Maps.newHashMap();
                    addressMap.put("value",server.getAddress());
                    params.put("address", addressMap);

                    Map<String,String> serverNameMap = Maps.newHashMap();
                    serverNameMap.put("value",server.getAddress());
                    params.put("server_name", serverNameMap);
                    singlePushDTO.setParams(params);
                    entity = wxPushFeign.pushToSingle(singlePushDTO);
                } else if (configMap.get("openid") instanceof Set) {
                    Set<String> openid = (Set) configMap.get("openid");
                    Map<String, Map<String, Object>> params = Maps.newHashMap();
                    openid.forEach(id -> {
                        Map<String, Object> paramValue = Maps.newHashMap();
                        Map<String, String> addressMap = Maps.newHashMap();
                        addressMap.put("value", server.getAddress());
                        paramValue.put("address", addressMap);
                        Map<String, Object> serverNameMap = Maps.newHashMap();
                        serverNameMap.put("value", server.getServerName());
                        paramValue.put("server_name", serverNameMap);
                        params.put(id,paramValue);
                    });
                    WxBatchPushDTO batchPushDTO = new WxBatchPushDTO();
                    batchPushDTO.setTemplateId(String.valueOf(configMap.get("templateId")));
                    batchPushDTO.setParams(params);
                    batchPushDTO.setUrl("");
                    entity = wxPushFeign.pushToBatch(batchPushDTO);
                }
                isSendMessage = entity != null;
            } finally {
                MonitorLog monitorLog = new MonitorLog();
                monitorLog.setCreateTime(new Date());
                monitorLog.setAddress(server.getAddress());
                monitorLog.setPort(server.getPort());
                monitorLog.setServerName(server.getServerName());
                monitorLog.setIsSendMessage(isSendMessage?1:0);
                if (socket.isConnected()) {
                    monitorLog.setIsEnable(1);
                } else {
                    monitorLog.setIsEnable(0);
                }
                logService.insert(monitorLog);
                try {
                    socket.close();
                } catch (IOException e) {
                    log.info(e.getMessage());
                }
            }

        });
        return new SuccessTip();
    }

}
