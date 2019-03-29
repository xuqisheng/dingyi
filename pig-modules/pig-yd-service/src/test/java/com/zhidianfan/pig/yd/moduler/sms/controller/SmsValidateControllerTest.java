package com.zhidianfan.pig.yd.moduler.sms.controller;

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

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SmsValidateControllerTest {
	private Logger log = LoggerFactory.getLogger(getClass());

	private MockMvc mvc;

	@Autowired
	private WebApplicationContext context;

	@Before
	public void setupMockMvc() {
		mvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	public void businessLog() {
		try {
			String smsBusinessLog = "/sms/business/log";
			MvcResult mvcResult = mvc.perform(
					MockMvcRequestBuilders.get(smsBusinessLog)
							.param("businessId", "30")
							.param("page","1")
							.param("limit","10")
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
	public void createOrUpdateTemplate(){
		try {
			String smsBusinessLog = "/sms/template";
			MvcResult mvcResult = mvc.perform(
					MockMvcRequestBuilders.post(smsBusinessLog)
							.param("id","1")
							.param("businessId","30")
							.param("templateTitle","测试")
							.param("templateContent","22222{$var}22222,{$var}")
							.contentType(MediaType.APPLICATION_FORM_URLENCODED)
							.accept(MediaType.APPLICATION_JSON_UTF8)
			).andReturn();
			String contentAsString = mvcResult.getResponse().getContentAsString();
			log.info(contentAsString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}