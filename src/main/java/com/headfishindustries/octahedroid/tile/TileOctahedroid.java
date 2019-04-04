package com.headfishindustries.octahedroid.tile;

import java.util.ArrayList;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.headfishindustries.octahedroid.OctaWSD;
import com.headfishindustries.octahedroid.Octahedroid;
import com.headfishindustries.octahedroid.OctaWSD.Channel;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.energy.CapabilityEnergy;

public class TileOctahedroid extends TileEntity implements IEnergyStorage, IFluidHandler, IItemHandler, ITickable {
	
	private int channelID = 0;
	private ArrayList<IEnergyStorage> connections = new ArrayList<IEnergyStorage>();
	
	private OctaWSD wsd() {
		return OctaWSD.get(this.world);
	}
	
	public Channel getChannel() {
		return this.wsd().getChannel(this.channelID);
	}
	
	public void setChannelID(int c) {
		this.channelID = c;
	}
	
	public int getChannelID() {
		return this.channelID;
	}

	@Override
	public int receiveEnergy(int maxReceive, boolean simulate) {
		return this.getChannel().receiveEnergy(maxReceive, simulate);
	}

	@Override
	public int extractEnergy(int maxExtract, boolean simulate) {
		return this.getChannel().extractEnergy(maxExtract, simulate);
	}

	@Override
	public int getEnergyStored() {
		return this.getChannel().getEnergyStored();
	}

	@Override
	public int getMaxEnergyStored() {
		return this.getChannel().getMaxEnergyStored();
	}

	@Override
	public boolean canExtract() {
		return this.getChannel().canExtract();
	}

	@Override
	public boolean canReceive() {
		return this.getChannel().canReceive();
	}

	
	/** Fluid handling **/
	
/*	@Override
	public FluidStack getFluid() {
		return this.getChannel().getFluid();
	}

	@Override
	public int getFluidAmount() {
		return this.getChannel().getFluidAmount();
	}

	@Override
	public int getCapacity() {
		return this.getChannel().getCapacity();
	}

	@Override
	public FluidTankInfo getInfo() {
		return this.getChannel().getInfo();
	}

	@Override
	public int fill(FluidStack resource, boolean doFill) {
		return this.getChannel().fill(resource, doFill);
	}

	@Override
	public FluidStack drain(int maxDrain, boolean doDrain) {
		return this.getChannel().drain(maxDrain, doDrain);
	}
*/
	@Override
	public ItemStack getStackInSlot(int slot) {
		return this.getChannel().getStackInSlot(slot);
	//	return ItemStack.EMPTY;
	}
	
	public void readFromNBT(NBTTagCompound compound)
    {
		super.readFromNBT(compound);
		this.channelID = compound.getInteger("CHANNEL");
    }
	
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setInteger("CHANNEL", this.channelID);
		return super.writeToNBT(compound);
		
	}

/*	@Override
	public String getName() {
		return "Octahedroid";
	}

	@Override
	public boolean hasCustomName() {
		return false;
	}

	@Override
	public int getSizeInventory() {
		return getChannel().getSlots();
	}

	@Override
	public boolean isEmpty() {
		for (int i = 0; i < getSizeInventory(); i++) {
			if (getChannel().getStackInSlot(i) != null) return false;
		}
		return true;
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		return getChannel().extractItem(index, count, false);
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		ItemStack s = getChannel().getStackInSlot(index).copy();
		return getChannel().extractItem(index, s.getCount(), true);
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		removeStackFromSlot(index);
		getChannel().insertItem(index, stack, false);
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		return true;
	}

	@Override
	public void openInventory(EntityPlayer player) {
		
		
	}

	@Override
	public void closeInventory(EntityPlayer player) {
		
		
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return getChannel().getStackInSlot(index).isItemEqual(stack) || getChannel().getStackInSlot(index).isEmpty();
	}

	@Override
	public int getField(int id) {
		if (id == 0) {
			return getChannelID();
		}
		
		return 0;
	}

	@Override
	public void setField(int id, int value) {
		if (id == 0) {
			setChannelID(value);
		}
		
	}

	@Override
	public int getFieldCount() {
		return 1;
	}

	@Override
	public void clear() {
		//yeah we don't need to do this probably
	}

	*/
	
	/** IItemHandler **/
	
	@Override
	public int getSlots() {
		return this.getChannel().getSlots();
	}

	@Override
	public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
		return this.getChannel().insertItem(slot, stack, simulate);
	}

	@Override
	public ItemStack extractItem(int slot, int amount, boolean simulate) {
		return this.getChannel().extractItem(slot, amount, simulate);
	}

	@Override
	public int getSlotLimit(int slot) {
		return this.getChannel().getSlotLimit(slot);
	}
	
	@Override
	public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing)
	{
		return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY 
				|| capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY
				|| capability == CapabilityEnergy.ENERGY
				|| super.hasCapability(capability, facing);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY 
		|| capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY
		|| capability == CapabilityEnergy.ENERGY) {
			return (T) this;
		}
		return super.getCapability(capability, facing);
		
	}

	@Override
	public IFluidTankProperties[] getTankProperties() {
		return this.getChannel().getTankProperties();
	}
	
	@Override
	public int fill(FluidStack resource, boolean doFill) {
		return this.getChannel().fill(resource, doFill);
	}

	@Override
	public FluidStack drain(int maxDrain, boolean doDrain) {
		return this.getChannel().drain(maxDrain, doDrain);
	}

	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain) {
		return this.getChannel().drain(resource, doDrain);
	}

	@Override
	public void update() {
		if (this.world.isRemote) return;
		if (this.world.getTotalWorldTime() % 40 == 0) {
			this.updateOutputs();
		}
		
		for (IEnergyStorage s : this.connections) {
			this.extractEnergy(s.receiveEnergy(this.getEnergyStored() / this.connections.size(), false), false);
		}
		
	}
	
	void updateOutputs() {
		this.connections.clear();
		for (EnumFacing f : EnumFacing.VALUES) {
			BlockPos p = this.pos.add(f.getFrontOffsetX(), f.getFrontOffsetY(), f.getFrontOffsetZ());
			TileEntity te = this.world.getTileEntity(p);
			if (te != null && te.hasCapability(CapabilityEnergy.ENERGY, f.getOpposite())) {
				IEnergyStorage s = te.getCapability(CapabilityEnergy.ENERGY, f.getOpposite());
				if (s.canReceive()) {
					this.connections.add(s);
				}
			}
		}
	}


	
	
	
}
