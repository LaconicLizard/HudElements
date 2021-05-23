# HudElements

_Movable HUD components for Minecraft._

This is a Fabric Mod.  Forge is not and will not be supported.

This is a library mod for Minecraft that provides an API for mods to provide user-customizable HUD components.  These components can be then moved around by clicking and dragging them in the `/alterhud` screen, or edited by right-clicking them in the same.  

### Dependencies

This mod requires [Fabric API](https://github.com/FabricMC/fabric), version `0.32.5+1.16` or later (just get the latest release unless you have a specific reason not to).

### Using this Mod: Players
If you are a player, simply download the normal jar (not the `sources` or `dev` ones) and add it to your mods folder.  Make sure you have Fabric API!

### Using this Mod: Devs

If you are a dev, add the following to your `build.gradle`:
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
See the [jitpack website](https://jitpack.io/) for more information.  

After that, extend the `HudElement` class with the element you wish to create; see the documentation on that class and the example implementation in `ExampleHudElement` for instructions/examples of how to use it.   