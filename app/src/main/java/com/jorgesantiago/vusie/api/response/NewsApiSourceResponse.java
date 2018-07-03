package com.jorgesantiago.vusie.api.response;

/**
 * Our Article Source JSON to POJO representation of the response we get from api
 */
public class NewsApiSourceResponse {

    private String name;

    public NewsApiSourceResponse(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
