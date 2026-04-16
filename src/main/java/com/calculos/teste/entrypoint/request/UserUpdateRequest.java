package com.calculos.teste.entrypoint.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequest {
    @Size(min = 3, max = 255)
    private String name;
    @Size(min = 5, max = 500)
    private String email;
}
