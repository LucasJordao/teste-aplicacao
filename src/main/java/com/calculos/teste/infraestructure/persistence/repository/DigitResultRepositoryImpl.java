package com.calculos.teste.infraestructure.persistence.repository;

import com.calculos.teste.core.domain.model.DigitResult;
import com.calculos.teste.core.domain.repository.DigitResultRepositoryPort;
import com.calculos.teste.infraestructure.mapper.DigitResultEntityMapper;
import com.calculos.teste.infraestructure.persistence.entity.DigitResultEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DigitResultRepositoryImpl implements DigitResultRepositoryPort {
    private final DigitResultJpaRepository repository;

    @Override
    public DigitResult save(DigitResult digitResult) {
        DigitResultEntity entity = repository.save(DigitResultEntityMapper.toEntity(digitResult));
        return DigitResultEntityMapper.fromEntity(entity);
    }

    @Override
    public List<DigitResult> findByUserId(UUID userId) {

        return repository.findByUserId(userId)
                .stream()
                .map(DigitResultEntityMapper::fromEntity)
                .toList();
    }
}
