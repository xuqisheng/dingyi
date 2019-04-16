package com.zhidianfan.pig.yd.moduler.wechat.util;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author wangyz
 * @version v 0.1 2019-04-15 14:12 wangyz Exp $
 */
public class WeChatUtils {

    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(WeChatUtils.class);

//    /**
//     * 微信appId
//     */
//    private static final String APP_ID = "wx5042f1d476e0bd6e";
//
//    /**
//     * 微信appSecret
//     */
//    private static final String APP_SECRET = "1feff45482416be546a80e0094d08981";

    /**
     * 测试微信appId
     */
    private static final String APP_ID = "wxb3cbb793920508de";

    /**
     * 测试微信appSecret
     */
    private static final String APP_SECRET = "6a3af28d5c23a79af96dd5247171ecb1";

    /**
     * 微信请求地址
     */
    private static final String WECHAT_URL = "api.weixin.qq.com";

    /**
     * 获取微信access_token的url
     */
    private static final String WECHAT_GET_TOKEN_URL = "/cgi-bin/token";

    /**
     * 推送微信公众号消息的url
     */
    private static final String WECHAT_POST_MESSAGE_URL = "/cgi-bin/message/template/send";

    /**
     * 获取用户信息
     */
    private static final String WECHAT_USER_INFO_URL = "/sns/userinfo";

    /**
     * 获取用户token地址
     */
    private static final String WECHAT_USER_ACCESS_TOKEN = "/sns/oauth2/access_token";

    /**
     * 刷新用户token 使其生效30天
     */
    private static final String WECHAT_REFRESH_USER_ACCESS_TOKEN = "/sns/oauth2/refresh_token";

    /**
     * 编码
     */
    public static final String ENCODING = Consts.UTF_8.name();

    /**
     * 数据格式
     */
    private static final String CONTENT_JSON_TYPE = "application/json";

    /**
     * 客户端token类型
     */
    private static final String WECHAT_GRANT_TYPE = "client_credential";

    /**
     * 客户端token类型
     */
    private static final String WECHAT_USER_GRANT_TYPE = "authorization_code";

    /**
     * token对象
     */
    private static AccessToken accessToken = getToken(getTokenUrl());


    /**
     * 获取accessToken
     *
     * @param type 1:客户端token 推送消息使用
     *             0:用户端token 获取用户信息
     * @param code 客户端token获取使用的code
     * @return accessToken
     */
    public static AccessToken getAccessToken(Integer type, String code) {
        if (type.equals(1)) {
            if (accessToken == null || accessToken.getRefresh()) {
                accessToken = getToken(getTokenUrl());
                return accessToken;
            }

            //如果过期了 重新获取
            Long timeout = (Duration.between(accessToken.getAccessTokenTime(), LocalDateTime.now()).toMillis()) / 1000;
            if (timeout >= accessToken.getExpiresIn())
                accessToken = getToken(getTokenUrl());

            return accessToken;
        } else {
            //此token生效时间较短 重新生成一次30天的token数据
            AccessToken token = getToken(getUserTokenUrl(code));
            if (token != null && StringUtils.isNotEmpty(token.getRefreshToken()))
                return getToken(getRefreshUserInfoUrl(token.getRefreshToken()));

            return null;
        }
    }

    /**
     * 获取微信返回的token内容
     *
     * @return 获取token
     */
    private static AccessToken getToken(URI uri) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(uri);
        try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
            String content = EntityUtils.toString(response.getEntity(), ENCODING);
            //如果请求成功 返回格式为 {"access_token":"ACCESS_TOKEN","expires_in":7200}
            JSONObject jsonObject = JSONObject.parseObject(content);

