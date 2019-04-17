package com.zhidianfan.pig.yd.moduler.manage.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.zhidianfan.pig.common.constant.ErrorTip;
import com.zhidianfan.pig.common.constant.SuccessTip;
import com.zhidianfan.pig.common.constant.Tip;
import com.zhidianfan.pig.common.util.JsonUtils;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.Agent;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.Business;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.SnCode;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.SnCodeLog;
import com.zhidianfan.pig.yd.moduler.common.service.IAgentService;
import com.zhidianfan.pig.yd.moduler.common.service.IBusinessService;
import com.zhidianfan.pig.yd.moduler.common.service.ISnCodeLogService;
import com.zhidianfan.pig.yd.moduler.common.service.ISnCodeService;
import com.zhidianfan.pig.yd.moduler.manage.dto.PageDTO;
import com.zhidianfan.pig.yd.moduler.manage.dto.SnCodeDTO;
import com.zhidianfan.pig.yd.moduler.manage.feign.SysDictFeign;
import com.zhidianfan.pig.yd.utils.ExcelUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Part;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author qqx
 * @Description
 * @Date Create in 2018/11/21
 * @Modified By:
 */
@Service
public class SnCodeManageService {

    @Autowired
    private ISnCodeService snCodeService;

    @Autowired
    private SysDictFeign sysDictFeign;

    @Autowired
    private ISnCodeLogService snCodeLogService;

    @Autowired
    private IAgentService agentService;

    @Autowired
    private IBusinessService businessService;

    private int batch = 50;

    /**
     * SN码列表 分页
     * @param snCodeDTO
     * @return
     */
    public PageDTO getSnCodePage(SnCodeDTO snCodeDTO){
        Page<SnCode> snCodePage = snCodeService.selectPage(new Page<>(snCodeDTO.getPage(),snCodeDTO.getRows()),new EntityWrapper<SnCode>()
                .eq(null != snCodeDTO.getBusinessId(),"business_id",snCodeDTO.getBusinessId())
                .eq(null != snCodeDTO.getAgentId(),"agent_id",snCodeDTO.getAgentId())
                .in(StringUtils.isNotBlank(snCodeDTO.getStatus()),"status",snCodeDTO.getStatus())
                .eq(StringUtils.isNotBlank(snCodeDTO.getAgentName1()),"agent_name",snCodeDTO.getAgentName1())
                .like(StringUtils.isNotBlank(snCodeDTO.getAgentName()),"agent_name",snCodeDTO.getAgentName())
                .like(StringUtils.isNotBlank(snCodeDTO.getBusinessName()),"business_name",snCodeDTO.getBusinessName())
                .like(StringUtils.isNotBlank(snCodeDTO.getCode()),"code",snCodeDTO.getCode())
                .ge(StringUtils.isNotBlank(snCodeDTO.getStartTime()),"created_at",snCodeDTO.getStartTime())
                .le(StringUtils.isNotBlank(snCodeDTO.getEndTime()),"created_at",snCodeDTO.getEndTime())
                .orderBy(true,snCodeDTO.getSidx(),"asc".equals(snCodeDTO.getSord()))
        );
        List<Map<String,Object>> list = new ArrayList<>();
        for(SnCode snCode : snCodePage.getRecords()){
            Map<String,Object> snCodeMap = JsonUtils.object2Map(snCode);
            snCodeMap.put("statusName",sysDictFeign.getDict(String.valueOf(snCode.getStatus()),"sncode_status"));
            if(null == snCode.getAgentId() || snCode.getAgentId() == 0){
                snCodeMap.put("agentStatusName","1".equals(String.valueOf(snCode.getStatus())) || "2".equals(String.valueOf(snCode.getStatus()))?"库存" : "报废");
            }else {
                snCodeMap.put("agentStatusName","1".equals(String.valueOf(snCode.getStatus())) || "2".equals(String.valueOf(snCode.getStatus()))?"已激活" : "维修中");
            }            snCodeMap.put("id",String.valueOf(snCode.getId()));
            if(null != snCodeDTO.getAgentId() && !"0".equals(String.valueOf(snCodeDTO.getAgentId()))){
                snCodeMap.put("agentName",agentService.selectOne(new EntityWrapper<Agent>().eq("id",snCodeDTO.getAgentId())).getAgentName());
            }
            list.add(snCodeMap);
        }
        PageDTO pageDTO = new PageDTO();
        pageDTO.setPage(snCodePage.getCurrent());
        pageDTO.setTotal(snCodePage.getPages());
        pageDTO.setRecords(snCodePage.getTotal());
        pageDTO.setRows(list);
        return pageDTO;
    }

