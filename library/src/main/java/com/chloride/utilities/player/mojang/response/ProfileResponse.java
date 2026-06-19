package com.chloride.utilities.player.mojang.response;

public record ProfileResponse(
            String id,
            String name,
            String cause,
            String errorMessage
    ) {
    }