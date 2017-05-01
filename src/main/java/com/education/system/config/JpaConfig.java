package com.education.system.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.education.system.domain.User;
import com.education.system.security.SecurityUtil;

@Configuration
@EnableTransactionManagement(mode = AdviceMode.ASPECTJ)
@EntityScan(basePackageClasses = { User.class, Jsr310JpaConverters.class })
@EnableJpaAuditing(auditorAwareRef = "auditor")
public class JpaConfig {
	@Bean
	public AuditorAware<User> auditor() {
		return () -> SecurityUtil.currentUser();
	}
}
