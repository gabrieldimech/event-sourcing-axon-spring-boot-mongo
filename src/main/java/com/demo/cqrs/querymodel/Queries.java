package com.demo.cqrs.querymodel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class Queries {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FindExchangeData {
        String exchangeId;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CheckExchangeDataExists {
        List<String> exchangeIds;
    }

}
