package net.bearster.bearsterschessmod.block.entity.custom;

import net.bearster.bearsterschessmod.block.entity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

public class ChessPieceBlockEntity extends BlockEntity {
    private Boolean pawnHasDoubleMoved = true;
    private static List<Integer> xList = new ArrayList<>();
    private static List<Integer> yList = new ArrayList<>();
    private static List<Integer> zList = new ArrayList<>();
    private static String lastPieceMoved;
    private static Boolean lastPieceMovedColour;
    private static Boolean lastPieceMovedWasDouble;
    private static Boolean whosTurnIsIt = true;
    private static Boolean forcedTurnTaking = true;

    public ChessPieceBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CHESS_PIECE_BE.get(), pos, state);
    }

    public Boolean getPawnHasDoubleMoved() {
        return pawnHasDoubleMoved;
    }

    public void setPawnHasDoubleMoved(Boolean pawnHasDoubleMoved) {
        this.pawnHasDoubleMoved = pawnHasDoubleMoved;
        setChanged();
    }

    public static String getLastPieceMoved() {
        return lastPieceMoved;
    }

    public static void setLastPieceMoved(String lastPieceMoved) {
        ChessPieceBlockEntity.lastPieceMoved = lastPieceMoved;
    }

    public static Boolean getLastPieceMovedColour() {
        return lastPieceMovedColour;
    }

    public static void setLastPieceMovedColour(Boolean lastPieceMovedColour) {
        ChessPieceBlockEntity.lastPieceMovedColour = lastPieceMovedColour;
    }

    public static Boolean getLastPieceMovedWasDouble() {
        return lastPieceMovedWasDouble;
    }

    public static void setLastPieceMovedWasDouble(Boolean lastPieceMovedWasDouble) {
        ChessPieceBlockEntity.lastPieceMovedWasDouble = lastPieceMovedWasDouble;
    }

    public static Boolean getWhosTurnIsIt() {
        return whosTurnIsIt;
    }

    public static void setWhosTurnIsIt(Boolean whosTurnIsIt) {
        ChessPieceBlockEntity.whosTurnIsIt = whosTurnIsIt;
    }

    public static Boolean getForcedTurnTaking() {
        return forcedTurnTaking;
    }

    public static void setForcedTurnTaking(Boolean forcedTurnTaking) {
        ChessPieceBlockEntity.forcedTurnTaking = forcedTurnTaking;
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
        pTag.putBoolean("pawnHasDoubleMoved", this.pawnHasDoubleMoved);
        pTag.putIntArray("xList",xList);
        pTag.putIntArray("yList",yList);
        pTag.putIntArray("zList",zList);
        pTag.putString("lastPieceMoved",this.lastPieceMoved);
        pTag.putBoolean("lastPieceMovedColour", this.lastPieceMovedColour);
        pTag.putBoolean("lastPieceMovedWasDouble", this.lastPieceMovedWasDouble);
        pTag.putBoolean("whosTurnIsiT", this.whosTurnIsIt);
        pTag.putBoolean("forcedTurnTaking", this.forcedTurnTaking);
    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.loadAdditional(pTag, pRegistries);
        this.pawnHasDoubleMoved = pTag.getBoolean("pawnHasDoubleMoved");
        this.lastPieceMoved = pTag.getString("lastPieceMoved");
        this.lastPieceMovedColour = pTag.getBoolean("lastPieceMovedColour");
        this.lastPieceMovedWasDouble = pTag.getBoolean("lastPieceMovedWasDouble");
        this.whosTurnIsIt = pTag.getBoolean("whosTurnIsiT");
        this.forcedTurnTaking = pTag.getBoolean("forcedTurnTaking");
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

