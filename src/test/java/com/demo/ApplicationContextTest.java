package com.demo;

import com.demo.reader.core.RawPhoneNumberFactory;
import lombok.extern.java.Log;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;

@Log
@Component
@SpringBootTest
@RunWith(SpringRunner.class)
public class ApplicationContextTest {

	@Autowired
	private ApplicationContext context;

		@Test
	public void contextLoads() {
		log.info("context loaded...");
		RawPhoneNumberFactory factory = context.getBean(RawPhoneNumberFactory.class);
		assertNotNull(factory);
	}

}
