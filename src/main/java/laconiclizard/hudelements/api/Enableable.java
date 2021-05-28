package laconiclizard.hudelements.api;

/** A generic interface for things that can be en/disabled */
public interface Enableable {

    /** Enable this, assuming that it is already disabled. */
    void enableStrict();

    /** Disable this, assuming that it is already disabled. */
    void disableStrict();

    /** Whether this is currently enabled. */
    boolean isEnabled();

    /**
     * Ensure that this is enabled.
     *
     * @return whether the state of this object changed as a result of this call
     */
    default boolean enable() {
        if (!isEnabled()) {
            enableStrict();
            return true;
        }
        return false;
    }

    /**
     * Ensure that this is disabled.
     *
     * @return whether the state of this object changed as a result of this call
     */
    default boolean disable() {
        if (isEnabled()) {
            disableStrict();
            return true;
        }
        return false;
    }

    /**
     * Toggle whether this is en/disabled.
     *
     * @return whether this is now enabled
     */
    default boolean toggle() {
        if (isEnabled()) {
            disableStrict();
            return false;
        } else {
            enableStrict();
            return true;
        }
    }

    /**
     * Set whether this is enabled.
     *
     * @param enable whether this should be enabled
     * @return whether the state of this object changed as a result of this call
     */
    default boolean setEnabled(boolean enable) {
        boolean isEnabled = isEnabled();
        if (isEnabled && !enable) {
            disableStrict();
            return true;
        } else if (!isEnabled && enable) {
            enableStrict();
            return true;
        }
        return false;
    }

}
