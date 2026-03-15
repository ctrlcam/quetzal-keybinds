package com.quetzalkeybinds;

import com.google.inject.Provides;
import net.runelite.api.Client;
import net.runelite.api.events.WidgetClosed;
import net.runelite.api.events.WidgetLoaded;
import net.runelite.api.gameval.InterfaceID;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.config.Keybind;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.input.KeyListener;
import net.runelite.client.input.KeyManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

import javax.inject.Inject;
import java.awt.event.KeyEvent;

@PluginDescriptor(
    name = "Quetzal Keybinds",
    description = "Keybinds for quetzal menu",
    tags = { "quetzal", "keybind", "varlamore", "qol", "teleport", "navigation" }
)
public class QuetzalKeybindsPlugin extends Plugin
{
    @Inject private Client client;
    @Inject private QuetzalKeybindsConfig config;
    @Inject private KeyManager keyManager;
    @Inject private ClientThread clientThread;
    @Inject private OverlayManager overlayManager;
    @Inject private QuetzalKeybindsOverlay overlay;

    @Provides
    QuetzalKeybindsConfig provideConfig(ConfigManager configManager) { return configManager.getConfig(QuetzalKeybindsConfig.class); }


    private final KeyListener hotkeyListener = new KeyListener() {
        @Override
        public void keyPressed(KeyEvent e) {
            // check if this keypress is one of configured keybinds
            for (int i = 0; i < QuetzalKeybindsConfig.KEYBINDS.size(); i++) {
                var entry = QuetzalKeybindsConfig.KEYBINDS.get(i);
                Keybind keybind = entry.getValue().apply(config);
                if (!matchKeybind(e, keybind)) continue;

                // keybind pressed -> interact with associated menu item by index
                final int index = i;
                clientThread.invoke(() -> {
                    var parent = client.getWidget(InterfaceID.QuetzalMenu.ICONS);
                    if (parent == null || parent.isHidden()) return;

                    var widget = parent.getChild(index);
                    if (widget == null || !widget.hasListener()) return;

                    String name = entry.getKey();
                    client.menuAction(index, widget.getId(), net.runelite.api.MenuAction.CC_OP, 1, -1, name, "");
                });
                return;
            }
        }

        boolean matchKeybind(KeyEvent e, Keybind keybind) {
            if (keybind == null || !keybind.matches(e)) return false;
            e.consume();
            return true;
        }

        @Override public void keyReleased(KeyEvent e) {}
        @Override public void keyTyped(KeyEvent e) {}
    };


    // region toggling
    /** for toggling  hotkeys and overlay. This should only be true when quetzal menu is open. */
    boolean isToggled = false;
    private void toggle(boolean toggled) {
        if (isToggled == toggled) return;

        isToggled = toggled;
        if (toggled) {
            keyManager.registerKeyListener(hotkeyListener);
            overlayManager.add(overlay);
        }
        else {
            keyManager.unregisterKeyListener(hotkeyListener);
            overlayManager.remove(overlay);
        }
    }

    @Override protected void shutDown() {
        toggle(false);
    }
    @Subscribe
    private void onWidgetLoaded(WidgetLoaded event) {
        // opened quetzal menu -> turn on
        if (event.getGroupId() == InterfaceID.QUETZAL_MENU) {
            toggle(true);
        }
    }
    @Subscribe
    private void onWidgetClosed(WidgetClosed event) {
        // closed quetzal menu -> turn off
        if (event.getGroupId() == InterfaceID.QUETZAL_MENU) {
            toggle(false);
        }
    }
    @Subscribe
    private void onConfigChanged(ConfigChanged event)  {
        if (!event.getGroup().equals(QuetzalKeybindsConfig.GROUP)) return;

        // changing any config setting will reset hotkeys and overlay (if previously toggled on)
        boolean wasToggled = isToggled;
        toggle(false);
        if (wasToggled) toggle(true);
    }
    // endregion
}