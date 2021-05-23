package lacliz.hudelements.api;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

/**
 * An example implementation of a HudElement.
 * <p>
 * Displays the text "Hello World!" at its location.
 */
public class ExampleHudElement extends HudElement {

    private final String message = "Hello World!";

    @Override public void save() {
        // normally we would save getX(), getY() and getZ() to a config file here
        // but for this example we have omitted this
    }

    private TextRenderer getTextRenderer() {
        return MinecraftClient.getInstance().textRenderer;
    }

    @Override public void render(MatrixStack matrices, float tickDelta) {
        getTextRenderer().draw(matrices, message, getX(), getY(), 0xffffff);
    }

    @Override public int getWidth() {
        return getTextRenderer().getWidth(message);
    }

    @Override public int getHeight() {
        return getTextRenderer().fontHeight;
    }

}
