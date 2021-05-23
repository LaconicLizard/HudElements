# HudElements

_Movable HUD components for Minecraft._

This is a Fabric mod.  Forge is not and will not be supported.

This is a client-side library mod for Minecraft that provides an API for modders to provide user-customizable HUD 
components.  These components can be then moved around by clicking and dragging them in the `/alterhud` screen, or 
edited by right-clicking them in the same.  

### Dependencies

This mod requires [Fabric API](https://github.com/FabricMC/fabric), version `0.32.5+1.16` or later (just get the 
latest release unless you have a specific reason not to).

### Using this Mod: Players
If you are a player, simply download the normal jar (not the `sources` or `dev` ones) and add it to your mods folder.  
Make sure you have Fabric API!

This mod does nothing on its own - make sure to get a mod that requires it.

Once you are using this mod, execute the command `/alterhud` to begin customizing your HUD.  All HUD components that 
support alteration will show a colored border and/or background.  You may click and drag on these to move them around, 
or right click on them to edit them further - note that not all mods support editing; if right-clicking doesn't do 
anything, then that element is likely not editable.  To exit this screen, press escape.

### Using this Mod: Modders

If you are a dev and wish to use `HudElements` in your mod, add the following to your `build.gradle`:
```
repositories {
    ... other repositories, if any
    maven { url 'https://jitpack.io' }
}

dependencies {
    ... other dependencies
    modImplementation "com.github.LaconicLizard:HudElements:<version>"
}
```
See the [jitpack website](https://jitpack.io/) for more information on how this works.  Also make sure to add 
`hudelements` as a dependency in your `fabric.mod.json` file!

After that, extend the `HudElement` class with the element you wish to create; see the documentation on that class 
and the example implementation in `ExampleHudElement` for instructions/examples of how to use it.   