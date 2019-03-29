package com.zhidianfan.pig.yd.moduler.resv.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.Anniversary;
import com.zhidianfan.pig.yd.moduler.common.service.IAnniversaryService;
import com.zhidianfan.pig.yd.moduler.resv.dto.AnniversaryDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: hzp
 * @Date: 2019-03-28 16:05
 * @Description:
 */
@Service
public class AnniversaryService {

    @Autowired
    IAnniversaryService iAnniversaryService;


    /**
     * 根据主键查询确切的纪念日
     * @param anniversaryId  纪念日id
     * @return 纪念日
     */
    public Anniversary getExactAnniversary(Integer anniversaryId) {


        Anniversary exactAnniversary = iAnniversaryService.selectById(anniversaryId);
        return exactAnniversary;
    }


    public Boolean deleteExactAnniversary(Integer anniversaryId) {

        Boolean b = iAnniversaryService.deleteById(anniversaryId);
        return b;
    }

    /**
     * 根据客户id 查询该客户的所有纪念日
     * @param vipId 客户id
     * @return 纪念日list
     */
    public List<Anniversary> getAnniversaryListByVipID(Integer vipId) {

        List<Anniversary> anniversaryList = iAnniversaryService.selectList(new EntityWrapper<Anniversary>()
                .eq("vip_id", vipId));

        return anniversaryList;
    }


    /**
     * 编辑客户纪念日
     * @param anniversaryDTO 纪念日
     * @return 返回编辑结果
     */
    public Boolean editExactAnniversary(AnniversaryDTO anniversaryDTO) {

        Anniversary anniversary= new Anniversary();

        BeanUtils.copyProperties(anniversaryDTO ,anniversary);

        //更新或者插入
        Boolean b = iAnniversaryService.insertOrUpdate(anniversary);

        return b;
    }
}
