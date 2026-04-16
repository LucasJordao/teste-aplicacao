package com.calculos.teste.core.usecase;

import com.calculos.teste.core.domain.model.User;
import com.calculos.teste.core.domain.repository.UserRepositoryPort;
import com.calculos.teste.core.exception.UserNotFoundException;
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
class DeleteUserByIdUseCaseTest {

    @Mock
    private UserRepositoryPort repository;

    @InjectMocks
    private DeleteUserByIdUseCase useCase;

    @Test
    void shouldDeleteUserSuccessfully() {
        UUID id = UUID.randomUUID();

        User user = new User();
        user.setId(id);

        when(repository.findById(id))
                .thenReturn(Optional.of(user));

        doNothing().when(repository).deleteById(id);

        useCase.execute(id);

        verify(repository).findById(id);
        verify(repository).deleteById(id);
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        UUID id = UUID.randomUUID();

        when(repository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> useCase.execute(id)
        );

        verify(repository).findById(id);
        verify(repository, never()).deleteById(any());
    }
}