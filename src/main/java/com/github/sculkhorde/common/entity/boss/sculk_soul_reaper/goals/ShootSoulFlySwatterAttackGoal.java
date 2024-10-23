package com.github.sculkhorde.common.entity.boss.sculk_soul_reaper.goals;

import com.github.sculkhorde.common.entity.boss.sculk_soul_reaper.SculkSoulReaperEntity;
import com.github.sculkhorde.common.entity.boss.sculk_soul_reaper.SoulFlySwatterProjectileEntity;
import com.github.sculkhorde.common.entity.projectile.AbstractProjectileEntity;
import com.github.sculkhorde.util.EntityAlgorithms;
import com.github.sculkhorde.util.TickUnits;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

public class ShootSoulFlySwatterAttackGoal extends Goal
{
    private final SculkSoulReaperEntity mob;
    protected final int executionCooldown = TickUnits.convertSecondsToTicks(3);
    protected int ticksElapsed = executionCooldown;
    protected final int baseCastingTime = TickUnits.convertSecondsToTicks(3);
    protected int castingTime = 0;
    boolean spellCasted = false;
    protected int minDifficulty = 0;
    protected int maxDifficulty = 0;

    public ShootSoulFlySwatterAttackGoal(SculkSoulReaperEntity mob, int minDifficulty, int maxDifficulty) {
        this.mob = mob;
        this.minDifficulty = minDifficulty;
        this.maxDifficulty = maxDifficulty;
    }

    public boolean requiresUpdateEveryTick() {
        return true;
    }

    protected int getCastingTimeElapsed()
    {
        return castingTime;
    }

    @Override
    public boolean canUse()
    {
        ticksElapsed++;

        if(mob.getTarget() == null)
        {
            return false;
        }

        if(ticksElapsed < executionCooldown)
        {
            return false;
        }

        if(EntityAlgorithms.getHeightOffGround(mob.getTarget()) <= 2)
        {
            return false;
        }

        if(mob.getMobDifficultyLevel() < minDifficulty || mob.getMobDifficultyLevel() > maxDifficulty)
        {
            return false;
        }

        return true;
    }

    @Override
    public boolean canContinueToUse()
    {
        return !spellCasted && mob.getTarget() != null;
    }

    @Override
    public void start()
    {
        super.start();

        if(mob.level().isClientSide())
        {
            return;
        }

        //getEntity().triggerAnim("attack_controller", "fireball_sky_summon_animation");
        //getEntity().triggerAnim("twitch_controller", "fireball_sky_twitch_animation");
        this.mob.getNavigation().stop();
    }

    @Override
    public void tick()
    {
        super.tick();

        if(mob.level().isClientSide())
        {
            return;
        }

        if(getCastingTimeElapsed() < baseCastingTime)
        {
            castingTime++;
            return;
        }

        if(spellCasted)
        {
            return;
        }

        shootProjectileAtTarget();
        spellCasted = true;
    }

    @Override
    public void stop()
    {
        super.stop();
        ticksElapsed = 0;
        spellCasted = false;
        castingTime = 0;
    }

    public double getRandomDoubleInRange(double min, double max)
    {
        return min + (mob.getRandom().nextFloat() * (max + min));
    }
    public int getRandomIntInRange(int min, int max)
    {
        return min + (mob.getRandom().nextInt() * (max + min));
    }

    public void shootProjectileAtTarget()
    {

        if(mob.getTarget() == null)
        {
            return;
        }

        AbstractProjectileEntity projectile =  new SoulFlySwatterProjectileEntity(mob.level(), mob, 10F);
        projectile.setPos(mob.position().add(0, mob.getEyeHeight() - projectile.getBoundingBox().getYsize() * .5f, 0));

        double spawnPosX = mob.getX();
        double spawnPosY = mob.getY() + mob.getEyeHeight();
        double spawnPosZ = mob.getZ();

        double targetPosX = mob.getTarget().getX() - spawnPosX  + getRandomDoubleInRange(0, 1);
        double targetPosY = mob.getTarget().getY() - spawnPosY + getRandomDoubleInRange(0, 1);
        double targetPosZ = mob.getTarget().getZ() - spawnPosZ + getRandomDoubleInRange(0, 1);

        // Create a vector for the direction
        Vec3 direction = new Vec3(targetPosX, targetPosY, targetPosZ).normalize();

        // Shoot the projectile in the direction vector
        projectile.shoot(direction);

        mob.playSound(SoundEvents.BLAZE_SHOOT, 1.0F, 1.0F / (mob.getRandom().nextFloat() * 0.4F + 0.8F));
        mob.level().addFreshEntity(projectile);

    }

}
//projectileEntity.shoot(d0, d1 + d3 * (double)0.2F, d2, 1.6F, (float)(14 - mob.level().getDifficulty().getId() * 4));