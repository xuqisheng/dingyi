package com.zhidianfan.pig.yd.moduler.order.task;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class MenuTaskTest {
	@Autowired
	private MenuTask menuTask;

	@Test
	public void update() {
		menuTask.update();
	}

	@Test
	public void updateMenuOrder() {
	}
}