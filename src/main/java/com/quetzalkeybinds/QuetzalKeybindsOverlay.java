package com.quetzalkeybinds;

import net.runelite.api.Client;
import net.runelite.api.gameval.InterfaceID;
import net.runelite.api.widgets.Widget;
import net.runelite.client.config.Keybind;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;

import javax.inject.Inject;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class QuetzalKeybindsOverlay extends Overlay
{
    private final Client client;
    private final QuetzalKeybindsConfig config;

    @Inject
    public QuetzalKeybindsOverlay(Client client, QuetzalKeybindsConfig config)  {
        this.client = client;
        this.config = config;

        setLayer(OverlayLayer.ABOVE_WIDGETS);
        setPosition(OverlayPosition.DYNAMIC);
    }

    @Override
    public Dimension render(Graphics2D graphics)  {
        float fontSize = config.fontSize();
        if (fontSize <= 0) return null;

        Widget iconsParent = client.getWidget(InterfaceID.QuetzalMenu.ICONS);
        if (iconsParent == null || iconsParent.isHidden()) return null;

        Widget[] children = iconsParent.getChildren();
        if (children == null) return null;


        Font prevFont = graphics.getFont();
        graphics.setFont(FontManager.getRunescapeBoldFont().deriveFont(fontSize));

        try {
            for (int i = 0; i < children.length && i < QuetzalKeybindsConfig.KEYBINDS.size(); i++) {
                Widget icon = children[i];
                if (icon == null || icon.isHidden() || !icon.hasListener()) continue;

                var entry = QuetzalKeybindsConfig.KEYBINDS.get(i);
                Keybind keybind = entry.getValue().apply(config);
                if (keybind == null || keybind == Keybind.NOT_SET) continue;
                int keyCode = keybind.getKeyCode();
                if (keyCode == 0) continue;
                String text = KeyEvent.getKeyText(keyCode);
                if (text.isEmpty() || text.startsWith("Unknown keyCode")) continue;
                String modifierText = getModifierText(keybind);
                if (!modifierText.isEmpty()) text = modifierText + text;

                Rectangle bounds = icon.getBounds();
                if (bounds == null) continue;

                drawCenteredString(graphics, text, bounds);
            }
        }
        finally {
            // not sure if setting back to original font is necessary. better safe than sorry
            graphics.setFont(prevFont);
        }

        return null;
    }

    private static String getModifierText(Keybind keybind) {
        int modifiers = keybind.getModifiers();
        if (modifiers == 0) return "";

        StringBuilder sb = new StringBuilder();
        if ((modifiers & InputEvent.CTRL_DOWN_MASK) != 0) sb.append("Ctrl+");
        if ((modifiers & InputEvent.SHIFT_DOWN_MASK) != 0) sb.append("Shift+");
        if ((modifiers & InputEvent.ALT_DOWN_MASK) != 0) sb.append("Alt+");
        if ((modifiers & InputEvent.META_DOWN_MASK) != 0) sb.append("Meta+");
        return sb.toString();
    }


    private void drawCenteredString(Graphics2D g, String text, Rectangle bounds) {
        FontMetrics metrics = g.getFontMetrics();

        int x = bounds.x + (bounds.width - metrics.stringWidth(text)) / 2;
        int y = bounds.y + ((bounds.height - metrics.getHeight()) / 2) + metrics.getAscent();

        var prevColor = g.getColor();
        g.setColor(config.fontBorderColor());

        try {
            var w = config.fontBorderWidth();
            g.drawString(text, x - w, y);
            g.drawString(text, x + w, y);
            g.drawString(text, x, y - w);
            g.drawString(text, x, y + w);

            g.setColor(config.fontColor());
            g.drawString(text, x, y);
        }
        finally {
            g.setColor(prevColor);
        }
    }
}