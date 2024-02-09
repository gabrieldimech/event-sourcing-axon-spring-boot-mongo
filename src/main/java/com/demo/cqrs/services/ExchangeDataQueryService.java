package com.demo.cqrs.services;

import com.demo.dto.ExchangeDataDto;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
@Service
public interface ExchangeDataQueryService {
    CompletableFuture<ExchangeDataDto> findExchange(String exchangeId);
}
