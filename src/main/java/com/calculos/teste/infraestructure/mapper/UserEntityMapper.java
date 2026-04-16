package com.calculos.teste.infraestructure.mapper;

import com.calculos.teste.core.domain.model.User;
import com.calculos.teste.infraestructure.persistence.entity.UserEntity;

public class UserEntityMapper {
    public static User fromEntity(UserEntity entity) {
        return User.builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .publicKey(entity.getPublicKey())
                .build();
    }

    public static UserEntity toEntity(User user) {
        return UserEntity.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .publicKey(user.getPublicKey())
                .build();
    }
}
