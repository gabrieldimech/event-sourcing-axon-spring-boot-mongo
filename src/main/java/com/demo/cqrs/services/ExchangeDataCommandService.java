package com.demo.cqrs.services;

import com.demo.dto.ExchangeDataDto;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public interface ExchangeDataCommandService {
    CompletableFuture<Void> addExchange(ExchangeDataDto exchangeDataDto);
    CompletableFuture<Void> updateExchange(String exchangeId, ExchangeDataDto exchangeDataDto);
}
