package com.dev.api.adacoinapi.controller;

import com.dev.api.adacoinapi.dto.CurrencyConversionDTO;
import com.dev.api.adacoinapi.model.CurrencyQuote;
import com.dev.api.adacoinapi.service.QuoteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(QuoteController.class)
@ExtendWith(MockitoExtension.class)
public class QuoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuoteService quoteService;

    @Test
    public void testGetRealTimeQuotes() throws Exception {
        Map<String, CurrencyQuote> quotes = new HashMap<>();
        quotes.put("USD", new CurrencyQuote());
        when(quoteService.getRealTimeQuotes(any())).thenReturn(quotes);

        mockMvc.perform(get("/api/quotes")
                .param("currencies", "USD,EUR"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.USD").exists());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testConvertCurrency() throws Exception {
        CurrencyConversionDTO conversionDTO = new CurrencyConversionDTO("USD", "EUR", BigDecimal.valueOf(100), BigDecimal.valueOf(85));
        when(quoteService.convertCurrency("USD", "EUR", BigDecimal.valueOf(100))).thenReturn(conversionDTO);

        mockMvc.perform(get("/api/quotes/convert/{fromCurrency}/{toCurrency}/{amount}", "USD", "EUR", "100")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.fromCurrency").value("USD"))
                .andExpect(jsonPath("$.toCurrency").value("EUR"))
                .andExpect(jsonPath("$.convertedAmount").value(85));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testUnauthorizedAccess() throws Exception {
        mockMvc.perform(get("/api/quotes/convert/{fromCurrency}/{toCurrency}/{amount}", "USD", "EUR", "100"))
                .andExpect(status().isForbidden());  // Adjust the expected status based on your security config
    }

    @Test
    public void testBadRequest() throws Exception {
        mockMvc.perform(get("/api/quotes/convert/{fromCurrency}/{toCurrency}/{amount}", "", "", "not-a-number"))
                .andExpect(status().isBadRequest());
    }
}
