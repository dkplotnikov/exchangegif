package com.example.exchangegif.services;

import com.example.exchangegif.clients.FeignGiphyClient;
import com.example.exchangegif.clients.FeignOpenExchangeRateClient;
import com.jayway.jsonpath.JsonPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;

@Service
public class ExchangeGifService {

    @Value("${openexchangerates.app_id}")
    private String appId;
    @Value("${giphy.api_key}")
    private String apiKey;
    @Value("${openexchangerates.base}")
    private String base;
    @Value("${giphy.url.source}")
    private String urlSource;

    private final FeignOpenExchangeRateClient feignOpenExchangeRateClient;
    private final FeignGiphyClient feignGiphyClient;

    @Autowired
    public ExchangeGifService(FeignOpenExchangeRateClient feignOpenExchangeRateClient,
                              FeignGiphyClient feignGiphyClient) {
        this.feignOpenExchangeRateClient = feignOpenExchangeRateClient;
        this.feignGiphyClient = feignGiphyClient;
    }

    public byte[] getExchangeGif(String rateCode) {
        String date = LocalDate.now().minusDays(1).toString();
        String jsonLatest = feignOpenExchangeRateClient.getLatestRates(appId, base, rateCode);
        String jsonYesterday = feignOpenExchangeRateClient.getYesterdayRates(date, appId , base, rateCode);

        String jsonPath = String.format("$.rates.%s", rateCode);
        double rateLatest = JsonPath.parse(jsonLatest).read(jsonPath, Double.class);
        double rateYesterday = JsonPath.parse(jsonYesterday).read(jsonPath, Double.class);

        String tag = "nothing at all";
        if (rateLatest > rateYesterday) {
            tag = "rich";
        } else if (rateLatest < rateYesterday) {
            tag = "broke";
        }

        String jsonGif = feignGiphyClient.getRichGif(apiKey, tag);
        String giphyId = JsonPath.read(jsonGif, "$.data.id");

        byte[] gifBytes;
        try (InputStream in = new URL(String.format(urlSource, giphyId)).openStream()) {
            gifBytes = in.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

        return gifBytes;
    }
}
