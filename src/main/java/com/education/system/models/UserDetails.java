package com.education.system.models;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String username;

	private String name;

	private String email;

	private String role;

	private LocalDateTime createdDate;
}
