package perfectvoid.enchantlib.config;

import eu.midnightdust.lib.config.MidnightConfig;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class Configs extends MidnightConfig {
    public static final String TABLE = "table";
    public static final String REDSTONE = "redstone";
    public static final String BOOKPOWER = "bookpower";

    @Client @Entry(category = TABLE, min=1) public static int particleChance = 16;

    //region path search
    public enum obstructionType {
        DEFAULT ,
        SOLID,
        NONE,
    }
    @Entry(category = TABLE) public static obstructionType obType = obstructionType.SOLID;
    @Entry(category = TABLE) public static boolean enableAllowList = false;
    @Condition(requiredOption = "enchantlib:enableAllowList")
    @Entry(category = TABLE) public static List<Identifier> allowList = new ArrayList<>();
    @Entry(category = TABLE) public static boolean enableDenyList = false;
    @Condition(requiredOption = "enchantlib:enableDenyList")
    @Entry(category = TABLE) public static List<Identifier> denyList = new ArrayList<>();
    //endregion path search

    // region Table Settings
    public enum tableSizeEnum {
        DEFAULT, BIGGER, MASSIVE, CUSTOM
    }
    @Entry(category = TABLE) public static tableSizeEnum tableSize = tableSizeEnum.DEFAULT;
    @Condition(requiredOption = "enchantlib:tableSize", requiredValue = "CUSTOM")
    @Entry(category = TABLE, min = 2) public static int XZSize = 4;
    @Condition(requiredOption = "enchantlib:tableSize", requiredValue = "CUSTOM")
    @Entry(category = TABLE, min = 1) public static int YSize = 3;
    @Condition(requiredOption = "enchantlib:tableSize", requiredValue = "CUSTOM")
    @Entry(category = TABLE) public static boolean yGoesDown = false;
    @Entry(category = TABLE) public static boolean getMoreShelves = false;
    //endregion

    @Client @Entry(category = TABLE) public static boolean disableLogging = false;

    //region Chiseled Bookshelf Redstone
    @Entry(category = REDSTONE) public static boolean modifyRedstoneOutput = false;
    @Condition(requiredOption = "enchantlib:modifyRedstoneOutput")
    @Entry(category = REDSTONE, max = 15) public static int normalBookPowerOutput = 1;
    @Condition(requiredOption = "enchantlib:modifyRedstoneOutput")
    @Entry(category = REDSTONE, max = 15) public static int enchantedBookPowerOutput = 2;
    //endregion

    //region Book Power
    @Entry(category = BOOKPOWER) public static float normalBookPower = 0.1666666666666667f; //Default power for normal books. its 0.1666666666666667f since it is 1 if multiplied by 6.
    @Entry(category = BOOKPOWER) public static boolean multiplyNormalBook = true;
    @Condition(requiredOption = "enchantlib:multiplyNormalBook", requiredValue = "false")
    @Entry(category = BOOKPOWER) public static float enchantedBookPower = 0.3333333333333334f; //Default power is 2x normal books.

    @Condition(requiredOption = "enchantlib:multiplyNormalBook")
    @Entry(category = BOOKPOWER, min=1) public static float multiplier = 2;
    //endregion
}
