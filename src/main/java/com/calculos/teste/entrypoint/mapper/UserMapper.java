package com.calculos.teste.entrypoint.mapper;

import com.calculos.teste.core.domain.model.User;
import com.calculos.teste.entrypoint.request.UserRequest;
import com.calculos.teste.entrypoint.request.UserUpdateRequest;
import com.calculos.teste.entrypoint.response.UserResponse;

public class UserMapper {
    public static User toUser(UserRequest user) {
        return User.builder()
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    public static User updateToUser(UserUpdateRequest user) {
        return User.builder()
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    public static UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }
}
