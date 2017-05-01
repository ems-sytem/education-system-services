package com.education.system.models;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileForm implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotEmpty
	private String name;

	@NotEmpty
	private String email;
}
