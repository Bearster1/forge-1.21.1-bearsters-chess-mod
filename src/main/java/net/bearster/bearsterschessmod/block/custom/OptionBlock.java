package net.bearster.bearsterschessmod.block.custom;

import net.bearster.bearsterschessmod.BearstersChessMod;
import net.bearster.bearsterschessmod.block.ModBlocks;
import net.bearster.bearsterschessmod.block.entity.custom.ChessPieceBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import java.util.List;

public class OptionBlock extends ChessPieceBlock {
    public OptionBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack pStack, BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHitResult) {
        if (!pLevel.isClientSide()) {
            if (pPlayer.isCrouching()) {
                BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
                if (blockEntity instanceof ChessPieceBlockEntity chessPieceBlockEntity) {
                    chessPieceBlockEntity.setForcedTurnTaking(!chessPieceBlockEntity.getForcedTurnTaking());
                    if (chessPieceBlockEntity.getForcedTurnTaking()) {
                        pPlayer.sendSystemMessage(Component.translatable("message.bearsterschessmod.forced_turn_taking_on"));
                        BearstersChessMod.LOGGER.info("Turned On Force Turn Taking");
                    } else {
                        pPlayer.sendSystemMessage(Component.translatable("message.bearsterschessmod.forced_turn_taking_off"));
                        BearstersChessMod.LOGGER.info("Turned Off Force Turn Taking");
                    }
                }
            } else {
                BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
                if (blockEntity instanceof ChessPieceBlockEntity chessPieceBlockEntity) {
                    chessPieceBlockEntity.setWhosTurnIsIt(true);
                    pPlayer.sendSystemMessage(Component.translatable("message.bearsterschessmod.game_started"));
                    BearstersChessMod.LOGGER.info("Started the game");
                }
            }
        }
        return ItemInteractionResult.SUCCESS;
    }

    @Override
    public void appendHoverText(ItemStack pStack, Item.TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
        pTooltipComponents.add(Component.translatable("tooltip.bearsterschessmod.right_click_to_start_game"));
        pTooltipComponents.add(Component.translatable("tooltip.bearsterschessmod.shift_right_click_to_toggle_color_boundary"));
        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
    }
}
