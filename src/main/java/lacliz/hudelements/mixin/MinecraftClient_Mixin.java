package lacliz.hudelements.mixin;

import lacliz.hudelements.internal.AlterHudScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.Screen;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MinecraftClient_Mixin {

    @Inject(method = "openScreen(Lnet/minecraft/client/gui/screen/Screen;)V",
            at = @At("HEAD"), cancellable = true)
    public void pre_openScreen(@Nullable Screen screen, CallbackInfo ci) {
        // prevent chat screen from closing AlterHudScreen when its opened via command
        Screen cs = MinecraftClient.getInstance().currentScreen;
        if (cs instanceof ChatScreen && screen instanceof AlterHudScreen) {
            synchronized (AlterHudScreen.SCCE_LOCK) {
                AlterHudScreen.SHOULD_CANCEL_CHAT_EXIT = true;
            }
        } else if (cs instanceof AlterHudScreen && screen == null) {
            synchronized (AlterHudScreen.SCCE_LOCK) {
                if (AlterHudScreen.SHOULD_CANCEL_CHAT_EXIT) {
                    AlterHudScreen.SHOULD_CANCEL_CHAT_EXIT = false;
                    ci.cancel();
                }
            }
        }
    }

}
