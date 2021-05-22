package lacliz.hudelements.api;

import lacliz.hudelements.internal.HudElement_Control;
import net.minecraft.client.util.math.MatrixStack;

public abstract class HudElement {

    protected float z = 0;

    /**
     * Set the position of this HudElement.
     * Typical implementations will involve writing to a configuration file.
     *
     * @param x new x coordinate for this HudElement
     * @param y new y coordinate for this HudElement
     */
    public abstract void setPos(int x, int y);

    /**
     * Render this HUD element at its current location.
     * Warning: do not enable/disable any HudElements in this function.
     *
     * @param matrices  MatrixStack with which to render
     * @param tickDelta tickDelta from render method
     */
    public abstract void render(MatrixStack matrices, float tickDelta);

    /**
     * Set the z-value of this HudElement relative to other HudElements.
     *
     * @param z new z-value for this HudElement
     */
    public void setZ(float z) {
        this.z = z;
    }

    /**
     * Get the current z-value of this HudElement.
     *
     * @return the current z-value of this HudElement
     */
    public float getZ() {
        return z;
    }

    /** Sets this HudElement to display on the HUD.  Without invoking this, it will be invisible. */
    public void enable() {
        HudElement_Control.addElement(this);
    }

    /** Stop this HudElement from rendering in the HUD. */
    public void disable() {
        HudElement_Control.removeElement(this);
    }

}
