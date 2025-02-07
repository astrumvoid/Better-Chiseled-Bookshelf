package aboe.bcbs.forge;

import aboe.bcbs.Better_cbs;
import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Better_cbs.MOD_ID)
public final class Better_cbsForge {
    public Better_cbsForge() {
        // Submit our event bus to let Architectury API register our content on the right time.
        EventBuses.registerModEventBus(Better_cbs.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());

        // Run our common setup.
        Better_cbs.init();
    }
}
