package com.zhidianfan.pig.yd.moduler.sms.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.zhidianfan.pig.common.util.JsonUtils;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.*;
import com.zhidianfan.pig.yd.moduler.common.dto.ErrorTip;
import com.zhidianfan.pig.yd.moduler.common.dto.SuccessTip;
import com.zhidianfan.pig.yd.moduler.common.dto.Tip;
import com.zhidianfan.pig.yd.moduler.common.service.*;
import com.zhidianfan.pig.yd.moduler.resv.dto.SmsMarkingDTO;
import com.zhidianfan.pig.yd.moduler.sms.dto.sms.SmsMarketingCustom;
import com.zhidianfan.pig.yd.moduler.sms.service.rmi.SmsFeign;
import com.zhidianfan.pig.yd.moduler.sms.service.rmi.dto.ClMsgParam;
import com.zhidianfan.pig.yd.moduler.sms.service.rmi.dto.SmsSendResDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author zhoubeibei
 * @Description
 * @Date Create in 2018-12-04
 * @Modified By:
 */
@Slf4j
@Service
public class SmsMarketingService {
    @Autowired
    private IVipService vipService;
    @Autowired
    private IVipStatisticsService vipStatisticsService;
    @Autowired
    private ISmsMarketingService smsMarketingService;
    @Autowired
    private IBusinessService businessService;

    @Autowired
    private ISmsMarketingBatchService smsMarketingBatchService;

    @Autowired
    private SmsFeign smsFeign;

    private static final Integer MAX_NUMBER = 800;

