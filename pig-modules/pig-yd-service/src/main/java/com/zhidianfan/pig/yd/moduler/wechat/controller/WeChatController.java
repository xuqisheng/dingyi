package com.zhidianfan.pig.yd.moduler.wechat.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.zhidianfan.pig.yd.moduler.wechat.util.AccessToken;
import com.zhidianfan.pig.yd.moduler.wechat.util.OrderTemplate;
import com.zhidianfan.pig.yd.moduler.wechat.util.WeChatUtils;
import com.zhidianfan.pig.yd.moduler.wechat.vo.PushMessageVO;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(WeChatController.class);

    @PostMapping("pushMessage")
    public String pushMessage(PushMessageVO pushMessageVO) {
        return WeChatUtils.pushMessage(
                pushMessageVO.getOpenId(),
                pushMessageVO.getOrderTemplate().getCode(),
                "",
                JSONObject.toJSONString(getMessageContent(pushMessageVO)));
    }

    @GetMapping("getUserInfo")
    public String getUserInfo(String code, String openid) {
        AccessToken accessToken = null;
        if (StringUtils.isNotEmpty(openid))
            accessToken = redisTemplate.opsForValue().get(openid);

        if (accessToken == null) {
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

    public static String getMessageContent(PushMessageVO vo) {
        JSONObject jsonObject = new JSONObject(Boolean.TRUE);

        if (OrderTemplate.ORDER_RESV_SUCCESS.equals(vo.getOrderTemplate())) {
            Map<String, Object> first = Maps.newHashMap();
            first.put("value", vo.getBusinessName() + "已接单");
            jsonObject.put("first", first);

            Map<String, Object> keyword1 = Maps.newHashMap();
            keyword1.put("value", vo.getPersonNum() + "位 " + vo.getTableArea() + " " + vo.getPhone() + " " + vo.getName() + (vo.getSex().equals("1") ? "先生" : "女士"));
            jsonObject.put("keyword1", keyword1);

            Map<String, Object> keyword2 = Maps.newHashMap();
            keyword2.put("value", vo.getDate());
            jsonObject.put("keyword2", keyword2);

            Map<String, Object> remark = Maps.newHashMap();
            remark.put("value", "期待您的光临！");
            jsonObject.put("remark", remark);
        }
        if (vo.getOrderTemplate().equals(OrderTemplate.ORDER_RESV_CANCEL)) {

        }
        if (vo.getOrderTemplate().equals(OrderTemplate.ORDER_RESV_HOTEL_CANCEL)) {

        }

        return jsonObject.toJSONString();
    }

    @Bean("accessTokenTemplate")
    public RedisTemplate<String, AccessToken> accessTokenTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, AccessToken> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

}