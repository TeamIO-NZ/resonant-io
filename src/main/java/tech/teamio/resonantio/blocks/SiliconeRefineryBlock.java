package tech.teamio.resonantio.blocks;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DefaultedList;
import net.minecraft.world.BlockView;
import tech.teamio.resonantio.blocks.blockentities.SiliconeRefineryEntity;

public class SiliconeRefineryBlock extends Block implements BlockEntityProvider {

    //Imports Minecraft.blocks or something...
    public SiliconeRefineryBlock() {
        super(FabricBlockSettings.of(Material.METAL).build());
    }

    @Override
    public BlockEntity createBlockEntity(BlockView view) {

        return new SiliconeRefineryEntity();
    }

    //Onuse is here. That calls the block entity to do it's thing.
}
