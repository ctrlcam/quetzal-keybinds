package com.quetzalkeybinds;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class QuetzalKeybindsPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(QuetzalKeybindsPlugin.class);
		RuneLite.main(args);
	}
}