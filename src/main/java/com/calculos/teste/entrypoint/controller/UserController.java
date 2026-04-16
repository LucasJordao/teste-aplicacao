package com.calculos.teste.entrypoint.controller;

import com.calculos.teste.core.usecase.*;
import com.calculos.teste.entrypoint.mapper.UserMapper;
import com.calculos.teste.entrypoint.request.DecryptRequest;
import com.calculos.teste.entrypoint.request.PublicKeyRequest;
import com.calculos.teste.entrypoint.request.UserRequest;
import com.calculos.teste.entrypoint.request.UserUpdateRequest;
import com.calculos.teste.entrypoint.response.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;


@Tag(name = "Users", description = "CRUD de usuários")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final CreateUserUseCase createUserUseCase;
    private final ListUserUseCase listUserUseCase;
    private final GetUserByIdUseCase getUserByIdUseCase;
    private final DeleteUserByIdUseCase deleteUserByIdUseCase;
    private final UpdateUserByIdUseCase updateUserByIdUseCase;
    private final SaveUserPublicKeyUseCase saveUserPublicKeyUseCase;
    private final DecryptUserInfosUseCase decryptUserInfosUseCase;

    @Operation(summary = "Buscar usuário por ID")
    @ApiResponse(responseCode = "200", description = "Usuário encontrado")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    @PostMapping
    public ResponseEntity<Void> createUser(@Valid @RequestBody UserRequest request) {
        var user = createUserUseCase.execute(UserMapper.toUser(request));

        return ResponseEntity.created(URI.create("/users/"+user.getId())).build();
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> listUsers() {
        var users = listUserUseCase.execute();
        return ResponseEntity.ok(users.stream()
                .map(UserMapper::toUserResponse)
                .toList());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> findUserById(@PathVariable("userId") UUID id) {
        var user = getUserByIdUseCase.execute(id);
        return ResponseEntity.ok(UserMapper.toUserResponse(user));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserResponse> updateById(@PathVariable("userId") UUID id, @Valid @RequestBody UserUpdateRequest request) {
        var user = updateUserByIdUseCase.execute(id, UserMapper.updateToUser(request));
        return ResponseEntity.ok(UserMapper.toUserResponse(user));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteById(@PathVariable("userId") UUID id) {
        deleteUserByIdUseCase.execute(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{userId}/public-key")
    public ResponseEntity<Void> savePublicKey(
            @PathVariable("userId") UUID id,
            @Valid @RequestBody PublicKeyRequest request) {

        saveUserPublicKeyUseCase.execute(id, request.getPublicKey());

        return ResponseEntity.ok().build();
    }

    @PostMapping("/{userId}/decrypt")
    public ResponseEntity<UserResponse> decryptUserInfos(
            @PathVariable("userId") UUID id,
            @Valid @RequestBody DecryptRequest request) {

        var user = decryptUserInfosUseCase.execute(id, request.getPrivateKey());

        return ResponseEntity.ok(UserMapper.toUserResponse(user));
    }
}
