package aboe.enchantlib.comp;

import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import aboe.enchantlib.EnchantLib;
import aboe.enchantlib.util.IEnchantmentPowerProvider;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.theme.IThemeHelper;
import snownee.jade.overlay.DisplayHelper;

public enum EnchPowerComponentProvider implements IBlockComponentProvider {
    INSTANCE;

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor blockAccessor, IPluginConfig iPluginConfig) {
        if (blockAccessor.getBlock() instanceof IEnchantmentPowerProvider powerProvider) {
            if (blockAccessor.getServerData().contains("ench_power")) return;

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
