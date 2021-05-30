package laconiclizard.hudelements.mixin;

import laconiclizard.hudelements.api.HudElement;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

import static laconiclizard.hudelements.HudElements.HUD_ELEMENTS;
import static laconiclizard.hudelements.HudElements.HUD_ELEMENTS_LOCK;

@Mixin(InGameHud.class)
public abstract class InGameHud_Mixin {

    private final List<HudElement> hudelements_lst = new ArrayList<>();

    @Inject(method = "render(Lnet/minecraft/client/util/math/MatrixStack;F)V",
            at = @At("HEAD"))
    public void pre_render(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
        hudelements_lst.clear();
        synchronized (HUD_ELEMENTS_LOCK) {  // copy of list to avoid potential deadlock and also efficiency
            hudelements_lst.addAll(HUD_ELEMENTS);
        }
        for (HudElement helt : hudelements_lst) {
            synchronized (helt.lock) {
                helt.render(matrices, tickDelta);
            }
        }
    }

}
