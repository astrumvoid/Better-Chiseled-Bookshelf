package aboe.enchantlib.config;

import eu.midnightdust.lib.config.MidnightConfig;

import java.util.ArrayList;
import java.util.List;

public class Configs extends MidnightConfig {
    public static final String TABLE = "table";
    public static final String TABLESIZE = "tablesize";
    public static final String REDSTONE = "redstone";
    public static final String BOOKPOWER = "bookpower";

    @Client
    @Entry(category = TABLE, min = 1)
    public static int particleChance = 16;

    //region path search
    public enum obstructionType {
        DEFAULT,
        SOLID,
        NONE,
    }

    @Entry(category = TABLE)
    public static obstructionType obType = obstructionType.SOLID;
    @Entry(category = TABLE)
    public static boolean enableAllowList = false;
    @Entry(category = TABLE)
    public static List<String> allowList = new ArrayList<>();
    @Entry(category = TABLE)
    public static boolean enableDenyList = false;
    @Entry(category = TABLE)
    public static List<String> denyList = new ArrayList<>();
    @Entry(category = TABLE)
    public static boolean getMoreShelves = false;
    //endregion path search

    // region TableSize Settings
    public enum tableSizeEnum {
        DEFAULT, BIGGER, MASSIVE, CUSTOM
    }

    @Entry(category = TABLESIZE)
    public static tableSizeEnum tableSize = tableSizeEnum.DEFAULT;
    @Entry(category = TABLESIZE, min = 2)
    public static int XZSize = 4;
    @Entry(category = TABLESIZE, min = 1)
    public static int YSize = 3;
    @Entry(category = TABLESIZE)
    public static boolean yGoesDown = false;
    //endregion

    @Client
    @Entry(category = TABLE)
    public static boolean disableLogging = false;

    //region Chiseled Bookshelf Redstone
    @Entry(category = REDSTONE)
    public static boolean modifyRedstoneOutput = false;
    @Entry(category = REDSTONE, max = 15)
    public static int normalBookPowerOutput = 1;
    @Entry(category = REDSTONE, max = 15)
    public static int enchantedBookPowerOutput = 2;
    //endregion

    //region Book Power
    @Entry(category = BOOKPOWER)
    public static float normalBookPower = 0.1666666666666667f; //Default power for normal books. its 0.1666666666666667f since it is 1 if multiplied by 6.
    @Entry(category = BOOKPOWER)
    public static boolean multiplyNormalBook = true;
    @Entry(category = BOOKPOWER)
    public static float enchantedBookPower = 0.3333333333333334f; //Default power is 2x normal books.


    @Entry(category = BOOKPOWER, min = 1)
    public static float multiplier = 2;
    //endregion
}
