package com.zhidianfan.pig.yd.moduler.meituan.service.rmi;

import com.zhidianfan.pig.yd.moduler.common.dto.Tip;
import com.zhidianfan.pig.yd.moduler.meituan.service.rmi.fallback.PushFeignFactory;
import com.zhidianfan.pig.yd.moduler.resv.dto.WSResultDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * @Author qqx
 * @Description
 * @Date: 2018/10/16
 * @Modified By:
 */
@FeignClient(name = "pig-yd-base",fallbackFactory = PushFeignFactory.class)
public interface PushFeign {

    /**
     * 推送消息到预订台
     * @param
     * @return
     */
    @PostMapping(value = "/push/msg")
    void pushMsg(@RequestParam(name = "type")  String type,
                 @RequestParam(name = "username") String username,
                 @RequestParam(name = "msgSeq") String msgSeq,
                 @RequestParam(name = "businessId") String businessId,
                 @RequestParam(name = "msg") String msg
    );

    /**
     * 查询该酒店在线设备
     * @param
     * @return
     */
    @GetMapping(value = "/ws/openid")
    WSResultDTO getOnlineSession(@RequestParam(name = "hotelId")  Integer hotelId);

}
