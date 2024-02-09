package com.demo.controllers;

import com.demo.controllers.ExchangeController;
import com.demo.cqrs.services.ExchangeDataCommandServiceImpl;
import com.demo.cqrs.services.ExchangeDataQueryServiceImpl;
import com.demo.dto.ExchangeDataDto;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.concurrent.CompletableFuture;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ExchangeControllerTest {

    @MockBean
    QueryGateway queryGatewayMock;
    @MockBean
    CommandGateway commandGatewayMock;
    @MockBean
    ExchangeDataQueryServiceImpl exchangeDataQueryService;
    @InjectMocks
    private ExchangeController controller;
    private String exchangeId = "XNYS";

    @Before
    public void setUp() {
        queryGatewayMock = mock(QueryGateway.class);
        commandGatewayMock = mock(CommandGateway.class);
        this.controller = new ExchangeController(new ExchangeDataCommandServiceImpl(commandGatewayMock), new ExchangeDataQueryServiceImpl(queryGatewayMock));
    }

    @Test
    public void testGetExchangePrices() throws Exception {
        ExchangeDataDto exchangeDataDto = ExchangeDataDto.builder().build();
        when(queryGatewayMock.query(any(), eq(ResponseTypes.instanceOf(ExchangeDataDto.class)))).thenReturn(CompletableFuture.completedFuture(exchangeDataDto));
        ExchangeDataDto result = this.controller.findExchange(exchangeId).get();
        assertNotNull(result);
        assertEquals(exchangeDataDto, result);
    }
}
