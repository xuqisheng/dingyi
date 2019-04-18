package com.zhidianfan.pig.yd.moduler.wechat.controller;

import com.alibaba.fastjson.JSONObject;
import com.zhidianfan.pig.yd.moduler.wechat.util.AccessToken;
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
                JSONObject.toJSONString(WeChatUtils.getMessageContent(pushMessageVO)));
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


    @Bean("accessTokenTemplate")
    public RedisTemplate<String, AccessToken> accessTokenTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, AccessToken> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

}