            Integer errcode = MapUtils.getInteger(jsonObject, "errcode");
            if (errcode != null && !errcode.equals(0)) {
                logger.info("微信获取token返回失败!内容:" + content);
                //获取失败,尝试重新获取
                return new AccessTokenBuilder().setRefresh(Boolean.TRUE).build();
            }
            return new AccessTokenBuilder()
                    .setRefresh(Boolean.FALSE)                                              //不刷新
                    .setAccessTokenTime(LocalDateTime.now())                                //token获取时间
                    .setExpiresIn(MapUtils.getLong(jsonObject, "expires_in"))           //token生效时间
                    .setAccessToken(MapUtils.getString(jsonObject, "access_token"))     //token内容
                    .setOpenid(MapUtils.getString(jsonObject, "openid"))                //用户的openId
                    .setScope(MapUtils.getString(jsonObject, "scope"))                  //用户的作用区域
                    .setRefreshToken(MapUtils.getString(jsonObject, "refresh_token"))   //用户刷新token
                    .build();
        } catch (Exception e) {
            logger.error("请求微信token异常:", e);
        }
        return null;
    }

    /**
     * 拼装url
     *
     * @return 请求地址
     */
    private static URI getTokenUrl() {
        try {
            return new URIBuilder()
                    .setScheme("https")
                    .setHost(WECHAT_URL)
                    .setPath(WECHAT_GET_TOKEN_URL)
                    .addParameter("grant_type", WECHAT_GRANT_TYPE)
                    .addParameter("appid", APP_ID)
                    .addParameter("secret", APP_SECRET)
                    .build();
        } catch (Exception e) {
            logger.error("生成微信uri异常:", e);
        }
        return null;
    }

    /**
     * 拼装url
     *
     * @return 请求地址
     */
    private static URI getUserTokenUrl(String code) {
        try {
            return new URIBuilder()
                    .setScheme("https")
                    .setHost(WECHAT_URL)
                    .setPath(WECHAT_USER_ACCESS_TOKEN)
                    .addParameter("grant_type", WECHAT_USER_GRANT_TYPE)
                    .addParameter("appid", APP_ID)
                    .addParameter("secret", APP_SECRET)
                    .addParameter("code", code)
                    .build();
        } catch (Exception e) {
            logger.error("生成微信uri异常:", e);
        }
        return null;
    }

    /**
     * 拼装url
     *
     * @return 请求地址
     */
    private static URI getPushMessageUrl() {
        try {
            return new URIBuilder()
                    .setScheme("https")
                    .setHost(WECHAT_URL)
                    .setPath(WECHAT_POST_MESSAGE_URL)
                    .addParameter("access_token", getAccessToken(1, null).getAccessToken())
                    .build();
        } catch (Exception e) {
            logger.error("生成微信uri异常:", e);
        }
        return null;
    }

    /**
     * 拼装url
     *
     * @return 请求地址
     */
    public static URI getUserInfoUrl(String openId, String token) {
        try {
            return new URIBuilder()
                    .setScheme("https")
                    .setHost(WECHAT_URL)
                    .setPath(WECHAT_USER_INFO_URL)
                    .addParameter("access_token", token)
                    .addParameter("openid", openId)
                    .addParameter("lang", "zh_CN")
                    .build();
        } catch (Exception e) {
            logger.error("生成微信uri异常:", e);
        }
        return null;
    }


    /**
     * 拼装url
     *
     * @return 请求地址
     */
    public static URI getRefreshUserInfoUrl(String refreshToken) {
        try {
            return new URIBuilder()
                    .setScheme("https")
                    .setHost(WECHAT_URL)
                    .setPath(WECHAT_REFRESH_USER_ACCESS_TOKEN)
                    .addParameter("appid", APP_ID)
                    .addParameter("grant_type", "refresh_token")
                    .addParameter("refresh_token", refreshToken)
                    .build();
        } catch (Exception e) {
            logger.error("生成微信uri异常:", e);
        }
        return null;
    }

    /**
     * 推送消息
     *
     * @param touser     推送用户的openId
     * @param templateId 推送的模板id
     * @param url        详情页的url
     * @param data       推送的内容
     */
    public static String pushMessage(final String touser, final String templateId, final String url, final String data) {
        if (touser == null)
            return "";

        Map<String, Object> bodyParam = Maps.newHashMap();
        bodyParam.put("url", url);
        bodyParam.put("data", data);
        bodyParam.put("touser", touser);
        bodyParam.put("templateId", templateId);

        HttpPost httpPost = new HttpPost(getPushMessageUrl());
        httpPost.setHeader(HTTP.CONTENT_TYPE, CONTENT_JSON_TYPE);
        StringEntity entity = new StringEntity(JSONObject.toJSONString(bodyParam), ENCODING);
        httpPost.setEntity(entity);

        CloseableHttpClient httpClient = HttpClients.createDefault();
        try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
            // { "errcode":0, "errmsg":"ok", "msgid":200228332 }
            String content = EntityUtils.toString(response.getEntity(), ENCODING);
            logger.info("推送记录:" + content);
            return content;
        } catch (Exception e) {
            logger.error("推送失败:", e);
        }
        return "";
    }

    /**
     * 刷新token
     */
    public static void refreshToken() {
        accessToken.setRefresh(Boolean.TRUE);
    }
}