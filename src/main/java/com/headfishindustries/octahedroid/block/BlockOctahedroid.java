package com.headfishindustries.octahedroid.block;

import com.headfishindustries.octahedroid.Octahedroid;
import com.headfishindustries.octahedroid.net.MessageUpdateChannel;
import com.headfishindustries.octahedroid.tile.TileOctahedroid;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockOctahedroid extends Block implements ITileEntityProvider{

	public BlockOctahedroid() {
		super(Material.IRON);
		setUnlocalizedName(Octahedroid.MODID + "_octahedroid");
		setRegistryName(Octahedroid.MODID, "octahedroid");
		setCreativeTab(CreativeTabs.DECORATIONS);
		setHardness(2f);
		setResistance(800f);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileOctahedroid();
	}
	
	 @SideOnly(Side.CLIENT)
	 public BlockRenderLayer getBlockLayer()
	 {
		 return BlockRenderLayer.SOLID;
	 }
	 
	 @Override
	 public boolean isOpaqueCube(IBlockState s) {
		 return false;
	 }
	 
	 @Override
	 public EnumBlockRenderType getRenderType(IBlockState s) {
		 return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
	 }


	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (world.isRemote) return true;
		TileOctahedroid te = (TileOctahedroid) world.getTileEntity(pos);
		Octahedroid.WRAPPER.sendTo(new MessageUpdateChannel(pos, te.getChannelID()), (EntityPlayerMP) player);
		player.openGui(Octahedroid.INSTANCE, 1, world, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}

	
}
