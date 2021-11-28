package com.example.exchangegif.services;

import com.example.exchangegif.clients.FeignOpenExchangeRateClient;
import com.example.exchangegif.exceptions.BadParametersException;
import com.example.exchangegif.model.ExchangeRate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class OpenExchangeRateService {

    @Value("${openexchangerates.app_id}")
    private String appId;
    @Value("${openexchangerates.base}")
    private String base;

    private final FeignOpenExchangeRateClient feignOpenExchangeRateClient;

    @Autowired
    public OpenExchangeRateService(FeignOpenExchangeRateClient feignOpenExchangeRateClient) {
        this.feignOpenExchangeRateClient = feignOpenExchangeRateClient;
    }

    public String getTag(String rateCode) {
        String date = LocalDate.now().minusDays(1).toString();
        ExchangeRate latestRate = feignOpenExchangeRateClient.getLatestRates(appId, base, rateCode);
        ExchangeRate yesterdayRate = feignOpenExchangeRateClient.getYesterdayRates(date, appId, base, rateCode);

        if (!latestRate.getRates().containsKey(rateCode)) {
            throw new BadParametersException();
        }

        double rateLatest = latestRate.getRates().get(rateCode);
        double rateYesterday = yesterdayRate.getRates().get(rateCode);

        String tag = "nothing at all";
        if (rateLatest > rateYesterday) {
            tag = "rich";
        } else if (rateLatest < rateYesterday) {
            tag = "broke";
        }

        return tag;
    }
}
