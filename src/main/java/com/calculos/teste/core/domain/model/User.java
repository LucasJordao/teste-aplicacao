package com.calculos.teste.core.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class User {
    private UUID id;
    private String name;
    private String email;
    private List<DigitResult> digitResults;
    @JsonIgnore
    private String publicKey;
}
