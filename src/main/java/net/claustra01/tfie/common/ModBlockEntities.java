package net.claustra01.tfie.common;

import net.claustra01.tfie.TFIE;
import java.util.stream.Stream;
import net.dries007.tfc.common.blockentities.CropBlockEntity;
import net.dries007.tfc.common.blockentities.TFCBlockEntities.Id;
import net.dries007.tfc.util.registry.RegistrationHelpers;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
        DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, TFIE.MOD_ID);

    public static Id<CropBlockEntity> CROP;

    static {
        CROP = new Id<>(RegistrationHelpers.register(
            BLOCK_ENTITIES,
            "crop",
            (pos, state) -> new CropBlockEntity(CROP.get(), pos, state),
            Stream.of(ModBlocks.HEMP)
        ));
    }

    private ModBlockEntities() {}
}
