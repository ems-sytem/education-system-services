package com.education.system.models;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupForm implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull
	@NotEmpty
	@Size(min = 6, message = "username should be consist of 6 to 20 characters")
	private String username;

	@NotNull
	@NotEmpty
	@Size(min = 6, message = "password should be consist of 6 to 20 characters")
	private String password;
}
