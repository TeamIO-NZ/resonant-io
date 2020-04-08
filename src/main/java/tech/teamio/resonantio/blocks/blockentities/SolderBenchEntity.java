package tech.teamio.resonantio.blocks.blockentities;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DefaultedList;


public class SolderBenchEntity extends BlockEntity {

    private DefaultedList<ItemStack> fuel = DefaultedList.ofSize(1, ItemStack.EMPTY);

    public SolderBenchEntity() {
        super(IOBlockEntities.SOLDER_BENCH_TYPE);
    }


}
