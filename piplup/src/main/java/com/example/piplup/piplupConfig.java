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
package com.example.piplup;

import net.runelite.client.config.*;

import java.awt.*;


@ConfigGroup("piplup")
public interface piplupConfig extends Config
{
	// Configuration menu entries go here


    @ConfigItem
            (
                    position = 1,
                    keyName = "booleansoundConfig",
                    name = "sound every tick",
                    description = "plays mining sound every tick, in game sound."
            )
    default boolean booleansoundConfig() { return false; }

    @ConfigItem
            (
                    position = 2,
                    keyName = "booleanleechConfig",
                    name = "Play leech sound when scythe without max stat",
                    description = "plays the sound"
            )
    default boolean booleanleechConfig() { return false; }

    @ConfigItem
            (
                    position = 3,
                    keyName = "booleanpipConfig",
                    name = "Play pip sound when someone says piplup",
                    description = "plays the sound"
            )
    default boolean booleanpipConfig() { return false; }


    @ConfigItem
            (
                    position = 4,
                    keyName = "booleanprojectileConfig",
                    name = "toggles piplup projectiles",
                    description = "piplup on every projectile"
            )
    default boolean booleanprojectileConfig() { return false; }

    @ConfigItem
            (
                    position = 5,
                    keyName = "booleanplayerPictureConfig",
                    name = "toggle pokemon pictures",
                    description = "image on players"
            )
    default boolean booleanplayerPictureConfig() { return false; }




    @Alpha
    @ConfigItem(
            keyName = "myColor",
            name = "color picker",
            description = "used to highlight player tile colour but deprecated.",
            position = 6
    )
    default Color myColor()
    {
        return Color.CYAN;
    }

    @ConfigItem
            (
                    position = 7,
                    keyName = "booleanownPlayerConfig",
                    name = "toggles piplup picture on your character",
                    description = "piplup on your char"
            )
    default boolean booleanownPlayerConfig() { return true; }


    @ConfigItem(
            keyName = "leechSoundFilePath",
            name = "leech .wav file path",
            description = "The path to the file to be used for \"leech\" sounds (short .wav only)"
    )
    default String leechPath()
    {
        return "D:/soundfx/rubybolt.wav";
    }

    @ConfigItem(
            keyName = "pipSoundFilePath",
            name = "pip .wav file path",
            description = "The path to the file to be used for \"pip\" sounds (short .wav only)"
    )
    default String pipPath()
    {
        return "D:\\soundfx\\pip.wav";
    }

    @ConfigItem(
            keyName = "lowhpSoundFilePath",
            name = "lowhp .wav file path",
            description = "The path to the file to be used for \"low hp\" sounds (short .wav only)"
    )
    default String lowhpPath()
    {
        return "D:\\soundfx\\lowhp1.wav";
    }

    @ConfigItem(
            keyName = "nukeSoundFilePath",
            name = "nuke .wav file path",
            description = "The path to the file to be used for \"sotet nuke\" sounds (short .wav only)"
    )
    default String nukePath()
    {
        return "D:\\soundfx\\pip.wav";
    }

    @ConfigItem(
            keyName = "battleSoundFilePath",
            name = "battle .wav file path",
            description = "The path to the file to be used for \"battle\" sounds (short .wav only)"
    )
    default String battlePath()
    {
        return "D:\\soundfx\\diamondbattle.wav";
    }

    @ConfigItem(
            keyName = "battle2SoundFilePath",
            name = "battle2 .wav file path",
            description = "The path to the file to be used for \"battle2 bloat\" sounds (short .wav only)"
    )
    default String battle2Path()
    {
        return "D:\\soundfx\\mew2battle.wav";
    }

    @ConfigItem(
            keyName = "battle3SoundFilePath",
            name = "battle3 .wav file path",
            description = "The path to the file to be used for \"battle3 verzik\" sounds (short .wav only)"
    )
    default String battle3Path()
    {
        return "D:\\soundfx\\diamondbattle.wav";
    }

    @ConfigItem(
            keyName = "victorySoundFilePath",
            name = "victory .wav file path",
            description = "The path to the file to be used for \"victory\" sounds (short .wav only)"
    )
    default String victoryPath()
    {
        return "D:\\soundfx\\victory.wav";
    }

    @ConfigItem(
            keyName = "piplupFilePath",
            name = "piplup .png file path",
            description = "The path to the file to be used for \"piplup\" images (preferably .png)"
    )
    default String piplupPath()
    {
        return "D:/gif/piplup.png";
    }

    @ConfigItem(
            keyName = "torchicFilePath",
            name = "torchic .png file path",
            description = "The path to the file to be used for \"torchic\" images (preferably .png)"
    )
    default String torchicPath()
    {
        return "D:/gif/torchic.png";
    }

    @ConfigItem(
            keyName = "spoonFilePath",
            name = "spoon .png file path",
            description = "The path to the file to be used for \"spoon\" images (preferably .png)"
    )
    default String spoonPath()
    {
        return "D:/gif/spoon.png";
    }

    @ConfigItem(
            keyName = "rarecandyPath",
            name = "rarecandy .png file path",
            description = "The path to the file to be used for \"rarecandy\" images (preferably .png)"
    )
    default String rarecandyPath()
    {
        return "D:/gif/rarecandy.png";
    }


    @Range(
            max = 80
    )
    @ConfigItem(
            keyName = "volume",
            name = "Volume modification",
            description = "Configures all the sounds produced by this plugun ^w^",
            position = 8
    )
    default int volume()
    {
        return 60;
    }

    @ConfigItem
            (
                    position = 9,
                    keyName = "hello",
                    name = "Turn off and on plugin to ensure changes in inputs are registered.",
                    description = "Turn off and on plugin to ensure changes in inputs are registered. This option does nothing."
            )
    default boolean booleanhello() { return true; }




}

