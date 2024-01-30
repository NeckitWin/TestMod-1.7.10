package com.neckitwin.testmod.common.network;

import com.neckitwin.testmod.common.tile.TileCollector;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;

public class RadiusPacket implements IMessage {
    private int radius;
    private int x, y, z;

    public RadiusPacket(int radius, int x, int y, int z) {
        this.radius = radius;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        radius = buf.readInt();
        x = buf.readInt();
        y = buf.readInt();
        z = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(radius);
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
    }

    public static class Handler implements IMessageHandler<RadiusPacket, IMessage> {
        @Override
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(RadiusPacket message, MessageContext ctx) {
            if(ctx.side == Side.CLIENT){
                TileEntity tileEntity = Minecraft.getMinecraft().theWorld.getTileEntity(message.x, message.y, message.z);
                if (tileEntity instanceof TileCollector) {
                    TileCollector tileCollector = (TileCollector) tileEntity;
                    tileCollector.RADIUS = message.radius;
                    tileCollector.particles();
                }
            }
            return null;
        }
    }
}