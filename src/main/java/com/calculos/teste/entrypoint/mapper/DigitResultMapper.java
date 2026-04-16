package com.calculos.teste.entrypoint.mapper;

import com.calculos.teste.core.domain.model.DigitResult;
import com.calculos.teste.core.domain.model.User;
import com.calculos.teste.entrypoint.request.UserRequest;
import com.calculos.teste.entrypoint.request.UserUpdateRequest;
import com.calculos.teste.entrypoint.response.DigitResponse;
import com.calculos.teste.entrypoint.response.UserResponse;

import java.util.List;

public class DigitResultMapper {
    public static DigitResponse toResponse(DigitResult digitResult) {
        return DigitResponse.builder()
                .n(digitResult.getN())
                .k(digitResult.getK())
                .result(digitResult.getResult())
                .fromCache(digitResult.getIsCache())
                .build();
    }
}
