package laconiclizard.hudelements.api;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/** A signal that can be listened to. */
public class Signal<T> {

    private final List<Consumer<T>> listeners = new ArrayList<>();

    public Signal() {
    }

    /**
     * Connect the given listener to this Signal.
     * Whenever this signal fires, listener will be invoked.
     * Listeners will be invoked in the order that they were connected.
     *
     * @param listener listener to connect to this Signal
     */
    public void connect(Consumer<T> listener) {
        listeners.add(listener);
    }

    /**
     * Disconnect the given listener from this Signal.
     *
     * @param listener listener to disconnect
     */
    public void disconnect(Consumer<T> listener) {
        listeners.remove(listener);
    }

    /**
     * Fires this signal, invoking all listeners.
     *
     * @param value value to emit to all listeners
     */
    public void fire(T value) {
        for (Consumer<T> listener : listeners) {
            listener.accept(value);
        }
    }

}
