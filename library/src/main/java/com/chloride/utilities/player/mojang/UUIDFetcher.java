package com.chloride.utilities.player.mojang;

import com.chloride.utilities.player.mojang.response.MojangProfile;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.concurrent.Callable;

public final class UUIDFetcher implements Callable<Map<String, UUID>> {

    private static final String PROFILE_URL =
            "https://api.mojang.com/profiles/minecraft";

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private final HttpClient client;
    private final List<String> names;

    public UUIDFetcher(HttpClient client, List<String> names) {
        this.client = client;
        this.names = List.copyOf(names);
    }

    @Override
    public Map<String, UUID> call() throws Exception {
        Map<String, UUID> result = new HashMap<>();

        for (int start = 0; start < names.size(); start += 100) {
            List<String> batch =
                    names.subList(start, Math.min(start + 100, names.size()));

            String body = MAPPER.writeValueAsString(batch);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(PROFILE_URL))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            List<MojangProfile> profiles =
                    MAPPER.readValue(
                            response.body(),
                            new TypeReference<>() {}
                    );

            for (MojangProfile profile : profiles) {
                result.put(
                        profile.name(),
                        parseUndashedUuid(profile.id())
                );
            }
        }

        return result;
    }

    private static UUID parseUndashedUuid(String id) {
        return UUID.fromString(
                id.substring(0, 8) + "-" +
                        id.substring(8, 12) + "-" +
                        id.substring(12, 16) + "-" +
                        id.substring(16, 20) + "-" +
                        id.substring(20)
        );
    }
}