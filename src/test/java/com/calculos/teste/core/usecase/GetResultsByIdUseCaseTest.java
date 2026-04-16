package com.calculos.teste.core.usecase;

import com.calculos.teste.core.domain.model.DigitResult;
import com.calculos.teste.core.domain.repository.DigitResultRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetResultsByIdUseCaseTest {

    @Mock
    private DigitResultRepositoryPort repository;

    @InjectMocks
    private GetResultsByIdUseCase useCase;

    @Test
    void shouldReturnResultsByUserId() {
        UUID userId = UUID.randomUUID();

        DigitResult result1 = new DigitResult();
        result1.setN("10");
        result1.setK(2);
        result1.setResult(1);

        DigitResult result2 = new DigitResult();
        result2.setN("5");
        result2.setK(3);
        result2.setResult(5);

        List<DigitResult> mockResults = List.of(result1, result2);

        when(repository.findByUserId(userId))
                .thenReturn(mockResults);

        List<DigitResult> response = useCase.execute(userId);

        assertNotNull(response);
        assertEquals(2, response.size());
        assertEquals("10", response.get(0).getN());

        verify(repository).findByUserId(userId);
    }

    @Test
    void shouldReturnEmptyListWhenNoResults() {
        UUID userId = UUID.randomUUID();

        when(repository.findByUserId(userId))
                .thenReturn(List.of());

        List<DigitResult> response = useCase.execute(userId);

        assertNotNull(response);
        assertTrue(response.isEmpty());

        verify(repository).findByUserId(userId);
    }
}