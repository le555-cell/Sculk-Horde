package com.github.sculkhorde.common.entity;

import java.util.function.Predicate;

import com.github.sculkhorde.common.entity.infection.CursorSurfaceInfectorEntity;
import com.github.sculkhorde.core.ModConfig;
import com.github.sculkhorde.core.ModEntities;
import com.github.sculkhorde.core.SculkHorde;
import com.github.sculkhorde.util.BlockAlgorithms;

import mod.azure.azurelib.animatable.GeoEntity;
import mod.azure.azurelib.constant.DefaultAnimations;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager;
import mod.azure.azurelib.util.AzureLibUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class SculkBeeInfectorEntity extends SculkBeeHarvesterEntity implements GeoEntity {

    /**
     * In order to create a mob, the following java files were created/edited.<br>
     * Edited core/ EntityRegistry.java<br>
     * Edited util/ ModEventSubscriber.java<br>
     * Edited client/ ClientModEventSubscriber.java<br>
     * Added common/entity/ SculkBeeInfectorEntity.java<br>
     * Added client/model/entity/ SculkBeeInfectorModel.java<br>
     * Added client/renderer/entity/ SculkBeeInfectorRenderer.java
     */

    //The Health
    public static final float MAX_HEALTH = 20F;
    //FOLLOW_RANGE determines how far away this mob can see and chase enemies
    public static final float FOLLOW_RANGE = 25F;
    //MOVEMENT_SPEED determines how fast this mob moves
    public static final float MOVEMENT_SPEED = 0.5F;

    private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);

    /**
     * The Constructor
     * @param type The Mob Type
     * @param worldIn The world to initialize this mob in
     */
    public SculkBeeInfectorEntity(EntityType<? extends SculkBeeInfectorEntity> type, Level worldIn) {
        super(type, worldIn);
    }

    /**
     * An Easier Constructor where you do not have to specify the Mob Type
     * @param worldIn  The world to initialize this mob in
     */
    public SculkBeeInfectorEntity(Level worldIn) {super(ModEntities.SCULK_BEE_INFECTOR.get(), worldIn);}

    /**
     * Determines & registers the attributes of the mob.
     * @return The Attributes
     */
    public static AttributeSupplier.Builder createAttributes()
    {
        return LivingEntity.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, MAX_HEALTH)
                .add(Attributes.FOLLOW_RANGE,FOLLOW_RANGE)
                .add(Attributes.MOVEMENT_SPEED, MOVEMENT_SPEED)
                .add(Attributes.FLYING_SPEED, 1.5F);
    }

    private final Predicate<BlockPos> IS_VALID_FLOWER = (blockPos) -> {
        BlockState blockState = level.getBlockState(blockPos);
        if (blockState.hasProperty(BlockStateProperties.WATERLOGGED) && blockState.getValue(BlockStateProperties.WATERLOGGED))
        {
            return false;
        }

        if(!SculkHorde.blockInfestationTable.isInfectable(level.getBlockState(blockPos)))
        {
            return false;
        }

        if(!BlockAlgorithms.isExposedToAir((ServerLevel) level, blockPos))
        {
            return false;
        }
        return true;

    };

    @Override
    public Predicate<BlockPos> getIsFlowerValidPredicate() {
        return this.IS_VALID_FLOWER;
    }

    public double getArrivalThreshold() {
        return 3D;
    }

    @Override
    protected void executeCodeOnPollination()
    {
        if(!ModConfig.SERVER.block_infestation_enabled.get())
        {
            return;
        }

        CursorSurfaceInfectorEntity cursor = new CursorSurfaceInfectorEntity(level);
        cursor.setPos(this.blockPosition().getX(), this.blockPosition().getY(), this.blockPosition().getZ());
        cursor.setMaxTransformations(100);
        cursor.setMaxRange(100);
        cursor.setTickIntervalMilliseconds(500);
        cursor.setSearchIterationsPerTick(20);
        level.addFreshEntity(cursor);
    }

    /** ~~~~~~~~ ANIMATION ~~~~~~~~ **/

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(DefaultAnimations.genericFlyController(this));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }


    @Override
    public boolean isFlying() {
        return true;
    }

    public boolean dampensVibrations() {
        return true;
    }


    /* DO NOT USE THIS FOR ANYTHING, CAUSES DESYNC
    @Override
    public void onRemovedFromWorld() {
        SculkHorde.savedData.addSculkAccumulatedMass((int) this.getHealth());
        super.onRemovedFromWorld();
    }
    */

}
