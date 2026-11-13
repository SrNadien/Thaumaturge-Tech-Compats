package nadiendev.ThaumaturgeTechCompats.device;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.auraconverter.AuraConverterIntegration;
import nadiendev.ThaumaturgeTechCompats.compats.enderio.EnderIOIntegration;
import nadiendev.ThaumaturgeTechCompats.flux.charger.ChargerIntegration;
import nadiendev.ThaumaturgeTechCompats.flux.core.CoreIntegration;
import nadiendev.ThaumaturgeTechCompats.flux.field.FieldIntegration;
import nadiendev.ThaumaturgeTechCompats.flux.link.LinkIntegration;
import nadiendev.ThaumaturgeTechCompats.compats.botania.BotaniaIntegration;
import nadiendev.ThaumaturgeTechCompats.infusion.InfusionIntegration;
import nadiendev.ThaumaturgeTechCompats.compats.voidminers.VoidMinersMagicalIntegration;
import nadiendev.ThaumaturgeTechCompats.compats.avaritia.machines.AvaritiaMachinesIntegration;
import nadiendev.ThaumaturgeTechCompats.compats.oritech.OritechIntegration;
import nadiendev.ThaumaturgeTechCompats.furnace.FurnaceIntegration;
import nadiendev.ThaumaturgeTechCompats.smelter.SmelterIntegration;
import nadiendev.ThaumaturgeTechCompats.stabilizer.StabilizerIntegration;
import nadiendev.ThaumaturgeTechCompats.transducer.TransducerIntegration;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagAppender;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.BlockTagsProvider;

import java.util.concurrent.CompletableFuture;

public class DeviceBlockTags extends BlockTagsProvider {
    public DeviceBlockTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookup) {
        super(output, lookup, ThaumaturgeTechCompats.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        TagAppender<Block, Block> pickaxe = tag(BlockTags.MINEABLE_WITH_PICKAXE);
        SmelterIntegration.blocks().forEach(pickaxe::add);
        StabilizerIntegration.blocks().forEach(pickaxe::add);
        TransducerIntegration.blocks().forEach(pickaxe::add);
        FurnaceIntegration.blocks().forEach(pickaxe::add);
        BotaniaIntegration.blocks().forEach(pickaxe::add);
        InfusionIntegration.blocks().forEach(pickaxe::add);
        VoidMinersMagicalIntegration.blocks().forEach(pickaxe::add);
        AvaritiaMachinesIntegration.blocks().forEach(pickaxe::add);
        AuraConverterIntegration.blocks().forEach(pickaxe::add);
        CoreIntegration.blocks().forEach(pickaxe::add);
        LinkIntegration.blocks().forEach(pickaxe::add);
        ChargerIntegration.blocks().forEach(pickaxe::add);
        FieldIntegration.blocks().forEach(pickaxe::add);
        EnderIOIntegration.blockIds().forEach(id -> getOrCreateRawBuilder(BlockTags.MINEABLE_WITH_PICKAXE).addOptionalElement(id));
        OritechIntegration.blockIds().forEach(id -> getOrCreateRawBuilder(BlockTags.MINEABLE_WITH_PICKAXE).addOptionalElement(id));
    }
}
