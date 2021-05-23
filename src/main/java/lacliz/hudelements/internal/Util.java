package lacliz.hudelements.internal;

import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

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
    public static void drawBorder(MatrixStack stack, int x1, int y1, int x2, int y2, int thickness, int color) {
        // left side (incl corners)
        DrawableHelper.fill(stack, x1 - thickness, y1 - thickness, x1, y2 + thickness, color);
        // right side (incl corners)
        DrawableHelper.fill(stack, x2, y1 - thickness, x2 + thickness, y2 + thickness, color);
        // top side (excl corners)
        DrawableHelper.fill(stack, x1, y1 - thickness, x2, y1, color);
        // bottom side (excl corners)
        DrawableHelper.fill(stack, x1, y2, x2, y2 + thickness, color);
    }

}