    /**
     * SN码列表
     * @param snCodeDTO
     * @return
     */
    public List<Map<String,Object>> getSnCode(SnCodeDTO snCodeDTO){
        List<SnCode> snCodeList = snCodeService.selectList(new EntityWrapper<SnCode>()
                .eq(null != snCodeDTO.getBusinessId(),"business_id",snCodeDTO.getBusinessId())
                .eq(null != snCodeDTO.getAgentId(),"agent_id",snCodeDTO.getAgentId())
                .in(StringUtils.isNotBlank(snCodeDTO.getStatus()),"status",snCodeDTO.getStatus())
                .like(StringUtils.isNotBlank(snCodeDTO.getAgentName()),"agent_name",snCodeDTO.getAgentName())
                .like(StringUtils.isNotBlank(snCodeDTO.getBusinessName()),"business_name",snCodeDTO.getBusinessName())
                .like(StringUtils.isNotBlank(snCodeDTO.getCode()),"code",snCodeDTO.getCode())
                .eq(null != snCodeDTO.getBind(),"business_id",0));
        List<Map<String,Object>> list = new ArrayList<>();
        for(SnCode snCode : snCodeList){
            Map<String,Object> snCodeMap = JsonUtils.object2Map(snCode);
            snCodeMap.put("statusName",sysDictFeign.getDict(String.valueOf(snCode.getStatus()),"sncode_status"));
            snCodeMap.put("id",String.valueOf(snCode.getId()));
            if(null == snCode.getAgentId() || snCode.getAgentId() == 0){
                snCodeMap.put("agentStatusName","1".equals(String.valueOf(snCode.getStatus())) || "2".equals(String.valueOf(snCode.getStatus()))?"库存" : "报废");
            }else {
                snCodeMap.put("agentStatusName","1".equals(String.valueOf(snCode.getStatus())) || "2".equals(String.valueOf(snCode.getStatus()))?"已激活" : "维修中");
            }            snCodeMap.put("id",String.valueOf(snCode.getId()));
            if(null != snCodeDTO.getAgentId() && !"0".equals(String.valueOf(snCodeDTO.getAgentId()))){
                snCodeMap.put("agentName",agentService.selectOne(new EntityWrapper<Agent>().eq("id",snCodeDTO.getAgentId())).getAgentName());
            }
            list.add(snCodeMap);
        }
        return list;
    }

    /**
     * SN码新增
     * @param snCodeDTO
     * @return
     */
    public Tip putSnCode(SnCodeDTO snCodeDTO){
        SnCode snCode = new SnCode();
        BeanUtils.copyProperties(snCodeDTO,snCode);
        snCode.setCreatedAt(new Date());
        SnCode snCode1 = snCodeService.selectOne(new EntityWrapper<SnCode>().eq("code",snCode.getCode()));
        if(snCode1 == null){
            boolean putStatus = snCodeService.insert(snCode);
            if(putStatus){
                setLog(snCodeDTO);
                return new SuccessTip(200,"入库成功!");
            }else {
                return new SuccessTip(4002,"入库失败!");
            }
        }else {
            return new SuccessTip(4001,"重复SN码");
        }
    }

    /**
     * SN码修改
     * @param snCodeDTO
     * @return
     */
    public Tip updateSncode(SnCodeDTO snCodeDTO){
        SnCode snCode = new SnCode();
        BeanUtils.copyProperties(snCodeDTO,snCode);
        snCode.setStatus(Integer.parseInt(snCodeDTO.getStatus()));
        snCode.setUpdatedAt(new Date());
        if((null == snCodeDTO.getAgentId()||"0".equals(String.valueOf(snCodeDTO.getAgentId()))) && StringUtils.isNotBlank(snCodeDTO.getAgentName())){
            Agent agent = agentService.selectOne(new EntityWrapper<Agent>().eq("agent_name",snCodeDTO.getAgentName()));
            if(null == agent){
                return new SuccessTip(4002,"经销商不存在");
            }else {
                snCode.setAgentId(agent.getId().longValue());
            }
        }
        if(null == snCodeDTO.getBusinessId() && StringUtils.isNotBlank(snCodeDTO.getBusinessName())){
            Business business = businessService.selectOne(new EntityWrapper<Business>().eq("business_name",snCodeDTO.getBusinessName()));
            if(null == business){
                return new SuccessTip(4002,"酒店不存在");
            }else {
                snCode.setBusinessId(business.getId().longValue());
            }
        }
        SnCode snCode1 = snCodeService.selectOne(new EntityWrapper<SnCode>().eq("id",snCode.getId()));
        if(snCode1 == null){
            return new SuccessTip(4002,"SN码不存在");
        }else {
            boolean updateStatus = snCodeService.updateById(snCode);
            if(updateStatus){
                setLog(snCodeDTO);
                return new SuccessTip(200,"SN码修改成功");
            }else {
                return new SuccessTip(4002,"SN码修改失败");
            }
        }
    }


