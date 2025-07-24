package net.bearster.bearsterschessmod.block.entity.custom;

import net.bearster.bearsterschessmod.BearstersChessMod;
import net.bearster.bearsterschessmod.block.entity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

public class PawnBlockEntity extends BlockEntity {
    private Boolean hasDoubleMoved = true;
    private List<Integer> xList = new ArrayList<>();
    private List<Integer> yList = new ArrayList<>();
    private List<Integer> zList = new ArrayList<>();

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

    public List<Integer> getXList() {
        return xList;
    }

    public List<Integer> getYList() {
        return yList;
    }

    public List<Integer> getZList() {
        return zList;
    }

    public void addToList(BlockPos pos) {
        xList.add(pos.getX());
        yList.add(pos.getY());
        zList.add(pos.getZ());
    }

    public void resetList() {
        xList.clear();
        yList.clear();
        zList.clear();
    }

    @Override
    protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.saveAdditional(pTag, pRegistries);
        pTag.putBoolean("hasDoubleMoved", this.hasDoubleMoved);
        pTag.putIntArray("xList",xList);
        pTag.putIntArray("yList",yList);
        pTag.putIntArray("zList",zList);
    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.loadAdditional(pTag, pRegistries);
        this.hasDoubleMoved = pTag.getBoolean("hasDoubleMoved");
        for (int i : pTag.getIntArray("xList")) {
            xList.add(i);
        }

        for (int i : pTag.getIntArray("yList")) {
            yList.add(i);
        }

        for (int i : pTag.getIntArray("zList")) {
            zList.add(i);
        }
    }




    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider pRegistries) {
        return saveWithoutMetadata(pRegistries);
    }

    @Override
    public void handleUpdateTag(CompoundTag tag, HolderLookup.Provider holders) {

    }
}

