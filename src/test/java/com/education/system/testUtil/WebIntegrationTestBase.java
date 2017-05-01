package com.education.system.testUtil;

import org.junit.Before;
import org.springframework.boot.context.embedded.LocalServerPort;

public class WebIntegrationTestBase extends IntegrationTestBase {

	@LocalServerPort
	protected int port;

	@Before
	public void setup() {
		clearData();
		initData();
	}

}
