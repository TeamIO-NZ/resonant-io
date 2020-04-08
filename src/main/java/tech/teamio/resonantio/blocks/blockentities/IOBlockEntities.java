package tech.teamio.resonantio.blocks.blockentities;


import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import tech.teamio.resonantio.blocks.IOBlocks;
import tech.teamio.resonantio.utils.ModConstants;

public class IOBlockEntities {
    public static final BlockEntityType<CoalGeneratorBlockEntity> COAL_GENERATOR_TYPE = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(ModConstants.MOD_ID, "coal_generator"), BlockEntityType.Builder.create(CoalGeneratorBlockEntity::new, IOBlocks.COAL_GENERATOR).build(null));
    public static final BlockEntityType<SolderBenchEntity> SOLDER_BENCH_TYPE = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(ModConstants.MOD_ID, "solder_bench.json"), BlockEntityType.Builder.create(SolderBenchEntity::new, IOBlocks.SOLDER_BENCH).build(null));
    public static final BlockEntityType<SiliconeRefineryEntity> SILICONE_REFINERY_TYPE = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(ModConstants.MOD_ID, "silicone_refinery"), BlockEntityType.Builder.create(SiliconeRefineryEntity::new, IOBlocks.SILICONE_REFINERY).build(null));
    public static final BlockEntityType<ChipManufacturerBlockEntity> CHIP_MANUFACTURER_ENTITY_TYPE = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(ModConstants.MOD_ID, "chip_manufacturer"), BlockEntityType.Builder.create(ChipManufacturerBlockEntity::new, IOBlocks.CHIP_MANUFACTURER).build(null));
    public static void init() {}
}
