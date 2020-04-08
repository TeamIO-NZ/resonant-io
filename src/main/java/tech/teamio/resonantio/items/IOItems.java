package tech.teamio.resonantio.items;

import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterials;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import tech.teamio.resonantio.ResonantIO;
import tech.teamio.resonantio.api.items.IOPickaxeItem;
import tech.teamio.resonantio.utils.ModConstants;

public class IOItems {
    // create items
    public static Item TIN_PICKAXE = Registry.register(
            Registry.ITEM,
            new Identifier(ModConstants.MOD_ID, "tin_pickaxe"),
            new IOPickaxeItem(
                    ToolMaterials.STONE,
                    1,
                    -2.0F,
                    new Item.Settings().group(ResonantIO.CREATIVE_GROUP)
            )
    );

    public static Item TIN_INGOT = Registry.register(
            Registry.ITEM,
            new Identifier(ModConstants.MOD_ID, "tin_ingot"),
            new Item(new Item.Settings().group(ResonantIO.CREATIVE_GROUP))
    );

    public static Item LEAD_INGOT = Registry.register(
            Registry.ITEM,
            new Identifier(ModConstants.MOD_ID, "lead_ingot"),
            new Item(new Item.Settings().group(ResonantIO.CREATIVE_GROUP))
    );

    public static Item COPPER_INGOT = Registry.register(
            Registry.ITEM,
            new Identifier(ModConstants.MOD_ID, "copper_ingot"),
            new Item(new Item.Settings().group(ResonantIO.CREATIVE_GROUP))
    );
    //Refined siliness :D
    public static Item SILICONE_INGOT = Registry.register(
            Registry.ITEM,
            new Identifier(ModConstants.MOD_ID, "silicone_ingot"),
            new Item(new Item.Settings().group(ResonantIO.CREATIVE_GROUP))
    );
    //From crafting sand.
    public static Item SILICA_DUST = Registry.register(
            Registry.ITEM,
            new Identifier(ModConstants.MOD_ID, "silica_dust"),
            new Item(new Item.Settings().group(ResonantIO.CREATIVE_GROUP))
    );


    //Plastic Ingot
    public static Item PLASTIC_INGOT = Registry.register(
         Registry.ITEM,
         new Identifier(ModConstants.MOD_ID, "plastic_ingot"),
         new Item(new Item.Settings().group(ResonantIO.CREATIVE_GROUP))
    );

    //Solder Item
public static Item SOLDER = Registry.register(
        Registry.ITEM,
            new Identifier(ModConstants.MOD_ID, "solder"),
            new Item(new Item.Settings().group(ResonantIO.CREATIVE_GROUP))
    );
    public static Item RESISTORS = Registry.register(
            Registry.ITEM,
            new Identifier(ModConstants.MOD_ID, "resistor_pack"),
            new Item(new Item.Settings().group(ResonantIO.CREATIVE_GROUP))
    );
    public static Item IC = Registry.register(
            Registry.ITEM,
            new Identifier(ModConstants.MOD_ID, "ic"),
            new Item(new Item.Settings().group(ResonantIO.CREATIVE_GROUP))
    );
    public static void init() {}
}
