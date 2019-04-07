package com.headfishindustries.octahedroid;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.FluidTankProperties;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.items.IItemHandler;

public class OctaWSD extends WorldSavedData{
	
	private static final String NAME = Octahedroid.MODID + "_StorageData";
	
	private static final String CHAN_ID = "CHANNELS";
	
	public HashMap<Integer, Channel> channels = new HashMap<Integer, Channel>();
	
	public OctaWSD() {
		super(NAME);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		compound = (compound == null) ? compound : new NBTTagCompound();
		int[] channel_ids = compound.getIntArray(CHAN_ID);
		for (int v : channel_ids) {
			Channel c = new Channel();
			c.deserializeNBT(compound.getCompoundTag(CHAN_ID + "_" + v));
			channels.put(v, c);
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound = (compound == null) ? compound : new NBTTagCompound();
		
		return compound;
	}
	
	public static OctaWSD get(World w) {
		MapStorage s = w.getMapStorage();
		OctaWSD instance = (OctaWSD) s.getOrLoadData(OctaWSD.class, NAME);
		
		if (instance == null) {
			instance = new OctaWSD();
			s.setData(NAME, instance);
		}
		
		return instance;
	}
	
	public Channel getChannel(int id) {
		if (!channels.containsKey(id)) channels.put(id, new Channel());
		
		return channels.get(id);
	}
	
	public static class Channel implements IEnergyStorage, IFluidHandler, IItemHandler, INBTSerializable<NBTTagCompound>{
		
		private NonNullList<ItemStack> itemStored;
		private FluidStack fluidStored;
		private int energyStored;
		
		private final int itemCapacity;
		private final int fluidCapacity;
		private final int energyCapacity;
		
		private static final String ITEM_TAG = "items_stored";
		private static final String FLUID_TAG = "fluid_stored";
		private static final String ENERGY_TAG = "energy_stored";
		
		
		public Channel(int capacityItem, int capacityFluid, int capacityEnergy) {
			this.itemCapacity = capacityItem;
			this.fluidCapacity = capacityFluid;
			this.energyCapacity = capacityEnergy;
			
			this.itemStored = NonNullList.withSize(this.itemCapacity, ItemStack.EMPTY);
		}
		
		public Channel() {
			this(10, OctaConfig.balance.maxThroughputFluid, OctaConfig.balance.maxThroughputEnergy);
		}

		/** Item Stuff **/
		
		@Override
		public int getSlots() {
			return this.itemCapacity;
		}

		@Override
		public ItemStack getStackInSlot(int slot) {
			return (this.itemStored.get(slot) != null)
					? this.itemStored.get(slot) 
					: ItemStack.EMPTY;
		}

		@Override
		public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
			ItemStack rem = stack.copy();
			ItemStack cur = getStackInSlot(slot).copy();
			
			if (ItemStack.areItemsEqual(cur, stack) || cur.isEmpty()) {
				int d = Math.min(cur.getMaxStackSize() - cur.getCount(), rem.getCount());
				if (cur.isEmpty()) {
					cur = rem.copy();
					cur.setCount(d);
				}else {
					cur.grow(d);
				}
				
				
				if (!simulate) {
					rem.shrink(d);
					this.itemStored.set(slot, cur);
				}
			}
			return rem;
		}

		@Override
		public ItemStack extractItem(int slot, int amount, boolean simulate) {
			int d = Math.min(getStackInSlot(slot).getCount(), amount);
			
			if (!simulate) {
				return this.itemStored.get(slot).splitStack(d);
			}else {
				ItemStack s = getStackInSlot(slot).copy();
				s.setCount(d);
				return s;
			}
		}

		@Override
		public int getSlotLimit(int slot) {
			return getStackInSlot(slot).getMaxStackSize();
		}

		
		/** Liquid goodness **/
		
/**		@Override
		public FluidStack getFluid() {
			return this.fluidStored;
		}

		@Override
		public int getFluidAmount() {
			return this.fluidStored.amount;
		}

		@Override
		public int getCapacity() {
			return this.fluidCapacity;
		}

		@Override
		public FluidTankInfo getInfo() {
			return new FluidTankInfo(this.fluidStored, this.fluidCapacity);
		} **/

		@Override
		public int fill(FluidStack resource, boolean doFill) {
			FluidStack r = resource.copy();
			int d;
			if (r.isFluidEqual(this.fluidStored)) 
				d = Math.min(this.fluidCapacity - this.fluidStored.amount, r.amount);
			else if (this.fluidStored == null) 
				d = Math.min(this.fluidCapacity, r.amount);
			else
				d = 0;
			
			if (doFill) {
				if (this.fluidStored == null) {
					this.fluidStored = r.copy();
					this.fluidStored.amount = d;
				}else {
					this.fluidStored.amount += d;
					r.amount -= d;
				}
			}
			
			return r.amount;
		}

		@Override
		public FluidStack drain(int maxDrain, boolean doDrain) {
			int d;
			
			if (this.fluidStored == null) return new FluidStack(FluidRegistry.WATER, 0, null);
			
			d = Math.min(this.fluidStored.amount, maxDrain);
			
			if (doDrain) this.fluidStored.amount -= d;
			
			return new FluidStack(this.fluidStored, d);
		}
		
		@Override
		public IFluidTankProperties[] getTankProperties() {
			return new FluidTankProperties[] {new FluidTankProperties(this.fluidStored, fluidCapacity)};
		}

		@Override
		public FluidStack drain(FluidStack resource, boolean doDrain) {
			int d;
			if (this.fluidStored == null) return new FluidStack(FluidRegistry.WATER, 0, null);
			return null;
		}
		

		/** EnergyStuff **/
		
		@Override
		public int receiveEnergy(int maxReceive, boolean simulate) {
			int d = Math.min(maxReceive, this.energyCapacity - this.energyStored);
			if (!simulate) this.energyStored += d;
			return d;
		}

		@Override
		public int extractEnergy(int maxExtract, boolean simulate) {
			int d = Math.min(maxExtract, this.energyStored);
			if (!simulate) this.energyStored -= d;
			return d;
		}

		@Override
		public int getEnergyStored() {
			return this.energyStored;
		}

		@Override
		public int getMaxEnergyStored() {
			return this.energyCapacity;
		}

		@Override
		public boolean canExtract() {
			return true;
		}

		@Override
		public boolean canReceive() {
			return true;
		}

		@Override
		public NBTTagCompound serializeNBT() {
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setInteger(ENERGY_TAG, this.energyStored);
			NBTTagCompound cmp = new NBTTagCompound();
			nbt.setTag(FLUID_TAG, this.fluidStored.writeToNBT(cmp));
			NBTTagList l = new NBTTagList();
			for (int i = 0; i < this.itemCapacity; i++) {
				l.set(i, this.itemStored.get(i).serializeNBT());
			}
			nbt.setTag(ITEM_TAG, l);
			return nbt;
		}

		@Override
		public void deserializeNBT(NBTTagCompound nbt) {
			this.energyStored = nbt.getInteger(ENERGY_TAG);
			this.fluidStored = FluidStack.loadFluidStackFromNBT(nbt.getCompoundTag(FLUID_TAG));
			
			for (int i = 0; i < this.itemCapacity; i++) {
				this.itemStored.get(i).deserializeNBT(nbt.getTagList(ITEM_TAG, 10).getCompoundTagAt(i));
			}
			
		}

		
	}

}