    /**
     * 绑定sn码
     * @param snCodeDTO
     * @return
     */
    public Tip bindSncode(SnCodeDTO snCodeDTO){
        SnCode snCode = new SnCode();
        BeanUtils.copyProperties(snCodeDTO,snCode);
        snCode.setUpdatedAt(new Date());
        snCode.setStatus(2);
        if(null == snCodeDTO.getAgentId() || "0".equals(String.valueOf(snCodeDTO.getAgentId()))){
            Agent agent = agentService.selectOne(new EntityWrapper<Agent>().eq("agent_name",snCodeDTO.getAgentName()));
            if(null == agent){
                return new SuccessTip(4002,"经销商不存在");
            }else {
                snCode.setAgentId(agent.getId().longValue());
            }
        }
        SnCode snCode1 = snCodeService.selectOne(new EntityWrapper<SnCode>().eq("code",snCode.getCode()).eq("agent_id",0));
        if(snCode1 == null){
            return new SuccessTip(4002,"SN码不存在或已被绑定");
        }else {
            boolean updateStatus = snCodeService.update(snCode,new EntityWrapper<SnCode>().eq("code",snCode.getCode()));
            if(updateStatus){
                setLog(snCodeDTO);
                return new SuccessTip(200,"SN码绑定成功");
            }else {
                return new SuccessTip(4002,"SN码绑定失败");
            }
        }
    }


    /**
     * 批量绑定设备码
     * @param snCodeDTO
     * @return
     */
    public Tip updateSncodes(SnCodeDTO snCodeDTO){
        Tip tip = null;
        String[] snCodesList = snCodeDTO.getSnCodes().split(",");
        for(String snCode : snCodesList){
            snCodeDTO.setCode(snCode);
            tip = bindSncode(snCodeDTO);
            if(tip.getCode() != 200){
                return tip;
            }
        }
        return new SuccessTip(200,"SN码修改成功");
    }

    /**
     * SN码删除
     * @param snCodeDTO
     * @return
     */
    public Tip deleteSncode(SnCodeDTO snCodeDTO){
        boolean deleteStatus = snCodeService.delete(new EntityWrapper<SnCode>().eq("id",snCodeDTO.getId()));
        if(deleteStatus){
            return new SuccessTip(200,"SN码删除成功");
        }else {
            return new SuccessTip(4002,"SN码删除失败");
        }
    }

    /**
     * 记录日志
     * @param snCodeDTO
     * @return
     */
    public boolean setLog(SnCodeDTO snCodeDTO){
        SnCodeLog snCodeLog = new SnCodeLog();
        snCodeLog.setAgentId(snCodeDTO.getAgentId());
        snCodeLog.setAgentName(snCodeDTO.getAgentName());
        snCodeLog.setCreatedAt(new Date());
        snCodeLog.setOperator(snCodeDTO.getOperator());
        snCodeLog.setRemark(snCodeDTO.getRemark());
        snCodeLog.setStatus(snCodeDTO.getOperatorStatus());
        snCodeLog.setStatusName(snCodeDTO.getOperatorStatusName());
        snCodeLog.setSnCode(snCodeDTO.getCode());
        return snCodeLogService.insert(snCodeLog);
    }

    /**
     * 日志列表 分页
     * @param snCodeDTO
     * @return
     */
    public PageDTO getSnCodeLog(SnCodeDTO snCodeDTO){
        Page<SnCodeLog> snCodeLogPage = snCodeLogService.selectPage(new Page<>(snCodeDTO.getPage(),snCodeDTO.getRows()),new EntityWrapper<SnCodeLog>()
                .eq(null != snCodeDTO.getAgentId(),"agent_id",snCodeDTO.getAgentId())
                .eq(null != snCodeDTO.getOperatorStatus(),"status",snCodeDTO.getOperatorStatus())
                .like(StringUtils.isNotBlank(snCodeDTO.getAgentName()),"agent_name",snCodeDTO.getAgentName())
                .like(StringUtils.isNotBlank(snCodeDTO.getCode()),"sn_code",snCodeDTO.getCode())
                .ge(StringUtils.isNotBlank(snCodeDTO.getStartTime()),"created_at",snCodeDTO.getStartTime())
                .le(StringUtils.isNotBlank(snCodeDTO.getEndTime()),"created_at",snCodeDTO.getEndTime())
                .orderBy(true,snCodeDTO.getSidx(),"asc".equals(snCodeDTO.getSord()))
        );
        PageDTO pageDTO = new PageDTO();
        pageDTO.setPage(snCodeLogPage.getCurrent());
        pageDTO.setTotal(snCodeLogPage.getPages());
        pageDTO.setRecords(snCodeLogPage.getTotal());
        pageDTO.setRows(snCodeLogPage.getRecords());
        return pageDTO;
    }

