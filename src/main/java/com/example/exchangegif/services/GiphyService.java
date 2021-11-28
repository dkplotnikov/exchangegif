package com.example.exchangegif.services;

import com.example.exchangegif.clients.FeignGiphyClient;
import com.example.exchangegif.exceptions.DefaultException;
import com.example.exchangegif.model.GiphyGif;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

@Service
public class GiphyService {

    @Value("${giphy.api_key}")
    private String apiKey;
    @Value("${giphy.url.source}")
    private String urlSource;

    private final FeignGiphyClient feignGiphyClient;

    @Autowired
    public GiphyService(FeignGiphyClient feignGiphyClient) {
        this.feignGiphyClient = feignGiphyClient;
    }

    public byte[] getRandomGifByTag(String tag) {
        GiphyGif giphyGif = feignGiphyClient.getGif(apiKey, tag);
        String giphyId = String.valueOf(giphyGif.getData().get("id"));

        byte[] gifBytes;
        try (InputStream in = new URL(String.format(urlSource, giphyId)).openStream()) {
            gifBytes = in.readAllBytes();
        } catch (IOException e) {
            throw new DefaultException(e.getMessage());
        }

        return gifBytes;
    }
}
