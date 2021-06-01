package laconiclizard.hudelements.api;

import net.minecraft.client.util.math.MatrixStack;

import java.util.Comparator;

import static laconiclizard.hudelements.HudElements.HUD_ELEMENTS;
import static laconiclizard.hudelements.HudElements.HUD_ELEMENTS_LOCK;

public abstract class HudElement implements Enableable {

    /* Fires immediately before the /alterhud screen is opened. */
    public static final Signal<Void> PRE_ALTERHUD = new Signal<>();
    /* Fires immediately after the /alterhud screen closes. */
    public static final Signal<Void> POST_ALTERHUD = new Signal<>();

    /* Lock which is acquired whenever state is being read/altered,
     * to ensure that only fully-consistent states are read. */
    public final Object lock = new Object();

    private float x, y, z;  // floats because rendering only has float-level precision anyways
    private boolean isEnabled = false;

    public HudElement() {
    }

    /**
     * Save all state of this HudElement (eg. its x/y position, and possibly z-value) to disk (usually a config file).
     * This method should assume that .lock has already been acquired.
     */
    public abstract void save();

    /**
     * Render this HUD element at its current location.
     * This method should assume that .lock has already been acquired.
     * <p>
     * Warning: do not enable/disable any HudElements in this function; doing so will lead to deadlock.
     *
     * @param matrices  MatrixStack with which to render
     * @param tickDelta tickDelta from render method
     */
    public abstract void render(MatrixStack matrices, float tickDelta);

    /**
     * The width of this HudElement.
     * This method should assume that .lock has already been acquired.
     *
     * @return the width of this HudElement
     */
    public abstract float getWidth();

    /**
     * The height of this HudElement.
     * This method should assume that .lock has already been acquired.
     *
     * @return the height of this HudElement
     */
    public abstract float getHeight();

    /**
     * Whether this HudElement may be edited via the .edit() method.
     * This method should assume that .lock has already been acquired.
     *
     * @return whether this HudElement may be edited via the .edit() method
     */
    public boolean isEditable() {
        return false;
    }

    /**
     * Begin editing this HudElement.
     * Typical implementations will open a new Screen.
     * When editing is complete, you should generally open a new AlterHudScreen, though this is not strictly required.
     * <p>
     * This method should assume that .lock has already been acquired.
     *
     * @throws UnsupportedOperationException if this HudElement is not editable.
     */
    public void edit() {
        throw new UnsupportedOperationException();
    }

    // ----- ----- provided methods ----- -----

    // ----- getters and setters -----

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        synchronized (HUD_ELEMENTS_LOCK) {
            this.z = z;
            if (isEnabled) {
                HUD_ELEMENTS.sort(Comparator.comparing(HudElement::getZ));
            }
        }
    }

    // ----- implementation -----

    @Override public void enableStrict() {
        synchronized (HUD_ELEMENTS_LOCK) {
            if (!isEnabled) {
                HUD_ELEMENTS.add(this);
                HUD_ELEMENTS.sort(Comparator.comparing(HudElement::getZ));
                isEnabled = true;
            }
        }
    }

    @Override public void disableStrict() {
        synchronized (HUD_ELEMENTS_LOCK) {
            if (isEnabled) {
                HUD_ELEMENTS.remove(this);
                // no need to sort on removal
                isEnabled = false;
            }
        }
    }

    @Override public boolean isEnabled() {
        return isEnabled;
    }

    // ----- /alterhud properties -----

    /** The color of the border around this HudElement when the HUD is being altered (via the /alterhud command). */
    public int alterHudBorderColor() {
        return 0xffffffff;
    }

    /** The thickness of the border around this HudElement when the HUD is being altered (via the /alterhud command). */
    public float alterHudBorderThickness() {
        return 1;
    }

    /** The background color behind this HudElement when the HUD is being altered (via the /alterhud command). */
    public int alterHudBackgroundColor() {
        return 0;
    }

}
