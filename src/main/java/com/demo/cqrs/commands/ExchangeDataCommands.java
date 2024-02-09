package com.demo.cqrs.commands;

import com.demo.dto.ExchangeDataDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class ExchangeDataCommands {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateExchangeDataCommand {
        String exchangeId;
        ExchangeDataDto exchangeDataDto;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UpdateExchangeDataCommand {
        @TargetAggregateIdentifier
        String exchangeId;
        ExchangeDataDto exchangeDataDto;
    }
}
