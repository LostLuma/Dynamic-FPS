package net.lostluma.dynamic_fps.impl.fabric.service;

import dynamic_fps.impl.Constants;
import dynamic_fps.impl.service.Platform;
import dynamic_fps.impl.util.Version;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public class FabricPlatform implements Platform {
	@Override
	public String getName() {
		return "Fabric";
	}

	@Override
	public Path getCacheDir() {
		Path base = FabricLoader.getInstance().getGameDir();
		return this.ensureDir(base.resolve(".cache").resolve(Constants.MOD_ID));
	}

	@Override
	public Path getConfigDir() {
		return FabricLoader.getInstance().getConfigDir();
	}

	@Override
	public boolean isDevelopmentEnvironment() {
		return FabricLoader.getInstance().isDevelopmentEnvironment();
	}

	@Override
	public boolean isModLoaded(String modId) {
		return FabricLoader.getInstance().isModLoaded(modId);
	}

	@Override
	public Optional<Version> getModVersion(String modId) {
		Optional<ModContainer> optional = FabricLoader.getInstance().getModContainer(modId);

		if (!optional.isPresent()) {
			return Optional.empty();
		}

		String raw = optional.get().getMetadata().getVersion().getFriendlyString();

		try {
			return Optional.of(Version.of(raw));
		} catch (Version.VersionParseException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void registerStartTickEvent(StartTickEvent event) {
		ClientTickEvents.START_CLIENT_TICK.register((minecraft) -> event.onStartTick());
	}

	private Path ensureDir(Path path) {
		try {
			Files.createDirectories(path);
		} catch (IOException e) {
            throw new RuntimeException("Failed to create Dynamic FPS directory.", e);
        }

		return path;
    }
}
