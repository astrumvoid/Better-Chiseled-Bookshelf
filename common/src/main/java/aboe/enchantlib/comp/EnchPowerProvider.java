package aboe.enchantlib.comp;

import aboe.enchantlib.util.EnchantmentPowerUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.Identifiers;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.theme.IThemeHelper;
import snownee.jade.overlay.DisplayHelper;

public enum EnchPowerProvider implements IBlockComponentProvider {
    INSTANCE;

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor blockAccessor, IPluginConfig iPluginConfig) {
        float enchPower = EnchantmentPowerUtils.getCurrentBlockPower(blockAccessor.getLevel(), blockAccessor.getPosition(), blockAccessor.getBlockState());
        if (enchPower > 0f) {
            tooltip.add(Component.translatable("jade.ench_power", IThemeHelper.get().info(DisplayHelper.dfCommas.format(enchPower))));
        }
    }

    @Override
    public ResourceLocation getUid() {
        return Identifiers.MC_ENCHANTMENT_POWER;
    }
}
