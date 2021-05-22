package lacliz.hudelements.api;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;

/**
 * An example implementation of a HudElement.
 * <p>
 * Displays the text "Hello!" at its location.
 */
public class ExampleHudElement extends HudElement {

    private int x, y;

    @Override public void setPos(int x, int y) {
        // normally you would write to config file here, but for this example we will store position in a local variable.
        // note that this means the position of this HudElement will not be preserved when the user restarts the game!
        this.x = x;
        this.y = y;
    }

    @Override public void render(MatrixStack matrices, float tickDelta) {
        TextRenderer tr = MinecraftClient.getInstance().textRenderer;
        tr.draw(matrices, "Hello!", x, y, 0xffffff);
    }

}
