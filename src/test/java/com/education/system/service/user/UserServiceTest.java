package com.education.system.service.user;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.education.system.domain.User;
import com.education.system.exception.PasswordMismatchedException;
import com.education.system.exception.ResourceNotFoundException;
import com.education.system.exception.UsernameAlreadyUsedException;
import com.education.system.models.UserDetails;
import com.education.system.repository.UserRepository;
import com.education.system.testUtil.Fixtures;
import com.education.system.testUtil.IntegrationTestBase;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTest extends IntegrationTestBase {

	@Inject
	UserRepository userRepository;

	@Inject
	UserService userService;

	User user;

	@Before
	public void setUp() {
		super.setup();
		user = userRepository.save(Fixtures.createUser("test", "test"));
	}

	@After
	public void tearDown() {
	}

	@Test
	public void testSaveUser() {

		UserDetails user1 = userService.findUserById(user.getId());
		assertNotNull(user1.getId());
		assertTrue("test".equals(user1.getUsername()));

		UserDetails user2 = userService.updateUser(user.getId(), Fixtures.createUserForm("test1", "test1"));
		assertTrue("test1".equals(user2.getUsername()));

		userService.updatePassword(user.getId(), Fixtures.createPasswordForm("test1", "test-new"));

	}

	@Test(expected = ResourceNotFoundException.class)
	public void testUserNotFound() {
		userService.findUserById(1000L);
	}

	@Test(expected = UsernameAlreadyUsedException.class)
	public void testUserIsTake() {
		userService.saveUser(Fixtures.createUserForm("test", "test1"));
	}

	@Test(expected = PasswordMismatchedException.class)
	public void testPasswordMismatched() {
		userService.updatePassword(user.getId(), Fixtures.createPasswordForm("test1111", "test-new"));
	}
}
