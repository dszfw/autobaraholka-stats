package com.maxi.parsers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SearchParser implements Parser {

    @Value("${search.url}")
    private String searchUrl;

    @Autowired
    ObjectMapper objectMapper;

    @Scheduled(fixedRate = 5000)
    public void execute() throws IOException {
        Content content = requestToOnliner();
        JsonNode json = objectMapper.readTree(content.asString());
    }

    public Content requestToOnliner() throws IOException {
        Request request = Request.Post(searchUrl)
                .setHeader("Accept", "application/json");
        Response response = request.execute();
        return response.returnContent();
    }
}
