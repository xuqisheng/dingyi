package com.zhidianfan.pig.yd.moduler.welcrm.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.Business;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.BusinessSyncAccount;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.Vip;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.VipAppraise;
import com.zhidianfan.pig.yd.moduler.common.dto.SuccessTip;
import com.zhidianfan.pig.yd.moduler.common.dto.Tip;
import com.zhidianfan.pig.yd.moduler.common.service.IBusinessService;
import com.zhidianfan.pig.yd.moduler.common.service.IBusinessSyncAccountService;
import com.zhidianfan.pig.yd.moduler.common.service.IVipAppraiseService;
import com.zhidianfan.pig.yd.moduler.common.service.IVipService;
import com.zhidianfan.pig.yd.moduler.welcrm.bo.BasicBO;
import com.zhidianfan.pig.yd.moduler.welcrm.constant.CrmMethod;
import com.zhidianfan.pig.yd.moduler.welcrm.dto.AppraiseDTO;
import com.zhidianfan.pig.yd.moduler.welcrm.dto.BasicDTO;
import com.zhidianfan.pig.yd.moduler.welcrm.dto.FiveiUserDTO;
import com.zhidianfan.pig.yd.moduler.welcrm.dto.PushDataDTO;
import com.zhidianfan.pig.yd.utils.SignUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;

/**
 * @Author qqx
 * @Description 微生活crm接口
 * @Date Create in 2018/10/9
 * @Modified By:
 */
