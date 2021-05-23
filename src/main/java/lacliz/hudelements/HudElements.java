package lacliz.hudelements;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.minecraft.client.MinecraftClient;

import static net.fabricmc.fabric.api.client.command.v1.ClientCommandManager.DISPATCHER;

public class HudElements implements ModInitializer {

    @Override public void onInitialize() {
        DISPATCHER.register(
                ClientCommandManager.literal("alterhud").executes(context -> {
                    MinecraftClient.getInstance().openScreen(new AlterHudScreen());
                    return 0;
                })
        );
    }

}
