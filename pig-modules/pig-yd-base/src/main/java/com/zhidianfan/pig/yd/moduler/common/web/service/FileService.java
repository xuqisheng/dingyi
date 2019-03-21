package com.zhidianfan.pig.yd.moduler.common.web.service;

import com.qiniu.util.Auth;
import com.zhidianfan.pig.yd.config.prop.YdPropertites;
import com.zhidianfan.pig.yd.moduler.common.dto.SysDict;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Author sherry
 * @Description
 * @Date Create in 2018/11/6
 * @Modified By:
 */
@Service
@Slf4j
public class FileService {

    @Autowired
    private YdPropertites ydPropertites;
    @Autowired
    private DictFeignService dictFeignService;

    //存储空间与域名的绑定
    private Map<String,String> map = new HashMap<>();

    {
        map.put("index","http://p3b3f2mce.bkt.clouddn.com");
        map.put("yiding-common","http://qiniu5.zhidianfan.com");
    }

    /**
     * 获取七牛云的上传凭证
     *
     * @param bucket
     * @return
     */
    public String getQiniuAuth(String bucket) {
        //不要把ak和sk暴露给客户端，给客户端凭证即可
        Auth auth = Auth.create(ydPropertites.getQiniu().getAk(), ydPropertites.getQiniu().getSk());
        String upToken = auth.uploadToken(bucket);
        return upToken;
    }



    /**
     * 根据存储空间获取在七牛云中绑定的域名
     * @param bucket
     * @return
     */
    public String getDomainOfBucket(String bucket) {

        //从pig服务的码表中获取配置信息
        List<SysDict> list = dictFeignService.findDictByType("bucket");
        for (SysDict sysDict:list){
            if (bucket.equals(sysDict.getValue())){
                return sysDict.getLabel();
            }
        }

        //使用本地配置
        return map.get(bucket);
    }
}
