package com.zhidianfan.pig.yd.moduler.wechat.controller;

import com.zhidianfan.pig.yd.moduler.common.dto.SuccessTip;
import com.zhidianfan.pig.yd.moduler.common.dto.Tip;
import com.zhidianfan.pig.yd.moduler.common.service.IResvOrderAndroidService;
import com.zhidianfan.pig.yd.moduler.wechat.util.AccessToken;
import com.zhidianfan.pig.yd.moduler.wechat.util.OrderTemplate;
import com.zhidianfan.pig.yd.moduler.wechat.util.WeChatUtils;
import com.zhidianfan.pig.yd.moduler.wechat.vo.PushMessageVO;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 微信
 *
 * @author wangyz
 * @version v 0.1 2019-04-15 11:04 wangyz Exp $
 */
@RestController
@RequestMapping("/wechat/")
public class WeChatController {

    @Autowired
    private RedisTemplate<String, AccessToken> redisTemplate;

    @Autowired
    private IResvOrderAndroidService resvOrderAndroidService;

    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(WeChatController.class);

    @GetMapping("getUserInfo")
    public String getUserInfo(String code, String openid) {
        AccessToken accessToken = null;
        if (StringUtils.isNotEmpty(openid))
            accessToken = redisTemplate.opsForValue().get(openid);

        if (accessToken == null || WeChatUtils.isExpiredToken(accessToken)) {
            accessToken = WeChatUtils.getAccessToken(0, code);
            if (accessToken == null || StringUtils.isEmpty(accessToken.getAccessToken()))
                return "code已失效";
            else
                redisTemplate.opsForValue().set(accessToken.getOpenid(), accessToken);
        }

        HttpGet httpGet = new HttpGet(WeChatUtils.getUserInfoUrl(accessToken.getOpenid(), accessToken.getAccessToken()));
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
            String content = EntityUtils.toString(response.getEntity(), WeChatUtils.ENCODING);
            logger.info("请求画像结果:" + content);
            return content;
        } catch (Exception e) {
            logger.error("请求用户画像异常:", e);
        }
        return "token已失效";
    }

    @PostMapping("pushMessageForThirdOrder")
    public ResponseEntity<Tip> pushMessageForThirdOrder() {
        LocalDateTime now = LocalDateTime.now().withNano(0);
        //防止延迟
        if (now.getMinute() < 10)
            now = now.withMinute(0).withSecond(0);
        else
            now = now.withMinute(30).withSecond(0);

        List<Map<String, Object>> list = resvOrderAndroidService.getAllWeChatThirdOrder(now.plusHours(1));
        for (Map<String, Object> order : list) {
            PushMessageVO pushMessageVO = new PushMessageVO();
            pushMessageVO.setBusinessName(MapUtils.getString(order, "business_name"));
            pushMessageVO.setDate(MapUtils.getString(order, "resv_date"));
            pushMessageVO.setTableArea(MapUtils.getString(order, "table_area_name") + "-" + MapUtils.getString(order, "table_name"));
            pushMessageVO.setBusinessAddr(MapUtils.getString(order, "business_addr"));
            pushMessageVO.setOrderTemplate(OrderTemplate.ORDER_RESV_REMIND);
            WeChatUtils.pushMessage(
                    MapUtils.getString(order, "openid"),
                    OrderTemplate.ORDER_RESV_REMIND.getCode(),
                    "http://eding.zhidianfan.com/#/OrderDetail?id=" + MapUtils.getString(order, "third_order_no"),
                    WeChatUtils.getMessageContent(pushMessageVO));
        }
        return ResponseEntity.ok(SuccessTip.SUCCESS_TIP);
    }

    @Bean("accessTokenTemplate")
    public RedisTemplate<String, AccessToken> accessTokenTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, AccessToken> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

}