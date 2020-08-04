package net.bloop.excavation.event;

import net.bloop.excavation.Excavation;
import net.bloop.excavation.network.ExcavationPacketHandler;
import net.bloop.excavation.network.PacketExcavate;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Excavation.MODID)
public class ServerEvent {

    private static boolean alreadyBreaking = false;
    private static boolean excavationPressed = false;

    @SubscribeEvent
    public static void veinMine(BlockEvent.BreakEvent e) {
        if(alreadyBreaking)
            return;
        PlayerEntity player = e.getPlayer();
        World world = e.getWorld().getWorld();
        BlockPos blockPos = e.getPos();

        if(!world.getBlockState(blockPos).getBlock().canHarvestBlock(world.getBlockState(blockPos), world, blockPos, player) || !world.isBlockLoaded(blockPos))
            return;

        if(!excavationPressed) {
            return;
        } else {
            alreadyBreaking = true;
            e.setCanceled(true);
            //this line crashes v cuz it calls Minecraft on the server
            ExcavationPacketHandler.INSTANCE.sendToServer(new PacketExcavate(blockPos));
        }
    }

    public static void setAlreadyBreaking(boolean breaking) {
        alreadyBreaking = breaking;
    }

    public static void setExcavationPressed(boolean pressed) {
        excavationPressed = pressed;
    }

}
