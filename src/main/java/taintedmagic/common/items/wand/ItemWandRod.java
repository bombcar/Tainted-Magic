package taintedmagic.common.items.wand;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import taintedmagic.common.TaintedMagic;

public class ItemWandRod extends Item {

    private static final int SUBTYPES = 2;
    private final IIcon[] icons = new IIcon[SUBTYPES];

    public ItemWandRod () {
        setCreativeTab(TaintedMagic.tabTM);
        setUnlocalizedName("ItemWandRod");
        setHasSubtypes(true);
    }

    @Override
    public EnumRarity getRarity (final ItemStack stack) {
        return EnumRarity.uncommon;
    }

    @Override
    @SideOnly (Side.CLIENT)
    public void registerIcons (final IIconRegister ir) {
        for (int i = 0; i < icons.length; i++) {
            icons[i] = ir.registerIcon("taintedmagic:ItemWandRod" + i);
        }
    }

    @Override
    @SideOnly (Side.CLIENT)
    public IIcon getIconFromDamage (final int i) {
        return icons[i];
    }

    @Override
    @SideOnly (Side.CLIENT)
    public void getSubItems (final Item item, final CreativeTabs tab, final List list) {
        for (int i = 0; i < SUBTYPES; i++) {
            list.add(new ItemStack(this, 1, i));
        }
    }

    @Override
    public String getUnlocalizedName (final ItemStack stack) {
        return super.getUnlocalizedName() + "." + stack.getItemDamage();
    }
}
