package tech.teamio.resonantio.blocks.blockentities;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DefaultedList;

public class SiliconeRefineryEntity extends BlockEntity {


    private boolean active;
    //Two items in a list. Each are empty.
    protected DefaultedList<ItemStack> inventory = DefaultedList.ofSize(2, ItemStack.EMPTY);


    public SiliconeRefineryEntity() {
        super(IOBlockEntities.SILICONE_REFINERY_TYPE);
    }



}
