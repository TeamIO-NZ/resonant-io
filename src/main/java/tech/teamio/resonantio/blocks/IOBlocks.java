package tech.teamio.resonantio.blocks;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import tech.teamio.resonantio.ResonantIO;
import tech.teamio.resonantio.utils.ModConstants;

public class IOBlocks {
    //Creating blocks
    public static Block LEAD_ORE = registerBlock(new Block(FabricBlockSettings.of(Material.STONE).build()), "lead_ore");
    public static Block TIN_ORE = registerBlock(new Block(FabricBlockSettings.of(Material.STONE).build()), "tin_ore");
    public static Block COPPER_ORE = registerBlock(new Block(FabricBlockSettings.of(Material.STONE).build()), "copper_ore");


    // power stuffs blocks
    public static Block COAL_GENERATOR = registerBlock(new CoalGeneratorBlock(), "coal_generator");
    public static Block WIRE = registerBlock(new WireBlock(), "wire");
    public static Block SILICONE_REFINERY = registerBlock(new SiliconeRefineryBlock(), "silicone_refinery");
    public static Block SOLDER_BENCH = registerBlock(new SolderBenchBlock(),"solder_bench");
    public static Block CHIP_MANUFACTURER = registerBlock(new ChipManufacturerBlock(), "chip_manufacturer");

    private static Block registerBlockWithoutItem(Block block, String id) {
        return Registry.register(Registry.BLOCK, new Identifier(ModConstants.MOD_ID, id), block);
    }

    private static Block registerBlock(Block block, String id) {
        Block registered = registerBlockWithoutItem(block, id);
        Registry.register(Registry.ITEM, new Identifier(ModConstants.MOD_ID, id), new BlockItem(registered, new Item.Settings().group(ResonantIO.CREATIVE_GROUP)));
        return registered;
    }

    public static void init() {}

}
