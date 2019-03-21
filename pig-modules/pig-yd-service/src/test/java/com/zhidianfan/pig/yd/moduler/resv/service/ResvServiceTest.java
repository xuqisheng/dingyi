package com.zhidianfan.pig.yd.moduler.resv.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import com.zhidianfan.pig.common.util.R;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.ResvOrder;
import com.zhidianfan.pig.yd.moduler.common.dao.entity.ResvOrderAndroid;
import com.zhidianfan.pig.yd.moduler.common.service.IResvOrderAndroidService;
import com.zhidianfan.pig.yd.moduler.resv.dto.AddTableDTO;
import com.zhidianfan.pig.yd.moduler.resv.dto.ExchangeTableDTO;
import com.zhidianfan.pig.yd.moduler.resv.dto.ResvOrderQueryDTO;
import com.zhidianfan.pig.yd.moduler.resv.dto.ResvTableOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ResvServiceTest {

	@Autowired
	IResvOrderAndroidService iResvOrderAndroidService;

	@Test
	public void insertOrder() {

		System.out.println("Insert--------------");

		for (int i = 1; i <= 1; i++) {
			ResvOrderAndroid resvOrderAndroid = new ResvOrderAndroid();
			resvOrderAndroid.setResvOrder("test"+i);
			resvOrderAndroid.setBusinessId(10086);
			resvOrderAndroid.setBusinessName("dingyiTest");
			resvOrderAndroid.setTableAreaId(i);
			resvOrderAndroid.setTableAreaName("name"+i);
			resvOrderAndroid.setTableId(i);
			resvOrderAndroid.setVipId(10086);
			resvOrderAndroid.setStatus("1");
			resvOrderAndroid.setTableName("tablename" + i);
			resvOrderAndroid.setMealTypeId(i);
			resvOrderAndroid.setMealTypeName("中餐Test");

			System.out.println(resvOrderAndroid.toString());
			iResvOrderAndroidService.insert(resvOrderAndroid);
		}
		System.out.println("over..........");
	}

	@Test
	public void selectOrderByOrder() {

		System.out.println("Select--------------");


		ResvOrderAndroid resvOrderAndroid = iResvOrderAndroidService.selectById(1);
		System.out.println(
				resvOrderAndroid.toString()
		);
		ResvOrderAndroid resvOrderAndroid18 = iResvOrderAndroidService.selectById(18);
		System.out.println(
				resvOrderAndroid18.toString()
		);

		System.out.println("over..........");
	}


	@Test
	public void truncateTabel() {

		System.out.println("TruncateTabel--------------");


		iResvOrderAndroidService.delete(
				new EntityWrapper<ResvOrderAndroid>().where("1=1",1));

		System.out.println("over..........");
	}

	@Test
	public void updateTabel() {

		System.out.println("updateTabel--------------");


		ResvOrderAndroid resvOrderAndroid = new ResvOrderAndroid();
		resvOrderAndroid.setId(1103921355747299329L);
		resvOrderAndroid.setBusinessId(1);

		iResvOrderAndroidService.updateById(resvOrderAndroid);
		System.out.println("over.........." );
	}

	@Test
	public void pageSelect() {

		System.out.println("updateTabel--------------");

		Page<ResvTableOrder> page = new Page<>(0,10);


		ResvOrderQueryDTO resvOrderQueryDTO = new ResvOrderQueryDTO();
		resvOrderQueryDTO.setBusinessId(30);
		resvOrderQueryDTO.setVipName("hu");
		resvOrderQueryDTO.setStartTime(new Date());


		iResvOrderAndroidService.conditionQueryResvOrder(page,resvOrderQueryDTO);
		System.out.println("over.........." );
	}


}