package com.dev.api.adacoinapi.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CurrencyConversionDTOTest {

    private final ObjectMapper objectMapper = new ObjectMapper();


    @Test
    void testSerialization() throws Exception {
        CurrencyConversionDTO dto = new CurrencyConversionDTO("USD", "EUR", new BigDecimal("100.00"), new BigDecimal("85.00"));

        String json = objectMapper.writeValueAsString(dto);
        CurrencyConversionDTO deserializedDto = objectMapper.readValue(json, CurrencyConversionDTO.class);

        assertEquals(dto.getFromCurrency(), deserializedDto.getFromCurrency());
        assertEquals(dto.getToCurrency(), deserializedDto.getToCurrency());
        assertEquals(0, dto.getAmount().compareTo(deserializedDto.getAmount()));
        assertEquals(0, dto.getConvertedAmount().compareTo(deserializedDto.getConvertedAmount()));
    }
}
