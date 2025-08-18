package dev.darmstadtium271.gtmplusplus.common.item;

import com.tterrag.registrate.util.entry.RegistryEntry;
import dev.darmstadtium271.gtmplusplus.api.radiation.RadiationSource;
import dev.darmstadtium271.gtmplusplus.api.radiation.RadiationWorldSavedData;
import dev.darmstadtium271.gtmplusplus.common.registry.GTMPPRegistration;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;

import java.util.Objects;

public class RadiationTestItem extends Item {

    public static final RegistryEntry<RadiationTestItem> ITEM = GTMPPRegistration.REGISTRATE.item("test", RadiationTestItem::new).lang("RadiationTestItem").register();

    public RadiationTestItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (context.getLevel() instanceof ServerLevel) {
            if (Objects.requireNonNull(context.getPlayer()).isShiftKeyDown()) {
                context.getPlayer().sendSystemMessage(Component.literal(String.valueOf(RadiationWorldSavedData.getOrCreate((ServerLevel) context.getLevel()).getRadiation(context.getClickedPos().getCenter()))));
                context.getPlayer().sendSystemMessage(Component.literal(String.valueOf(RadiationWorldSavedData.getOrCreate((ServerLevel) context.getLevel()).getRadiationSources())));
            } else {
                BlockPos block = context.getClickedPos();
                RadiationWorldSavedData.getOrCreate((ServerLevel) context.getLevel()).addRadiationSource(new RadiationSource(10, context.getLevel().getDayTime(), context.getClickedPos()));
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.CONSUME;
    }

    public static void init() {
    }
}
