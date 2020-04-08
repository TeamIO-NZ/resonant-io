package tech.teamio.resonantio.api.items;

import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolMaterial;

public class IOPickaxeItem extends PickaxeItem {
    public IOPickaxeItem(ToolMaterial material, int attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }
}
