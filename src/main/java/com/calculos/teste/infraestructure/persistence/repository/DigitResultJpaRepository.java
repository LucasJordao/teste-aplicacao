package com.calculos.teste.infraestructure.persistence.repository;


import com.calculos.teste.infraestructure.persistence.entity.DigitResultEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DigitResultJpaRepository extends JpaRepository<DigitResultEntity, UUID> {
    List<DigitResultEntity> findByUserId(UUID userId);
}