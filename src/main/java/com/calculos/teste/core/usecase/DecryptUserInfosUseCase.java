package com.calculos.teste.core.usecase;

import com.calculos.teste.core.domain.model.User;
import com.calculos.teste.core.domain.repository.UserRepositoryPort;
import com.calculos.teste.core.util.EncryptUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DecryptUserInfosUseCase {

    private final UserRepositoryPort userRepository;

    public User execute(UUID userId, String privateKey) {

        var user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setName(EncryptUtil.decrypt(user.getName(), privateKey));
        user.setEmail(EncryptUtil.decrypt(user.getEmail(), privateKey));

        return user;
    }
}