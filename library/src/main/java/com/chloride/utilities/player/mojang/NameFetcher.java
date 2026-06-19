package com.chloride.utilities.player.mojang;

import com.chloride.utilities.player.mojang.response.ProfileResponse;
import com.google.common.collect.ImmutableList;
import tools.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;

public final class NameFetcher implements Callable<Map<UUID, String>> {

    private static final String PROFILE_URL =
            "https://sessionserver.mojang.com/session/minecraft/profile/";

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private final HttpClient client;
    private final List<UUID> uuids;

    public NameFetcher(HttpClient client, List<UUID> uuids) {
        this.client = client;
        this.uuids = ImmutableList.copyOf(uuids);
    }

    @Override
    public Map<UUID, String> call() throws Exception {
        Map<UUID, String> result = new HashMap<>();

        for (UUID uuid : this.uuids) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(PROFILE_URL + uuid.toString().replace("-", "")))
                    .GET()
                    .build();

            HttpResponse<InputStream> response =
                    client.send(request, HttpResponse.BodyHandlers.ofInputStream());

            if (response.statusCode() != 200) {
                continue;
            }

            ProfileResponse profile =
                    MAPPER.readValue(response.body(), ProfileResponse.class);

            if (profile.cause() != null && !profile.cause().isBlank()) {
                throw new IllegalStateException(profile.errorMessage());
            }

            if (profile.name() != null) {
                result.put(uuid, profile.name());
            }
        }

        return result;
    }
}