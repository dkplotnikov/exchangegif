package com.example.exchangegif.controllers;

import com.example.exchangegif.services.ExchangeGifService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExchangeGifController {

    private final ExchangeGifService exchangegifService;

    @Autowired
    public ExchangeGifController(ExchangeGifService exchangegifService) {
        this.exchangegifService = exchangegifService;
    }

    @GetMapping("/{foo}")
    public ResponseEntity<byte[]> getExchangeGif(
            @PathVariable(name = "foo") String rateCode) {
        byte[] bytes = exchangegifService.getExchangeGif(rateCode.toUpperCase());
        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_GIF)
                .body(bytes);
    }
}
