package com.smartTrade.backend;

import com.smartTrade.backend.APITest.BasicTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BackendApplicationTests {

	private BasicTest basicTest = new BasicTest();

	@Test
	void contextLoads() {}

	@Test
	void runBasicTest() {
		basicTest.serverIsRunning();
		basicTest.welcomeMessage();
		basicTest.teamMembers();
	}

}
