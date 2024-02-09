package com.demo.cqrs.services;

import com.demo.cqrs.querymodel.Queries;
import com.demo.dto.ExchangeDataDto;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class ExchangeDataQueryServiceImpl implements ExchangeDataQueryService {
    private final QueryGateway queryGateway;

    public ExchangeDataQueryServiceImpl(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @Override
    public CompletableFuture<ExchangeDataDto> findExchange(String exchangeId) {
        return queryGateway.query(new Queries.FindExchangeData(exchangeId), ResponseTypes.instanceOf(ExchangeDataDto.class));
    }
}
