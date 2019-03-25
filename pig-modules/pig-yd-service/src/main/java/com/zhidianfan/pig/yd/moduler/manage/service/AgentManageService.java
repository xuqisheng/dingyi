package com.zhidianfan.pig.yd.moduler.manage.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.zhidianfan.pig.common.constant.SuccessTip;
import com.zhidianfan.pig.common.constant.Tip;
import com.zhidianfan.pig.common.dto.WxSinglePushDTO;
import com.zhidianfan.pig.common.util.JsonUtils;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.Agent;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.SnCode;
import com.zhidianfan.pig.yd.moduler.common.service.IAgentService;
import com.zhidianfan.pig.yd.moduler.common.service.ISnCodeService;
import com.zhidianfan.pig.yd.moduler.manage.dto.AgentDTO;
import com.zhidianfan.pig.yd.moduler.manage.dto.PageDTO;
import com.zhidianfan.pig.yd.moduler.manage.dto.UserDTO;
import com.zhidianfan.pig.yd.moduler.manage.feign.AuthFeign;
import com.zhidianfan.pig.yd.moduler.manage.feign.SysDictFeign;
import com.zhidianfan.pig.yd.moduler.manage.feign.WxPushFeign;
import com.zhidianfan.pig.yd.moduler.sms.service.rmi.SmsFeign;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Author qqx
 * @Description
 * @Date Create in 2018/11/2
 * @Modified By:
 */
@Service
public class AgentManageService {

    @Autowired
    private IAgentService agentService;

    @Autowired
    private SysDictFeign sysDictFeign;

    @Autowired
    private WxPushFeign wxPushFeign;

    @Autowired
    private AuthFeign authFeign;

    @Autowired
    private ISnCodeService snCodeService;

    @Autowired
    private SmsFeign smsFeign;

    /**
     * 经销商列表
     * @param agentDTO
     * @return
     */
    public PageDTO getAgent(AgentDTO agentDTO){
        Page<Agent> agents = agentService.selectPage(new Page(agentDTO.getPage(),agentDTO.getRows()),new EntityWrapper<Agent>().eq(null != agentDTO.getProvinceId(),"province_id",agentDTO.getProvinceId())
                .eq(null != agentDTO.getCityId(),"city_id",agentDTO.getCityId()).eq(null != agentDTO.getDevice(),"device",agentDTO.getDevice())
                .in(StringUtils.isNotBlank(agentDTO.getStatus()),"status",agentDTO.getStatus()).eq(null != agentDTO.getType(),"type",agentDTO.getType())
                .like(StringUtils.isNotBlank(agentDTO.getAgentName()),"agent_name",agentDTO.getAgentName())
                .orderBy(true,"status",true)
                .orderBy(true,"created_at",false));
        List<Map<String,Object>> list = new ArrayList<>();
        for(Agent agent : agents.getRecords()){
            Map<String,Object> agentMap = JsonUtils.object2Map(agent);
            agentMap.put("typeName",sysDictFeign.getDict(String.valueOf(agent.getType()),"agent_type"));
            agentMap.put("statusName",sysDictFeign.getDict(String.valueOf(agent.getStatus()),"agent_status"));
            agentMap.put("agentLevelName",sysDictFeign.getDict(String.valueOf(agent.getAgentLevel()),"agent_level"));
            agentMap.put("id",String.valueOf(agent.getId()));
            List<SnCode> snCodeList = snCodeService.selectList(new EntityWrapper<SnCode>().eq("agent_id",agent.getId()));
            agentMap.put("deviceCount",snCodeList.size());
            list.add(agentMap);
        }
        PageDTO pageDTO = new PageDTO();
        pageDTO.setPage(agents.getCurrent());
        pageDTO.setTotal(agents.getPages());
        pageDTO.setRecords(agents.getTotal());
        pageDTO.setRows(list);
        return pageDTO;
    }

    /**
     * 未审核数量
     * @return
     */
    public Map<String, Integer> getAgentCount(AgentDTO agentDTO){
        Map<String,Integer> result = new HashMap<>();
        List<Agent> agentList = agentService.selectList(new EntityWrapper<Agent>().eq("status",'0').eq(null != agentDTO.getDevice(),"device",agentDTO.getDevice())
        );
        result.put("count",agentList.size());
        return result;
    }

