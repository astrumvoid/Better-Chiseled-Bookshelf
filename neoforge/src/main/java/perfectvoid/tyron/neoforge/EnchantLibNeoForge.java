package perfectvoid.tyron.neoforge;

import perfectvoid.tyron.EnchantLib;
import net.neoforged.fml.common.Mod;

@Mod(EnchantLib.MOD_ID)
public final class EnchantLibNeoForge {
    public EnchantLibNeoForge() {
        // Run our common setup.
        EnchantLib.init();
    }
}
