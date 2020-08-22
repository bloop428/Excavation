package bloop.excavationfabric;

import io.netty.buffer.Unpooled;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.LiteralText;
import org.lwjgl.glfw.GLFW;

public class KeyBindings implements ClientModInitializer {

    private static boolean excavationPressed = false;

    @Override
    public void onInitializeClient() {
        KeyBinding excavate = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.excavation.excavate", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_GRAVE_ACCENT, "category.excavation.excavation"));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            //press
            if(excavate.isPressed() && !excavationPressed) {
                excavationPressed = true;
                client.player.sendMessage(new LiteralText("Down"), false);

                PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
                passedData.writeBoolean(true);
                ClientSidePacketRegistry.INSTANCE.sendToServer(Excavation.EXCAVATE_PRESSED_PACKET_ID, passedData);
            }

            //release
            if(!excavate.isPressed() && excavationPressed) {
                excavationPressed = false;
                client.player.sendMessage(new LiteralText("Up"), false);

                PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
                passedData.writeBoolean(false);
                ClientSidePacketRegistry.INSTANCE.sendToServer(Excavation.EXCAVATE_PRESSED_PACKET_ID, passedData);
            }
        });
    }
}