    public int calcTargetPhone(SmsMarketing smsMarketing) {
        log.info("计算目标手机号码:{}", smsMarketing);
//        if(StringUtils.isEmpty(smsMarketing.getVipClassId()) && (StringUtils.isEmpty(smsMarketing.getCustom()) || StringUtils.equalsIgnoreCase(smsMarketing.getCustom(),"{}"))){//没有传递客户价值和自定义客户类型返回0
//            return 0;
//        }
        String custom = smsMarketing.getCustom();
        String vipValueId = smsMarketing.getVipValueId();
        List<Vip> vips = Lists.newArrayList();
        log.info("vipValueId:{}", vipValueId);
        if (StringUtils.isNotBlank(vipValueId)) {
            if (StringUtils.equals("0", vipValueId)) {
                vips = vipService.selectList(new EntityWrapper<Vip>().eq("business_id", smsMarketing.getBusinessId()));
            } else {
                vips = vipService.selectList(new EntityWrapper<Vip>().eq("business_id", smsMarketing.getBusinessId()).in("vip_value_id", Arrays.asList(vipValueId.split(","))));
            }
            log.info("vips:{}", vips);
        } else if (StringUtils.isNotBlank(custom)) {
            EntityWrapper<VipStatistics> entityWrapper = (EntityWrapper<VipStatistics>) new EntityWrapper<VipStatistics>()
                    .eq("business_id", smsMarketing.getBusinessId());
            SmsMarketingCustom smsMarketingCustom = JsonUtils.jsonStr2Obj(custom, SmsMarketingCustom.class);
            if (smsMarketingCustom != null) {
                String gt = "大于";
                String lt = "小于";
                //预定次数
                if (smsMarketingCustom.getResv() != null && StringUtils.startsWith(smsMarketingCustom.getResv(), gt)) {
                    String resv = StringUtils.substringAfter(smsMarketingCustom.getResv(), gt);
                    entityWrapper.gt("resv_batch_count", resv);
                } else if (smsMarketingCustom.getResv() != null && StringUtils.startsWith(smsMarketingCustom.getResv(), lt)) {
                    String resv = StringUtils.substringAfter(smsMarketingCustom.getResv(), lt);
                    entityWrapper.lt("resv_batch_count", resv);
                }

                //就餐次数
                if (smsMarketingCustom.getEat() != null && StringUtils.startsWith(smsMarketingCustom.getEat(), gt)) {
                    String eat = StringUtils.substringAfter(smsMarketingCustom.getEat(), gt);
                    entityWrapper.gt("meal_batch_count", eat);
                } else if (smsMarketingCustom.getEat() != null && StringUtils.startsWith(smsMarketingCustom.getEat(), lt)) {
                    String eat = StringUtils.substringAfter(smsMarketingCustom.getEat(), lt);
                    entityWrapper.lt("meal_batch_count", eat);
                }

                //最近预定时间
                if (smsMarketingCustom.getLast() != null && StringUtils.startsWith(smsMarketingCustom.getLast(), gt)) {
                    String last = StringUtils.substringAfter(smsMarketingCustom.getLast(), gt);
                    entityWrapper.gt("last_meal_date", last);
                } else if (smsMarketingCustom.getLast() != null && StringUtils.startsWith(smsMarketingCustom.getLast(), lt)) {
                    String last = StringUtils.substringAfter(smsMarketingCustom.getLast(), lt);
                    entityWrapper.lt("last_meal_date", last);
                }

                //消费金额
                if (smsMarketingCustom.getAmount() != null && StringUtils.startsWith(smsMarketingCustom.getAmount(), gt)) {
                    String amount = StringUtils.substringAfter(smsMarketingCustom.getAmount(), gt);
                    entityWrapper.gt("amount", amount);
                } else if (smsMarketingCustom.getLast() != null && StringUtils.startsWith(smsMarketingCustom.getAmount(), lt)) {
                    String amount = StringUtils.substringAfter(smsMarketingCustom.getAmount(), lt);
                    entityWrapper.lt("amount", amount);
                }
            }
            List<VipStatistics> vipStatistics = vipStatisticsService.selectList(entityWrapper);
            Set<Integer> vipIds = Sets.newHashSet();
            vipStatistics.forEach(statistics -> {
                vipIds.add(statistics.getVipId());
            });
            if (!CollectionUtils.isEmpty(vipIds)) {
                vips = vipService.selectBatchIds(vipIds);
            }
        }

        //获取所有手机号码
        Set<String> phones = Sets.newHashSet();
        vips.forEach(vip -> {
            phones.add(vip.getVipPhone());
        });
        smsMarketing.setTargetPhones(StringUtils.join(phones, ","));
        String content = StringUtils.replace(smsMarketing.getContent(), "{#var}", "");
        log.info("发送手机号码:{}", phones);
        int size;
        if (StringUtils.isEmpty(content)) {
            size = phones.size();
        } else {
            int contentSize = content.length();
            if (StringUtils.isNotEmpty(smsMarketing.getVariable())) {
                String[] vars = smsMarketing.getVariable().split(",");
                for (String var : vars) {
                    if (StringUtils.equals(var, "vipFamilyName")) {
                        contentSize += 2;
                    } else if (StringUtils.equals(var, "vipName")) {
                        contentSize += 3;
                    } else if (StringUtils.equals(var, "vipSex")) {
                        contentSize += 2;
                    } else if (StringUtils.equals(var, "businessName")) {
                        Business business = businessService.selectById(smsMarketing.getBusinessId());
                        contentSize += business.getBusinessName().length();
                    }
                }
            }
            //70 67
            int smsSize = 1;
            if (contentSize <= 70) {
                smsSize = 1;
            } else {
                smsSize = 1;
                smsSize = smsSize + (contentSize - 70) / 67;
                if ((contentSize - 70) % 67 != 0) {
                    smsSize = smsSize + 1;
                }
            }

            size = phones.size() * smsSize;
            smsMarketing.setSmsNum(size);
            smsMarketing.setNum(size);
        }

        return size;
    }

