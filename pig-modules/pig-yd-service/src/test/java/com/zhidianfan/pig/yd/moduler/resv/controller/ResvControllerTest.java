package com.zhidianfan.pig.yd.moduler.resv.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import com.zhidianfan.pig.yd.moduler.resv.dto.AddTableDTO;
import com.zhidianfan.pig.yd.moduler.resv.dto.ExchangeTableDTO;
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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ResvControllerTest {
	private Logger log = LoggerFactory.getLogger(getClass());
	private MockMvc mvc;
	@Autowired
	private WebApplicationContext context;

	@Before
	public void setupMockMvc() {
		mvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	public void getAllResvOrders() {
		try {
			String orders = "/resv/orders";
			MvcResult mvcResult = mvc.perform(
					MockMvcRequestBuilders.get(orders)
							.param("businessId", "30")
							.param("mealTypeId", "10")
							.param("resvDate", new Date().toString())
							.param("tableAreaId", "9,10,11")
							.param("orderStatus", "1")
							.contentType(MediaType.APPLICATION_FORM_URLENCODED)
							.accept(MediaType.APPLICATION_JSON_UTF8)
			).andReturn();
			String contentAsString = mvcResult.getResponse().getContentAsString();
			log.info(contentAsString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void updateTable() {
		try {
			ExchangeTableDTO exchangeTableDTO = new ExchangeTableDTO();
			exchangeTableDTO.setBusinessId(30);
			exchangeTableDTO.setTableId(79190);
			exchangeTableDTO.setResvOrder("1524819740984615");
			exchangeTableDTO.setResvDate(new Date());
			exchangeTableDTO.setMealTypeId(10);

			ObjectMapper mapper = new ObjectMapper();
			String EXCHANGE_TABLE_URL = "/resv/order/exchange/table";
			MvcResult mvcResult = mvc.perform(
					MockMvcRequestBuilders.post(EXCHANGE_TABLE_URL)
							.contentType(MediaType.APPLICATION_FORM_URLENCODED)
							.accept(MediaType.APPLICATION_JSON_UTF8)
							.param("businessId", "30")
							.param("tableId", "79190")
							.param("resvOrder", "1524819740984615")
							.param("resvDate", new Date().toString())
							.param("mealTypeId", "10")
//					.content(mapper.writeValueAsString(exchangeTableDTO))
			).andReturn();
			String contentAsString = mvcResult.getResponse().getContentAsString();
			log.info(contentAsString);


		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void updateConfirm() {
		try {
			Map<String, String> params = Maps.newHashMap();
			params.put("resvOrder", "1524819740984615");
			params.put("confirm", "1");
			ObjectMapper mapper = new ObjectMapper();
			String s = mapper.writeValueAsString(params);
			String CONFIRM = "/resv/order/confirm";
			MvcResult mvcResult = mvc.perform(
					MockMvcRequestBuilders.post(CONFIRM)
							.contentType(MediaType.APPLICATION_FORM_URLENCODED)
							.accept(MediaType.APPLICATION_JSON_UTF8)
							.param("resvOrder", "1524819740984615")
							.param("confirm", "1")
//							.content(mapper.writeValueAsString(params))
			).andReturn();
			String contentAsString = mvcResult.getResponse().getContentAsString();
			log.info(contentAsString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void addTable() {
		try {
			AddTableDTO addTableDTO = new AddTableDTO();
			addTableDTO.setResvOrder("1524819740984615");
			addTableDTO.setTableId(79192);
			ObjectMapper mapper = new ObjectMapper();
			String ADD_TABLE = "/resv/order/table/add";
			MvcResult mvcResult = mvc.perform(
					MockMvcRequestBuilders.post(ADD_TABLE)
							.contentType(MediaType.APPLICATION_FORM_URLENCODED)
							.accept(MediaType.APPLICATION_JSON_UTF8)
							.param("resvOrder", "1524819740984615")
							.param("tableId", "79192")
//					.content(mapper.writeValueAsString(addTableDTO))
			).andReturn();
			String contentAsString = mvcResult.getResponse().getContentAsString();
			log.info(contentAsString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}