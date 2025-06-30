package tvrk.fart.balls.modules;


import tvrk.fart.balls.AddonTemplate;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.IntSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.player.FindItemResult;
import meteordevelopment.meteorclient.utils.world.BlockUtils;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AutoSwastika extends Module {

    private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
    MinecraftClient MC = MinecraftClient.getInstance();
    private int turkTicks = 0;

    //cba to port simpletimer
    private final Setting<Integer> delaySetting = sgGeneral.add(new IntSetting.Builder()
        .name("delay")
        .description("Placement delay in ticks")
        .defaultValue(1)
        .range(0, 10)
        .build()
    );

    public AutoSwastika() {
        super(AddonTemplate.CATEGORY, "AutoSwastika", "Builds swastikas");
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        if(MC.player == null || MC.world == null) return;
        turkTicks++;

        int blockSlot = findBlockInHotbar();
        if(blockSlot == -1) return;

        MC.player.getInventory().setSelectedSlot(blockSlot);
        MC.getNetworkHandler().sendPacket(new UpdateSelectedSlotC2SPacket(blockSlot));

        if(turkTicks >= delaySetting.get()){
            for(BlockPos pos : getSwastika()) {
                BlockState state = MC.world.getBlockState(pos);
                if (state.isReplaceable() || !MC.world.getFluidState(pos).isEmpty()) {
                    FindItemResult itemResult = new FindItemResult(blockSlot, 1);
                    BlockUtils.place(pos, itemResult, false, 0);
                    turkTicks = 0;
                    return;
                }
            }
        }
    }

    private int findBlockInHotbar() {
        for (int i = 36; i <= 44; i++) {
            ItemStack stack = MC.player.currentScreenHandler.getSlot(i).getStack();
            if (stack.getItem() instanceof BlockItem)
                return this.toHotbar(i);
        }
        return -1;
    }

    private int toHotbar(int slot) {
        return slot - 36;
    }

    private List<BlockPos> getSwastika() {
        List<BlockPos> northSwastika = new ArrayList<>();
        BlockPos swastikaPos = new BlockPos(0, 0, 0).north().north();

        northSwastika.add(swastikaPos);
        northSwastika.add(swastikaPos.west());
        northSwastika.add(swastikaPos.west().west());
        northSwastika.add(swastikaPos.up());
        northSwastika.add(swastikaPos.up().up());
        northSwastika.add(swastikaPos.up().up().west());
        northSwastika.add(swastikaPos.up().up().west().west());
        northSwastika.add(swastikaPos.up().up().west().west().up());
        northSwastika.add(swastikaPos.up().up().west().west().up().up());
        northSwastika.add(swastikaPos.up().up().east());
        northSwastika.add(swastikaPos.up().up().east().east());
        northSwastika.add(swastikaPos.up().up().east().east().down());
        northSwastika.add(swastikaPos.up().up().east().east().down().down());
        northSwastika.add(swastikaPos.up().up().up());
        northSwastika.add(swastikaPos.up().up().up().up());
        northSwastika.add(swastikaPos.up().up().up().up().east());
        northSwastika.add(swastikaPos.up().up().up().up().east().east());
        return getPositionsNextToPlayer(rotateFromNorth(northSwastika));
    }

    private List<BlockPos> getPositionsNextToPlayer(List<BlockPos> shapePositions) {
        return shapePositions.stream()
            .map(pos -> pos.add(MC.player.getBlockPos().getX(), MC.player.getBlockPos().getY(), MC.player.getBlockPos().getZ()))
            .collect(Collectors.toList());
    }

    private List<BlockPos> rotateFromNorth(List<BlockPos> northPos) {
        return switch (MC.player.getHorizontalFacing()) {
            default -> northPos;
            case EAST -> northPos.stream().map(pos -> pos.rotate(BlockRotation.CLOCKWISE_90)).collect(Collectors.toList());
            case SOUTH -> northPos.stream().map(pos -> pos.rotate(BlockRotation.CLOCKWISE_180)).collect(Collectors.toList());
            case WEST -> northPos.stream().map(pos -> pos.rotate(BlockRotation.COUNTERCLOCKWISE_90)).collect(Collectors.toList());
        };
    }

}