    public Map calcTargetPhoneByAppUser(SmsMarkingDTO smsMarkingDTO) {
        log.info("计算目标手机号码:{}", smsMarkingDTO);
//        if(StringUtils.isEmpty(smsMarketing.getVipClassId()) && (StringUtils.isEmpty(smsMarketing.getCustom()) || StringUtils.equalsIgnoreCase(smsMarketing.getCustom(),"{}"))){//没有传递客户价值和自定义客户类型返回0
//            return 0;
//        }
        String custom = smsMarkingDTO.getCustom();
        String vipValueId = smsMarkingDTO.getVipValueId();
        List<Vip> vips = Lists.newArrayList();
        log.info("vipValueId:{}", vipValueId);
        if (StringUtils.isNotBlank(vipValueId)) {
            if (StringUtils.equals("0", vipValueId)) {
                vips = vipService.selectList(new EntityWrapper<Vip>().eq("business_id", smsMarkingDTO.getBusinessId()));
            } else {
                vips = vipService.selectList(new EntityWrapper<Vip>().eq("business_id", smsMarkingDTO.getBusinessId()).in("vip_value_id", Arrays.asList(vipValueId.split(","))));
            }
            log.info("vips:{}", vips);
        } else if (StringUtils.isNotBlank(custom)) {
            EntityWrapper<VipStatistics> entityWrapper = (EntityWrapper<VipStatistics>) new EntityWrapper<VipStatistics>()
                    .eq("business_id", smsMarkingDTO.getBusinessId());
            SmsMarketingCustom smsMarketingCustom = JsonUtils.jsonStr2Obj(custom, SmsMarketingCustom.class);
            if (smsMarketingCustom != null) {
                String gt = "大于";
                String lt = "小于";
                //预定次数
                if (smsMarketingCustom.getResv() != null && StringUtils.startsWith(smsMarketingCustom.getResv(), gt)) {
                    String resv = StringUtils.substringAfter(smsMarketingCustom.getResv(), gt);
                    entityWrapper.gt("resv_batch_count", resv);
                } else if (smsMarketingCustom.getResv() != null && StringUtils.startsWith(smsMarketingCustom.getResv(), lt)) {
                    String resv = StringUtils.substringAfter(smsMarketingCustom.getResv(), lt);
                    entityWrapper.lt("resv_batch_count", resv);
                }

                //就餐次数
                if (smsMarketingCustom.getEat() != null && StringUtils.startsWith(smsMarketingCustom.getEat(), gt)) {
                    String eat = StringUtils.substringAfter(smsMarketingCustom.getEat(), gt);
                    entityWrapper.gt("meal_batch_count", eat);
                } else if (smsMarketingCustom.getEat() != null && StringUtils.startsWith(smsMarketingCustom.getEat(), lt)) {
                    String eat = StringUtils.substringAfter(smsMarketingCustom.getEat(), lt);
                    entityWrapper.lt("meal_batch_count", eat);
                }

                //最近预定时间
                if (smsMarketingCustom.getLast() != null && StringUtils.startsWith(smsMarketingCustom.getLast(), gt)) {
                    String last = StringUtils.substringAfter(smsMarketingCustom.getLast(), gt);
                    entityWrapper.gt("last_meal_date", last);
                } else if (smsMarketingCustom.getLast() != null && StringUtils.startsWith(smsMarketingCustom.getLast(), lt)) {
                    String last = StringUtils.substringAfter(smsMarketingCustom.getLast(), lt);
                    entityWrapper.lt("last_meal_date", last);
                }

                //消费金额
                if (smsMarketingCustom.getAmount() != null && StringUtils.startsWith(smsMarketingCustom.getAmount(), gt)) {
                    String amount = StringUtils.substringAfter(smsMarketingCustom.getAmount(), gt);
                    entityWrapper.gt("amount", amount);
                } else if (smsMarketingCustom.getLast() != null && StringUtils.startsWith(smsMarketingCustom.getAmount(), lt)) {
                    String amount = StringUtils.substringAfter(smsMarketingCustom.getAmount(), lt);
                    entityWrapper.lt("amount", amount);
                }
            }
            List<VipStatistics> vipStatistics = vipStatisticsService.selectList(entityWrapper);
            Set<Integer> vipIds = Sets.newHashSet();
            vipStatistics.forEach(statistics -> {
                vipIds.add(statistics.getVipId());
            });
            Integer appUserId = smsMarkingDTO.getAppUserId();
            if (!CollectionUtils.isEmpty(vipIds) && appUserId != null && appUserId != 0) {
                for (Integer vipId : vipIds) {
                    Vip vip = vipService.selectOne(new EntityWrapper<Vip>().eq("id", vipId)
                            .eq("app_user_id", appUserId));
                    if (vip != null) {
                        vips.add(vip);
                    }
                }
            } else if (!CollectionUtils.isEmpty(vipIds)) {
                vips = vipService.selectBatchIds(vipIds);
            }
        }

        //获取所有手机号码
        Set<String> phones = Sets.newHashSet();
        vips.forEach(vip -> {
            phones.add(vip.getVipPhone());
        });
        smsMarkingDTO.setTargetPhones(StringUtils.join(phones, ","));
        String content = StringUtils.replace(smsMarkingDTO.getContent(), "{#var}", "");
        log.info("发送手机号码:{}", phones);
        int size;
        if (StringUtils.isEmpty(content)) {
            size = phones.size();
        } else {
            int contentSize = content.length();
            if (StringUtils.isNotEmpty(smsMarkingDTO.getVariable())) {
                String[] vars = smsMarkingDTO.getVariable().split(",");
                for (String var : vars) {
                    if (StringUtils.equals(var, "vipFamilyName")) {
                        contentSize += 2;
                    } else if (StringUtils.equals(var, "vipName")) {
                        contentSize += 3;
                    } else if (StringUtils.equals(var, "vipSex")) {
                        contentSize += 2;
                    } else if (StringUtils.equals(var, "businessName")) {
                        Business business = businessService.selectById(smsMarkingDTO.getBusinessId());
                        contentSize += business.getBusinessName().length();
                    }
                }
            }
            //70 67
            int smsSize = 1;
            if (contentSize <= 70) {
                smsSize = 1;
            } else {
                smsSize = 1;
                smsSize = smsSize + (contentSize - 70) / 67;
                if ((contentSize - 70) % 67 != 0) {
                    smsSize = smsSize + 1;
                }
            }

            size = phones.size() * smsSize;
            smsMarkingDTO.setSmsNum(size);
            smsMarkingDTO.setNum(size);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("targetPhones", smsMarkingDTO.getTargetPhones());
        map.put("count", size);
        return map;
    }

    public Tip sendMarketingSms(Integer id, Integer businessId) {
        Wrapper<SmsMarketing> wrapper = new EntityWrapper<SmsMarketing>().eq("id", id)
                .eq("business_id", businessId)
                .eq("status", 3)
                .le("timer", new Date());
        SmsMarketing smsMarketing = smsMarketingService.selectOne(wrapper);
        if (smsMarketing == null) {
            log.info("短信不存在:params(id:{},businessId:{})", id, businessId);
            return new SuccessTip(500, "短信不存在");
        }
        return sendMarketingSms(smsMarketing);
    }


    public Tip sendMarketingSms(SmsMarketing smsMarketing) {
        SmsSendResDTO smsSendResDTO = null;
        try {
            if (smsMarketing.getVersion() == 1) {
                return new SuccessTip(200, "正在发送");
            }
            smsMarketing.setVersion(1);
            boolean update = smsMarketingService.update(
                    smsMarketing,
                    new EntityWrapper<SmsMarketing>()
                            .eq("id", smsMarketing.getId())
                            .eq("business_id", smsMarketing.getBusinessId())
                            .eq("version", 0)
            );
            if (!update) {
                return new SuccessTip(200, "正在发送");
            }
            String targetPhones = smsMarketing.getTargetPhones();
            if (StringUtils.isBlank(targetPhones)) {
                return new SuccessTip(500, "目标手机号码为空");
            }
            ClMsgParam clMsgParam = new ClMsgParam();
            String[] phones = targetPhones.split(",");
            List<String> sendPhones = Arrays.asList(phones).stream()
                    .filter(phone -> StringUtils.isNotEmpty(phone) && phone.length() == 11)
                    .collect(Collectors.toList());
            clMsgParam.setPhone(sendPhones);
            clMsgParam.setMsg(smsMarketing.getContent());

            smsSendResDTO = smsFeign.sendBatchmarkMsg(clMsgParam);
            log.info("发送短信返回信息:{}", smsSendResDTO);
        } catch (Exception e) {
            log.error("发送短信失败:{}", e.getMessage());
        } finally {
            //version 设置为0更新短信记录,防止短信发送失败无法重发
            smsMarketing.setVersion(0);
            smsMarketingService.update(
                    smsMarketing,
                    new EntityWrapper<SmsMarketing>()
                            .eq("id", smsMarketing.getId())
                            .eq("business_id", smsMarketing.getBusinessId())
                            .eq("version", 1)
            );
        }
        if (smsSendResDTO == null) {
            return new ErrorTip(400, "发送失败");
        }
        if (StringUtils.equals(smsSendResDTO.getCode(), "0")) {
            smsMarketing.setStatus("4");
            smsMarketing.setSendType(1);
            smsMarketingService.updateById(smsMarketing);
        }
        return StringUtils.equals(smsSendResDTO.getCode(), "0") ? new SuccessTip(200, "发送成功") : new ErrorTip(400, "发送失败");
    }

    /**
     * 发送营销短信
     *
     * @param smsMarketing
     * @return
     */
    public SuccessTip sendMarketingSmsV2(SmsMarketing smsMarketing) {
        String targetPhones = smsMarketing.getTargetPhones();
        if (StringUtils.isBlank(targetPhones)) {
            return new SuccessTip(500, "目标手机号码为空");
        }
        String[] phones = targetPhones.split(",");
        List<String> list = Arrays.asList(phones);
        int limit = countStep(phones.length);
        //查询短信分批信息
        List<SmsMarketingBatch> smsMarketingBatches = smsMarketingBatchService.selectList(
                new EntityWrapper<SmsMarketingBatch>()
                        .eq("sms_marketing_id", smsMarketing.getId()));
        //没有进行过分批
        if (CollectionUtils.isEmpty(smsMarketingBatches)) {
            ///分组生成分批数据
            Stream.iterate(0, n -> n + 1)
                    .limit(limit)
                    .parallel()
                    .map(n -> {
                                List<String> sendPhones = list.stream()
                                        .skip(n * MAX_NUMBER)
                                        .limit(MAX_NUMBER)
                                        .parallel()
                                        .collect(Collectors.toList());
                                SmsMarketingBatch marketingBatch = new SmsMarketingBatch();
                                marketingBatch.setStatus(0);
                                marketingBatch.setTargetPhones(StringUtils.join(sendPhones, ","));
                                marketingBatch.setSmsMarketingId(smsMarketing.getId());
                                //添加分批数据
                                smsMarketingBatchService.insert(marketingBatch);
                                return sendPhones;
                            }
                    );
            smsMarketingBatches = smsMarketingBatchService.selectList(
                    new EntityWrapper<SmsMarketingBatch>()
                            .eq("sms_marketing_id", smsMarketing.getId()));
        }

        Map<Object, Object> map = Maps.newHashMap();
        List<String> errors = Lists.newArrayList();
        List<Object> errorPhoneList = Lists.newArrayList();
        String pattern = "^[1][34578][0-9]{9}$";
        Pattern regex = Pattern.compile(pattern);
        //遍历分批数据
        smsMarketingBatches.stream()
                //过滤已经完成的
                .filter(smsMarketingBatch -> !StringUtils.equals(smsMarketing.getStatus(), "1"))
                .forEach(smsMarketingBatch -> {
                    ClMsgParam clMsgParam = new ClMsgParam();
                    List<String> errorPhones = Arrays.stream(targetPhones.split(","))
                            .filter(phone -> !regex.matcher(phone).find())
                            .collect(Collectors.toList());
                    if (CollectionUtils.isNotEmpty(errorPhones)) {
                        errorPhoneList.addAll(errorPhones);
                    }
                    List<String> sendPhones = Arrays.stream(targetPhones.split(","))
                            .filter(phone -> regex.matcher(phone).find())
                            .collect(Collectors.toList());
                    clMsgParam.setPhone(sendPhones);
                    clMsgParam.setMsg(smsMarketing.getContent());
                    clMsgParam.setSendtime(DateFormatUtils.format(smsMarketing.getTimer(), "yyyyMMddHHmm"));

                    SmsSendResDTO smsSendResDTO = null;

                    try {
                        smsSendResDTO = smsFeign.sendBatchmarkMsg(clMsgParam);
                    } catch (Exception e) {
                        log.error("短信发送失败：{}", e.getMessage(), e);
                    }
                    if (smsSendResDTO == null) {
                        map.put("flag", false);
                        if (!errors.contains("短信模块异常")) {
                            errors.add("短信模块异常");
                        }
                        return;
                    }
                    if (StringUtils.equals(smsSendResDTO.getCode(), "0")) {
                        smsMarketingBatch.setStatus(1);
                        smsMarketingBatch.setMsgId(smsSendResDTO.getMsgId().toString());
                    } else {
                        map.put("flag", false);
                        if (!errors.contains(smsSendResDTO.getErrorMsg())) {
                            errors.add(smsSendResDTO.getErrorMsg());
                        }
                        map.put("errorMsg", smsSendResDTO.getErrorMsg());
                        smsMarketingBatch.setStatus(2);
                        if (smsSendResDTO.getMsgId() != null) {
                            smsMarketingBatch.setMsgId(smsSendResDTO.getMsgId().toString());
                        }
                    }
                    smsMarketingBatchService.updateById(smsMarketingBatch);
                });

        if (CollectionUtils.isNotEmpty(errorPhoneList)) {
            errors.add("错误的手机号码有：" + StringUtils.join(errorPhoneList, ","));
        }

        if (!(boolean) map.get("flag")) {
            smsMarketing.setStatus("5");
        } else {
            smsMarketing.setStatus("4");
            smsMarketing.setSendType(1);
        }

        smsMarketing.setErrorMsg(StringUtils.join(errors, ","));

        boolean result = smsMarketingService.updateById(smsMarketing);

        log.debug("短信发送处理结果：{}", result);


        if (result) {
            return new SuccessTip(200, "发送成功");
        } else {
            return new SuccessTip(500, smsMarketing.getErrorMsg());
        }
    }

    private static Integer countStep(Integer size) {
        return (size + MAX_NUMBER - 1) / MAX_NUMBER;
    }
}
