package tvrk.fart.balls.modules;


import meteordevelopment.meteorclient.events.packets.PacketEvent;
import meteordevelopment.meteorclient.settings.BoolSetting;
import meteordevelopment.meteorclient.settings.Setting;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.ExplosionS2CPacket;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import tvrk.fart.balls.AddonTemplate;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.MinecraftClient;
import java.util.Optional;
import java.util.Random;

public class AntiKnockback extends Module {

    private final SettingGroup sgGeneral = this.settings.getDefaultGroup();

    private final Setting<Boolean> moanSetting = sgGeneral.add(new BoolSetting.Builder()
        .name("Moan")
        .description("Helps you oil up at spawn")
        .defaultValue(true)
        .build()
    );

    MinecraftClient MC = MinecraftClient.getInstance();

    public AntiKnockback() {
        super(AddonTemplate.CATEGORY, "AntiTurkback", "The Certified FraazT0 AntiTurkback bypass (LEGIT)");
    }

    @EventHandler
    public void onIncPacket(PacketEvent.Receive event){
        if(MC.player == null || MC.world == null) return;

        Packet<?> pac = event.packet;

        if(pac instanceof EntityVelocityUpdateS2CPacket) event.setCancelled(true);

        if(pac instanceof ExplosionS2CPacket packet) {
            packet.playerKnockback = Optional.empty();

            if(moanSetting.get())
            MC.world.playSound(
                MC.player,
                packet.center().getX(),
                packet.center().getY(),
                packet.center().getZ(),
                SoundEvents.ENTITY_VILLAGER_CELEBRATE,
                SoundCategory.NEUTRAL,
                1.0F,
                getRandomPitch(),
                1
            );

        }
    }

    float getRandomPitch() {
        Random random = new Random();
        return 0.3F + random.nextFloat() * 0.7F;
    }

}
