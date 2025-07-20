package net.bearster.bearsterschessmod.block.entity.custom;

import net.bearster.bearsterschessmod.block.entity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class PawnBlockEntity extends BlockEntity {
    private Boolean hasDoubleMoved = true;

    public PawnBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.PAWN_BE.get(), pos, state);
    }

    public Boolean getHasDoubleMoved() {
        return hasDoubleMoved;
    }

    public void setHasDoubleMoved(Boolean hasDoubleMoved) {
        this.hasDoubleMoved = hasDoubleMoved;
        setChanged();
    }

    @Override
    protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.saveAdditional(pTag, pRegistries);
        pTag.putBoolean("hasDoubleMoved", this.hasDoubleMoved);
    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.loadAdditional(pTag, pRegistries);
        this.hasDoubleMoved = pTag.getBoolean("hasDoubleMoved");
    }




    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider pRegistries) {
        return saveWithoutMetadata(pRegistries);
    }

    @Override
    public void handleUpdateTag(CompoundTag tag, HolderLookup.Provider holders) {

    }
}

