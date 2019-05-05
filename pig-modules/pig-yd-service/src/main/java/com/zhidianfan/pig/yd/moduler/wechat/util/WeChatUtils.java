package com.zhidianfan.pig.yd.moduler.wechat.util;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zhidianfan.pig.yd.moduler.wechat.vo.PushMessageVO;
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
import java.util.List;
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
        //内部accessToken
        if (type.equals(1)) {
            if (accessToken == null || accessToken.getRefresh()) {
                accessToken = getToken(getTokenUrl());
                return accessToken;
            }

            if (isExpiredToken(accessToken))
                accessToken = getToken(getTokenUrl());

            return accessToken;
        } else if (type.equals(0)) {                //第一次获取用户token
            return getToken(getUserTokenUrl(code));
        } else if (type.equals(2)) {                //再次获取用户token
            return getToken(getRefreshUserInfoUrl(code));
        }
        return null;
    }

    /**
     * 验证token是否在有效期内
     *
     * @param accessToken
     * @return
     */
    public static Boolean isExpiredToken(AccessToken accessToken) {
        Long timeout = (Duration.between(accessToken.getAccessTokenTime(), LocalDateTime.now()).toMillis()) / 1000;
        return timeout >= accessToken.getExpiresIn();
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
    private static URI getRefreshUserInfoUrl(String refreshToken) {
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
    public static String pushMessage(final String touser, final String templateId, final String url, final JSONObject data) {
        if (touser == null)
            return "";

        logger.info(data.toJSONString());

        Map<String, Object> bodyParam = Maps.newLinkedHashMap();
        bodyParam.put("touser", touser);
        bodyParam.put("template_id", templateId);
        bodyParam.put("url", url);
        bodyParam.put("data", data);

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

    public static JSONObject getMessageContent(PushMessageVO vo) {
        Map<String, Object> first = Maps.newHashMap();
        Map<String, Object> keyword1 = Maps.newHashMap();
        Map<String, Object> keyword2 = Maps.newHashMap();
        Map<String, Object> keyword3 = Maps.newHashMap();
        Map<String, Object> keyword4 = Maps.newHashMap();
        Map<String, Object> keyword5 = Maps.newHashMap();
        Map<String, Object> remark = Maps.newHashMap();


        if (OrderTemplate.ORDER_RESV_SUCCESS.equals(vo.getOrderTemplate())) {
            first.put("value", "您好，您在" + vo.getBusinessName() + "订位成功");
            keyword1.put("value", vo.getDate());
            keyword2.put("value", vo.getTableType() + " " + vo.getTableArea());
            keyword3.put("value", vo.getPersonNum() + "位");
            keyword4.put("value", vo.getName() + (vo.getSex().equals("1") ? "先生" : "女士") + " " + vo.getPhone());
            keyword5.put("value", StringUtils.isEmpty(vo.getDesc()) ? "无" : vo.getDesc());
            remark.put("value", "感谢您的使用");
        }
        if (OrderTemplate.ORDER_RESV_HOTEL_CANCEL.equals(vo.getOrderTemplate())) {
            first.put("value", "非常抱歉，餐厅已满座，请预约其它时段。");
            keyword1.put("value", vo.getBusinessName());
            keyword2.put("value", vo.getBusinessAddr());
            keyword3.put("value", vo.getDate() + " " + vo.getPersonNum() + "人 " + vo.getTableType());
            keyword4.put("value", StringUtils.isEmpty(vo.getDesc()) ? "无" : vo.getDesc());
            remark.put("value", "再次抱歉。");
        }
        if (OrderTemplate.ORDER_RESV_REMIND.equals(vo.getOrderTemplate())) {
            first.put("value", "尊敬的客户您好，易订提醒您离就餐还有一个小时。");
            keyword1.put("value", vo.getBusinessName());
            keyword2.put("value", vo.getDate());
            keyword3.put("value", vo.getTableArea());
            keyword4.put("value", vo.getBusinessAddr());
            remark.put("value", "谢谢你的预约！");
        }

        first.put("color", "#173177");
        keyword1.put("color", "#173177");
        keyword2.put("color", "#173177");
        keyword3.put("color", "#173177");
        keyword4.put("color", "#173177");
        keyword5.put("color", "#173177");
        remark.put("color", "#173177");

        return getMessageData(Lists.newArrayList(first, keyword1, keyword2, keyword3, keyword4, keyword5, remark));
    }

    private static JSONObject getMessageData(List<Map<String, Object>> data) {
        if (data == null || data.size() == 0)
            return null;
        JSONObject jsonObject = new JSONObject(Boolean.TRUE);
        jsonObject.put("first", data.get(0));
        for (int i = 1; i < data.size() - 1; i++) {
            if (data.get(i).size() > 0)
                jsonObject.put("keyword" + i, data.get(i));
        }
        jsonObject.put("remark", data.get(data.size() - 1));
        return jsonObject;
    }


    /**
     * 刷新token
     */
    public static void refreshToken() {
        accessToken.setRefresh(Boolean.TRUE);
    }
}