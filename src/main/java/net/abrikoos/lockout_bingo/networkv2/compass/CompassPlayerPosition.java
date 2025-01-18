package net.abrikoos.lockout_bingo.networkv2.compass;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.server.network.ServerPlayerEntity;

import java.nio.charset.Charset;

public class CompassPlayerPosition {
    public double x;
    public double y;
    public double z;
    public String uuid;

    public CompassPlayerPosition(double x, double y, double z, String uuid) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.uuid = uuid;
    }

    public static final PacketCodec<ByteBuf, CompassPlayerPosition> PACKET_CODEC = new PacketCodec<ByteBuf, CompassPlayerPosition>() {
        @Override
        public void encode(ByteBuf buf, CompassPlayerPosition value) {
            buf.writeDouble(value.x);
            buf.writeDouble(value.y);
            buf.writeDouble(value.z);
            buf.writeCharSequence(value.uuid, Charset.defaultCharset());
        }

        @Override
        public CompassPlayerPosition decode(ByteBuf byteBuf) {
            double x = byteBuf.readDouble();
            double y = byteBuf.readDouble();
            double z = byteBuf.readDouble();
            String uuid = byteBuf.readCharSequence(36, Charset.defaultCharset()).toString();
            return new CompassPlayerPosition(x, y, z, uuid);
        }
    };

    public static CompassPlayerPosition fromServerPlayer(ServerPlayerEntity player) {
        return new CompassPlayerPosition(player.getX(), player.getY(), player.getZ(), player.getUuidAsString());
    }
}
