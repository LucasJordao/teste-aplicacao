package com.calculos.teste.core.usecase;

import com.calculos.teste.core.domain.model.User;
import com.calculos.teste.core.domain.repository.UserRepositoryPort;
import com.calculos.teste.core.util.EncryptUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SaveUserPublicKeyUseCaseTest {

    @Mock
    private UserRepositoryPort userRepository;

    @InjectMocks
    private SaveUserPublicKeyUseCase useCase;

    @Test
    void shouldSaveUserWithEncryptedDataAndPublicKey() {
        UUID userId = UUID.randomUUID();

        User user = new User();
        user.setName("plain-name");
        user.setEmail("plain-email");

        when(userRepository.findById(userId))
                .thenReturn(Optional.of(user));

        try (MockedStatic<EncryptUtil> mocked = mockStatic(EncryptUtil.class)) {

            mocked.when(() -> EncryptUtil.encrypt("plain-name", "public-key"))
                    .thenReturn("encrypted-name");

            mocked.when(() -> EncryptUtil.encrypt("plain-email", "public-key"))
                    .thenReturn("encrypted-email");

            useCase.execute(userId, "public-key");

            assertEquals("encrypted-name", user.getName());
            assertEquals("encrypted-email", user.getEmail());
            assertEquals("public-key", user.getPublicKey());

            verify(userRepository).findById(userId);
            verify(userRepository).save(user);
        }
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        UUID userId = UUID.randomUUID();

        when(userRepository.findById(userId))
                .thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> useCase.execute(userId, "public-key")
        );

        assertEquals("User not found", ex.getMessage());

        verify(userRepository).findById(userId);
        verify(userRepository, never()).save(any());
    }
}