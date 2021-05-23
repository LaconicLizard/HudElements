package lacliz.hudelements;

import lacliz.hudelements.api.ExampleHudElement;
import lacliz.hudelements.internal.AlterHudScreen;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.TranslatableText;

import static net.fabricmc.fabric.api.client.command.v1.ClientCommandManager.DISPATCHER;

public class HudElements implements ModInitializer {

    @Override public void onInitialize() {
        DISPATCHER.register(
                ClientCommandManager.literal("alterhud").executes(context -> {
                    MinecraftClient.getInstance().openScreen(new AlterHudScreen(new TranslatableText("title.hud-elements.screen")));
                    return 0;
                })
        );
    }

}
