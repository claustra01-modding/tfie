package net.claustra01.tfie.mixin;

import blusunrize.immersiveengineering.api.energy.IRotationAcceptor;
import blusunrize.immersiveengineering.common.blocks.IEBaseBlockEntity;
import blusunrize.immersiveengineering.common.blocks.IEBlockInterfaces;
import blusunrize.immersiveengineering.common.blocks.metal.DynamoBlockEntity;
import blusunrize.immersiveengineering.common.blocks.ticking.IEServerTickableBE;
import net.claustra01.tfie.config.TFIEConfig;
import net.dries007.tfc.common.blockentities.rotation.RotationSinkBlockEntity;
import net.dries007.tfc.util.rotation.NetworkAction;
import net.dries007.tfc.util.rotation.Node;
import net.dries007.tfc.util.rotation.SinkNode;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DynamoBlockEntity.class)
public abstract class DynamoBlockEntityMixin extends IEBaseBlockEntity
    implements IEBlockInterfaces.IStateBasedDirectional, IEServerTickableBE, RotationSinkBlockEntity {

    @Shadow @Final
    private IRotationAcceptor rotationCap;

    @Unique
    private SinkNode tfie$node;

    protected DynamoBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void tfie$createRotationNode(BlockPos pos, BlockState state, CallbackInfo callback) {
        tfie$node = new SinkNode(pos, getFacing()) {
            @Override
            public @NotNull String toString() {
                return "Dynamo[pos=%s, direction=%s]".formatted(pos(), getFacing());
            }
        };
    }

    @Override
    public void onLoad() {
        super.onLoad();
        performNetworkAction(NetworkAction.ADD);
    }

    @Override
    public void onChunkUnloaded() {
        super.onChunkUnloaded();
        performNetworkAction(NetworkAction.REMOVE);
    }

    @Override
    public void setRemovedIE() {
        super.setRemovedIE();
        performNetworkAction(NetworkAction.REMOVE);
    }

    @Override
    public @NotNull Node getRotationNode() {
        return tfie$node;
    }

    @Override
    public void tickServer() {
        if (tfie$node.rotation() != null) {
            rotationCap.inputRotation(Math.abs(
                tfie$node.rotation().speed() * 500 * TFIEConfig.TFC_ROTATIONAL_ENERGY_MODIFIER.get()));
        }
    }
}
