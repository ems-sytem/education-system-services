package com.education.system.testUtil;

import javax.inject.Inject;

import org.junit.Before;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.education.system.domain.User;
import com.education.system.repository.UserRepository;

public class IntegrationTestBase {

	protected static final String USER_NAME = "admin";

	protected final static String PASSWORD = "test123";

	@Inject
	UserRepository users;

	@Inject
	PasswordEncoder passwordEncoder;

	@Before
	public void setup() {
		clearData();
	}

	public void clearData() {
		users.deleteAllInBatch();
	}

	public void initData() {
		users.save(User.builder().username(USER_NAME).password(passwordEncoder.encode(PASSWORD)).name("Administrator")
				.role("ADMIN").build());
	}
}
