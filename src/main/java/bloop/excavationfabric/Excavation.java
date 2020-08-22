package bloop.excavationfabric;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.options.StickyKeyBinding;
import net.minecraft.text.LiteralText;
import org.lwjgl.glfw.GLFW;

public class Excavation implements ModInitializer, ClientModInitializer {

    @Override
    public void onInitialize() {

        //Break block event
        PlayerBlockBreakEvents.BEFORE.register(((world, playerEntity, blockPos, blockState, blockEntity) -> {
            return false; //cancels the breaking
        }));
    }

    @Override
    public void onInitializeClient() {
        KeyBinding excavate = KeyBindingHelper.registerKeyBinding(new StickyKeyBinding("key.excavation.excavate", GLFW.GLFW_KEY_GRAVE_ACCENT, "category.excavation.excavation", () -> true));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if(excavate.isPressed()) {
                client.player.sendMessage(new LiteralText("Yeet"), false);
            }
        });
    }
}
