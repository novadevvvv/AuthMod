package authmod;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public final class AuthModConfigScreen {
    private AuthModConfigScreen() {
    }

    public static Screen create(Screen parent, AuthModConfigManager configManager) {
        AuthModConfig workingCopy = configManager.get();

        ConfigBuilder builder = ConfigBuilder.create()
            .setParentScreen(parent)
            .setTitle(Text.literal("Auth Mod"));

        ConfigEntryBuilder entries = builder.entryBuilder();
        ConfigCategory general = builder.getOrCreateCategory(Text.literal("Settings"));

        general.addEntry(entries.startStrField(Text.literal("Command"), workingCopy.command)
            .setDefaultValue("register")
            .setTooltip(Text.literal("Use the command name without the slash."))
            .setSaveConsumer(value -> workingCopy.command = value)
            .build());

        general.addEntry(entries.startIntField(Text.literal("Code Length"), workingCopy.codeLength)
            .setDefaultValue(4)
            .setMin(1)
            .setMax(16)
            .setTooltip(Text.literal("How many characters each generated code should have."))
            .setSaveConsumer(value -> workingCopy.codeLength = value)
            .build());

        general.addEntry(entries.startEnumSelector(Text.literal("Charset"), CodeCharset.class, workingCopy.charset)
            .setDefaultValue(CodeCharset.BASE32)
            .setEnumNameProvider(value -> ((CodeCharset) value).displayText())
            .setTooltip(Text.literal("Pick the alphabet to count through."))
            .setSaveConsumer(value -> workingCopy.charset = value)
            .build());

        general.addEntry(entries.startIntField(Text.literal("Delay Ticks"), workingCopy.delayTicks)
            .setDefaultValue(2)
            .setMin(0)
            .setMax(200)
            .setTooltip(Text.literal("0 means the mod will send one command every tick."))
            .setSaveConsumer(value -> workingCopy.delayTicks = value)
            .build());

        builder.setSavingRunnable(() -> configManager.save(workingCopy));
        return builder.build();
    }
}
