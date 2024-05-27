package com.dev.api.adacoinapi.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CurrencyConversionDTOTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public CurrencyConversionDTOTest() {

    }

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

    @Test
    void testNullFields() throws Exception {
        CurrencyConversionDTO dto = new CurrencyConversionDTO(null, null, null, null);

        String json = objectMapper.writeValueAsString(dto);
        CurrencyConversionDTO deserializedDto = objectMapper.readValue(json, CurrencyConversionDTO.class);

        assertEquals(dto.getFromCurrency(), deserializedDto.getFromCurrency());
        assertEquals(dto.getToCurrency(), deserializedDto.getToCurrency());
        assertNull(deserializedDto.getAmount());
        assertNull(deserializedDto.getConvertedAmount());
    }

    @Test
    void testExtremeValues() throws Exception {
        CurrencyConversionDTO dto = new CurrencyConversionDTO("XYZ", "ABC", new BigDecimal("999999999999999.9999"), new BigDecimal("888888888888888.8888"));

        String json = objectMapper.writeValueAsString(dto);
        CurrencyConversionDTO deserializedDto = objectMapper.readValue(json, CurrencyConversionDTO.class);

        assertEquals(dto.getFromCurrency(), deserializedDto.getFromCurrency());
        assertEquals(dto.getToCurrency(), deserializedDto.getToCurrency());
        assertEquals(0, dto.getAmount().compareTo(deserializedDto.getAmount()));
        assertEquals(0, dto.getConvertedAmount().compareTo(deserializedDto.getConvertedAmount()));
    }
}

