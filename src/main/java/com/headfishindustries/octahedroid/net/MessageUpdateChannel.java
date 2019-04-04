package com.headfishindustries.octahedroid.net;

import com.headfishindustries.octahedroid.tile.TileOctahedroid;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class MessageUpdateChannel implements IMessage{
	
	private BlockPos pos = new BlockPos(0, 0, 0);
	private int id = 0;
	
	public MessageUpdateChannel() {
		
	}

	public MessageUpdateChannel(BlockPos pos, int newID) {
		this.pos = pos;
		this.id = newID;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.pos = BlockPos.fromLong(buf.readLong());
		this.id = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeLong(pos.toLong());
		buf.writeInt(id);
	}
	
	public static class MessageUpdateChannelHandler implements IMessageHandler<MessageUpdateChannel, IMessage>{

		@Override
		public IMessage onMessage(MessageUpdateChannel message, MessageContext ctx) {
			
			
			if (ctx.side == Side.CLIENT) {
				WorldClient w = Minecraft.getMinecraft().world;
				((TileOctahedroid) w.getTileEntity(message.pos)).setChannelID(message.id);
				
			}else {
				WorldServer w = ctx.getServerHandler().player.getServerWorld();
			
				w.addScheduledTask(() -> {
					((TileOctahedroid) w.getTileEntity(message.pos)).setChannelID(message.id);
					w.getTileEntity(message.pos).markDirty();
				});
			
				
			}
			
			return null;
		}
		
	}

}
