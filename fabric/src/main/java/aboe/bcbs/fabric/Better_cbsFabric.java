package aboe.bcbs.fabric;

import aboe.bcbs.Better_cbs;
import net.fabricmc.api.ModInitializer;

public final class Better_cbsFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        // Run our common setup.
        Better_cbs.init();
    }
}
