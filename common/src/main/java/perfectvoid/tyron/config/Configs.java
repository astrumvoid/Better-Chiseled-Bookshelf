package perfectvoid.tyron.config;

import eu.midnightdust.lib.config.MidnightConfig;

public class Configs extends MidnightConfig {
    public static final String TABLE = "table";
    public static final String REDSTONE = "redstone";
    public static final String BOOKPOWER = "bookpower";

    //The smaller the number, the more particles you'll have. Default is 16.
    @Client
    @Entry(category = TABLE, min=1) public static int particleChance = 16;

    public enum obstructionType{
        DEFAULT, SOLID, NONE
    }
    @Entry(category = TABLE) public static obstructionType obType = obstructionType.SOLID;

    //Size to which the table will look for shelves
    public enum tableSizeEnum {
        DEFAULT, BIGGER, CUSTOM
    }
    @Entry(category = TABLE) public static tableSizeEnum tableSize = tableSizeEnum.DEFAULT; //Decides if the bookshelves should be further away
    @Condition(requiredOption = "enchantlib:tableSize", requiredValue = "CUSTOM")
    @Entry(category = TABLE, min = 2) public static int XZSize = 4;
    @Condition(requiredOption = "enchantlib:tableSize", requiredValue = "CUSTOM")
    @Entry(category = TABLE, min = 2) public static int YSize = 3;
    @Condition(requiredOption = "enchantlib:tableSize", requiredValue = "CUSTOM")
    @Entry(category = TABLE) public static boolean yGoesDown = false;

    //If set to false, it will go back to the default "Last Interacted Slot", instead of the output being in pair with the items in the shelf.
    @Entry(category = REDSTONE) public static boolean modifyRedstoneOutput = false;
    @Condition(requiredOption = "enchantlib:modifyRedstoneOutput")
    @Entry(category = REDSTONE, max = 15) public static int normalBookPowerOutput = 1;
    @Condition(requiredOption = "enchantlib:modifyRedstoneOutput")
    @Entry(category = REDSTONE, max = 15) public static int enchantedBookPowerOutput = 2;


    //Default power for normal books. its 0.1666666666666667f since it is 1 if multiplied by 6.
    @Entry(category = BOOKPOWER) public static float normalBookPower = 0.1666666666666667f;

    //Sets the bookMultiplier for the enchanted book. Default is 2, which means that enchanted books will be two times more powerful than normal ones.
    @Entry(category = BOOKPOWER) public static boolean multiplyNormalBook = true;

    //Default power for enchanted books. It's set to be double the normal one by default.
    @Condition(requiredOption = "enchantlib:multiplyNormalBook", requiredValue = "false")
    @Entry(category = BOOKPOWER) public static float enchantedBookPower = 0.3333333333333334f;

    @Condition(requiredOption = "enchantlib:multiplyNormalBook")
    @Entry(category = BOOKPOWER, min=1) public static int multiplier = 2;
}
