package com.education.system.service.user;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.education.system.domain.User;
import com.education.system.exception.PasswordMismatchedException;
import com.education.system.exception.ResourceNotFoundException;
import com.education.system.exception.UsernameAlreadyUsedException;
import com.education.system.models.PasswordForm;
import com.education.system.models.ProfileForm;
import com.education.system.models.SignupForm;
import com.education.system.models.UserDetails;
import com.education.system.models.UserForm;
import com.education.system.repository.UserRepository;
import com.education.system.utils.DTOUtils;

@Service
@Transactional
public class UserService {
	
	private static final Logger log = LoggerFactory.getLogger(UserService.class);

	@Inject
	private PasswordEncoder passwordEncoder;

	@Inject
	private UserRepository userRepository;

	public Page<UserDetails> findAll(String q, String role, Pageable page) {

		log.debug("find all users by keyword@" + q + ", role:" + role);

		Page<User> users = userRepository.findAll(UserSpecifications.filterUsersByKeyword(q, role), page);

		return DTOUtils.mapPage(users, UserDetails.class);
	}

	public UserDetails registerUser(SignupForm form) {
		Assert.notNull(form, " @@ SignupForm is null");

		log.debug("saving user@" + form);

		if (userRepository.findByUsername(form.getUsername()) != null) {
			throw new UsernameAlreadyUsedException(form.getUsername());
		}

		User user = DTOUtils.map(form, User.class);
		user.setPassword(passwordEncoder.encode(form.getPassword()));

		User saved = userRepository.save(user);

		// TODO sending an activation email.
		return DTOUtils.map(saved, UserDetails.class);
	}

	public UserDetails saveUser(UserForm form) {
		Assert.notNull(form, " @@ UserForm is null");

		log.debug("saving user@" + form);

		if (userRepository.findByUsername(form.getUsername()) != null) {
			throw new UsernameAlreadyUsedException(form.getUsername());
		}

		User user = DTOUtils.map(form, User.class);
		user.setPassword(passwordEncoder.encode(form.getPassword()));

		User saved = userRepository.save(user);

		return DTOUtils.map(saved, UserDetails.class);
	}

	public UserDetails updateUser(Long id, UserForm form) {
		Assert.notNull(id, "user id can not be null");

		log.debug("update user by id @" + id);

		User user = userRepository.findOne(id);

		if (user == null) {
			throw new ResourceNotFoundException(id);
		}

		DTOUtils.mapTo(form, user);
		user.setPassword(passwordEncoder.encode(form.getPassword()));

		User updated = userRepository.save(user);

		if (log.isDebugEnabled()) {
			log.debug("updated user @" + updated);
		}

		return DTOUtils.map(updated, UserDetails.class);
	}

	public void updatePassword(Long id, PasswordForm form) {
		Assert.notNull(id, "user id can not be null");

		log.debug("update user password by id @" + id);

		User user = userRepository.findOne(id);

		if (!passwordEncoder.matches(form.getOldPassword(), user.getPassword())) {
			throw new PasswordMismatchedException();
		}

		user.setPassword(passwordEncoder.encode(form.getNewPassword()));

		User saved = userRepository.save(user);

		if (log.isDebugEnabled()) {
			log.debug("updated user @" + saved);
		}
	}

	public void updateProfile(Long id, ProfileForm form) {
		Assert.notNull(id, "user id can not be null");

		log.debug("update profile for user @" + id + ", profile form@" + form);

		User user = userRepository.findOne(id);

		DTOUtils.mapTo(form, user);

		User saved = userRepository.save(user);

		if (log.isDebugEnabled()) {
			log.debug("updated user @" + saved);
		}
	}

	public UserDetails findUserById(Long id) {
		Assert.notNull(id, "user id can not be null");

		log.debug("find user by id @" + id);

		User user = userRepository.findOne(id);

		if (user == null) {
			throw new ResourceNotFoundException(id);
		}

		return DTOUtils.map(user, UserDetails.class);
	}

	public UserDetails findUserByUsername(String username) {
		Assert.notNull(username, "user id can not be null");

		log.debug("find user by username @" + username);

		User user = userRepository.findByUsername(username);

		if (user == null) {
			throw new ResourceNotFoundException(String.format("username %s was not found", username));
		}

		return DTOUtils.map(user, UserDetails.class);
	}

	public void deleteUser(Long id) {
		Assert.notNull(id, "user id can not be null");

		log.debug("delete user by id @" + id);

		userRepository.delete(id);
	}
}
