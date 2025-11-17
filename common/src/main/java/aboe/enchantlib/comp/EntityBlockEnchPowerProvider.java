package aboe.enchantlib.comp;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import aboe.enchantlib.util.IEnchantmentPowerProvider;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.theme.IThemeHelper;
import snownee.jade.overlay.DisplayHelper;

public enum EntityBlockEnchPowerProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {
    INSTANCE;

    @Override
    public void appendServerData(NbtCompound nbtCompound, BlockAccessor blockAccessor) {
        if (blockAccessor.getBlock() instanceof IEnchantmentPowerProvider powerProvider) {
            nbtCompound.putFloat("ench_power",
                    powerProvider.getEnchantmentPower(blockAccessor.getLevel(), blockAccessor.getPosition(), blockAccessor.getBlockState()));
        }
    }


    @Override
    public void appendTooltip(ITooltip iTooltip, BlockAccessor blockAccessor, IPluginConfig iPluginConfig) {
        if (blockAccessor.getServerData().contains("ench_power") && blockAccessor.getServerData().getFloat("ench_power") > 0f) {

            iTooltip.add(Text.translatable("enchantlib.ench_power", IThemeHelper.get().info(
                    DisplayHelper.dfCommas.format(blockAccessor.getServerData().getFloat("ench_power")))));
        }
    }

    public Identifier getUid() {
        return Identifier.of("minecraft", "enchantment_power");
    }
}
