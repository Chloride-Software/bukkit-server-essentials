package com.chloride.utilities.server.timer.event;

import com.chloride.utilities.server.timer.Timer;
import com.chloride.library.LibraryPlugin;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
public class TimerClearEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();
    private final Optional<UUID> userUUID;
    private final Timer timer;
    private Optional<Player> player;

    public TimerClearEvent(Timer timer) {
        this.userUUID = Optional.empty();
        this.timer = timer;
    }

    public TimerClearEvent(UUID userUUID, Timer timer) {
        this.userUUID = Optional.of(userUUID);
        this.timer = timer;
    }

    public TimerClearEvent(Player player, Timer timer) {
        Objects.requireNonNull(player);

        this.player = Optional.of(player);
        this.userUUID = Optional.of(player.getUniqueId());
        this.timer = timer;
    }

    public static HandlerList getHandlerList() {
        return TimerClearEvent.HANDLERS;
    }

    public Optional<Player> getPlayer() {
        if (player.isEmpty()) {
            player = this.userUUID.map(uuid -> LibraryPlugin.getInstance().getServer().getPlayer(uuid));
        }

        return player;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return TimerClearEvent.HANDLERS;
    }
}
