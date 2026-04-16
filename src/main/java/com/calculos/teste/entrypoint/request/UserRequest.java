package com.calculos.teste.entrypoint.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserRequest {
    @NotNull @NotBlank @Size(min = 3, max = 255)
    private String name;
    @NotNull @NotBlank @Size(min = 5, max = 500)
    private String email;
}
