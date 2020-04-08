package tech.teamio.resonantio.blocks;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.BlockView;
import tech.teamio.resonantio.blocks.blockentities.ChipManufacturerBlockEntity;
import tech.teamio.resonantio.blocks.blockentities.CoalGeneratorBlockEntity;

public class ChipManufacturerBlock extends Block implements BlockEntityProvider {

    public ChipManufacturerBlock() {
        super(FabricBlockSettings.of(Material.METAL).build());
    }

    @Override
    public BlockEntity createBlockEntity(BlockView view) {
        return new ChipManufacturerBlockEntity();
    }

}
