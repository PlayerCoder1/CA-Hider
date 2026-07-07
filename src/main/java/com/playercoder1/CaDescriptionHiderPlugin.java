package com.playercoder1;

import com.google.inject.Provides;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.events.ClientTick;
import net.runelite.api.widgets.Widget;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

@PluginDescriptor(
        name = "CA Description Hider",
        description = "Hides Combat Achievement task descriptions so you can guess the task from its name",
        tags = {"combat", "achievements", "ca", "tasks", "hide", "spoiler"}
)
public class CaDescriptionHiderPlugin extends Plugin
{
    private static final int CA_TASKS_GROUP_ID = 715;
    private static final int TASKS_DESCRIPTION_CHILD_ID = 13;

    private static final String DESCRIPTION_PREFIX = "Description:";

    @Inject
    private Client client;

    @Inject
    private CaDescriptionHiderConfig config;

    @Provides
    CaDescriptionHiderConfig provideConfig(ConfigManager configManager)
    {
        return configManager.getConfig(CaDescriptionHiderConfig.class);
    }

    @Subscribe
    public void onClientTick(ClientTick event)
    {
        hideDescriptions(client.getWidget(CA_TASKS_GROUP_ID, TASKS_DESCRIPTION_CHILD_ID));
    }

    private void hideDescriptions(Widget container)
    {
        if (container == null || container.isHidden())
        {
            return;
        }

        replaceInChildren(container.getDynamicChildren());
        replaceInChildren(container.getStaticChildren());
        replaceInChildren(container.getNestedChildren());
    }

    private void replaceInChildren(Widget[] children)
    {
        if (children == null)
        {
            return;
        }

        final String replacement = DESCRIPTION_PREFIX + " <col=ffffff>" + config.replacementText() + "</col>";

        for (Widget child : children)
        {
            if (child == null)
            {
                continue;
            }

            String text = child.getText();
            if (text != null
                    && text.startsWith(DESCRIPTION_PREFIX)
                    && !text.equals(replacement))
            {
                child.setText(replacement);
            }
        }
    }
}
