package com.demo.cqrs.events;

import com.demo.cqrs.querymodel.Queries;
import com.demo.dto.ExchangeDataDto;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.axonframework.queryhandling.QueryUpdateEmitter;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.mongodb.client.model.Filters.*;

@Service
@ProcessingGroup("exchanges")
public class MongoExchangeEventHandler implements ExchangeEventHandler {
    private final MongoCollection<Document> exchanges;
    private final QueryUpdateEmitter emitter;
    private static final String EXCHANGE_COLLECTION_NAME = "exchanges";
    private static final String AXON_FRAMEWORK_DATABASE_NAME = "axonframework";
    private static final String EXCHANGE_ID_PROPERTY_NAME = "exchangeId";
    private static final String STOCK_PRICES_PROPERTY_NAME = "stockPrices";

    public MongoExchangeEventHandler(MongoClient client, QueryUpdateEmitter emitter) {
        exchanges = client
                .getDatabase(AXON_FRAMEWORK_DATABASE_NAME)
                .getCollection(EXCHANGE_COLLECTION_NAME);
        exchanges.createIndex(Indexes.ascending(EXCHANGE_ID_PROPERTY_NAME),
                new IndexOptions().unique(true));
        this.emitter = emitter;
    }

    private Document exchangeToDocument(ExchangeDataDto exchangeDataDto) {
        return new Document(EXCHANGE_ID_PROPERTY_NAME, exchangeDataDto.getExchangeId()).append(STOCK_PRICES_PROPERTY_NAME, exchangeDataDto.getStockPrices());
    }

    @Override
    @EventHandler
    public void on(ExchangeDataEvents.ExchangeDataCreatedEvent event) {
        exchanges.insertOne(exchangeToDocument(event.getExchangeDataDto()));
    }

    @Override
    @EventHandler
    public void on(ExchangeDataEvents.ExchangeDataUpdatedEvent event) {
        exchanges.replaceOne(eq(EXCHANGE_ID_PROPERTY_NAME, event.getExchangeId()), exchangeToDocument(event.getExchangeDataDto()));
    }
    @Override
    @QueryHandler
    public ExchangeDataDto handle(Queries.FindExchangeData query) {
        return getExchangeDto(query.getExchangeId()).orElse(null);
    }

    @Override
    @QueryHandler
    public boolean handle(Queries.CheckExchangeDataExists query) {
        return allExchangesExist(query.getExchangeIds());
    }

    private Optional<ExchangeDataDto> getExchangeDto(String exchangeId) {
        return Optional.ofNullable(exchanges.find(eq(EXCHANGE_ID_PROPERTY_NAME, exchangeId))
                        .first())
                .map(this::documentToExchangeDto);
    }

    private boolean allExchangesExist(List<String> exchangeIds) {
        return exchanges.countDocuments(in(EXCHANGE_ID_PROPERTY_NAME, exchangeIds)) == exchangeIds.size();
    }

    private ExchangeDataDto documentToExchangeDto(@NotNull Document document) {

        return ExchangeDataDto.builder()
                .exchangeId(document.getString(EXCHANGE_ID_PROPERTY_NAME))
                .stockPrices((Map<String, BigDecimal>) document.get(STOCK_PRICES_PROPERTY_NAME))
                .build();
    }
}
