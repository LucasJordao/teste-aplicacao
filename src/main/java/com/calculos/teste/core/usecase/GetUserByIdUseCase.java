package com.calculos.teste.core.usecase;

import com.calculos.teste.core.domain.model.User;
import com.calculos.teste.core.domain.repository.UserRepositoryPort;
import com.calculos.teste.core.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetUserByIdUseCase {
    private final UserRepositoryPort repository;

    public User execute(UUID id) {
        log.info("Execute get user by id");
        var userFounded = repository.findById(id);

        if(userFounded.isEmpty()) {
            throw new UserNotFoundException();
        }

        log.info("User founded with successfully");
        return userFounded.get();
    }
}
