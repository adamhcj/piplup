/*
 * Copyright (c) 2018, Tomas Slusny <slusnucky@gmail.com>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.runelite.client.plugins.piplup;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;


@ConfigGroup("piplup")
public interface piplupConfig extends Config
{
	// Configuration menu entries go here
    @ConfigItem
            (
                    position = 1,
                    keyName = "booleanpublicConfig",
                    name = "Public Chat",
                    description = "Check this to log public chat"
            )
    default boolean booleanpublicConfig() { return false; }

    @ConfigItem
            (
                    position = 2,
                    keyName = "booleanprivateConfig",
                    name = "Private Chat",
                    description = "Check this to log private chat"
            )
    default boolean booleanprivateConfig() { return false; }

    @ConfigItem
            (
                    position = 3,
                    keyName = "booleanfriendsConfig",
                    name = "Friends Chat",
                    description = "Check this to log friends chat"
            )
    default boolean booleanfriendsConfig() { return false; }

    @ConfigItem
            (
                    position = 4,
                    keyName = "booleananyConfig",
                    name = "Any Chat",
                    description = "Check this to log any chat"
            )
    default boolean booleananyConfig() { return false; }

    @ConfigItem
            (
                    position = 5,
                    keyName = "booleansoundConfig",
                    name = "sound every tick",
                    description = "plays mining sound every tick"
            )
    default boolean booleansoundConfig() { return false; }

    @ConfigItem
            (
                    position = 6,
                    keyName = "booleanleechConfig",
                    name = "Play leech sound when scythe without max stat",
                    description = "plays the sound"
            )
    default boolean booleanleechConfig() { return false; }

    @ConfigItem
            (
                    position = 7,
                    keyName = "booleanpipConfig",
                    name = "Play pip sound when scythe without max stat",
                    description = "plays the sound"
            )
    default boolean booleanpipConfig() { return false; }


    @ConfigItem(
            keyName = "leechSoundFilePath",
            name = "leech .wav file path",
            description = "The path to the file to be used for \"leech\" sounds (short .wav only)"
    )
    default String leechPath()
    {
        return "";
    }

    @ConfigItem(
            keyName = "pipSoundFilePath",
            name = "pip .wav file path",
            description = "The path to the file to be used for \"pip\" sounds (short .wav only)"
    )
    default String pipPath()
    {
        return "";
    }

    @ConfigItem(
            keyName = "volume",
            name = "Volume modification",
            description = "Configures leech volume; only effects custom sounds."
    )
    default int volume()
    {
        return 60;
    }


}

