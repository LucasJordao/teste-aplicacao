package com.calculos.teste.infraestructure.persistence.repository;

import com.calculos.teste.core.domain.model.User;
import com.calculos.teste.core.domain.repository.UserRepositoryPort;
import com.calculos.teste.infraestructure.mapper.UserEntityMapper;
import com.calculos.teste.infraestructure.persistence.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryPort {
    private final UserJpaRepository repository;

    @Override
    public User save(User user) {
        UserEntity entity = repository.save(UserEntityMapper.toEntity(user));
        return UserEntityMapper.fromEntity(entity);
    }

    @Override
    public Optional<User> findById(UUID id) {
        return repository.findById(id).map(UserEntityMapper::fromEntity);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email).map(UserEntityMapper::fromEntity);
    }

    @Override
    public List<User> findAll() {
        return repository.findAll().stream().map(UserEntityMapper::fromEntity).toList();
    }

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
