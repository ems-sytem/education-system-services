package com.education.system.testUtil;

import com.education.system.domain.User;
import com.education.system.models.PasswordForm;
import com.education.system.models.UserForm;

public class Fixtures {

	public static User createUser(String username, String password) {
        return User.builder()
                .username(username)
                .password(password)
                .name("test user")
                .email("hantsy@test.com")
                .build();
    }

    public static UserForm createUserForm(String username, String password) {
        return UserForm.builder()
                .username(username)
                .password(password)
                .name("test user")
                .email("hantsy@test.com")
                .build();
    }

    public static PasswordForm createPasswordForm(String oldPassword, String newPassword) {
        return PasswordForm.builder()
                .oldPassword(oldPassword)
                .newPassword(newPassword)
                .build();
    }

}
