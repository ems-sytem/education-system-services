package com.education.system.service.user;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.education.system.domain.User;
import com.education.system.domain.User_;

public class UserSpecifications {
	
	private UserSpecifications() {}

	public static Specification<User> filterUsersByKeyword(final String keyword, final String role) {

		return (Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteria) -> {

			List<Predicate> predicates = new ArrayList<>();

			if (StringUtils.hasText(keyword)) {
				predicates.add(criteria.or(criteria.like(root.get(User_.name), "%" + keyword + "%"),
						criteria.like(root.get(User_.username), "%" + keyword + "%")));
			}
			if (StringUtils.hasText(role) && !"ALL".equals(role)) {
				predicates.add(criteria.equal(root.get(User_.role), role));

			}
			return criteria.and(predicates.toArray(new Predicate[predicates.size()]));
		};
	}

}
