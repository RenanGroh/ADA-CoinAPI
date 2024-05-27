package com.dev.api.adacoinapi.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CurrencyComparisonDTOTest {

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    public void testSerialization() throws Exception {
        CurrencyComparisonDTO dto = new CurrencyComparisonDTO();
        dto.setCurrencyCode("USD");
        dto.setCurrencyName("US Dollar");
        dto.setCurrentBid(new BigDecimal("1.12"));
        dto.setCurrentAsk(new BigDecimal("1.15"));
        dto.setPreviousBid(new BigDecimal("1.10"));
        dto.setPreviousAsk(new BigDecimal("1.13"));

        String json = objectMapper.writeValueAsString(dto);
        CurrencyComparisonDTO deserializedDto = objectMapper.readValue(json, CurrencyComparisonDTO.class);

        assertEquals(dto, deserializedDto);
    }
}

