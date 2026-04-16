package com.calculos.teste.core.usecase;

import com.calculos.teste.core.domain.model.User;
import com.calculos.teste.core.domain.repository.UserRepositoryPort;
import com.calculos.teste.core.exception.UserAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateUserUseCase {
    private final UserRepositoryPort repository;

    public User execute(User user) {
        log.info("Execute user creation");
        var userFounded = repository.findByEmail(user.getEmail());

        if(userFounded.isPresent()) {
            throw new UserAlreadyExistsException();
        }

        var userSaved = repository.save(user);

        log.info("User created with successfully");
        return userSaved;
    }
}
