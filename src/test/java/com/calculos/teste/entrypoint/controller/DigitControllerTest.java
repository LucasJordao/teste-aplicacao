package com.calculos.teste.entrypoint.controller;

import com.calculos.teste.core.domain.model.DigitResult;
import com.calculos.teste.core.usecase.CalculateDigitUseCase;
import com.calculos.teste.core.usecase.GetResultsByIdUseCase;
import com.calculos.teste.entrypoint.request.DigitRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class DigitControllerTest {

    @Mock
    private CalculateDigitUseCase calculateDigitUseCase;

    @Mock
    private GetResultsByIdUseCase getResultsByIdUseCase;

    @InjectMocks
    private DigitController controller;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();
    }

    @Test
    void shouldCalculateDigitSuccessfully() throws Exception {

        UUID userId = UUID.randomUUID();

        DigitRequest request = new DigitRequest();
        request.setN("9875");
        request.setK(4);
        request.setUserId(userId);

        DigitResult result = new DigitResult();
        result.setN("9875");
        result.setK(4);
        result.setResult(8);

        when(calculateDigitUseCase.execute("9875", 4, userId))
                .thenReturn(result);

        mockMvc.perform(post("/digits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        verify(calculateDigitUseCase).execute("9875", 4, userId);
    }

    @Test
    void shouldReturnResultsByUserId() throws Exception {

        UUID userId = UUID.randomUUID();

        DigitResult result1 = new DigitResult();
        result1.setN("10");
        result1.setK(2);
        result1.setResult(1);

        DigitResult result2 = new DigitResult();
        result2.setN("5");
        result2.setK(3);
        result2.setResult(5);

        when(getResultsByIdUseCase.execute(userId))
                .thenReturn(List.of(result1, result2));

        mockMvc.perform(get("/digits/user/" + userId))
                .andExpect(status().isOk());

        verify(getResultsByIdUseCase).execute(userId);
    }
}