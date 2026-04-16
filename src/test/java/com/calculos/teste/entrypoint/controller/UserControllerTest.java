package com.calculos.teste.entrypoint.controller;

import com.calculos.teste.core.domain.model.User;
import com.calculos.teste.core.usecase.*;
import com.calculos.teste.entrypoint.mapper.UserMapper;
import com.calculos.teste.entrypoint.request.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock private CreateUserUseCase createUserUseCase;
    @Mock private ListUserUseCase listUserUseCase;
    @Mock private GetUserByIdUseCase getUserByIdUseCase;
    @Mock private DeleteUserByIdUseCase deleteUserByIdUseCase;
    @Mock private UpdateUserByIdUseCase updateUserByIdUseCase;
    @Mock private SaveUserPublicKeyUseCase saveUserPublicKeyUseCase;
    @Mock private DecryptUserInfosUseCase decryptUserInfosUseCase;

    @InjectMocks
    private UserController controller;

    @Test
    void shouldCreateUser() {
        UserRequest request = new UserRequest();
        request.setName("Lucas");
        request.setEmail("lucas@email.com");

        User user = new User();
        user.setId(UUID.randomUUID());

        when(createUserUseCase.execute(any())).thenReturn(user);

        ResponseEntity<Void> response = controller.createUser(request);

        assertEquals(201, response.getStatusCodeValue());
        assertTrue(response.getHeaders().getLocation().toString().contains("/users/"));

        verify(createUserUseCase, times(1)).execute(any());
    }

    @Test
    void shouldListUsers() {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setName("Test");
        user.setEmail("test@email.com");

        when(listUserUseCase.execute()).thenReturn(List.of(user));

        ResponseEntity<?> response = controller.listUsers();

        assertEquals(200, response.getStatusCodeValue());
        verify(listUserUseCase).execute();
    }

    @Test
    void shouldFindUserById() {
        UUID id = UUID.randomUUID();

        User user = new User();
        user.setId(id);

        when(getUserByIdUseCase.execute(id)).thenReturn(user);

        ResponseEntity<?> response = controller.findUserById(id);

        assertEquals(200, response.getStatusCodeValue());
        verify(getUserByIdUseCase).execute(id);
    }

    @Test
    void shouldUpdateUser() {
        UUID id = UUID.randomUUID();

        UserUpdateRequest request = new UserUpdateRequest();
        request.setName("Updated");
        request.setEmail("updated@email.com");

        User updated = new User();
        updated.setId(id);

        when(updateUserByIdUseCase.execute(eq(id), any())).thenReturn(updated);

        ResponseEntity<?> response = controller.updateById(id, request);

        assertEquals(200, response.getStatusCodeValue());
        verify(updateUserByIdUseCase).execute(eq(id), any());
    }

    @Test
    void shouldDeleteUser() {
        UUID id = UUID.randomUUID();

        ResponseEntity<Void> response = controller.deleteById(id);

        assertEquals(200, response.getStatusCodeValue());
        verify(deleteUserByIdUseCase).execute(id);
    }

    @Test
    void shouldSavePublicKey() {
        UUID id = UUID.randomUUID();

        PublicKeyRequest request = new PublicKeyRequest();
        request.setPublicKey("fake-key");

        ResponseEntity<Void> response = controller.savePublicKey(id, request);

        assertEquals(200, response.getStatusCodeValue());
        verify(saveUserPublicKeyUseCase).execute(id, "fake-key");
    }

    @Test
    void shouldDecryptUserInfos() {
        UUID id = UUID.randomUUID();

        DecryptRequest request = new DecryptRequest();
        request.setPrivateKey("private-key");

        User user = new User();
        user.setId(id);

        when(decryptUserInfosUseCase.execute(id, "private-key")).thenReturn(user);

        ResponseEntity<?> response = controller.decryptUserInfos(id, request);

        assertEquals(200, response.getStatusCodeValue());
        verify(decryptUserInfosUseCase).execute(id, "private-key");
    }
}