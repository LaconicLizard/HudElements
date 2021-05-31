package laconiclizard.hudelements;

import laconiclizard.hudelements.api.HudElement;
import laconiclizard.hudelements.api.Signal;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.minecraft.client.MinecraftClient;

import java.util.ArrayList;
import java.util.List;

import static net.fabricmc.fabric.api.client.command.v1.ClientCommandManager.DISPATCHER;

public class HudElements implements ModInitializer {

    public static final Object HUD_ELEMENTS_LOCK = new Object();
    public static final List<HudElement> HUD_ELEMENTS = new ArrayList<>();

    @Override public void onInitialize() {
        DISPATCHER.register(
                ClientCommandManager.literal("alterhud").executes(context -> {
                    MinecraftClient.getInstance().openScreen(new AlterHudScreen());
                    return 0;
                })
        );
    }

}
