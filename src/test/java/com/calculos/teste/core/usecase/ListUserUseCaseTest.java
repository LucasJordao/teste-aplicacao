package com.calculos.teste.core.usecase;

import com.calculos.teste.core.domain.model.User;
import com.calculos.teste.core.domain.repository.UserRepositoryPort;
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
class ListUserUseCaseTest {

    @Mock
    private UserRepositoryPort repository;

    @InjectMocks
    private ListUserUseCase useCase;

    @Test
    void shouldReturnAllUsers() {
        User user1 = new User();
        user1.setId(UUID.randomUUID());
        user1.setName("User 1");

        User user2 = new User();
        user2.setId(UUID.randomUUID());
        user2.setName("User 2");

        List<User> mockUsers = List.of(user1, user2);

        when(repository.findAll())
                .thenReturn(mockUsers);

        List<User> result = useCase.execute();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("User 1", result.get(0).getName());

        verify(repository).findAll();
    }

    @Test
    void shouldReturnEmptyListWhenNoUsers() {

        when(repository.findAll())
                .thenReturn(List.of());

        List<User> result = useCase.execute();

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(repository).findAll();
    }
}