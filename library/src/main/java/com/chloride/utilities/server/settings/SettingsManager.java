package com.chloride.utilities.server.settings;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import com.chloride.library.LibraryPlugin;

import java.util.HashSet;
import java.util.Set;

@Getter
@RequiredArgsConstructor
public class SettingsManager {

	private final Set<SettingsHandler> settingsHandlers = new HashSet<>();

	private final LibraryPlugin plugin;

	public void addSettingsHandler(SettingsHandler settingsHandler) {
		this.settingsHandlers.add(settingsHandler);
	}
}
