package nadiendev.ThaumaturgeTechCompats.compats.industrialforegoing.laser.client;

import com.hrznstudio.titanium.client.screen.addon.EnergyBarScreenAddon;
import nadiendev.ThaumaturgeTechCompats.compats.industrialforegoing.IndustrialForegoingConfig;
import nadiendev.ThaumaturgeTechCompats.compats.industrialforegoing.IndustrialForegoingTranslations;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.energy.IEnergyStorage;

import java.text.DecimalFormat;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class VisBarScreenAddon extends EnergyBarScreenAddon {
    private static final DecimalFormat FORMAT = new DecimalFormat("#,##0.##");

    private final IEnergyStorage storage;

    public VisBarScreenAddon(int x, int y, IEnergyStorage storage) {
        super(x, y, storage);
        this.storage = storage;
    }

    @Override
    public List<Component> getTooltipLines() {
        String stored = FORMAT.format(storage.getEnergyStored() / (double) IndustrialForegoingConfig.UNITS_PER_VIS);
        String capacity = FORMAT.format(storage.getMaxEnergyStored() / (double) IndustrialForegoingConfig.UNITS_PER_VIS);
        return List.of(
                Component.translatable(IndustrialForegoingTranslations.AURA_TITLE).withStyle(ChatFormatting.LIGHT_PURPLE),
                Component.translatable(IndustrialForegoingTranslations.AURA_AMOUNT, stored, capacity).withStyle(ChatFormatting.WHITE));
    }
}
