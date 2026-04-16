package com.calculos.teste.core.usecase;

import com.calculos.teste.core.domain.model.User;
import com.calculos.teste.core.domain.repository.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ListUserUseCase {
    private final UserRepositoryPort repository;

    public List<User> execute() {
        log.info("Execute list user");
        var users = repository.findAll();

        log.info("Users listed with successfully");
        return users;
    }
}
