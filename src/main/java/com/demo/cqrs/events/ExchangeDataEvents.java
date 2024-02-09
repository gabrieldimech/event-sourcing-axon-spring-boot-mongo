package com.demo.cqrs.events;

import com.demo.dto.ExchangeDataDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

public class ExchangeDataEvents {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ExchangeDataCreatedEvent {
        @NonNull
        String exchangeId;
        @NonNull
        ExchangeDataDto exchangeDataDto;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ExchangeDataUpdatedEvent {
        @NonNull
        String exchangeId;
        @NonNull
        ExchangeDataDto exchangeDataDto;
    }
}
