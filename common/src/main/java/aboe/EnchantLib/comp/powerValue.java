package aboe.EnchantLib.comp;

import aboe.EnchantLib.util.IEnchantmentPowerProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public enum powerValue implements IBlockComponentProvider {
    INSTANCE;

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        if (accessor.getBlock() instanceof IEnchantmentPowerProvider power) {
            tooltip.add(Component.translatable("jade.ench_power",
                    power.getEnchantmentPower(accessor.getBlockState(), accessor.getLevel(), accessor.getPosition())));
        }
    }

    @Override
    public ResourceLocation getUid() {
        return new ResourceLocation("aboe.enchantlib", "powerlevel");
    }
}