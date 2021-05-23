package lacliz.hudelements.internal;

import lacliz.hudelements.api.HudElement;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import static lacliz.hudelements.internal.HudElement_Control._HUD_ELEMENTS;
import static lacliz.hudelements.internal.HudElement_Control._LOCK;

public class AlterHudScreen extends Screen {

    public static final Object SCCE_LOCK = new Object();
    public static boolean SHOULD_CANCEL_CHAT_EXIT = false;

    private HudElement selectedElement = null;
    private double xOffset, yOffset;

    public AlterHudScreen(Text title) {
        super(title);
    }

    @Override public boolean isPauseScreen() {
        return true;
    }

    @Override public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        super.render(matrices, mouseX, mouseY, delta);
        synchronized (_LOCK) {
            int x, y;
            for (HudElement elt : _HUD_ELEMENTS) {
                elt.render(matrices, delta);
                x = elt.getX();
                y = elt.getY();
                Util.drawBorder(matrices, x, y, x + elt.getWidth(), y + elt.getHeight(), 1, 0xff0000ff);
            }
        }
    }

    @Override public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) {
            synchronized (_LOCK) {
                HudElement elt;
                int x, y;
                // reverse order, so if we click on a "top" element it won't select the ones underneath
                for (int i = _HUD_ELEMENTS.size() - 1; i >= 0; i -= 1) {
                    elt = _HUD_ELEMENTS.get(i);
                    // local variables so we don't invoke getX/Y() twice and get possibly-inconsistent results
                    x = elt.getX();
                    y = elt.getY();
                    if (mouseX >= x && mouseX <= x + elt.getWidth()
                            && mouseY >= y && mouseY <= y + elt.getHeight()) {
                        selectedElement = elt;
                        xOffset = mouseX - x;
                        yOffset = mouseY - y;
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
