package com.calculos.teste.core.usecase;

import com.calculos.teste.core.domain.model.User;
import com.calculos.teste.core.domain.repository.UserRepositoryPort;
import com.calculos.teste.core.util.EncryptUtil;
import org.junit.jupiter.api.BeforeEach;
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
class DecryptUserInfosUseCaseTest {

    @Mock
    private UserRepositoryPort userRepository;

    @InjectMocks
    private DecryptUserInfosUseCase useCase;

    private UUID userId;

    @BeforeEach
    void setup() {
        userId = UUID.randomUUID();
    }

    @Test
    void shouldDecryptUserSuccessfully() {
        User user = new User();
        user.setName("encrypted-name");
        user.setEmail("encrypted-email");

        when(userRepository.findById(userId))
                .thenReturn(Optional.of(user));

        try (MockedStatic<EncryptUtil> mocked = mockStatic(EncryptUtil.class)) {

            mocked.when(() -> EncryptUtil.decrypt("encrypted-name", "private-key"))
                    .thenReturn("name-decrypted");

            mocked.when(() -> EncryptUtil.decrypt("encrypted-email", "private-key"))
                    .thenReturn("email-decrypted");

            User result = useCase.execute(userId, "private-key");

            assertEquals("name-decrypted", result.getName());
            assertEquals("email-decrypted", result.getEmail());

            verify(userRepository).findById(userId);
        }
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {

        when(userRepository.findById(userId))
                .thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> useCase.execute(userId, "private-key")
        );

        assertEquals("User not found", ex.getMessage());

        verify(userRepository).findById(userId);
    }
}