    /**
     * excel 导入
     *
     * @param file 文件
     * @return 成功失败标志
     */
    public Tip importExcel(Part file) throws InvocationTargetException, IllegalAccessException {

        try {
            //检查文件格式
            ExcelUtil.preReadCheck(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            ErrorTip errorTip = ErrorTip.ERROR_TIP;
            errorTip.setMsg("文件不是excel或者文件不存在");
            return errorTip;
        }

        //读取
        List<Map<String, Object>> list = ExcelUtil.ReadExcel(file, "sncode",0);


        List<SnCode> snCodeList = new ArrayList<>();
        Date date = new Date();
        String snCodes = "";

        for (Map<String, Object> map : list) {
            //筛选重复sn码
            if ((map.get("code") != null && !"".equals(map.get("code")))) {
                SnCode snCode1 = snCodeService.selectOne(new EntityWrapper<SnCode>().eq("code",map.get("code")));
                if(snCode1 == null){
                    snCodes += map.get("code") + ",";
                }else {
                    SnCode snCode = new SnCode();
                    org.apache.commons.beanutils.BeanUtils.populate(snCode, map);
                    snCode.setCreatedAt(date);
                    snCodeList.add(snCode);
                }
            }

        }

        if("".equals(snCodes)){
            boolean putStatus = snCodeService.insertBatch(snCodeList);
            if(putStatus){
                return new SuccessTip(200,"导入SN码成功");
            }else {
                return new SuccessTip(4001,"导入SN码失败");
            }
        }else {
            return new SuccessTip(4001,snCodes.substring(0,snCodes.length()-1) + "重复");
        }
    }

    @Transactional
    public Tip importList(SnCodeDTO snCodeDTO){

        try {
            if(null == snCodeDTO.getSnCodeList() || snCodeDTO.getSnCodeList().size() == 0){
                return new SuccessTip(4001,"导入列表为空");
            }

            List<String> list = new ArrayList<>(new HashSet(snCodeDTO.getSnCodeList()));

            if(list.size() != snCodeDTO.getSnCodeList().size()){
                return new SuccessTip(4001,"导入列表重复");
            }

            List<SnCode> snCodeList = snCodeService.selectList(new EntityWrapper<SnCode>().in("code",StringUtils.join(snCodeDTO.getSnCodeList(),",")));

            if(snCodeList.size() > 0){
                String snCodes = "";
                for(SnCode snCode : snCodeList){
                    snCodes += snCode.getCode() + ",";
                }
                return new SuccessTip(4001,snCodes.substring(0,snCodes.length()-1) + "重复");
            }else {
                List<SnCode> snCodeList1 = new ArrayList<>();
                Date date = new Date();
                for(String snCode : list){
                    if(!isSpecialChar(snCode.toUpperCase())){
                        return new SuccessTip(4001,snCode + "SN码或长度不符合要求");
                    }
                    SnCode snCode1 = new SnCode();
                    snCode1.setCode(snCode.toUpperCase());
                    snCode1.setCreatedAt(date);
                    snCodeList1.add(snCode1);
                }
                for (int sidx = 0, eidx = 0; eidx < snCodeList1.size();) {
                    eidx = sidx + batch > snCodeList1.size() ? snCodeList1.size() : sidx + batch;
                    snCodeList1.subList(sidx, eidx);
                    snCodeService.insertBatch(snCodeList1);
                    sidx = eidx;
                }
                return new SuccessTip(200,"导入SN码成功");
            }
        }catch (Exception e){
            e.printStackTrace();
            return new SuccessTip(4001,"导入失败");
        }

    }

    /**
     * 判断是否为英文数字
     *
     * @param str
     * @return true为是，false为否
     */
    public static boolean isSpecialChar(String str) {
        String regEx = "^[A-Z0-9]{15,16}$";
        return regEx.matches(regEx);
    }

}
