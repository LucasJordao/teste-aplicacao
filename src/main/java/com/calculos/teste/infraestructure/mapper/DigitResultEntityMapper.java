package com.calculos.teste.infraestructure.mapper;

import com.calculos.teste.core.domain.model.DigitResult;
import com.calculos.teste.infraestructure.persistence.entity.DigitResultEntity;

public class DigitResultEntityMapper {
    public static DigitResult fromEntity(DigitResultEntity entity) {
        return DigitResult.builder()
                .id(entity.getId())
                .n(entity.getN())
                .k(entity.getK())
                .result(entity.getResult())
                .user(UserEntityMapper.fromEntity(entity.getUser()))
                .build();
    }

    public static DigitResultEntity toEntity(DigitResult digitResult) {
        return DigitResultEntity.builder()
                .id(digitResult.getId())
                .n(digitResult.getN())
                .k(digitResult.getK())
                .result(digitResult.getResult())
                .user(UserEntityMapper.toEntity(digitResult.getUser()))
                .build();
    }
}
