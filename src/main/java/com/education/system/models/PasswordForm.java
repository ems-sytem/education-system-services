package com.education.system.models;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PasswordForm implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull
	@NotEmpty
	private String oldPassword;

	@NotNull
	@NotEmpty
	private String newPassword;

	public String getOldPassword() {
		return oldPassword;
	}
}
