package com.calculos.teste.core.usecase;

import com.calculos.teste.core.domain.model.DigitResult;
import com.calculos.teste.core.domain.model.User;
import com.calculos.teste.core.domain.repository.DigitResultRepositoryPort;
import com.calculos.teste.core.domain.repository.UserRepositoryPort;
import com.calculos.teste.core.exception.UserNotFoundException;
import com.calculos.teste.infraestructure.cache.DigitCache;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CalculateDigitUseCaseTest {


    @Mock
    private UserRepositoryPort userRepository;

    @Mock
    private DigitResultRepositoryPort digitResultRepository;

    @Mock
    private DigitCache cache;

    @InjectMocks
    private CalculateDigitUseCase useCase;

    @Test
    void should_throw_exception_when_user_not_found() {
        UUID userId = UUID.randomUUID();

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> useCase.execute("9875", 4, userId));
    }

    @Test
    void should_return_value_from_cache() {
        UUID userId = UUID.randomUUID();

        DigitResult cached = new DigitResult();
        cached.setResult(8);

        when(userRepository.findById(userId)).thenReturn(Optional.of(new User()));
        when(cache.exists("9875-4")).thenReturn(true);
        when(cache.get("9875-4")).thenReturn(cached);

        var result = useCase.execute("9875", 4, userId);

        assertEquals(8, result.getResult());
        assertTrue(result.getIsCache());

        verify(digitResultRepository, never()).save(any());
    }

    @Test
    void should_calculate_and_save_when_not_in_cache() {
        UUID userId = UUID.randomUUID();

        User user = new User();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(cache.exists("9875-4")).thenReturn(false);

        doNothing().when(cache).put(anyString(), any());
        when(digitResultRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        var result = useCase.execute("9875", 4, userId);

        assertEquals(8, result.getResult());

        verify(cache).put(eq("9875-4"), any(DigitResult.class));
        verify(digitResultRepository).save(any(DigitResult.class));
    }
}