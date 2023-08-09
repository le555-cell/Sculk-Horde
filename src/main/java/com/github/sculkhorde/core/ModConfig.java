package com.github.sculkhorde.core;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

import java.io.File;

public class ModConfig {

    public static final Server SERVER;
    public static final ForgeConfigSpec SERVER_SPEC;

    public static final DataGen DATAGEN;
    public static final ForgeConfigSpec DATAGEN_SPEC;

    public static class Server {

        public final ForgeConfigSpec.ConfigValue<Boolean> target_faw_entities;

        public final ForgeConfigSpec.ConfigValue<Boolean> target_spore_entities;

        public final ForgeConfigSpec.ConfigValue<Integer> gravemind_mass_goal_for_immature_stage;

        public final ForgeConfigSpec.ConfigValue<Integer> gravemind_mass_goal_for_mature_stage;

        public final ForgeConfigSpec.ConfigValue<Integer> sculk_node_chunkload_radius;

        public final ForgeConfigSpec.ConfigValue<Boolean> should_sculk_mites_spawn_in_deep_dark;

        public final ForgeConfigSpec.ConfigValue<Integer> sculk_raid_enderman_scouting_duration_minutes;

        public final ForgeConfigSpec.ConfigValue<Integer> sculk_raid_global_cooldown_between_raids_minutes;

        public final ForgeConfigSpec.ConfigValue<Integer> sculk_raid_no_raid_zone_duration_minutes;


        public Server(ForgeConfigSpec.Builder builder) {

            builder.push("Mod Compatability");
            this.target_faw_entities = builder.comment("Default false").define("Should the Sculk Horde attack mobs from the mod 'From Another World'?",false);
            this.target_spore_entities = builder.comment("Default false").define("Should the Sculk Horde attack mobs from the mod 'Fungal Infection:Spore'?",false);
            builder.pop();


            builder.push("Gravemind Variables");
            this.gravemind_mass_goal_for_immature_stage = builder.comment("Default 5000").define("How much mass is needed for the Gravemind to enter the immature stage?",5000);
            this.gravemind_mass_goal_for_mature_stage = builder.comment("Default 20000").define("How much mass is needed for the Gravemind to enter the mature stage?",20000);
            builder.pop();

            builder.push("Sculk Node Variables");
            this.sculk_node_chunkload_radius = builder.comment("Default 15").define("How many chunks should be loaded around a sculk node?",15);
            builder.pop();

            builder.push("Sculk Mite Variables");
            this.should_sculk_mites_spawn_in_deep_dark = builder.comment("Default false").define("Should sculk mites spawn in deep dark?",false);
            builder.pop();

            builder.push("Sculk Raid Variables");
            this.sculk_raid_enderman_scouting_duration_minutes = builder.comment("Default 8").define("How long should the Sculk Enderman scout for?",8);
            this.sculk_raid_global_cooldown_between_raids_minutes = builder.comment("Default 300").define("How long should the global cooldown between raids be in minutes?",300);
            this.sculk_raid_no_raid_zone_duration_minutes = builder.comment("Default 480").define("How long should the no raid zone last at a location in minutes? This occurs when a raid succeeds or fails so that the same location is not raided for a while.",480);
            builder.pop();
        }
    }

    public static class DataGen {

        public DataGen(ForgeConfigSpec.Builder builder){

        }

    }

    static {
        Pair<Server, ForgeConfigSpec> commonSpecPair = new ForgeConfigSpec.Builder().configure(Server::new);
        SERVER = commonSpecPair.getLeft();
        SERVER_SPEC = commonSpecPair.getRight();

        Pair<DataGen , ForgeConfigSpec> commonPair = new ForgeConfigSpec.Builder().configure(DataGen::new);
        DATAGEN = commonPair.getLeft();
        DATAGEN_SPEC = commonPair.getRight();

    }

    public static void loadConfig(ForgeConfigSpec config, String path) {
        final CommentedFileConfig file = CommentedFileConfig.builder(new File(path)).sync().autosave().writingMode(WritingMode.REPLACE).build();
        file.load();
        config.setConfig(file);
    }
}