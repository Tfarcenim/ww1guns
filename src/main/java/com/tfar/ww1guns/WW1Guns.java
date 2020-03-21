package com.tfar.ww1guns;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import org.apache.logging.log4j.Logger;

import java.util.HashSet;
import java.util.Set;

@Mod(modid = WW1Guns.MODID, name = WW1Guns.NAME, version = WW1Guns.VERSION)
@Mod.EventBusSubscriber
public class WW1Guns {
    public static final String MODID = "ww1guns";
    public static final String NAME = "WW1 Guns";
    public static final String VERSION = "0.0";

    private static Logger logger;

    public static final Set<Item> guns = new HashSet<>();

    @SubscribeEvent
    public static void items(RegistryEvent.Register<Item> e){
        registerItem(new GunItem(new GunSettings().setDamage(4).setMagazineSize(8)),"ruby",e.getRegistry());
        registerItem(new GunItem(new GunSettings().setDamage(4).setMagazineSize(8)),"selb1906schrubin",e.getRegistry());
        registerItem(new GunItem(new GunSettings().setDamage(4).setMagazineSize(8)),"springfield1903",e.getRegistry());
        registerItem(new GunItem(new GunSettings().setDamage(4).setMagazineSize(8)),"wz1910",e.getRegistry());
    }

    @SubscribeEvent
    public static void models(ModelRegistryEvent e){
        guns.forEach(WW1Guns::setModel);
    }

    public static void setModel(Block item) {
        setModel(Item.getItemFromBlock(item));
    }

    public static void setModel(Item item) {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }

    public static void registerItem(Item item, String name,IForgeRegistry<Item> registry){
        registerItem(item,new ResourceLocation(MODID,name),registry);
    }

    public static void registerItem(Item item, ResourceLocation name,IForgeRegistry<Item> registry){
        registry.register(item.setRegistryName(name).setCreativeTab(CreativeTabs.COMBAT).setTranslationKey(name.toString().replace(':','.')));
        guns.add(item);
    }
}
