package com.example.exchangegif.clients;

import com.example.exchangegif.model.ExchangeRate;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@FeignClient(value = "exchangeRate", url = "${openexchangerates.url.main}")
public interface FeignOpenExchangeRateClient {

    @GetMapping("/latest.json")
    ExchangeRate getLatestRates(@RequestParam(name = "app_id") String appId,
                                @RequestParam(name = "base") String base,
                                @RequestParam(name = "symbols") String rateCode);

    @GetMapping("/historical/{date}.json")
    ExchangeRate getYesterdayRates(@PathVariable("date") String date,
                                   @RequestParam(name = "app_id") String appId,
                                   @RequestParam(name = "base") String base,
                                   @RequestParam(name = "symbols") String rateCode);
}