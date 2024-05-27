package com.dev.api.adacoinapi.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CurrencyQuoteDTOTest {

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // necessário para manipular corretamente tipos de dados como Instant
    }

    @Test
    public void testSerialization() throws Exception {
        Instant timestamp = Instant.now();
        CurrencyQuoteDTO currencyQuoteDTO = new CurrencyQuoteDTO();
        currencyQuoteDTO.setId(1L);
        currencyQuoteDTO.setCurrencyCode("USD");
        currencyQuoteDTO.setCurrencyName("Dólar Americano");
        currencyQuoteDTO.setBid(new BigDecimal("5.20"));
        currencyQuoteDTO.setAsk(new BigDecimal("5.25"));
        currencyQuoteDTO.setTimestamp(timestamp);

        
        String json = objectMapper.writeValueAsString(currencyQuoteDTO);

        CurrencyQuoteDTO deserializedDto = objectMapper.readValue(json, CurrencyQuoteDTO.class);

        assertEquals(currencyQuoteDTO.getId(), deserializedDto.getId());
        assertEquals(currencyQuoteDTO.getCurrencyCode(), deserializedDto.getCurrencyCode());
        assertEquals(currencyQuoteDTO.getCurrencyName(), deserializedDto.getCurrencyName());
        assertEquals(0, currencyQuoteDTO.getBid().compareTo(deserializedDto.getBid()));
        assertEquals(0, currencyQuoteDTO.getAsk().compareTo(deserializedDto.getAsk()));
        assertEquals(currencyQuoteDTO.getTimestamp(), deserializedDto.getTimestamp());
    }
}