@Service
public class CrmService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private IBusinessSyncAccountService businessSyncAccountService;

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private IVipService vipService;

    @Autowired
    private IBusinessService businessService;

    @Autowired
    private IVipAppraiseService vipAppraiseService;


    /**
     * 会员信息接口
     * @param cno
     * @return
     * @throws Exception
     */
    public BasicBO getUserInfo(String cno,Integer businessId) throws Exception{
        BasicDTO basicDTO = new BasicDTO();
        BasicBO basicBO = new BasicBO();
        BusinessSyncAccount businessSyncAccount = businessSyncAccountService.selectOne(new EntityWrapper<BusinessSyncAccount>().eq("business_id",businessId));
        if(businessSyncAccount == null || (businessSyncAccount.getAppidCrm() == null && businessSyncAccount.getStoreId() == null)){
            basicBO.setData(null);
            basicBO.setErrmsg("未开通crm接口");
            basicBO.setErrcode(500);
        }else if(businessSyncAccount.getAppidCrm() != null){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("cno",cno);
            jsonObject.put("SHOPID",businessSyncAccount.getShopId());
            basicDTO.setData(jsonObject);
            basicDTO.setUrl(CrmMethod.USER_INFO_URL);
            basicDTO.setAppid(businessSyncAccount.getAppidCrm());
            basicDTO.setAppkey(businessSyncAccount.getAppkeyCrm());
            basicBO = restTemplatePost(basicDTO);
            if(basicBO.getErrcode() == 0){
                Map<String, Object> data = new HashMap<>();
                data.put("userName",basicBO.getRes().getJSONObject(0).get("name"));
                data.put("userPhone",basicBO.getRes().getJSONObject(0).get("phone"));
                data.put("userGrade",basicBO.getRes().getJSONObject(0).get("grade_name"));
                data.put("userCredit",basicBO.getRes().getJSONObject(0).get("credit"));
                data.put("userBalance",basicBO.getRes().getJSONObject(0).get("balance"));
                data.put("userBirthday",basicBO.getRes().getJSONObject(0).get("birthday"));
                data.put("userSex",basicBO.getRes().getJSONObject(0).get("gender"));
                data.put("userConsumeNum",basicBO.getRes().getJSONObject(0).get("user_consume_num"));
                data.put("userConsumeAmount",basicBO.getRes().getJSONObject(0).get("user_consume_amount"));
                data.put("LastConsumeShopName",basicBO.getRes().getJSONObject(0).get("last_consume_shop_name"));
                JSONArray couponsList = (JSONArray) basicBO.getRes().getJSONObject(0).get("coupons");
                JSONArray jsonArray = new JSONArray();
                for(Object coupons : couponsList){
                    JSONObject jsonObject1 = JSONObject.parseObject(coupons.toString(),JSONObject.class);
                    jsonArray.add(jsonObject1.get("title"));
                }
                data.put("couponsName",jsonArray);
                basicBO.setData(data);
            }else {
                basicBO.setData(null);
            }
        }else if(businessSyncAccount.getStoreId() != null){
            String xml = "<xml><MsgType><![CDATA[searchMemberConsu]]></MsgType><Code><![CDATA["+cno+"]]></Code><CodeType>1</CodeType><StoreId><![CDATA["+businessSyncAccount.getStoreId()+"]]></StoreId><CreateId><![CDATA["+businessSyncAccount.getCreatedId()+"]]></CreateId></xml>";
            String randomnum = getRandom();
            String timestamp = String.valueOf(Instant.now().getEpochSecond());
            ResponseEntity<String> entity = restTemplate.getForEntity(CrmMethod.FIVEI_USER_URL+"?username=yiding&randomnum="+randomnum+"&timestamp="+timestamp+"&signature="+createSgin1(businessSyncAccount.getPassword(),randomnum,timestamp)+"&content="+xml
                    , String.class);
            FiveiUserDTO fiveiUserDTO = JSONObject.parseObject(entity.getBody(), FiveiUserDTO.class);
            if(fiveiUserDTO.getResult().getCode() == 0){
                Map<String, Object> data = new HashMap<>();
                data.put("userName",fiveiUserDTO.getConsuVo().getConsuDetailList().get(0).getName());
                data.put("userPhone",cno);
                data.put("userGrade",fiveiUserDTO.getConsuVo().getConsuDetailList().get(0).getMemberLevelName());
                data.put("userCredit",fiveiUserDTO.getConsuVo().getConsuDetailList().get(0).getMemberScore());
                data.put("userBalance",0);
                data.put("userBirthday","");
                data.put("userSex","");
                basicBO.setData(data);
                basicBO.setErrcode(fiveiUserDTO.getResult().getCode());
                basicBO.setErrmsg(fiveiUserDTO.getResult().getMsg());
            }else {
                basicBO.setData(null);
                basicBO.setErrcode(500);
                basicBO.setErrmsg(fiveiUserDTO.getResult().getMsg());
            }
        }
        basicBO.setRes(null);
        if(basicBO.getData() != null){
            Vip vip = vipService.selectOne(new EntityWrapper<Vip>().eq("business_id",businessId).eq("vip_phone",basicBO.getData().get("userPhone")));
            if(vip == null){
                Business business = businessService.selectById(businessId);
                Vip vip1 = new Vip();
                vip1.setBusinessId(businessId);
                vip1.setCreatedAt(new Date());
                vip1.setBusinessName(business.getBusinessName());
                vip1.setVipBirthday(String.valueOf(basicBO.getData().get("userBirthday")));
                vip1.setVipPhone(String.valueOf(basicBO.getData().get("userPhone")));
                vip1.setVipSex("1".equals(String.valueOf(basicBO.getData().get("userSex"))) ? "男" : "女");
                vip1.setVipName(String.valueOf(basicBO.getData().get("userName")));
                vipService.insert(vip1);
            }
        }
        return basicBO;
    }

    public BasicBO setUserInfo(String phone,Integer businessId,Integer sex,String userName) throws Exception{
        BasicBO basicBO = new BasicBO();
        BusinessSyncAccount businessSyncAccount = businessSyncAccountService.selectOne(new EntityWrapper<BusinessSyncAccount>().eq("business_id",businessId));
        if(businessSyncAccount == null || businessSyncAccount.getStoreId() == null){
            basicBO.setData(null);
            basicBO.setErrmsg("未开通crm接口");
            basicBO.setErrcode(500);
        }else {
            String xml = "<xml><MsgType><![CDATA[sentMemberInfo]]></MsgType><Mobile><![CDATA["+phone+"]]></Mobile><StoreId><![CDATA["+businessSyncAccount.getStoreId()+"]]></StoreId><CreateId><![CDATA["+businessSyncAccount.getCreatedId()+"]]></CreateId><Name><![CDATA["+userName+"]]></Name><Sex><![CDATA["+sex+"]]></Sex></xml>";
            String randomnum = getRandom();
            String timestamp = String.valueOf(Instant.now().getEpochSecond());
            ResponseEntity<String> entity = restTemplate.getForEntity(CrmMethod.FIVEI_SET_USER_URL+"?username=yiding&randomnum="+randomnum+"&timestamp="+timestamp+"&signature="+createSgin1(businessSyncAccount.getPassword(),randomnum,timestamp)+"&content="+xml
                    , String.class);
            FiveiUserDTO fiveiUserDTO = JSONObject.parseObject(entity.getBody(), FiveiUserDTO.class);
            basicBO.setData(null);
            basicBO.setErrcode(fiveiUserDTO.getResult().getCode());
            basicBO.setErrmsg(fiveiUserDTO.getResult().getMsg());
        }
        return basicBO;
    }

    /**
     * 接收微生活评价
     * @param appraiseDTO
     * @return
     */
    public Tip saveAppraiseInfo(AppraiseDTO appraiseDTO) throws ParseException {

        JSONArray pushDataDTOList = JSONObject.parseArray(appraiseDTO.getPushData());

        for(Object object : pushDataDTOList){
            PushDataDTO pushDataDTO = JSONObject.parseObject(object.toString(),PushDataDTO.class);
            BusinessSyncAccount businessSyncAccount = businessSyncAccountService.selectOne(new EntityWrapper<BusinessSyncAccount>().eq("shop_id",pushDataDTO.getSid()));
            if(businessSyncAccount != null){
                VipAppraise vipAppraise = new VipAppraise();
                vipAppraise.setBusinessId(businessSyncAccount.getBusinessId());
                vipAppraise.setCreateTime(new Date());
                vipAppraise.setVipPhone(pushDataDTO.getUser_phone());
                vipAppraise.setDishId(pushDataDTO.getDish_id());
                vipAppraise.setDishOptions(pushDataDTO.getDish_options());
                vipAppraise.setServerId(pushDataDTO.getServer_id());
                vipAppraise.setServerScore(pushDataDTO.getServer_score());
                vipAppraise.setServerOptions(pushDataDTO.getServer_options());
                vipAppraise.setTableId(pushDataDTO.getTable_id());
                vipAppraise.setTctotalFee(pushDataDTO.getTctotal_fee());
                vipAppraise.setTcFee(pushDataDTO.getTcFee());
                vipAppraise.setTcId(pushDataDTO.getTc_id());
                vipAppraise.setDishScore(pushDataDTO.getDish_score());
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                vipAppraise.setTcTime(format.parse(pushDataDTO.getTc_time()));
                vipAppraise.setCmTime(format.parse(pushDataDTO.getCm_time()));

                vipAppraiseService.insert(vipAppraise);
            }
        }

        return new SuccessTip(200,"接收成功");
    }

    /**
     * restTemplate post
     *
     * @param basicDTO
     * @return ResponseEntity<String>
     */
    public BasicBO restTemplatePost(BasicDTO basicDTO) throws Exception{
        Tip tip;
        LinkedMultiValueMap map = new LinkedMultiValueMap();
        map.set("req", basicDTO.getData());
        map.set("appid", basicDTO.getAppid());
        map.set("v", CrmMethod.VERSION);
        map.set("ts", String.valueOf(Instant.now().getEpochSecond()));
        Map sig = JSON.parseObject(basicDTO.getData().toJSONString());
        map.set("sig", createSgin(sig,basicDTO.getAppid(),basicDTO.getAppkey()));
        map.set("fmt", basicDTO.getData().toJSONString());
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType(CrmMethod.CONTENTTYPE);
        headers.setContentType(type);
        BasicBO basicBO = new BasicBO();
        HttpEntity<LinkedMultiValueMap> httpEntity = new HttpEntity<>(map, headers);
        try {
            ResponseEntity<String> entity = restTemplate.postForEntity(basicDTO.getUrl(), httpEntity, String.class);
            JSONObject jsonObject = JSONObject.parseObject(entity.getBody(), JSONObject.class);
            if (entity.getStatusCodeValue() != 200) {
                basicBO.setErrcode(500);
                basicBO.setErrmsg("获取会员信息错误");
                basicBO.setRes(null);
            } else {
                if("0".equals(String.valueOf(jsonObject.get("errcode")))){
                    basicBO = JSONObject.parseObject(entity.getBody(), BasicBO.class);
                }else {
                    basicBO.setErrcode(Integer.parseInt(String.valueOf(jsonObject.get("errcode"))));
                    basicBO.setErrmsg(String.valueOf(jsonObject.get("errmsg")));
                    basicBO.setRes(null);
                }
            }
            return basicBO;
        } catch (Exception e) {
            e.printStackTrace();
            basicBO.setErrcode(500);
            basicBO.setErrmsg("获取会员信息错误");
            basicBO.setRes(null);
            return basicBO;
        }
    }

    /**
     * 生成微生活签名
     *
     * @param params
     * @return String
     */
    private static String createSgin(Map<String, Object> params,String appid,String appkey) throws Exception{
        String sig = SignUtil.genSignStr1(params) + "appid=" + appid + "&appkey=" + appkey + "&v=" + CrmMethod.VERSION + "&ts=" + String.valueOf(Instant.now().getEpochSecond());
        return DigestUtils.md5Hex(sig).toLowerCase();
    }

    /**
     * 生成5i签名
     * @param password
     * @param randomnum
     * @param timestamp
     * @return
     * @throws Exception
     */
    private static String createSgin1(String password,String randomnum,String timestamp) throws Exception{
        String sign1 = DigestUtils.md5Hex(password+randomnum).toUpperCase();
        return DigestUtils.md5Hex(sign1+timestamp).toUpperCase();
    }

    private static String getRandom(){
        String str="ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb=new StringBuilder(4);
        for(int i=0;i<4;i++)
        {
            char ch=str.charAt(new Random().nextInt(str.length()));
            sb.append(ch);
        }
        return (sb.toString());
    }
}
