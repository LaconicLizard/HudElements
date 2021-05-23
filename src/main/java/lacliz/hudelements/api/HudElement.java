package lacliz.hudelements.api;

import net.minecraft.client.util.math.MatrixStack;

import java.util.Comparator;

import static lacliz.hudelements.internal.HudElement_Control._HUD_ELEMENTS;
import static lacliz.hudelements.internal.HudElement_Control._LOCK;

public abstract class HudElement {

    private int x, y;
    private float z = 0;
    private int alterHudBorderColor = 0xff0000ff, alterHudBorderThickness = 1, alterHudBackgroundColor = 0x400000ff;

    public HudElement(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Save all state of this HudElement (ie. its x/y position, and possibly z-value) to disk (usually a config file).
     */
    public abstract void save();

    /**
     * Render this HUD element at its current location.
     * Warning: do not enable/disable any HudElements in this function.
     *
     * @param matrices  MatrixStack with which to render
     * @param tickDelta tickDelta from render method
     */
    public abstract void render(MatrixStack matrices, float tickDelta);

    /** The width of this HudElement. */
    public abstract int getWidth();

    /** The height of this HudElement. */
    public abstract int getHeight();

    /**
     * Begin editing this HudElement.
     * Typical implementations will open a new Screen.
     * When editing is complete, you should generally open a new AlterHudScreen, though this is not strictly required.
     *
     * @throws UnsupportedOperationException if this HudElement is not editable.
     */
    public void edit() {
        throw new UnsupportedOperationException();
    }

    // ----- ----- provided methods ----- -----

    /** Set the position of this HudElement. */
    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /** Get the x position of this HudElement. */
    public int getX() {
        return x;
    }

    /** Get the y position of this HudElement. */
    public int getY() {
        return y;
    }

    /** Set the z-value for this HudElement. */
    public void setZ(float z) {
        synchronized (_LOCK) {
            this.z = z;
            if (_HUD_ELEMENTS.contains(this)) {
                _HUD_ELEMENTS.sort(Comparator.comparing(HudElement::getZ));
            }
        }
    }

    /** Get the z-value for this HudElement. */
    public float getZ() {
        return z;
    }

    /** Sets this HudElement to display on the HUD.  Without invoking this, it will be invisible. */
    public void enable() {
        synchronized (_LOCK) {
            _HUD_ELEMENTS.add(this);
            _HUD_ELEMENTS.sort(Comparator.comparing(HudElement::getZ));
        }
    }

    /** Stop this HudElement from rendering in the HUD. */
    public void disable() {
        synchronized (_LOCK) {
            _HUD_ELEMENTS.remove(this);
            // no need to sort on removal
        }
    }

    // --- simple getters and setters -----

    /** The color of the border around this HudElement when the HUD is being altered. */
    public int getAlterHudBorderColor() {
        return alterHudBorderColor;
    }

    public void setAlterHudBorderColor(int alterHudBorderColor) {
        this.alterHudBorderColor = alterHudBorderColor;
    }

    /** The thickness of the border around this HudElement when the HUD is being altered. */
    public int getAlterHudBorderThickness() {
        return alterHudBorderThickness;
    }

    public void setAlterHudBorderThickness(int alterHudBorderThickness) {
        this.alterHudBorderThickness = alterHudBorderThickness;
    }

    /** The background color behind this HudElement when the HUD is being altered. */
    public int getAlterHudBackgroundColor() {
        return alterHudBackgroundColor;
    }

    public void setAlterHudBackgroundColor(int alterHudBackgroundColor) {
        this.alterHudBackgroundColor = alterHudBackgroundColor;
    }

}
