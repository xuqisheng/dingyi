package com.zhidianfan.pig.yd.moduler.config.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.ConfigArea;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.ConfigDict;
import com.zhidianfan.pig.yd.moduler.common.service.IConfigAreaService;
import com.zhidianfan.pig.yd.moduler.common.service.IConfigDictService;
import com.zhidianfan.pig.yd.moduler.config.dto.AreaDTO;
import com.zhidianfan.pig.yd.moduler.config.dto.DistDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.*;

/**
 * @Author qqx
 * @Description
 * @Date Create in 2018/11/5
 * @Modified By:
 */
@Service
public class ConfigService {

    @Autowired
    private IConfigAreaService configAreaService;

    @Autowired
    private IConfigDictService configDictService;

    @Autowired
    private RestTemplate restTemplate;

    public final static String APPID = "wx5042f1d476e0bd6e";

    public final static String APPSECERT = "1feff45482416be546a80e0094d08981";

    public List<ConfigDict> getDict(DistDTO distDTO){
        List<ConfigDict> configDicts = configDictService.selectList(new EntityWrapper<ConfigDict>().eq("key",distDTO.getKey())
                .eq(null != distDTO.getParentId(),"parent_id",distDTO.getParentId()).eq("is_use",1).orderBy(true,"sort_num",true));
        return configDicts;
    }

    public List<ConfigArea> getArea(AreaDTO areaDTO){
        List<ConfigArea> configAreas = configAreaService.selectList(new EntityWrapper<ConfigArea>().eq("type",areaDTO.getType())
        .eq(null != areaDTO.getParentCode(),"parent_code",areaDTO.getParentCode()).orderBy(true,"sout_num",true));
        return configAreas;
    }

    public JSONArray getAreaList(){
        List<ConfigArea> provices = configAreaService.selectList(new EntityWrapper<ConfigArea>().eq("type",0)
                .orderBy(true,"sout_num",true));
        JSONArray jsonArray = new JSONArray();
        for(ConfigArea provice : provices){
            JSONObject jsonObject = new JSONObject();
            List<ConfigArea> citys = configAreaService.selectList(new EntityWrapper<ConfigArea>().eq("type",1).eq("parent_code",provice.getAreaCode())
                    .orderBy(true,"sout_num",true));
            JSONArray jsonArray1 = new JSONArray();
            for(ConfigArea city : citys){
                JSONObject jsonObject1 = new JSONObject();
                List<ConfigArea> areas = configAreaService.selectList(new EntityWrapper<ConfigArea>().eq("type",2).eq("parent_code",city.getAreaCode())
                        .orderBy(true,"sout_num",true));
                jsonObject1.put("cityName",city.getAreaName());
                jsonObject1.put("cityId",city.getAreaCode());
                jsonObject1.put("areaList",areas);
                jsonArray1.add(jsonObject1);
            }
            jsonObject.put("provinceName",provice.getAreaName());
            jsonObject.put("provinceId",provice.getAreaCode());
            jsonObject.put("cityList",jsonArray1);
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

    public Map<String, Object> getOpenid(String code) {
        Map<String, Object> params = new HashMap<>();
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+APPID+"&secret="+APPSECERT+"&code="+code+"&grant_type=authorization_code";
        ResponseEntity<String> entity = restTemplate.getForEntity(url, String.class);
        JSONObject jsonObject = JSONObject.parseObject(entity.getBody(), JSONObject.class);
        params.put("access_token",jsonObject.getString("access_token"));
        params.put("refresh_token",jsonObject.getString("refresh_token"));
        params.put("openid",jsonObject.getString("openid"));
        params.put("scope",jsonObject.getString("scope"));
        return params;
    }



}
