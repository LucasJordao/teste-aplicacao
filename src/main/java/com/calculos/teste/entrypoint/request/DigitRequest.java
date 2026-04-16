package com.calculos.teste.entrypoint.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DigitRequest {
    @Pattern(regexp = "\\d+", message = "só é permitido dígitos numéricos")
    @NotBlank @NotNull
    private String n;
    @NotNull
    private Integer k;
    @NotNull
    private UUID userId;
}
