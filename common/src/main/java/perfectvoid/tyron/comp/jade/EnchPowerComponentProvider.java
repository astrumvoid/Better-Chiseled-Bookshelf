package perfectvoid.tyron.comp.jade;

import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import perfectvoid.tyron.EnchantLib;
import perfectvoid.tyron.util.IEnchantmentPowerProvider;
import snownee.jade.api.*;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.theme.IThemeHelper;
import snownee.jade.overlay.DisplayHelper;

public enum EnchPowerComponentProvider implements IBlockComponentProvider {
    INSTANCE;

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor blockAccessor, IPluginConfig iPluginConfig) {
        if (blockAccessor.getBlock() instanceof IEnchantmentPowerProvider powerProvider) {
            float enchPower = powerProvider.getEnchantmentPower(blockAccessor.getLevel(), blockAccessor.getPosition(), blockAccessor.getBlockState());

            if (enchPower > 0f)
                tooltip.add(Text.translatable("enchantlib.ench_power", IThemeHelper.get().info(DisplayHelper.dfCommas.format(enchPower))));
        }
    }

    @Override
    public Identifier getUid() {
        return Identifier.of(EnchantLib.MOD_ID, "enchantment_power");
    }
}
