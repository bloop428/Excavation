package bloop.excavationfabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.util.Identifier;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Excavation implements ModInitializer {

    public static final Identifier EXCAVATE_PRESSED_PACKET_ID = new Identifier("excavationfabric", "excavate");
    public static Set<UUID> playersWithButtonDown = new HashSet<>();

    @Override
    public void onInitialize() {

        ServerSidePacketRegistry.INSTANCE.register(EXCAVATE_PRESSED_PACKET_ID, (packetContext, attatchedData) -> {
            boolean pressed = attatchedData.readBoolean();

            packetContext.getTaskQueue().execute(() -> {
                if(pressed) {
                    addPlayer(packetContext.getPlayer().getUuid());
                } else {
                    removePlayer(packetContext.getPlayer().getUuid());
                }
            });
        });

        //Break block event
        PlayerBlockBreakEvents.BEFORE.register(((world, playerEntity, blockPos, blockState, blockEntity) -> {
            return false; //cancels the breaking
        }));
    }

    public static void addPlayer(UUID uuid) {
        playersWithButtonDown.add(uuid);
    }

    public static void removePlayer(UUID uuid) {
        playersWithButtonDown.remove(uuid);
    }
}
