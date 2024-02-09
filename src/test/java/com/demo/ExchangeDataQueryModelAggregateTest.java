package com.demo;

import com.demo.cqrs.aggregates.ExchangeDataAggregate;
import com.demo.cqrs.commands.ExchangeDataCommands;
import com.demo.cqrs.events.ExchangeDataEvents;
import com.demo.cqrs.querymodel.Queries;
import com.demo.dto.ExchangeDataDto;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CompletableFuture;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ExchangeDataQueryModelAggregateTest {

    private FixtureConfiguration<ExchangeDataAggregate> fixture;
    private QueryGateway queryGatewayMock;
    private String exchangeId = "XNYS";

    @Before
    public void setUp() {
        fixture = new AggregateTestFixture<>(ExchangeDataAggregate.class);
        queryGatewayMock = mock(QueryGateway.class);
    }

    @Test
    public void testCreateExchangeData() {

        ExchangeDataDto exchangeDataDto = TestUtil.buildExchange(exchangeId);

        //mock the exchange aggregate
        fixture.registerInjectableResource(queryGatewayMock);
        when(queryGatewayMock.query(any(Queries.CheckExchangeDataExists.class), eq(ResponseTypes.instanceOf(boolean.class)))).thenReturn(CompletableFuture.completedFuture(true));
        when(queryGatewayMock.query(any(Queries.FindExchangeData.class), eq(ResponseTypes.instanceOf(ExchangeDataDto.class)))).thenReturn(CompletableFuture.completedFuture(null));

        fixture.givenNoPriorActivity()
                .when(new ExchangeDataCommands.CreateExchangeDataCommand(exchangeId, exchangeDataDto))
                .expectEvents(new ExchangeDataEvents.ExchangeDataCreatedEvent(exchangeId, exchangeDataDto));
    }
}
