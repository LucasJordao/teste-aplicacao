package com.calculos.teste.entrypoint.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PublicKeyRequest {
    @NotBlank
    private String publicKey;
}
