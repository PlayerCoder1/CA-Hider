package com.playercoder1;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("cadescriptionhider")
public interface CaDescriptionHiderConfig extends Config
{
    @ConfigItem(
            keyName = "replacementText",
            name = "Replacement text",
            description = "What to show instead of the real Combat Achievement description",
            position = 0
    )
    default String replacementText()
    {
        return "HIDDEN";
    }
}
