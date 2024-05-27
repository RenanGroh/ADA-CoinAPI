package com.dev.api.adacoinapi.dto;

import com.dev.api.adacoinapi.model.CurrencyQuote;
import com.dev.api.adacoinapi.model.User;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserDTOTest {

    @Test
    void testUserDTOConversion() {
        CurrencyQuote quote1 = new CurrencyQuote();
        quote1.setId(1L);
        quote1.setCurrencyCode("USD");
        quote1.setCurrencyName("US Dollar");
        quote1.setBid(BigDecimal.valueOf(1.0));
        quote1.setAsk(BigDecimal.valueOf(1.1));
        quote1.setTimestamp(Instant.now());

        CurrencyQuote quote2 = new CurrencyQuote();
        quote2.setId(2L);
        quote2.setCurrencyCode("EUR");
        quote2.setCurrencyName("Euro");
        quote2.setBid(BigDecimal.valueOf(0.9));
        quote2.setAsk(BigDecimal.valueOf(1.0));
        quote2.setTimestamp(Instant.now());

        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setPassword("password"); // Ensure sensitive data like password is not included in DTO
        user.setFavoriteCurrencies(Arrays.asList(quote1, quote2));

        UserDTO userDTO = new UserDTO(user);

        assertEquals(user.getId(), userDTO.getId());
        assertEquals(user.getUsername(), userDTO.getUsername());
        assertTrue(userDTO.getFavoriteCurrencies().size() == 2);

        userDTO.getFavoriteCurrencies().forEach(dto -> {
            CurrencyQuote originalQuote = user.getFavoriteCurrencies().stream()
                .filter(q -> q.getCurrencyCode().equals(dto.getCurrencyCode()))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Matching CurrencyQuote not found for DTO"));

            assertEquals(originalQuote.getCurrencyCode(), dto.getCurrencyCode());
            assertEquals(originalQuote.getCurrencyName(), dto.getCurrencyName());
            assertEquals(0, originalQuote.getBid().compareTo(dto.getBid()));
            assertEquals(0, originalQuote.getAsk().compareTo(dto.getAsk()));
            assertEquals(originalQuote.getTimestamp(), dto.getTimestamp());
        });
    }

    @Test
    void testUserDTOWithNoCurrencies() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setFavoriteCurrencies(Arrays.asList());

        UserDTO userDTO = new UserDTO(user);

        assertEquals(user.getId(), userDTO.getId());
        assertEquals(user.getUsername(), userDTO.getUsername());
        assertTrue(userDTO.getFavoriteCurrencies().isEmpty(), "Favorite currencies list should be empty");
    }
}
