package lacliz.hudelements;

import lacliz.hudelements.api.HudElement;
import lacliz.hudelements.internal.Util;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.TranslatableText;

import static lacliz.hudelements.internal.HudElement_Control._HUD_ELEMENTS;
import static lacliz.hudelements.internal.HudElement_Control._LOCK;

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
        super(new TranslatableText("title.hud-elements.screen"));
    }

    @Override public boolean isPauseScreen() {
        return true;
    }

    @Override public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        super.render(matrices, mouseX, mouseY, delta);
        synchronized (_LOCK) {
            int x1, y1, x2, y2, t, bc;
            for (HudElement elt : _HUD_ELEMENTS) {
                x1 = elt.getX();
                y1 = elt.getY();
                x2 = x1 + elt.getWidth();
                y2 = y1 + elt.getHeight();
                t = elt.getAlterHudBorderThickness();
                bc = elt.getAlterHudBorderColor();
                // draw background
                DrawableHelper.fill(matrices, x1, y1, x2, y2, elt.getAlterHudBackgroundColor());
                elt.render(matrices, delta);  // draw widget
                // draw border
                Util.drawBorder(matrices, x1, y1, x2, y2, t, bc);
            }
        }
    }

    @Override public boolean mouseClicked(double mouseX, double mouseY, int button) {
        synchronized (_LOCK) {
            HudElement elt;
            int x, y;
            // reverse order, so if we click on a "top" element it won't select the ones underneath
            // (because _HUD_ELEMENTS is ordered by z-value from bottom to top)
            for (int i = _HUD_ELEMENTS.size() - 1; i >= 0; i -= 1) {
                elt = _HUD_ELEMENTS.get(i);
                // local variables so we don't invoke getX/Y() twice and get possibly-inconsistent results
                x = elt.getX();
                y = elt.getY();
                if (mouseX >= x && mouseX <= x + elt.getWidth()
                        && mouseY >= y && mouseY <= y + elt.getHeight()) {
                    if (button == 0) {
                        selectedElement = elt;
                        xOffset = mouseX - x;
                        yOffset = mouseY - y;
                        break;
                    } else if (button == 1 && selectedElement == null) {
                        elt.edit();
                        break;
                    }
                }
            }
        }
        return true;
    }

    @Override public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (button == 0) {
            HudElement elt = selectedElement;
            if (elt != null) {
                elt.save();
                selectedElement = null;
            }
        }
        return true;
    }

    @Override public void mouseMoved(double mouseX, double mouseY) {
        HudElement elt = selectedElement;
        if (elt == null) return;
        // drag element
        elt.setPos((int) (mouseX - xOffset), (int) (mouseY - yOffset));
    }

}
