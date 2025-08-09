package aboe.enchantlib.config;

import eu.midnightdust.lib.config.MidnightConfig;

public class Configs extends MidnightConfig {
    public static final String OTHERS = "others";
    public static final String BOOKPOWER = "bookpower";

    //I don't recommend you getting any of the values from here.

    //Size to look the bigger bookshelf offset
    @Entry(category = OTHERS) public static boolean biggerTable = false; //Decides if the bookshelves should be further away

    //If set to false, it will go back to the default "Last Interacted Slot", instead of the output being in pair with the items in the shelf.

    @Entry(category = OTHERS) public static boolean ModifyRedstoneOutput = false;

    //The smaller the number, the more particles you will have, this can not be 0. Default is 16.
    @Entry(category = OTHERS, min=1,max=32) public static int particleChance = 16;

    //Default power for normal books. its 0.1666666666666667f since it is 1 if multiplied by 6.
    @Entry(category = BOOKPOWER) public static float normalBookPower = 0.1666666666666667f;

    //Default power for enchanted books. It's set to be double the normal one by default.
    @Entry(category = BOOKPOWER) public static float enchantedBookPower = 0.3333333333333334f;

    //Sets the bookMultiplier for the enchanted book. Default is 2, which means that enchanted books will be two times more powerful than normal ones.
    @Entry(category = BOOKPOWER) public static boolean multiplyNormalBook = true;
    @Entry(category = BOOKPOWER, min=1) public static int bookMultiplier = 2;
}
