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
class UpdateUserByIdUseCaseTest {

    @Mock
    private UserRepositoryPort repository;

    @InjectMocks
    private UpdateUserByIdUseCase useCase;

    @Test
    void shouldUpdateUserSuccessfully() {
        UUID id = UUID.randomUUID();

        User existingUser = new User();
        existingUser.setId(id);
        existingUser.setName("Old Name");
        existingUser.setEmail("old@email.com");

        User newData = new User();
        newData.setName("New Name");
        newData.setEmail("new@email.com");

        when(repository.findById(id))
                .thenReturn(Optional.of(existingUser));

        when(repository.save(any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        User result = useCase.execute(id, newData);

        assertNotNull(result);
        assertEquals("New Name", result.getName());
        assertEquals("new@email.com", result.getEmail());

        verify(repository).findById(id);
        verify(repository).save(existingUser);
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        UUID id = UUID.randomUUID();

        when(repository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> useCase.execute(id, new User())
        );

        verify(repository).findById(id);
        verify(repository, never()).save(any());
    }
}