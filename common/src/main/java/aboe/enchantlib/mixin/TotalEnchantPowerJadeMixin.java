package aboe.enchantlib.mixin;

import aboe.enchantlib.config.ConfigGetter;
import aboe.enchantlib.config.Configs;
import aboe.enchantlib.util.EnchantmentPowerUtil;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import snownee.jade.addon.vanilla.TotalEnchantmentPowerProvider;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.theme.IThemeHelper;
import snownee.jade.overlay.DisplayHelper;

@Mixin(TotalEnchantmentPowerProvider.class)
public abstract class TotalEnchantPowerJadeMixin implements IBlockComponentProvider {

    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        float power = EnchantmentPowerUtil.getPowerFromArea(accessor.getLevel(), accessor.getPosition(),
                ConfigGetter.getTableSize(), Configs.obType, Configs.getMoreShelves);

        if (power > 0.0F) {
            tooltip.add(Text.translatable("jade.ench_power", IThemeHelper.get().info(DisplayHelper.dfCommas.format(power))));
        }

    }
}
