package com.calculos.teste.core.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DigitResult {
    private UUID id;
    private String n;
    private Integer k;
    private Integer result;
    private User user;
    private Boolean isCache;
}
