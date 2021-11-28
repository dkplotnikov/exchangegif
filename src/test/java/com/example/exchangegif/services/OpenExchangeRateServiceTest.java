package com.example.exchangegif.services;

import com.example.exchangegif.clients.FeignOpenExchangeRateClient;
import com.example.exchangegif.exceptions.BadParametersException;
import com.example.exchangegif.model.ExchangeRate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;

@SpringBootTest
class OpenExchangeRateServiceTest {

    @Autowired
    private OpenExchangeRateService underTest;

    @MockBean
    private FeignOpenExchangeRateClient feignOpenExchangeRateClient;

    @BeforeEach
    void setUp() {
        Map<String, Double> yRates = new HashMap<>();
        yRates.put("INCR", 1d);
        yRates.put("DECR", 1d);
        yRates.put("SAME", 1d);
        ExchangeRate yesterdayRatesTest = new ExchangeRate();
        yesterdayRatesTest.setRates(yRates);

        Map<String, Double> lRates = new HashMap<>();
        lRates.put("INCR", 1.5d);
        lRates.put("DECR", 0.5d);
        lRates.put("SAME", 1d);
        ExchangeRate latestRatesTest = new ExchangeRate();
        latestRatesTest.setRates(lRates);

        Mockito.when(feignOpenExchangeRateClient.getYesterdayRates(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(yesterdayRatesTest);
        Mockito.when(feignOpenExchangeRateClient.getLatestRates(anyString(), anyString(), anyString()))
                .thenReturn(latestRatesTest);
    }

    @Test
    void checkTag_rateIncreasing() {
        // when
        String expected = underTest.getTag("INCR");

        // then
        assertThat(expected).isEqualTo("rich");
    }

    @Test
    void checkTag_rateDecreasing() {
        // when
        String expected = underTest.getTag("DECR");

        // then
        assertThat(expected).isEqualTo("broke");
    }

    @Test
    void checkTag_rateRemainsTheSame() {
        // when
        String expected = underTest.getTag("SAME");

        // then
        assertThat(expected).isEqualTo("nothing at all");
    }

    @Test
    void throw_rateCodeIsIncorrect() {
        // when
        // then
        assertThatThrownBy(() -> underTest.getTag("TEST"))
                .isInstanceOf(BadParametersException.class);
    }
}