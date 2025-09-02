package perfectvoid.tyron.comp.jade;

import net.minecraft.block.Block;
import net.minecraft.block.BlockWithEntity;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public class EnchPowerPlugin implements IWailaPlugin {

    public void register(IWailaCommonRegistration registration) {
        registration.registerBlockDataProvider(EntityBlockEnchPowerProvider.INSTANCE, BlockWithEntity.class);
    }

    public void registerClient(IWailaClientRegistration registration) {
        registration.registerBlockComponent(EntityBlockEnchPowerProvider.INSTANCE, BlockWithEntity.class);
        registration.registerBlockComponent(EnchPowerComponentProvider.INSTANCE, Block.class);
    }
}
