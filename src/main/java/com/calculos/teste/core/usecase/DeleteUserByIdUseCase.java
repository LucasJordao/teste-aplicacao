package com.calculos.teste.core.usecase;

import com.calculos.teste.core.domain.repository.UserRepositoryPort;
import com.calculos.teste.core.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeleteUserByIdUseCase {
    private final UserRepositoryPort repository;

    public void execute(UUID id) {
        log.info("Execute delete user by id");
        var userFounded = repository.findById(id);

        if(userFounded.isEmpty()) {
            throw new UserNotFoundException();
        }

        repository.deleteById(id);

        log.info("User deleted with successfully");
    }
}
