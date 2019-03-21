package com.zhidianfan.pig.user.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.zhidianfan.pig.common.dto.WxSinglePushDTO;
import com.zhidianfan.pig.common.util.JsonUtils;
import com.zhidianfan.pig.user.dao.entity.Agent;
import com.zhidianfan.pig.user.dao.entity.YdUser;
import com.zhidianfan.pig.user.dao.entity.YdUserRole;
import com.zhidianfan.pig.user.dao.service.IAgentService;
import com.zhidianfan.pig.user.dao.service.IYdUserRoleService;
import com.zhidianfan.pig.user.dao.service.IYdUserService;
import com.zhidianfan.pig.user.dto.AgentDTO;
import com.zhidianfan.pig.user.dto.PageDTO;
import com.zhidianfan.pig.user.dto.SuccessTip;
import com.zhidianfan.pig.user.dto.Tip;
import com.zhidianfan.pig.user.feign.SysDictFeign;
import com.zhidianfan.pig.user.feign.WxPushFeign;
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
public class AgentService {

    @Autowired
    private IAgentService agentService;

    @Autowired
    private SysDictFeign sysDictFeign;

    @Autowired
    private IYdUserService ydUserService;

    @Autowired
    private IYdUserRoleService ydUserRoleService;

    @Autowired
    private WxPushFeign wxPushFeign;

    /**
     * 经销商列表
     * @param agentDTO
     * @return
     */
    public PageDTO getAgent(AgentDTO agentDTO){
        Page<Agent> agents = agentService.selectPage(new Page(agentDTO.getPage(),agentDTO.getRows()),new EntityWrapper<Agent>().eq(null != agentDTO.getProvinceId(),"province_id",agentDTO.getProvinceId())
                .eq(null != agentDTO.getCityId(),"city_id",agentDTO.getCityId()).eq(null != agentDTO.getAreaId(),"area_id",agentDTO.getAreaId())
                .eq(null != agentDTO.getStatus(),"status",agentDTO.getStatus()).eq(null != agentDTO.getType(),"type",agentDTO.getType())
                .like(StringUtils.isNotBlank(agentDTO.getAgentName()),"agent_name",agentDTO.getAgentName())
                .orderBy(true,"created_at",false));
        List<Map<String,Object>> list = new ArrayList<>();
        for(Agent agent : agents.getRecords()){
            Map<String,Object> agentMap = JsonUtils.object2Map(agent);
            agentMap.put("typeName",sysDictFeign.getDict(String.valueOf(agent.getType()),"agent_type"));
            agentMap.put("statusName",sysDictFeign.getDict(String.valueOf(agent.getStatus()),"agent_status"));
            agentMap.put("agentLevelName",sysDictFeign.getDict(String.valueOf(agent.getStatus()),"agent_level"));
            agentMap.put("id",String.valueOf(agent.getId()));
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
     * 经销商修改
     * @param agentDTO
     * @return
     */
    public Tip updateAgent(AgentDTO agentDTO){
        try {
            Agent agent = new Agent();
            BeanUtils.copyProperties(agentDTO,agent);
            agent.setUpdatedAt(new Date());
            boolean isApply = false;
            if("0".equals(String.valueOf(agentDTO.getOldStatus())) && !agentDTO.getOldStatus().equals(agentDTO.getStatus())){
                agent.setReviewTime(new Date());
                isApply = true;
            }
            boolean updateStatus = agentService.update(agent,new EntityWrapper<Agent>().eq("id",agentDTO.getId()));
            if(updateStatus){
                if(isApply && "1".equals(String.valueOf(agentDTO.getStatus()))){
                    YdUser ydUser = new YdUser();
                    ydUser.setClientId(sysDictFeign.getDict("agent","user_type"));
                    ydUser.setUsername(agent.getUsername());
                    ydUser.setPassword(agent.getPassword());
                    ydUser.setPhone(agent.getAgentPhone());
                    ydUser.setCreateTime(new Date());
                    boolean userStatus = ydUserService.insert(ydUser);
                    YdUserRole ydUserRole = new YdUserRole();
                    ydUserRole.setUsername(agent.getUsername());
                    ydUserRole.setRoleName("admin");
                    boolean userRoleStatu = ydUserRoleService.insert(ydUserRole);
                    if(userStatus && userRoleStatu){
                        putSuccessNotice(agent.getOpenId(),new Date(),agent.getAgentName(),"经销商",true);
                        return new SuccessTip(200,"修改成功",null);
                    }else {
                        return new SuccessTip(4001,"修改失败",null);
                    }
                }else if(isApply && "2".equals(String.valueOf(agentDTO.getStatus()))){
                    putSuccessNotice(agent.getOpenId(),new Date(),agent.getAgentName(),"经销商",false);
                    return new SuccessTip(200,"修改成功",null);
                }
                return new SuccessTip(200,"修改成功",null);
            } else {
                return new SuccessTip(4001,"修改失败",null);
            }
        }catch (Exception e){
            e.printStackTrace();
            return new SuccessTip(4001,"修改失败",null);
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
            return new SuccessTip(4001,"账号已存在",null);
        }else {
            boolean putStatus = agentService.insert(agent);
            if(putStatus){
                String openid = sysDictFeign.getDict("agent_openid","agent_openid");
                putApplyNotice(openid,agent.getCreatedAt(),agent.getAgentName(),"经销商");
                return new SuccessTip(200,"添加成功",null);
            }else {
                return new SuccessTip(4001,"添加失败",null);
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
     * @param ydUser
     * @return
     */
    public Tip loginCheck(YdUser ydUser){
        YdUser ydUser1 = ydUserService.selectOne(new EntityWrapper<YdUser>().eq("username",ydUser.getUsername())
        .eq("password",ydUser.getPassword()).eq("client_id",ydUser.getClientId()).eq("del_flag",'0'));
        if(null != ydUser1){
            Agent agent = agentService.selectOne(new EntityWrapper<Agent>().eq("username",ydUser.getUsername()));
            return new SuccessTip(200,"登录成功",agent);
        }else {
            Agent agent = agentService.selectOne(new EntityWrapper<Agent>().eq("username",ydUser.getUsername()).eq("status",0));
            if(agent != null){
                return new SuccessTip(4001,"经销商还在审核中,有问题请联系管理员!",null);
            }else {
                return new SuccessTip(4001,"用户名或密码错误!",null);
            }
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
            return new SuccessTip(4001,"合同编号已存在",null);
        }else {
            return new SuccessTip(200,"",null);
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
        System.out.print(tip);
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
        System.out.print(tip);
    }

}
