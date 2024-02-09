package com.demo.cqrs.aggregates;

import com.demo.cqrs.commands.ExchangeDataCommands;
import com.demo.cqrs.events.ExchangeDataEvents;
import com.demo.dto.ExchangeDataDto;
import com.demo.exceptions.ExchangeValidationException;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aggregate
public class ExchangeDataAggregate {
    @AggregateIdentifier
    String exchangeId;
    ExchangeDataDto exchangeDataDto;
    Logger logger = LoggerFactory.getLogger(ExchangeDataAggregate.class);

    protected ExchangeDataAggregate() {
    }

    @CommandHandler
    public ExchangeDataAggregate(ExchangeDataCommands.CreateExchangeDataCommand command) {
        validateExchange(command.getExchangeDataDto());
        AggregateLifecycle.apply(new ExchangeDataEvents.ExchangeDataCreatedEvent(command.getExchangeId(), command.getExchangeDataDto()));
    }

    @CommandHandler
    public void handle(ExchangeDataCommands.UpdateExchangeDataCommand command) {
        validateExchange(command.getExchangeDataDto());
        AggregateLifecycle.apply(new ExchangeDataEvents.ExchangeDataUpdatedEvent(command.getExchangeId(), command.getExchangeDataDto()));
    }

    private void validateExchange(ExchangeDataDto exchangeDataDto) {
        //validate sum of fractions
        boolean fractionsValid = true; //todo validations go here

        if (!fractionsValid) {
            logger.error("exchangeId: {} has errors when validating" , exchangeId);
            throw new ExchangeValidationException("exchangeId: " + exchangeId + " has errors when validating: " + exchangeDataDto.getStockPrices());
        }
    }

    @EventSourcingHandler
    public void on(ExchangeDataEvents.ExchangeDataCreatedEvent exchangeDataCreatedEvent) {
        this.exchangeId = exchangeDataCreatedEvent.getExchangeId();
        this.exchangeDataDto = exchangeDataCreatedEvent.getExchangeDataDto();
    }

    @EventSourcingHandler
    public void on(ExchangeDataEvents.ExchangeDataUpdatedEvent exchangeDataUpdatedEvent) {
        this.exchangeId = exchangeDataUpdatedEvent.getExchangeId();
        this.exchangeDataDto = exchangeDataUpdatedEvent.getExchangeDataDto();
    }
}
