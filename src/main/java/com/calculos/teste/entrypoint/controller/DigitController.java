package com.calculos.teste.entrypoint.controller;

import com.calculos.teste.core.usecase.CalculateDigitUseCase;
import com.calculos.teste.core.usecase.GetResultsByIdUseCase;
import com.calculos.teste.entrypoint.mapper.DigitResultMapper;
import com.calculos.teste.entrypoint.request.DigitRequest;
import com.calculos.teste.entrypoint.response.DigitResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@Tag(name = "Digits Result", description = "Realizar calculos de digitos")
@RestController
@RequestMapping("/digits")
@RequiredArgsConstructor
public class DigitController {
    private final CalculateDigitUseCase calculateDigitUseCase;
    private final GetResultsByIdUseCase getResultsByIdUseCase;
    @PostMapping
    public ResponseEntity<DigitResponse> calculate(@Valid @RequestBody DigitRequest request) {
        var result = calculateDigitUseCase.execute(request.getN(), request.getK(), request.getUserId());

        return ResponseEntity.ok(DigitResultMapper.toResponse(result));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<DigitResponse>> getByUser(@PathVariable UUID userId) {

        var results = getResultsByIdUseCase.execute(userId);

        return ResponseEntity.ok(
                results.stream()
                        .map(DigitResultMapper::toResponse)
                        .toList()
        );
    }
}
