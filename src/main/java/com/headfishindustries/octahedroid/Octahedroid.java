package com.headfishindustries.octahedroid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.headfishindustries.octahedroid.block.BlockOctahedroid;
import com.headfishindustries.octahedroid.client.gui.GuiProxy;
import com.headfishindustries.octahedroid.client.render.RenderItemOctahedroid;
import com.headfishindustries.octahedroid.net.MessageUpdateChannel;
import com.headfishindustries.octahedroid.proxy.CommonProxy;
import com.headfishindustries.octahedroid.tile.TileOctahedroid;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = Octahedroid.MODID, version = Octahedroid.VERSION, name = Octahedroid.NAME)
public class Octahedroid {

	public static final String MODID = "octahedroid";
	public static final String VERSION = "%gradle.version%";
	public static final String NAME = "Octahedroid";
	
	public static final Logger LOGGER = LogManager.getLogger(MODID);

	@Mod.Instance
	public static Octahedroid INSTANCE;
	
	public static final SimpleNetworkWrapper WRAPPER = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);
	static int packID = 0;
	
	private static BlockOctahedroid octa = new BlockOctahedroid();
	public static Item octa_item = new ItemBlock(octa).setRegistryName(octa.getRegistryName()).setUnlocalizedName(octa.getUnlocalizedName());
	
	@SidedProxy(serverSide="com.headfishindustries.octahedroid.proxy.CommonProxy", clientSide="com.headfishindustries.octahedroid.proxy.ClientProxy")
	public static CommonProxy proxy = null;
	
	@EventHandler
	public static void preInit(FMLPreInitializationEvent e) {
		MinecraftForge.EVENT_BUS.register(Octahedroid.class);
		proxy.preInit(e);
		
		WRAPPER.registerMessage(MessageUpdateChannel.MessageUpdateChannelHandler.class, MessageUpdateChannel.class, packID++, Side.SERVER);
		WRAPPER.registerMessage(MessageUpdateChannel.MessageUpdateChannelHandler.class, MessageUpdateChannel.class, packID++, Side.CLIENT);
	}
	
	@EventHandler
	public static void init(FMLInitializationEvent e) {
		proxy.init(e);
	}
	
	@SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
		event.getRegistry().register(octa);
		GameRegistry.registerTileEntity(TileOctahedroid.class, new ResourceLocation(MODID, "tile_octahedroid"));
	}
	
	@SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
//		octa_item.setTileEntityItemStackRenderer(new RenderItemOctahedroid());
		event.getRegistry().register(octa_item);
	}
	
	@SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
//		ForgeHooksClient.registerTESRItemStack(Octahedroid.octa_item, 0, TileOctahedroid.class);
		ModelLoader.setCustomModelResourceLocation(octa_item, 0, new ModelResourceLocation(MODID + ":octahedroid", "inventory"));
	}

}
