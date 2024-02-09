package com.demo.controllers;

import com.demo.cqrs.services.ExchangeDataCommandService;
import com.demo.cqrs.services.ExchangeDataQueryService;
import com.demo.dto.ExchangeDataDto;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
public class ExchangeController {
    private final ExchangeDataCommandService exchangeDataCommandService;
    private final ExchangeDataQueryService exchangeDataQueryService;

    public ExchangeController(ExchangeDataCommandService exchangeDataCommandService, ExchangeDataQueryService exchangeDataQueryService) {
        this.exchangeDataCommandService = exchangeDataCommandService;
        this.exchangeDataQueryService = exchangeDataQueryService;
    }

    @PostMapping("/demo/exchange")
    public CompletableFuture<Void> addExchange(@RequestBody ExchangeDataDto exchangeDataDto) {
        return exchangeDataCommandService.addExchange(exchangeDataDto);
    }

    @GetMapping("/demo/exchange/{exchangeId}")
    public CompletableFuture<ExchangeDataDto> findExchange(@PathVariable String exchangeId) {
        return exchangeDataQueryService.findExchange(exchangeId);
    }

    @PutMapping("/demo/exchange/{exchangeId}")
    public CompletableFuture<Void> updateExchange(@PathVariable String exchangeId, @RequestBody ExchangeDataDto exchangeDataDto) {
        return exchangeDataCommandService.updateExchange(exchangeId, exchangeDataDto);
    }
}
