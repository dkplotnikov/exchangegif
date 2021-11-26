package com.example.exchangegif.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "gif", url = "${giphy.url.main}")
public interface FeignGiphyClient {

    @GetMapping()
    String getRichGif(@RequestParam(name = "api_key") String apiKey,
                      @RequestParam(name = "tag") String tag);

    @GetMapping()
    String getBrokeGif(@RequestParam(name = "api_key") String apiKey,
                       @RequestParam(name = "tag") String tag);
}
