package com.zhidianfan.pig.yd.moduler;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.MealType;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.XmsBusiness;
import com.zhidianfan.pig.yd.moduler.common.service.IMealTypeService;
import com.zhidianfan.pig.yd.moduler.common.service.IXmsBusinessService;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class PageTest {
	@Autowired
	private IXmsBusinessService xmsBusinessService;
	@Autowired
	private IMealTypeService mealTypeService;

	@Test
	public void sqlTest() {
		int currentPage = 2;
		int pageSize = 1000;
		Page<XmsBusiness> page = new Page<>(currentPage, pageSize);
		Page<XmsBusiness> xmsBusinessPage = xmsBusinessService.selectPage(page);
	}

	@Test
	public void  t(){
		XmsBusiness xms = new XmsBusiness();
		xms.setBusinessId(965);
		String time = DateFormatUtils.format(new Date(), "HH:mm");
		MealType mealType = mealTypeService.selectOne(
				new EntityWrapper<MealType>()
						.eq("business_id", xms.getBusinessId())
						.le("resv_start_time", time)
						.ge("resv_end_time", time)
		);
	}
}
