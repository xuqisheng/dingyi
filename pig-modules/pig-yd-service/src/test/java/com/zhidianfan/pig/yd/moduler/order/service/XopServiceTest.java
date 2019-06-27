package com.zhidianfan.pig.yd.moduler.order.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class XopServiceTest {

	@Autowired
	private XopService xopService;

	@Test
	public void tables() {
	}

	@Test
	public void posInfo() {

	}

	@Test
	public void checkTable(){
		boolean checkTable = xopService.checkTable(170, "1003");
	}
}