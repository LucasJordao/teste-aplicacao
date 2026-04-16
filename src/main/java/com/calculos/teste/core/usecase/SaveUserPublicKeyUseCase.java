package com.calculos.teste.core.usecase;

import com.calculos.teste.core.domain.repository.UserRepositoryPort;
import com.calculos.teste.core.util.EncryptUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SaveUserPublicKeyUseCase {

    private final UserRepositoryPort userRepository;

    public void execute(UUID userId, String publicKey) {

        var user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setName(EncryptUtil.encrypt(user.getName(), publicKey));
        user.setEmail(EncryptUtil.encrypt(user.getEmail(), publicKey));
        user.setPublicKey(publicKey);

        userRepository.save(user);
    }
}