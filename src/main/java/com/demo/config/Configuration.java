package com.demo.config;

import com.mongodb.client.MongoClient;
import org.axonframework.eventhandling.tokenstore.TokenStore;
import org.axonframework.extensions.mongo.DefaultMongoTemplate;
import org.axonframework.extensions.mongo.eventsourcing.tokenstore.MongoTokenStore;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.json.JacksonSerializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@org.springframework.context.annotation.Configuration
@ComponentScan(basePackages = {"com.demo.*"})
public class Configuration {
    @Qualifier("messageSerializer")
    public Serializer messageSerializer() {
        return JacksonSerializer.defaultSerializer();
    }

    @Bean
    public TokenStore getTokenStore(MongoClient client, Serializer serializer){
        return MongoTokenStore.builder()
                .mongoTemplate(
                        DefaultMongoTemplate.builder()
                                .mongoDatabase(client)
                                .build()
                )
                .serializer(serializer)
                .build();
    }

}
