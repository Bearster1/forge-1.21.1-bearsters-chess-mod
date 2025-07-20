package net.bearster.bearsterschessmod.datagen;

import com.mojang.logging.LogUtils;
import net.bearster.bearsterschessmod.BearstersChessMod;
import net.bearster.bearsterschessmod.block.ModBlocks;
import net.bearster.bearsterschessmod.item.ModCreativeModeTabs;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.common.data.LanguageProvider;
import org.slf4j.Logger;

public class ModLanguageProvider extends LanguageProvider {
    public ModLanguageProvider(PackOutput output) {
        super(output, BearstersChessMod.MOD_ID, "en_us");
    }

    public static final Logger LOGGER = LogUtils.getLogger();

    @Override
    protected void addTranslations() {
        add(ModBlocks.WHITE_SQUARE.get(), "White Square");
        add(ModBlocks.BLACK_SQUARE.get(), "Black Square");
        add(ModBlocks.WHITE_KNIGHT.get(), "White Knight");
        add(ModBlocks.BLACK_KNIGHT.get(), "Black Knight");
        /*
        add(ModBlocks.WHITE_PAWN.get(), "White Pawn");
        add(ModBlocks.BLACK_PAWN.get(), "Black Pawn");

         */
        add(ModBlocks.WHITE_ROOK.get(), "White Rook");
        add(ModBlocks.BLACK_ROOK.get(), "Black Rook");
        add(ModBlocks.WHITE_BISHOP.get(), "White Bishop");
        add(ModBlocks.BLACK_BISHOP.get(), "Black Bishop");
        add(ModBlocks.WHITE_QUEEN.get(), "White Queen");
        add(ModBlocks.BLACK_QUEEN.get(), "Black Queen");
        add(ModBlocks.WHITE_KING.get(), "White King");
        add(ModBlocks.BLACK_KING.get(), "Black King");
        add(ModBlocks.MOVEABLE_SQUARE.get(), "Moveable Square");

        addCreativeTabType(ModCreativeModeTabs.BEARSTERS_CHESS_MOD_TAB.get(), "Bearster's Chess Mod");
    }

    public void addCreativeTabType(CreativeModeTab creativeModeTab, String name) {
        add(creativeModeTab.getDisplayName().getString(), name);
    }

    public void addMusicDiscType(SoundEvent songIdentifier, String songName, String description) {
        String song = songIdentifier.getLocation().toString();
        String newSong = song.replace(':','.');
        add("item."+newSong+"_music_disc", songName+" Music Disc");
        add("jukebox_song."+newSong, description);

    }

    public void addSoundType(SoundEvent soundIdentifier, String soundName) {
        String sound = soundIdentifier.getLocation().toString();
        String newSound = sound.replace(':','.');
        add("sounds."+newSound, soundName);

    }

    public void addPaintingType(ResourceKey paintingVariants, String paintingName, String paintingAuthor) {
        String painting = paintingVariants.location().toString();
        String newPainting = painting.replace(':','.');
        add("painting."+newPainting+".title", paintingName);
        add("painting."+newPainting+".author", paintingAuthor);

    }

    public void addNameType(String keyName, String translatedName) {
        add("name.jojojetzmod."+keyName, translatedName);
    }

    public void addAdvancementType(String keyName,
                                   String translatedTitle,
                                   String translatedDescription) {
        add("advancements.jojojetzmod."+keyName+".title", translatedTitle);
        add("advancements.jojojetzmod."+keyName+".description", translatedDescription);
    }
}
