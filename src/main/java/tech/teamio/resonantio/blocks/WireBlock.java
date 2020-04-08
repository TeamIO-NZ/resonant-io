package tech.teamio.resonantio.blocks;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;

public class WireBlock extends Block {

    private static BooleanProperty ATTACHED_NORTH = BooleanProperty.of("attached_north");
    private static BooleanProperty ATTACHED_EAST = BooleanProperty.of("attached_east");
    private static BooleanProperty ATTACHED_SOUTH = BooleanProperty.of("attached_south");
    private static BooleanProperty ATTACHED_WEST = BooleanProperty.of("attached_west");
    private static BooleanProperty ATTACHED_UP = BooleanProperty.of("attached_up");
    private static BooleanProperty ATTACHED_DOWN = BooleanProperty.of("attached_down");

    public WireBlock() {
        super(FabricBlockSettings.of(Material.METAL).build());
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> factory) {
        super.appendProperties(factory);
        factory.add(ATTACHED_NORTH, ATTACHED_EAST, ATTACHED_SOUTH, ATTACHED_WEST, ATTACHED_UP, ATTACHED_DOWN);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState()
                .with(ATTACHED_NORTH, true)
                .with(ATTACHED_SOUTH, true)
                .with(ATTACHED_EAST, true)
                .with(ATTACHED_WEST, true)
                .with(ATTACHED_UP, true)
                .with(ATTACHED_DOWN, true);
    }
}
