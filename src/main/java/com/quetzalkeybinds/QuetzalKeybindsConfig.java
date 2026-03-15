package com.quetzalkeybinds;


import net.runelite.client.config.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@ConfigGroup(QuetzalKeybindsConfig.GROUP)
public interface QuetzalKeybindsConfig extends Config
{
    String GROUP = "quetzalkeybinds";


    @ConfigSection(name = "Keybinds", description = "Keybinds for each each Quetzal location", position = 0)
    String keybindSection = "keybindSection";

    @ConfigSection(name = "Font Settings", description = "Font Settings for keybinds on the Quetzal Map", position = 1)
    String fontSection = "fontSection";

    @ConfigItem(keyName = "fontSize", name = "Font Size", description = "Font Size of Keybinds on Quetzal Map (0 to hide)", section= fontSection)
    @Range(max=50)
    default int fontSize() { return 26; }
    @ConfigItem(keyName = "fontColor", name = "Font Color", description = "Color of the keybind text", section = fontSection)
    default Color fontColor() { return Color.WHITE; }

    @ConfigItem(keyName = "fontBorderWidth", name = "Font Border Width", description = "Border width of the keybind text", section= fontSection)
    @Range(max=10)
    default int fontBorderWidth() { return 3; }
    @ConfigItem(keyName = "fontBorderColor", name = "Font Border Color", description = "Border color of the keybind text", section = fontSection)
    default Color fontBorderColor() { return Color.BLACK; }


    @ConfigItem(keyName = "aldarinKeybind", name = "Aldarin", description = "Keybind for Aldarin location", section=keybindSection)
    default Keybind aldarinKeybind() { return new Keybind(KeyEvent.VK_L, 0); }

    @ConfigItem(keyName = "auburnvaleKeybind", name = "Auburnvale", description = "Keybind for Auburnvale location", section=keybindSection)
    default Keybind auburnvaleKeybind() { return new Keybind(KeyEvent.VK_A, 0); }

    @ConfigItem(keyName = "camTorumEntranceKeybind", name = "Cam Torum Entrance", description = "Keybind for Cam Torum Entrance location", section=keybindSection)
    default Keybind camTorumEntranceKeybind() { return new Keybind(KeyEvent.VK_M, 0); }

    @ConfigItem(keyName = "civitasKeybind", name = "Civitas illa Fortis", description = "Keybind for Civitas illa Fortis location", section=keybindSection)
    default Keybind civitasKeybind() { return new Keybind(KeyEvent.VK_F, 0); }

    @ConfigItem(keyName = "colossalWyrmRemainsKeybind", name = "Colossal Wyrm Remains", description = "Keybind for Colossal Wyrm Remains location", section=keybindSection)
    default Keybind colossalWyrmRemainsKeybind() { return new Keybind(KeyEvent.VK_W, 0); }

    @ConfigItem(keyName = "fortisColosseumKeybind", name = "Fortis Colosseum", description = "Keybind for Fortis Colosseum location", section=keybindSection)
    default Keybind fortisColosseumKeybind() { return new Keybind(KeyEvent.VK_C, 0); }

    @ConfigItem(keyName = "hunterGuildKeybind", name = "Hunter Guild", description = "Keybind for Hunter Guild location", section=keybindSection)
    default Keybind hunterGuildKeybind() { return new Keybind(KeyEvent.VK_H, 0); }

    @ConfigItem(keyName = "kastoriKeybind", name = "Kastori", description = "Keybind for Kastori location", section=keybindSection)
    default Keybind kastoriKeybind() { return new Keybind(KeyEvent.VK_K, 0); }

    @ConfigItem(keyName = "outerFortisKeybind", name = "Outer Fortis", description = "Keybind for Outer Fortis location", section=keybindSection)
    default Keybind outerFortisKeybind() { return new Keybind(KeyEvent.VK_O, 0); }

    @ConfigItem(keyName = "quetzacalliGorgeKeybind", name = "Quetzacalli Gorge", description = "Keybind for Quetzacalli Gorge location", section=keybindSection)
    default Keybind quetzacalliGorgeKeybind() { return new Keybind(KeyEvent.VK_Q, 0); }

    @ConfigItem(keyName = "salvagerOverlookKeybind", name = "Salvager Overlook", description = "Keybind for Salvager Overlook location", section=keybindSection)
    default Keybind salvagerOverlookKeybind() { return new Keybind(KeyEvent.VK_V, 0); }

    @ConfigItem(keyName = "sunsetCoastKeybind", name = "Sunset Coast", description = "Keybind for Sunset Coast location", section=keybindSection)
    default Keybind sunsetCoastKeybind() { return new Keybind(KeyEvent.VK_S, 0); }

    @ConfigItem(keyName = "talTeklanKeybind", name = "Tal Teklan", description = "Keybind for Tal Teklan location", section=keybindSection)
    default Keybind talTeklanKeybind() { return new Keybind(KeyEvent.VK_T, 0); }

    @ConfigItem(keyName = "teomatKeybind", name = "The Teomat", description = "Keybind for The Teomat location", section=keybindSection)
    default Keybind teomatKeybind() { return new Keybind(KeyEvent.VK_O, 0); }

    // NOTE: index within array must be consistent with index of corresponding widget in QuetzalMenu.ICONS
    //       This maps quetzal location names to functions for retrieving associated keybind
    List<Map.Entry<String, Function<QuetzalKeybindsConfig, Keybind>>> KEYBINDS = List.of(
        Map.entry("Civitas illa Fortis", QuetzalKeybindsConfig::civitasKeybind),
        Map.entry("The Teomat", QuetzalKeybindsConfig::teomatKeybind),
        Map.entry("Sunset Coast", QuetzalKeybindsConfig::sunsetCoastKeybind),
        Map.entry("Hunter Guild", QuetzalKeybindsConfig::hunterGuildKeybind),
        Map.entry("Cam Torum Entrance", QuetzalKeybindsConfig::camTorumEntranceKeybind),
        Map.entry("Colossal Wyrm Remains", QuetzalKeybindsConfig::colossalWyrmRemainsKeybind),
        Map.entry("Outer Fortis", QuetzalKeybindsConfig::outerFortisKeybind),
        Map.entry("Fortis Colosseum", QuetzalKeybindsConfig::fortisColosseumKeybind),
        Map.entry("Aldarin", QuetzalKeybindsConfig::aldarinKeybind),
        Map.entry("Quetzacalli Gorge", QuetzalKeybindsConfig::quetzacalliGorgeKeybind),
        Map.entry("Salvager Overlook", QuetzalKeybindsConfig::salvagerOverlookKeybind),
        Map.entry("Tal Teklan", QuetzalKeybindsConfig::talTeklanKeybind),
        Map.entry("Auburnvale", QuetzalKeybindsConfig::auburnvaleKeybind),
        Map.entry("Kastori", QuetzalKeybindsConfig::kastoriKeybind)
    );
}

