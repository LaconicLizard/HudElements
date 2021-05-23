package laconiclizard.hudelements.api;

import laconiclizard.hudelements.AlterHudScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;

/**
 * An example implementation of a HudElement.
 * <p>
 * Displays a fixed string at its location.
 */
public class ExampleHudElement extends HudElement {

    private String message;

    public ExampleHudElement(String message) {
        super(0, 0);
        this.message = message;
    }

    @Override public void save() {
        // normally we would save getX(), getY() and getZ() to a config file here
        // but for this example we have omitted this
    }

    private static TextRenderer getTextRenderer() {
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

    @Override public void edit() {
        // open a screen to edit .message
        MinecraftClient.getInstance().openScreen(new EditExampleHudElementScreen(this));
    }

    /** Screen that has a single TextFieldWidget and a confirm button, used to edit the message of an ExampleHudElement. */
    public static class EditExampleHudElementScreen extends Screen {

        public static int WIDTH = 100;

        public ExampleHudElement elt;

        private TextFieldWidget textFieldWidget;

        public EditExampleHudElementScreen(ExampleHudElement elt) {
            super(new LiteralText("Edit Message"));
            this.elt = elt;
        }

        @Override protected void init() {
            super.init();
            Window w = MinecraftClient.getInstance().getWindow();
            TextRenderer tr = MinecraftClient.getInstance().textRenderer;
            int h = tr.fontHeight + 5;
            // text field to edit message
            textFieldWidget = new TextFieldWidget(tr,
                    (w.getScaledWidth() - WIDTH) / 2, (w.getScaledHeight() - h) / 2,
                    WIDTH, h,
                    new LiteralText("Edit Message"));
            textFieldWidget.setText(elt.message);
            addChild(textFieldWidget);
            // button to confirm edits
            addButton(new ButtonWidget((w.getScaledWidth() - WIDTH) / 2, (w.getScaledHeight() + h) / 2,
                    WIDTH, h,
                    new LiteralText("Confirm"),
                    buttonWidget -> {
                        // save edits
                        String newMessage = textFieldWidget.getText();
                        elt.message = newMessage.isEmpty() ? "<empty>" : newMessage;
                        // close screen
                        this.onClose();
                    }));
        }

        @Override public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
            super.render(matrices, mouseX, mouseY, delta);
            textFieldWidget.render(matrices, mouseX, mouseY, delta);
        }

        @Override public void onClose() {
            // if screen is closed without pressing confirm, then do not save edits
            MinecraftClient.getInstance().openScreen(new AlterHudScreen());
        }

    }

}
