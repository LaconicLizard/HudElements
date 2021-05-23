package lacliz.hudelements.internal;

import lacliz.hudelements.api.HudElement;

import java.util.ArrayList;
import java.util.List;

public class HudElement_Control {

    /* lock used for synchronization */
    public static final Object _LOCK = new Object();

    /* List of all enabled HudElements, sorted by z-value. */
    public static final List<HudElement> _HUD_ELEMENTS = new ArrayList<>();

}
