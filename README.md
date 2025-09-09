A simple mod that makes the chiseled bookshelves better by making them give enchantment power based on many books are in it, working as a normal shelf once full. As a nice bonus, enchanted books gives double the enchantment power.

![nothing to add](https://cdn.modrinth.com/data/cached_images/b3a5399208259320870e88e7525e070a55b23c1f.png)

**Compatible with Easy Magic and Jade. (and Enchantment disabler if 1.21.5+)**

In case of bugs, report to me on **voidtyron** in discord

Can technically be used as a library, but I'm working on giving it more uses.

## Available Settings:
### Table Configs: 
- **Particle Spawn Chance (affects client only):** How frequent the particles should spawn, with 1 being pretty much always. 
- **Shelf Obstruction type:** Defines what kind of blocks will block the shelf power. Solid means only full blocks, default is Minecraft default and none means nothing will block the power.
- **Maximum Shelf Distance:** Defines how much to each side the table should search for shelves. Ex: 5 means it will search 5 block to each side of the table (Values higher than 10 might cause lag)
- **Maximum Shelf Hight:** Defines how much up/down the table should search for shelves. Again, values bigger than 10 might cause lag.
- **Should Get Shelves Below:** Makes so she "Maximum Shelf Hight" also applies for blocks below the enchantment table. Ex: Will search 5 block up and 5 block down.
### Redstone Configs:
- **Modify Chiseled Bookshelf Redstone Output:** If set to true, the comparator output will be equal to the book in the shelf instead of the last interacted slot. 
- **Normal Book Redstone Power:** Defines how much redstone power the book should give
- **Enchanted Book Redstone Power:** Defines how much redstone power the enchanted book will give
### Book Power Configs:
- **Normal Book Power:** How much power each normal book should give.
- **Multiply Normal Book Power For Enchanted Book:** If set to true, the enchanted book power will be the normal book x multiplier 
- **Enchanted Book Power Multiplier:** the multiplier for the enchanted book
- **Enchanted Book Power:** Available if the "Multiply Normal Book" is set to false, allows you to set a custom value for the enchanted book.

## For Forge Users
This mod will not receive any future update for forge (will be changed to neoforge). The reasons are:
- No newer Architectury API version for forge
- Config API that I'm using does not support newer forge versions.
