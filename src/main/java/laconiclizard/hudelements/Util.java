package laconiclizard.hudelements;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Matrix4f;

public class Util {

    /**
     * Draws a border around the rectangle defined by (x1,y1), (x2, y2).
     * Border does not intrude upon the defined rectangle, but instead protrudes outside of it.
     * Note that input assumes x1 <= x2 and y1 <= y2.
     *
     * @param stack     the MatrixStack into which to draw the border
     * @param x1        x-coordinate of left side of internal rectangle
     * @param y1        y-coordinate of upper side of internal rectangle
     * @param x2        x-coordinate of right side of internal rectangle
     * @param y2        y-coordinate of lower side of internal rectangle
     * @param thickness thickness of border to draw around internal rectangle
     * @param color     color of border to draw
     */
    public static void drawBorder(MatrixStack stack, float x1, float y1, float x2, float y2, int thickness, int color) {
        Matrix4f matrix = stack.peek().getModel();
        // left side (incl corners)
        fill(matrix, x1 - thickness, y1 - thickness, x1, y2 + thickness, color);
        // right side (incl corners)
        fill(matrix, x2, y1 - thickness, x2 + thickness, y2 + thickness, color);
        // top side (excl corners)
        fill(matrix, x1, y1 - thickness, x2, y1, color);
        // bottom side (excl corners)
        fill(matrix, x1, y2, x2, y2 + thickness, color);
    }

    public static void fill(Matrix4f matrix, float x1, float y1, float x2, float y2, int color) {
        // adapted from DrawableHelper.fill(...) to accept float parameters
        // normalize inputs (x2 < x1, y2 < y1)
        float j;
        if (x1 < x2) {
            j = x1;
            x1 = x2;
            x2 = j;
        }
        if (y1 < y2) {
            j = y1;
            y1 = y2;
            y2 = j;
        }

        // extract color components
        float f = (float) (color >> 24 & 255) / 255.0F;
        float g = (float) (color >> 16 & 255) / 255.0F;
        float h = (float) (color >> 8 & 255) / 255.0F;
        float k = (float) (color & 255) / 255.0F;

        // draw
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        RenderSystem.enableBlend();
        RenderSystem.disableTexture();
        RenderSystem.defaultBlendFunc();
        bufferBuilder.begin(7, VertexFormats.POSITION_COLOR);
        bufferBuilder.vertex(matrix, x1, y2, 0.0F).color(g, h, k, f).next();
        bufferBuilder.vertex(matrix, x2, y2, 0.0F).color(g, h, k, f).next();
        bufferBuilder.vertex(matrix, x2, y1, 0.0F).color(g, h, k, f).next();
        bufferBuilder.vertex(matrix, x1, y1, 0.0F).color(g, h, k, f).next();
        bufferBuilder.end();
        BufferRenderer.draw(bufferBuilder);
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
    }

}
