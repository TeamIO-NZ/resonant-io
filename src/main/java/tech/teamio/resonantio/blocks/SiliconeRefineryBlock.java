package tech.teamio.resonantio.blocks;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.DefaultedList;
import net.minecraft.world.BlockView;
import tech.teamio.resonantio.blocks.blockentities.SiliconeRefineryEntity;

public class SiliconeRefineryBlock extends Block implements BlockEntityProvider {

    private static final BooleanProperty active = BooleanProperty.of("active");
    private static final DirectionProperty facing = Properties.HORIZONTAL_FACING;

    //Imports Minecraft.blocks or something...
    public SiliconeRefineryBlock() {
        super(FabricBlockSettings.of(Material.METAL).build());
    }

    @Override
    public BlockEntity createBlockEntity(BlockView view) {

        return new SiliconeRefineryEntity();
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(active);
        builder.add(facing);
    }


    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx)  {
        return this.getDefaultState()
                .with(active, false)
                .with(facing, ctx.getPlayerFacing().getOpposite());
    }

    //Onuse is here. That calls the block entity to do it's thing.
}
