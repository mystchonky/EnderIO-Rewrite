package com.enderio.conduits.client.model.conduit.modifier;

import com.enderio.conduits.api.ConduitType;
import com.enderio.conduits.api.model.ConduitCoreModelModifier;
import com.enderio.conduits.api.model.RegisterConduitCoreModelModifiersEvent;
import me.liliandev.ensure.ensures.EnsureSide;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.neoforged.fml.ModLoader;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ConduitCoreModelModifiers {
    private static Map<ConduitType<?>, ConduitCoreModelModifier> MODIFIERS;

    @EnsureSide(EnsureSide.Side.CLIENT)
    public static void init() {
        var event = new RegisterConduitCoreModelModifiersEvent();
        ModLoader.postEvent(event);
        var factories = event.getModifiers();

        MODIFIERS = new HashMap<>();
        factories.forEach((t, f) -> MODIFIERS.put(t, f.createModifier()));
    }

    @EnsureSide(EnsureSide.Side.CLIENT)
    @Nullable
    public static ConduitCoreModelModifier getModifier(ConduitType<?> type) {
        return MODIFIERS.get(type);
    }

    @EnsureSide(EnsureSide.Side.CLIENT)
    public static Set<ModelResourceLocation> getAllModelDependencies() {
        return MODIFIERS.values().stream().flatMap(modifier -> modifier.getModelDependencies().stream()).collect(Collectors.toSet());
    }
}
