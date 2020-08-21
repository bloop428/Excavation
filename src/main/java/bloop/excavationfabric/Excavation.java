package bloop.excavationfabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;

public class Excavation implements ModInitializer {

    @Override
    public void onInitialize() {
        PlayerBlockBreakEvents.BEFORE.register(((world, playerEntity, blockPos, blockState, blockEntity) -> {
            return false; //cancels the breaking
        }));
    }
}
