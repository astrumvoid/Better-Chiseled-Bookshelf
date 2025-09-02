package perfectvoid.tyron.comp.jade;

import dev.architectury.platform.Platform;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import perfectvoid.tyron.util.IEnchantmentPowerProvider;
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
    public void appendTooltip(ITooltip iTooltip, BlockAccessor blockAccessor, IPluginConfig iPluginConfig) {
        if (!blockAccessor.getServerData().contains("ench_power")) return;

        float enchPower = blockAccessor.getServerData().getFloat("ench_power");

        iTooltip.add(Text.translatable("enchantlib.ench_power", IThemeHelper.get().info(DisplayHelper.dfCommas.format(enchPower))));
    }

    @Override
    public void appendServerData(NbtCompound serverData, BlockAccessor blockAccessor) {
        if (blockAccessor.getBlock() instanceof IEnchantmentPowerProvider powerProvider) {
            if (Platform.isModLoaded("easymagic") && blockAccessor.getBlockState().isOf(Blocks.CHISELED_BOOKSHELF)) return;

            float power = powerProvider.getEnchantmentPower(blockAccessor.getLevel(), blockAccessor.getPosition(), blockAccessor.getBlockState());

            if (power > 0f) serverData.putFloat("ench_power", power);
        }
    }

    @Override
    public Identifier getUid() {
        return Identifier.ofVanilla("enchantment_power");
    }

}
