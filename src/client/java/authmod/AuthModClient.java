package authmod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class AuthModClient implements ClientModInitializer {
    public static final AuthModConfigManager CONFIG_MANAGER = new AuthModConfigManager();

    private static final String KEY_CATEGORY = "category.authmod.controls";
    private static KeyBinding openMenuKey;
    private static KeyBinding startStopKey;
    private static KeyBinding pauseResumeKey;

    private AuthModRunner runner;

    @Override
    public void onInitializeClient() {
        CONFIG_MANAGER.load();
        runner = new AuthModRunner(CONFIG_MANAGER);

        openMenuKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.authmod.open_menu",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_O,
            KEY_CATEGORY
        ));

        startStopKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.authmod.start_stop",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_P,
            KEY_CATEGORY
        ));

        pauseResumeKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.authmod.pause_resume",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_UNKNOWN,
            KEY_CATEGORY
        ));

        ClientTickEvents.END_CLIENT_TICK.register(this::onEndTick);
    }

    private void onEndTick(MinecraftClient client) {
        while (openMenuKey.wasPressed()) {
            client.setScreen(AuthModConfigScreen.create(client.currentScreen, CONFIG_MANAGER));
        }

        while (startStopKey.wasPressed()) {
            runner.toggleStartStop(client);
        }

        while (pauseResumeKey.wasPressed()) {
            runner.togglePause(client);
        }

        runner.onEndTick(client);
    }
}