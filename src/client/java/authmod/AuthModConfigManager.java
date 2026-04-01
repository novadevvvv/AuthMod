package authmod;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import net.fabricmc.loader.api.FabricLoader;

public class AuthModConfigManager {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private final Path configPath = FabricLoader.getInstance().getConfigDir().resolve("authmod.json");
    private AuthModConfig config = new AuthModConfig();

    public void load() {
        if (!Files.exists(configPath)) {
            config.normalize();
            save(config);
            return;
        }

        try (Reader reader = Files.newBufferedReader(configPath)) {
            AuthModConfig loaded = GSON.fromJson(reader, AuthModConfig.class);
            config = loaded == null ? new AuthModConfig() : loaded;
        } catch (IOException exception) {
            config = new AuthModConfig();
        }

        config.normalize();
        save(config);
    }

    public AuthModConfig get() {
        return config.copy();
    }

    public void save(AuthModConfig nextConfig) {
        nextConfig.normalize();
        config = nextConfig.copy();

        try {
            Files.createDirectories(configPath.getParent());

            try (Writer writer = Files.newBufferedWriter(configPath)) {
                GSON.toJson(config, writer);
            }
        } catch (IOException ignored) {
        }
    }
}
