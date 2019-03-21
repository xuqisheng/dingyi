package com.zhidianfan.pig.yd.moduler.manage.feign;

import com.zhidianfan.pig.common.constant.Tip;
import com.zhidianfan.pig.common.dto.WxSinglePushDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Author qqx
 * @Description
 * @Date Create in 2018/11/09
 * @Modified By:
 */
@FeignClient(name = "pig-yd-push",fallbackFactory = WxPushFeignFactory.class)
public interface WxPushFeign {

    @PostMapping("/wx/push/single")
    Tip pushToSingle(@RequestBody WxSinglePushDTO pushDTO);

}
