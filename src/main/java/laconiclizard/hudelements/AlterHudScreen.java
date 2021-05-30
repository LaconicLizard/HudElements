package laconiclizard.hudelements;

import laconiclizard.hudelements.api.HudElement;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.TranslatableText;

import java.util.ArrayList;
import java.util.List;

import static laconiclizard.hudelements.HudElements.HUD_ELEMENTS;
import static laconiclizard.hudelements.HudElements.HUD_ELEMENTS_LOCK;

public class AlterHudScreen extends Screen {

    // lock may be unnecessary, but given that MinecraftClient.openScreen(...) is likely to be invoked by many mods,
    //  possibly from the wrong thread, we use a lock anyways to ensure consistency
    public static final Object SCCE_LOCK = new Object();
    public static boolean SHOULD_CANCEL_CHAT_EXIT = false;

    // dev note: we don't need synchronization around selectedElement because it is only accessed
    //  via the render thread (even the key callbacks and such).
    private HudElement selectedElement = null;
    private double xOffset, yOffset;

    public AlterHudScreen() {
        super(new TranslatableText("title.hudelements.screen"));
    }

    @Override public boolean isPauseScreen() {
        return true;
    }

    @Override public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        super.render(matrices, mouseX, mouseY, delta);
        List<HudElement> hudElements;
        synchronized (HUD_ELEMENTS_LOCK) {  // copy list (to avoid potential deadlock)
            hudElements = new ArrayList<>(HUD_ELEMENTS);
        }
        float x1, y1, x2, y2;
        for (HudElement helt : hudElements) {
            synchronized (helt.lock) {
                x1 = helt.getX();
                y1 = helt.getY();
                x2 = x1 + helt.getWidth();
                y2 = y1 + helt.getHeight();
                Util.fill(matrices.peek().getModel(), x1, y1, x2, y2, helt.alterHudBackgroundColor());
                helt.render(matrices, delta);
                Util.drawBorder(matrices, x1, y1, x2, y2,
                        helt.alterHudBorderThickness(), helt.alterHudBorderColor());
            }
        }
    }

    @Override public boolean mouseClicked(double mouseX, double mouseY, int button) {
        List<HudElement> hudElements;
        synchronized (HUD_ELEMENTS_LOCK) {  // copy to avoid potential deadlock
            hudElements = new ArrayList<>(HUD_ELEMENTS);
        }
        HudElement helt;
        float x, y;
        // reverse order, so if we click on a "top" element it won't select the ones underneath
        // (because hudElements is ordered by z-value from bottom to top)
        for (int i = hudElements.size() - 1; i >= 0; i -= 1) {
            helt = hudElements.get(i);
            synchronized (helt.lock) {
                x = helt.getX();
                y = helt.getY();
                if (mouseX >= x && mouseX <= x + helt.getWidth()
                        && mouseY >= y && mouseY <= y + helt.getHeight()) {
                    if (button == 0) {
                        selectedElement = helt;
                        xOffset = mouseX - x;
                        yOffset = mouseY - y;
                        break;
                    } else if (button == 1 && selectedElement == null && helt.isEditable()) {
                        helt.edit();
                        break;
                    }
                }
            }
        }
        return true;
    }

    @Override public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (button == 0) {
            HudElement helt = selectedElement;
            if (helt != null) {
                synchronized (helt.lock) {
                    helt.save();
                }
                selectedElement = null;
            }
        }
        return true;
    }

    @Override public void mouseMoved(double mouseX, double mouseY) {
        HudElement helt = selectedElement;
        if (helt == null) return;
        synchronized (helt.lock) {  // drag element
            helt.setX((float) (mouseX - xOffset));
            helt.setY((float) (mouseY - yOffset));
        }
    }

}
