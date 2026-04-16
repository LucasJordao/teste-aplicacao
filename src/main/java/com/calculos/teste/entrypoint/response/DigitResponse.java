package com.calculos.teste.entrypoint.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DigitResponse {
    private String n;
    private Integer k;
    private Integer result;
    private Boolean fromCache;
}