    /**
     * 经销商修改
     * @param agentDTO
     * @return
     */
    public Tip updateAgent(AgentDTO agentDTO){
        try {
            if(null == agentDTO.getId()){
                Tip tip = putAgent(agentDTO);
                return tip;
            }else {
                Agent agent = new Agent();
                BeanUtils.copyProperties(agentDTO,agent);
                agent.setUpdatedAt(new Date());
                boolean isApply = false;
                if("0".equals(String.valueOf(agentDTO.getOldStatus())) && !agentDTO.getOldStatus().equals(agentDTO.getStatus())){
                    agent.setReviewTime(new Date());
                    isApply = true;
                }
                boolean updateStatus = agentService.updateById(agent);
                if(updateStatus){
                    if(isApply && "1".equals(String.valueOf(agentDTO.getStatus()))){
                        UserDTO userDTO = new UserDTO();
                        userDTO.setClientId(sysDictFeign.getDict("agent","user_type"));
                        userDTO.setUsername(agentDTO.getUsername());
                        userDTO.setPassword(agentDTO.getPassword());
                        userDTO.setPhone(agentDTO.getPhoneNum());
                        Tip tip = authFeign.createUserAdmin(userDTO);
                        if(tip.getCode() == 200){
                            return new SuccessTip(200,"修改成功");
                        }else {
                            return new SuccessTip(200,"修改成功");
                        }
                    }else if(isApply && "2".equals(String.valueOf(agentDTO.getStatus()))){
                        putSuccessNotice(agent.getOpenId(),new Date(),agent.getAgentName(),"经销商",false);
                        return new SuccessTip(200,"修改成功");
                    }
                    return new SuccessTip(200,"修改成功");
                } else {
                    return new SuccessTip(4001,"修改失败");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return new SuccessTip(4001,"修改失败");
        }
    }

    /**
     * 经销商审核
     * @param agentDTO
     * @return
     */
    public Tip putAgent(AgentDTO agentDTO){
        Agent agent = new Agent();
        BeanUtils.copyProperties(agentDTO,agent);
        agent.setCreatedAt(new Date());
        List<Agent> agents = agentService.selectList(new EntityWrapper<Agent>().eq("username",agentDTO.getUsername()));
        if(agents.size() > 0){
            return new SuccessTip(4001,"账号已存在");
        }else {
            boolean putStatus = agentService.insert(agent);
            if(putStatus){
                String openid = sysDictFeign.getDict("agent_openid","agent_openid");
                putApplyNotice(openid,agent.getCreatedAt(),agent.getAgentName(),"经销商");

                //todo 经销商注册发送短信审核 发送给胡鹏程
                String phone = "13028939980";
                String msg= "叮叮,有渠道提交申请请求，请及时处理。登录查看详情:   manager.zhidianfan.com";
                smsFeign.sendNormalMsg(phone,msg);

                return new SuccessTip(200,"添加成功");
            }else {
                return new SuccessTip(4001,"添加失败");
            }
        }
    }

    /**
     * 经销商信息
     * @param id
     * @return
     */
    public Agent getAgentById(Long id){
        Agent agent = agentService.selectById(id);
        return agent;
    }

    /**
     * 登录
     * @param username
     * @param password
     * @param clientId
     * @return
     */
    public Tip loginCheck(String username,String password,String clientId){
        Agent agent = agentService.selectOne(new EntityWrapper<Agent>().eq("username",username).eq("password",password));
        if(null != agent && "1".equals(agent.getStatus())){
            SuccessTip.SUCCESS_TIP.setContent(agent);
            return SuccessTip.SUCCESS_TIP;
        }else if(agent != null && "0".equals(agent.getStatus())){
             return new SuccessTip(4001,"经销商还在审核中,有问题请联系管理员!");
        }else if(agent != null && "3".equals(agent.getStatus())){
            return new SuccessTip(4001,"账号已停用!");
        }else {
            return new SuccessTip(4001,"账号密码输入错误!");
        }
    }

    /**
     * 合同编号校验
     * @param contractNo
     * @return
     */
    public Tip contractNoCheck(String contractNo){
        List<Agent> agents = agentService.selectList(new EntityWrapper<Agent>().eq("contract_no",contractNo));
        if(agents.size() > 0){
            return new SuccessTip(4001,"合同编号已存在");
        }else {
            return new SuccessTip(200,"");
        }
    }

    /**
     * 发送微信公众号消息  审核
     * @param openid
     * @param createdAt
     * @param name
     * @param type
     */
    public void putApplyNotice(String openid,Date createdAt,String name,String type){
        WxSinglePushDTO wxSinglePushDTO = new WxSinglePushDTO();
        wxSinglePushDTO.setOpenId(openid);
        wxSinglePushDTO.setTemplateId("9BzVSjN1Sx6yA0TApfWQim9p3oQsF1Q6J3dTJjy_WnU");
        Map<String,Object> params = new HashMap<>();
        Map<String,Object> params1 = new HashMap<>();
        Map<String,Object> params2 = new HashMap<>();
        Map<String,Object> params3 = new HashMap<>();
        Map<String,Object> params4 = new HashMap<>();
        params1.put("value","您好！有新发布的"+ type +"需要您审核");
        params.put("first",params1);
        params2.put("value",type + "审核");
        params.put("keyword1",params2);
        params3.put("value",DateFormatUtils.format(createdAt,"yyyy-MM-dd HH:mm:ss"));
        params.put("keyword2",params3);
        params4.put("value",name+"发布的"+ type +"需要您审核");
        params.put("remark",params4);
        wxSinglePushDTO.setParams(params);
        Tip tip = wxPushFeign.pushToSingle(wxSinglePushDTO);
    }

    /**
     * 发送微信公众号消息  申请
     * @param openid
     * @param createdAt
     * @param name
     * @param type
     * @param pass
     */
    public void putSuccessNotice(String openid,Date createdAt,String name,String type,boolean pass){
        WxSinglePushDTO wxSinglePushDTO = new WxSinglePushDTO();
        wxSinglePushDTO.setOpenId(openid);
        wxSinglePushDTO.setTemplateId("MRn_KGx5VF_bFxOnxfTB7rd_ev82dFimctVrWwi2Shg");
        Map<String,Object> params = new HashMap<>();
        Map<String,Object> params1 = new HashMap<>();
        Map<String,Object> params2 = new HashMap<>();
        Map<String,Object> params3 = new HashMap<>();
        Map<String,Object> params4 = new HashMap<>();
        String concent = pass?"已经通过审核":"已被拒绝";
        String concent1 = pass?"您提交的申请已经通过审核，谢谢您使用!":"您提交的申请已被拒绝,详情请联系管理员!";
        params1.put("value","您好！您提交的"+ type +"申请" + concent);
        params.put("first",params1);
        params2.put("value",type + "审核");
        params.put("keyword1",params2);
        params3.put("value", DateFormatUtils.format(createdAt,"yyyy-MM-dd HH:mm:ss"));
        params.put("keyword2",params3);
        params4.put("value",concent1);
        params.put("remark",params4);
        wxSinglePushDTO.setParams(params);
        Tip tip = wxPushFeign.pushToSingle(wxSinglePushDTO);
    }

}
