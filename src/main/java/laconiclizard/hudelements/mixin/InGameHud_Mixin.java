package laconiclizard.hudelements.mixin;

import laconiclizard.hudelements.api.HudElement;
import laconiclizard.hudelements.internal.HudElement_Control;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class InGameHud_Mixin {

    @Inject(method = "render(Lnet/minecraft/client/util/math/MatrixStack;F)V",
            at = @At("HEAD"))
    public void pre_render(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
        synchronized (HudElement_Control._LOCK) {
            for (HudElement helt : HudElement_Control._HUD_ELEMENTS) {
                helt.render(matrices, tickDelta);
            }
        }
    }

}
