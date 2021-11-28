package com.example.exchangegif.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExchangeGifService {

    private final OpenExchangeRateService openExchangeRateService;
    private final GiphyService giphyService;

    @Autowired
    public ExchangeGifService(OpenExchangeRateService openExchangeRateService, GiphyService giphyService) {
        this.openExchangeRateService = openExchangeRateService;
        this.giphyService = giphyService;
    }

    public byte[] getExchangeGif(String rateCode) {
        String tag = openExchangeRateService.getTag(rateCode);
        return giphyService.getRandomGifByTag(tag);
    }
}
