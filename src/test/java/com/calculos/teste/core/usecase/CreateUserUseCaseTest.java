package com.calculos.teste.core.usecase;

import com.calculos.teste.core.domain.model.User;
import com.calculos.teste.core.domain.repository.UserRepositoryPort;
import com.calculos.teste.core.exception.UserAlreadyExistsException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateUserUseCaseTest {

    @Mock
    private UserRepositoryPort repository;

    @InjectMocks
    private CreateUserUseCase useCase;

    @Test
    void should_throw_exception_when_user_already_exists() {
        User user = new User();
        user.setEmail("test@test.com");

        when(repository.findByEmail("test@test.com"))
                .thenReturn(Optional.of(new User()));

        assertThrows(UserAlreadyExistsException.class,
                () -> useCase.execute(user));

        verify(repository, never()).save(any());
    }

    @Test
    void should_create_user_successfully() {
        User user = new User();
        user.setEmail("test@test.com");

        User saved = new User();
        saved.setEmail("test@test.com");

        when(repository.findByEmail("test@test.com"))
                .thenReturn(Optional.empty());

        when(repository.save(user))
                .thenReturn(saved);

        var result = useCase.execute(user);

        assertEquals("test@test.com", result.getEmail());

        verify(repository).findByEmail("test@test.com");
        verify(repository).save(user);
    }
}