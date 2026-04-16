package com.calculos.teste.core.domain.repository;

import com.calculos.teste.core.domain.model.DigitResult;
import com.calculos.teste.core.domain.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DigitResultRepositoryPort {
    DigitResult save(DigitResult user);
    List<DigitResult> findByUserId(UUID userId);
}
