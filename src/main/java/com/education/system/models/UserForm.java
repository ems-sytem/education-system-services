package com.education.system.models;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserForm implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull
	private String username;

	@NotNull
	private String password;

	@NotNull
	private String name;

	@NotNull
	@Email
	private String email;

	@NotNull
	private String role;
}
