package com.example.exchangegif.controllers;

import com.example.exchangegif.exceptions.BadParametersException;
import com.example.exchangegif.services.GiphyService;
import com.example.exchangegif.services.OpenExchangeRateService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ExchangeGifControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OpenExchangeRateService openExchangeRateService;
    @MockBean
    private GiphyService giphyService;

    @Test
    void chekStatus_correctRateCode() throws Exception {
        // when
        Mockito.when(openExchangeRateService.getTag(anyString()))
                .thenReturn("");
        Mockito.when(giphyService.getRandomGifByTag(anyString()))
                .thenReturn(new byte[0]);

        // then
        mockMvc.perform(get("/eur"))
                .andExpect(status().isOk());
    }

    @Test
    void chekStatus_incorrectRateCode() throws Exception {
        // when
        Mockito.when(openExchangeRateService.getTag(anyString()))
                .thenThrow(BadParametersException.class);

        // then
        mockMvc.perform(get("/test"))
                .andExpect(status().isBadRequest());
    }
}