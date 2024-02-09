package com.demo.cqrs.events;

import com.demo.cqrs.querymodel.Queries;
import com.demo.dto.ExchangeDataDto;

public interface ExchangeEventHandler {
    void on(ExchangeDataEvents.ExchangeDataCreatedEvent event);
    void on(ExchangeDataEvents.ExchangeDataUpdatedEvent event);
    ExchangeDataDto handle(Queries.FindExchangeData query);
    boolean handle(Queries.CheckExchangeDataExists query);

}
