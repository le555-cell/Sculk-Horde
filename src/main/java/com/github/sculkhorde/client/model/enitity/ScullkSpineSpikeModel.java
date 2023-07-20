package com.github.sculkhorde.client.model.enitity;

import com.github.sculkhorde.common.entity.specialeffects.ChaosTeleporationRiftEntity;
import com.github.sculkhorde.common.entity.specialeffects.SculkSpineSpikeAttackEntity;
import com.github.sculkhorde.core.SculkHorde;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class ScullkSpineSpikeModel extends DefaultedEntityGeoModel<SculkSpineSpikeAttackEntity> {
    public ScullkSpineSpikeModel() {
        super(new ResourceLocation(SculkHorde.MOD_ID, "sculk_spine_spike"));
    }

    // We want our model to render using the translucent render type
    @Override
    public RenderType getRenderType(SculkSpineSpikeAttackEntity animatable, ResourceLocation texture) {
        return RenderType.entityTranslucent(getTextureResource(animatable));
    }
}
