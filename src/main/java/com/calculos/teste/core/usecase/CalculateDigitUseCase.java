package com.calculos.teste.core.usecase;

import com.calculos.teste.core.domain.model.DigitResult;
import com.calculos.teste.core.domain.repository.DigitResultRepositoryPort;
import com.calculos.teste.core.domain.repository.UserRepositoryPort;
import com.calculos.teste.core.exception.UserNotFoundException;
import com.calculos.teste.infraestructure.cache.DigitCache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CalculateDigitUseCase {

    private final UserRepositoryPort userRepository;
    private final DigitResultRepositoryPort digitResultRepository;
    private final DigitCache cache;

    public DigitResult execute(String n, int k, UUID userId) {

        var userFounded = userRepository.findById(userId);

        if(userFounded.isEmpty()) {
            throw new UserNotFoundException();
        }

        String key = n + "-" + k;

        if (cache.exists(key)) {
            var cacheValue = cache.get(key);
            cacheValue.setIsCache(true);
            return cacheValue;
        }

        int result = superDigit(n, k);

        DigitResult digit = new DigitResult();
        digit.setN(n);
        digit.setK(k);
        digit.setResult(result);
        digit.setUser(userFounded.get());

        cache.put(key, digit);

        digitResultRepository.save(digit);

        return digit;
    }

    private int superDigit(String n, int k) {
        long sum = 0;

        for (char c : n.toCharArray()) {
            sum += c - '0';
        }

        sum *= k;

        while (sum >= 10) {
            long temp = 0;
            while (sum > 0) {
                temp += sum % 10;
                sum /= 10;
            }
            sum = temp;
        }

        return (int) sum;
    }
}
