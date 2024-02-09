package com.demo.cqrs.services;

import com.demo.cqrs.commands.ExchangeDataCommands;
import com.demo.dto.ExchangeDataDto;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class ExchangeDataCommandServiceImpl implements ExchangeDataCommandService {
    private final CommandGateway commandGateway;

    public ExchangeDataCommandServiceImpl(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @Override
    public CompletableFuture<Void> addExchange(ExchangeDataDto exchangeDataDto) {
        return commandGateway.send(new ExchangeDataCommands.CreateExchangeDataCommand(exchangeDataDto.getExchangeId(), exchangeDataDto));
    }

    @Override
    public CompletableFuture<Void> updateExchange(String exchangeId, ExchangeDataDto exchangeDataDto) {
        return commandGateway.send(new ExchangeDataCommands.UpdateExchangeDataCommand(exchangeId, exchangeDataDto));
    }
}
