package com.demo;

import com.demo.dto.ExchangeDataDto;

import java.math.BigDecimal;
import java.util.*;

public class TestUtil {

    public static ExchangeDataDto buildExchange(String exchangeId) {
        ExchangeDataDto exchangeDataDto = ExchangeDataDto.builder()
                .exchangeId(exchangeId)
                .build();

        Map<String, BigDecimal> stockPrices = new HashMap<>();

        for (int i = 1; i < 10; i++) {
            stockPrices.put(UUID.randomUUID().toString(), getRandomReading());
        }

        return exchangeDataDto.toBuilder().stockPrices(stockPrices).build();
    }


    private static BigDecimal getRandomReading() {
        return BigDecimal.valueOf(Math.random()).multiply(BigDecimal.valueOf(100));
    }

}
