package com.calculos.teste.core.usecase;

import com.calculos.teste.core.domain.model.DigitResult;
import com.calculos.teste.core.domain.repository.DigitResultRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetResultsByIdUseCase{

    private final DigitResultRepositoryPort repository;

    public List<DigitResult> execute(UUID userId) {
        return repository.findByUserId(userId);
    }
}