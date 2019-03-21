package com.zhidianfan.pig.yd.moduler.order.task;

import com.zhidianfan.pig.yd.moduler.order.bo.TablesBO;
import com.zhidianfan.pig.yd.moduler.order.service.XopService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class OrderTaskTest {
	@Autowired
	private XopService xopService;

	@Test
	public void createOrUpdate() {
		TablesBO tables = xopService.tables(170, "");
		System.out.println(tables);
	}

	@Test
	public void cancel() {
	}
}