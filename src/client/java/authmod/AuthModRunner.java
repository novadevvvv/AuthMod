package authmod;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class AuthModRunner {
    private final AuthModConfigManager configManager;
    private RunnerState state = RunnerState.STOPPED;
    private CodeGenerator generator;
    private int cooldownTicks;

    public AuthModRunner(AuthModConfigManager configManager) {
        this.configManager = configManager;
    }

    public void toggleStartStop(MinecraftClient client) {
        if (state == RunnerState.RUNNING || state == RunnerState.PAUSED) {
            stop(client, "Runner stopped.");
            return;
        }

        AuthModConfig config = configManager.get();
        generator = new CodeGenerator(config.charset, config.codeLength);
        cooldownTicks = 0;
        state = RunnerState.RUNNING;
        sendMessage(client, "Runner started with " + config.charset.name() + " at length " + config.codeLength + ".");
    }

    public void togglePause(MinecraftClient client) {
        if (state == RunnerState.STOPPED) {
            sendMessage(client, "Runner is not active.");
            return;
        }

        state = state == RunnerState.PAUSED ? RunnerState.RUNNING : RunnerState.PAUSED;
        sendMessage(client, state == RunnerState.PAUSED ? "Runner paused." : "Runner resumed.");
    }

    public void onEndTick(MinecraftClient client) {
        if (state != RunnerState.RUNNING || client.player == null || client.player.networkHandler == null) {
            return;
        }

        if (cooldownTicks > 0) {
            cooldownTicks--;
            return;
        }

        AuthModConfig config = configManager.get();
        String code = generator == null ? null : generator.nextCode();

        if (code == null) {
            stop(client, "All codes were tried.");
            return;
        }

        // Fabric sends commands without the leading slash here.
        String command = config.command + " " + code;
        client.player.networkHandler.sendChatCommand(command);
        cooldownTicks = config.delayTicks;
        sendMessage(client, "Tried /" + command);
    }

    public RunnerState getState() {
        return state;
    }

    private void stop(MinecraftClient client, String reason) {
        state = RunnerState.STOPPED;
        generator = null;
        cooldownTicks = 0;
        sendMessage(client, reason);
    }

    private void sendMessage(MinecraftClient client, String message) {
        if (client.player != null) {
            client.player.sendMessage(Text.literal("[Auth Mod] " + message), true);
        }
    }
}
