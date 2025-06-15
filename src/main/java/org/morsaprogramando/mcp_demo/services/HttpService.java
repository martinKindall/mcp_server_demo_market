package org.morsaprogramando.mcp_demo.services;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class HttpService {

    private final HttpClient client;
    private final ObjectMapper mapper;

    public HttpService(ObjectMapper mapper) {
        this.client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
        this.mapper = mapper;
    }

    public <T> T getRequest(String url, Class<T> clazz) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(30))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new RuntimeException("HTTP Error: " + response.statusCode());
            }

            return mapper.readValue(response.body(), clazz);

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
