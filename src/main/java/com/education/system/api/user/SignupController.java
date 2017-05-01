package com.education.system.api.user;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.education.system.constants.Constants;
import com.education.system.exception.InvalidRequestException;
import com.education.system.models.SignupForm;
import com.education.system.models.UserDetails;
import com.education.system.service.user.UserService;

@RestController
@RequestMapping(value = Constants.URI_API_PREFIX)
public class SignupController {
	
	private static final Logger log = LoggerFactory.getLogger(SignupController.class);

	@Inject
	private UserService userService;

	@PostMapping(value = { "/signup" })
	public ResponseEntity<Void> signup(@RequestBody @Valid SignupForm form, BindingResult errors,
			HttpServletRequest req) {
		log.debug("signup data@" + form);

		if (errors.hasErrors()) {
			throw new InvalidRequestException(errors);
		}

		UserDetails saved = userService.registerUser(form);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ServletUriComponentsBuilder.fromContextPath(req)
				.path(Constants.URI_API_PREFIX + Constants.URI_USERS + "/{id}").buildAndExpand(saved.getId()).toUri());

		return new ResponseEntity<>(headers, HttpStatus.CREATED);
	}
}
