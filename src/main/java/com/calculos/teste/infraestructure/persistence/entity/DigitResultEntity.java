package com.calculos.teste.infraestructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Entity
@Table(name = "tb_results")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DigitResultEntity {

    @Id
    @GeneratedValue
    private UUID id;

    private String n;
    private int k;
    private int result;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
