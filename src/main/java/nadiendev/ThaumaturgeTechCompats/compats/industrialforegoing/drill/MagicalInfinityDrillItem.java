package nadiendev.ThaumaturgeTechCompats.compats.industrialforegoing.drill;

import com.leclowndu93150.thaumaturge.api.items.RechargeAccess;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.SimpleFluidContent;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;
import net.minecraft.core.Holder;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class MagicalInfinityDrillItem extends Item {
    public static final String KEY = ThaumaturgeTechCompats.MOD_ID + ".magical_infinity_drill.";
    public static final String TOOLTIP = "tooltip." + KEY;
    public static final String MESSAGE = "message." + KEY;
    private static final int BUCKET = 1000;
    private static final int BAR_COLOR = 0x9A5CFF;

    public MagicalInfinityDrillItem(Properties properties) {
        super(properties);
    }

    public record Area(BlockPos first, BlockPos second) {
    }

    public static int storedVis(ItemStack stack) {
        return RechargeAccess.storedVis(stack);
    }

    public static DrillTier unlockedTier(ItemStack stack) {
        return DrillTier.unlocked(storedVis(stack));
    }

    public static DrillTier selectedTier(ItemStack stack) {
        int selected = stack.getOrDefault(MagicalInfinityDrillRegistry.SELECTED_LEVEL.get(), DrillTier.values().length - 1);
        return DrillTier.byOrdinal(Math.min(selected, unlockedTier(stack).ordinal()));
    }

    public static boolean isCharging(ItemStack stack) {
        return stack.getOrDefault(MagicalInfinityDrillRegistry.CHARGING.get(), MagicalInfinityDrillConfig.chargingByDefault());
    }

    public static int liquid(ItemStack stack) {
        return stack.getOrDefault(MagicalInfinityDrillRegistry.LIQUID.get(), SimpleFluidContent.EMPTY).copy().getAmount();
    }

    public static ItemStack fullStack() {
        ItemStack stack = new ItemStack(MagicalInfinityDrillRegistry.ITEM.get());
        RechargeAccess.addVis(stack, MagicalInfinityDrillConfig.maxVis());
        IFluidHandlerItem handler = stack.getCapability(Capabilities.FluidHandler.ITEM);
        if (handler != null) {
            handler.fill(new FluidStack(DrillTank.liquidDeath(), MagicalInfinityDrillConfig.liquidCapacity()), IFluidHandler.FluidAction.EXECUTE);
        }
        return stack;
    }

    public static Area area(BlockPos pos, Direction face, DrillTier tier) {
        int radius = tier.radius();
        boolean vertical = face.getAxis() == Direction.Axis.Y;
        BlockPos first = pos.relative(vertical ? Direction.SOUTH : Direction.DOWN, radius)
                .relative(vertical ? Direction.WEST : face.getCounterClockWise(), radius);
        BlockPos second = pos.relative(vertical ? Direction.NORTH : Direction.UP, radius)
                .relative(vertical ? Direction.EAST : face.getClockWise(), radius);
        if (!vertical && radius > 0) {
            first = first.relative(Direction.UP, radius - 1);
            second = second.relative(Direction.UP, radius - 1);
        }
        if (hasDepth(tier)) {
            second = second.relative(face.getOpposite(), radius * 2);
        }
        return new Area(first, second);
    }

    public static String formatArea(DrillTier tier) {
        int width = tier.radius() * 2 + 1;
        return width + "x" + width + "x" + (hasDepth(tier) ? width : 1);
    }

    private static boolean hasDepth(DrillTier tier) {
        return tier.isLast() && MagicalInfinityDrillConfig.artifactDepth();
    }

    private static boolean liquidCovers(ItemStack stack) {
        int cost = MagicalInfinityDrillConfig.liquidPerBlock();
        return MagicalInfinityDrillConfig.liquidEnabled() && cost > 0 && liquid(stack) >= cost;
    }

    public static boolean hasFuel(ItemStack stack) {
        if (MagicalInfinityDrillConfig.worksWithoutVis() || liquidCovers(stack)) {
            return true;
        }
        int cost = MagicalInfinityDrillConfig.visPerBlock();
        return cost <= 0 || storedVis(stack) >= cost;
    }

    private static void consume(ServerLevel level, ItemStack stack, RandomSource random) {
        if (MagicalInfinityDrillConfig.unbreakingSavesVis()) {
            Holder<Enchantment> unbreaking = level.registryAccess().holderOrThrow(Enchantments.UNBREAKING);
            int unbreakingLevel = EnchantmentHelper.getItemEnchantmentLevel(unbreaking, stack);
            if (unbreakingLevel > 0 && random.nextInt(unbreakingLevel + 1) != 0) {
                return;
            }
        }
        if (liquidCovers(stack)) {
            IFluidHandlerItem handler = stack.getCapability(Capabilities.FluidHandler.ITEM);
            if (handler != null) {
                handler.drain(MagicalInfinityDrillConfig.liquidPerBlock(), IFluidHandler.FluidAction.EXECUTE);
            }
            return;
        }
        int cost = MagicalInfinityDrillConfig.visPerBlock();
        if (cost > 0 && !MagicalInfinityDrillConfig.worksWithoutVis()) {
            RechargeAccess.spendVis(stack, cost);
        }
    }

    private static boolean mineable(BlockState state) {
        return state.is(BlockTags.MINEABLE_WITH_PICKAXE) || state.is(BlockTags.MINEABLE_WITH_SHOVEL);
    }

    @Override
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state) {
        ItemStack pickaxe = new ItemStack(Items.DIAMOND_PICKAXE);
        ItemStack shovel = new ItemStack(Items.DIAMOND_SHOVEL);
        return Items.DIAMOND_PICKAXE.isCorrectToolForDrops(pickaxe, state) || Items.DIAMOND_SHOVEL.isCorrectToolForDrops(shovel, state);
    }

    @Override
    public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
        ItemStack pickaxe = new ItemStack(Items.DIAMOND_PICKAXE);
        ItemStack shovel = new ItemStack(Items.DIAMOND_SHOVEL);
        return Items.DIAMOND_PICKAXE.supportsEnchantment(pickaxe, enchantment) || Items.DIAMOND_SHOVEL.supportsEnchantment(shovel, enchantment);
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return MagicalInfinityDrillConfig.enchantability() > 0;
    }

    @Override
    public int getEnchantmentValue() {
        return MagicalInfinityDrillConfig.enchantability();
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return slotChanged || !oldStack.is(newStack.getItem());
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        if (MagicalInfinityDrillConfig.enabled() && mineable(state) && hasFuel(stack)) {
            return MagicalInfinityDrillConfig.miningSpeed();
        }
        return super.getDestroySpeed(stack, state);
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity miner) {
        if (!(level instanceof ServerLevel server) || !(miner instanceof ServerPlayer player)
                || !MagicalInfinityDrillConfig.enabled() || !mineable(state) || !hasFuel(stack)) {
            return super.mineBlock(stack, level, state, pos, miner);
        }
        consume(server, stack, player.getRandom());
        DrillTier tier = selectedTier(stack);
        Area area = area(pos, faceOf(player, pos), tier);
        List<ItemStack> drops = new ArrayList<>();
        int experience = 0;
        for (BlockPos candidate : BlockPos.betweenClosed(area.first(), area.second())) {
            if (!hasFuel(stack)) {
                break;
            }
            if (candidate.equals(pos)) {
                continue;
            }
            experience += breakBlock(server, player, stack, candidate.immutable(), drops);
        }
        for (ItemStack drop : drops) {
            Block.popResource(server, player.blockPosition(), drop);
        }
        if (experience > 0) {
            ExperienceOrb.award(server, player.position(), experience);
        }
        return super.mineBlock(stack, level, state, pos, miner);
    }

    private static Direction faceOf(ServerPlayer player, BlockPos pos) {
        Vec3 eye = player.getEyePosition();
        Vec3 look = player.getViewVector(1.0F);
        BlockHitResult hit = Shapes.block().clip(eye, eye.add(look.scale(player.blockInteractionRange() + 1.0D)), pos);
        if (hit != null) {
            return hit.getDirection();
        }
        return Direction.getNearest(look.x, look.y, look.z).getOpposite();
    }

    private static int breakBlock(ServerLevel level, ServerPlayer player, ItemStack stack, BlockPos target, List<ItemStack> drops) {
        BlockState state = level.getBlockState(target);
        if (state.isAir() || state.getBlock() instanceof LiquidBlock || !mineable(state)) {
            return 0;
        }
        if (state.getDestroySpeed(level, target) < 0.0F || !stack.getItem().isCorrectToolForDrops(stack, state)) {
            return 0;
        }
        BlockEntity entity = level.getBlockEntity(target);
        if (entity != null && MagicalInfinityDrillConfig.skipBlockEntities()) {
            return 0;
        }
        if (!level.mayInteract(player, target) || player.blockActionRestricted(level, target, player.gameMode.getGameModeForPlayer())) {
            return 0;
        }
        if (NeoForge.EVENT_BUS.post(new BlockEvent.BreakEvent(level, target, state, player)).isCanceled()) {
            return 0;
        }
        Block block = state.getBlock();
        int experience = EnchantmentHelper.processBlockExperience(level, stack, block.getExpDrop(state, level, target, entity, player, stack));
        FluidState fluid = state.getFluidState();
        if (!block.onDestroyedByPlayer(state, level, target, player, true, fluid)) {
            return 0;
        }
        block.destroy(level, target, state);
        for (ItemStack drop : Block.getDrops(state, level, target, entity, player, stack)) {
            merge(drops, drop);
        }
        consume(level, stack, player.getRandom());
        return experience;
    }

    private static void merge(List<ItemStack> into, ItemStack stack) {
        for (ItemStack existing : into) {
            if (ItemStack.isSameItemSameComponents(existing, stack)) {
                int moved = Math.min(stack.getCount(), existing.getMaxStackSize() - existing.getCount());
                if (moved > 0) {
                    existing.grow(moved);
                    stack.shrink(moved);
                }
            }
            if (stack.isEmpty()) {
                return;
            }
        }
        if (!stack.isEmpty()) {
            into.add(stack);
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!MagicalInfinityDrillConfig.enabled()) {
            return InteractionResultHolder.pass(stack);
        }
        if (player.isShiftKeyDown()) {
            if (!level.isClientSide) {
                cycleArea(stack, player);
            }
            return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
        }
        BlockHitResult hit = getPlayerPOVHitResult(level, player, ClipContext.Fluid.SOURCE_ONLY);
        if (hit.getType() == HitResult.Type.BLOCK && scoop(level, player, stack, hit.getBlockPos())) {
            return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
        }
        if (!level.isClientSide) {
            boolean charging = !isCharging(stack);
            stack.set(MagicalInfinityDrillRegistry.CHARGING.get(), charging);
            player.displayClientMessage(Component.translatable(MESSAGE + (charging ? "charging_on" : "charging_off")), true);
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
    }

    private static void cycleArea(ItemStack stack, Player player) {
        DrillTier unlocked = unlockedTier(stack);
        DrillTier current = selectedTier(stack);
        DrillTier next = current.ordinal() == 0 ? unlocked : DrillTier.byOrdinal(current.ordinal() - 1);
        stack.set(MagicalInfinityDrillRegistry.SELECTED_LEVEL.get(), next.ordinal());
        player.displayClientMessage(Component.translatable(MESSAGE + "area", formatArea(next)), true);
    }

    private static boolean scoop(Level level, Player player, ItemStack stack, BlockPos pos) {
        FluidState fluid = level.getFluidState(pos);
        if (!MagicalInfinityDrillConfig.liquidEnabled() || !fluid.isSource() || !DrillTank.isLiquidDeath(fluid.getType())) {
            return false;
        }
        int capacity = MagicalInfinityDrillConfig.liquidCapacity();
        int amount = Math.min(BUCKET, capacity);
        if (capacity - liquid(stack) < amount) {
            return false;
        }
        if (level.isClientSide) {
            return true;
        }
        BlockState state = level.getBlockState(pos);
        if (!level.mayInteract(player, pos) || !(state.getBlock() instanceof BucketPickup pickup)) {
            return false;
        }
        if (pickup.pickupBlock(player, level, pos, state).isEmpty()) {
            return false;
        }
        IFluidHandlerItem handler = stack.getCapability(Capabilities.FluidHandler.ITEM);
        if (handler != null) {
            handler.fill(new FluidStack(fluid.getType(), amount), IFluidHandler.FluidAction.EXECUTE);
        }
        level.playSound(null, pos, SoundEvents.BUCKET_FILL, SoundSource.PLAYERS, 1.0F, 1.0F);
        return true;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        if (level.isClientSide || !(entity instanceof Player player) || !MagicalInfinityDrillConfig.enabled() || !isCharging(stack)) {
            return;
        }
        if (level.getGameTime() % MagicalInfinityDrillConfig.rechargeInterval() != 0 || RechargeAccess.roomFor(stack) <= 0) {
            return;
        }
        RechargeAccess.rechargeItem(level, stack, player.blockPosition(), player, MagicalInfinityDrillConfig.rechargeAmount());
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return super.isFoil(stack) || (MagicalInfinityDrillConfig.enabled() && unlockedTier(stack).isLast());
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return true;
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        return Math.round(RechargeAccess.fillFraction(stack) * 13.0F);
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return BAR_COLOR;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        DrillTier selected = selectedTier(stack);
        DrillTier unlocked = unlockedTier(stack);
        NumberFormat format = NumberFormat.getIntegerInstance();
        int vis = storedVis(stack);

        tooltip.add(label("area", value(formatArea(selected), ChatFormatting.WHITE)));
        tooltip.add(label("level", Component.translatable(TOOLTIP + "tier." + selected.id()).withStyle(selected.color())));
        if (unlocked.isLast()) {
            tooltip.add(label("vis_full", value(format.format(vis), ChatFormatting.LIGHT_PURPLE), value(format.format(MagicalInfinityDrillConfig.maxVis()), ChatFormatting.GRAY)));
        } else {
            tooltip.add(label("vis", value(format.format(vis), ChatFormatting.LIGHT_PURPLE), value(format.format(unlocked.next().minVis()), ChatFormatting.GRAY)));
        }
        if (MagicalInfinityDrillConfig.liquidEnabled()) {
            tooltip.add(label("liquid", value(format.format(liquid(stack)), ChatFormatting.DARK_GREEN), value(format.format(MagicalInfinityDrillConfig.liquidCapacity()), ChatFormatting.GRAY)));
        }
        tooltip.add(label("max_area", value(formatArea(unlocked), ChatFormatting.WHITE)));
        boolean charging = isCharging(stack);
        tooltip.add(label("charging", Component.translatable(TOOLTIP + (charging ? "enabled" : "disabled")).withStyle(charging ? ChatFormatting.GREEN : ChatFormatting.RED)));
        if (unlocked.isLast()) {
            tooltip.add(Component.translatable(TOOLTIP + "glowing").withStyle(ChatFormatting.GOLD));
        }
        tooltip.add(Component.translatable(TOOLTIP + "hint_area").withStyle(ChatFormatting.DARK_GRAY));
        tooltip.add(Component.translatable(TOOLTIP + "hint_charge").withStyle(ChatFormatting.DARK_GRAY));
    }

    private static MutableComponent label(String key, Object... arguments) {
        return Component.translatable(TOOLTIP + key, arguments).withStyle(ChatFormatting.GRAY);
    }

    private static MutableComponent value(String text, ChatFormatting color) {
        return Component.literal(text).withStyle(color);
    }
}
