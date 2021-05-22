package lacliz.hudelements.internal;

import lacliz.hudelements.api.HudElement;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class HudElement_Control {

    /* lock used for synchronization */
    public static final Object _LOCK = new Object();

    /* List of all enabled HudElements, sorted by z-value. */
    public static final List<HudElement> _HUD_ELEMENTS = new ArrayList<>();

    public static void addElement(HudElement elt) {
        synchronized (_LOCK) {
            _HUD_ELEMENTS.add(elt);
            _HUD_ELEMENTS.sort(Comparator.comparing(HudElement::getZ));
        }
    }

    public static void removeElement(HudElement elt) {
        synchronized (_LOCK) {
            _HUD_ELEMENTS.remove(elt);
            // no need to sort on removal
        }
    }

}
