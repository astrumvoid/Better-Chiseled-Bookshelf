package aboe.enchantlib.forge;

import aboe.enchantlib.EnchantLib;
import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(EnchantLib.MOD_ID)
public final class EnchantLibForge {
    public EnchantLibForge() {
        // Submit our event bus to let Architectury API register our content on the right time.
        EventBuses.registerModEventBus(EnchantLib.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());

        // Run our common setup.
        EnchantLib.init();
    }
}
