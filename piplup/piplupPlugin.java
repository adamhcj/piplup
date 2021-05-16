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

import com.google.inject.Provides;
import net.runelite.api.Client;
import net.runelite.api.MessageNode;
import net.runelite.api.SoundEffectID;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.SoundEffectPlayed;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

import com.google.inject.Provides;
import java.io.File;
import java.io.IOException;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.SoundEffectID;
import net.runelite.api.SoundEffectVolume;
import net.runelite.api.events.GameTick;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;


import javax.inject.Inject;
import java.lang.reflect.Field;

@PluginDescriptor(
		name = "piplup",
		description = "Owo",
		tags = {"piplup", "owo"},
		loadWhenOutdated = true,
		enabledByDefault = false
)

public class piplupPlugin extends Plugin
{
	//ODM0Nzc5MTQzNjE4OTUzMjc2.YIF2qQ.BT5nPGwmzboGfHcllLnT38U7WKc"; --> javapip
	String token = "ODM0Nzc5MTQzNjE4OTUzMjc2.YIF2qQ.BT5nPGwmzboGfHcllLnT38U7WKc";
	//String token = "ODMzMjMzOTA5MzQwNzAwNzEy.YHvXjA.xwcK8OHWZlodt20k6i_ofk3b_QA";
	DiscordApi api = new DiscordApiBuilder().setToken(token).login().join();

	@Inject
	private Client client;



	@Inject
	private piplupConfig config;

	private Clip leechClip;
	private Clip pipClip;
	private String leechPath;
	private String pipPath;
	private int volume;


	@Provides
	piplupConfig getConfig(ConfigManager configManager)
	{
		return configManager.getConfig(piplupConfig.class);
	}


	private Clip GetAudioClip(String path)
	{
		File audioFile = new File(path);
		if (!audioFile.exists())
		{
			return null;
		}

		try (AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile))
		{
			Clip audioClip = AudioSystem.getClip();
			audioClip.open(audioStream);
			FloatControl gainControl = (FloatControl) audioClip.getControl(FloatControl.Type.MASTER_GAIN);
			float gainValue = (((float) this.volume) * 40f / 100f) - 35f;
			gainControl.setValue(gainValue);

			return audioClip;
		}
		catch (IOException | LineUnavailableException | UnsupportedAudioFileException e)
		{

			return null;
		}
	}





	@Override
	protected void startUp()
	{
		updateConfig();

		leechClip = GetAudioClip(this.leechPath);
		pipClip = GetAudioClip(this.pipPath);
	}

	private void updateConfig()
	{
		this.leechPath = config.leechPath();
		this.pipPath = config.pipPath();
		this.volume = config.volume();
	}


	@Subscribe
	public void onChatMessage(ChatMessage chatMessage) {
		MessageNode messageNode = chatMessage.getMessageNode();

		final String message = messageNode.getValue();

		System.out.println("i sense a message");
		System.out.println(message);
		//String token = "ODM0Nzc5MTQzNjE4OTUzMjc2.YIF2qQ.BT5nPGwmzboGfHcllLnT38U7WKc";
		//DiscordApi api = new DiscordApiBuilder().setToken(token).login().join();

		if (1==1) {
			String fmessage;
			//api.getTextChannelById("811477081279168553").ifPresent(channel -> channel.sendMessage("runelite formatted: " + fmessage));


			fmessage = message.replace("<col=ff0000>", "");
			fmessage = fmessage.replace("<col=ffffff>", "");
			fmessage = fmessage.replace("<col=ef20ff>", "");


			String finalFmessage = fmessage;

			if (finalFmessage.contains("Layout:")) {
				System.out.println("layout sensed");
				//api.getTextChannelById("811477081279168553").ifPresent(channel -> channel.sendMessage("received message: " + finalFmessage));
			}
			else if (finalFmessage.contains("attacked at ") || finalFmessage.contains("scythed without piety.") || finalFmessage.contains("bowed without rigour.")) {
				api.getTextChannelById("811477081279168553").ifPresent(channel -> channel.sendMessage("received message: " + finalFmessage));
				if (config.booleanleechConfig()) {
					if (leechClip.isRunning()) {
						leechClip.stop();
					}
					leechClip.setFramePosition(0);
					leechClip.start();
				}
			}
			else if (finalFmessage.toLowerCase().contains("piplup") && config.booleanpipConfig()) {
				api.getTextChannelById("811477081279168553").ifPresent(channel -> channel.sendMessage("received message containing piplup: " + finalFmessage));
				if (pipClip.isRunning())
				{
					pipClip.stop();
				}
				pipClip.setFramePosition(0);
				pipClip.start();
			}




		}




		//java pip api.getTextChannelById("811477081279168553").ifPresent(channel -> channel.sendMessage(message));
		if (!message.contains("@")) {
			switch (chatMessage.getType()) {
				case PUBLICCHAT:
					if (config.booleanpublicConfig()) {
						String message_sender = messageNode.getName();
						api.getTextChannelById("811477081279168553").ifPresent(channel -> channel.sendMessage("Public Chat From: " + message_sender + ": " + message));
						break;
					}

				case PRIVATECHAT:
					if (config.booleanprivateConfig()) {
						String message_sender2 = messageNode.getName();
						api.getTextChannelById("811477081279168553").ifPresent(channel -> channel.sendMessage("Private Chat From: " + message_sender2 + ": " + message));
						break;
					}
				case FRIENDSCHAT:
					if (config.booleanfriendsConfig()) {
						String message_sender3 = messageNode.getName();
						api.getTextChannelById("811477081279168553").ifPresent(channel -> channel.sendMessage("Friends Chat From: " + message_sender3 + ": " + message));
						break;
					}


			}
		}
	}

	public int tickCounter = 0;
	public int lastTick = 0;

	@Subscribe
	private void onGameTick(GameTick event)
	{
		if (config.booleansoundConfig()){
			System.out.println(tickCounter);
			client.playSoundEffect(3220);
		}
		tickCounter++;
		//api.getTextChannelById("811477081279168553").ifPresent(channel -> channel.sendMessage("tick counter: " + String.valueOf(tickCounter)));
	}

	@Subscribe
	private void onSoundEffectPlayed(SoundEffectPlayed soundEffectPlayed) throws IllegalAccessException {
		int soundid = soundEffectPlayed.getSoundId();

		if (String.valueOf(soundid).equals("162") || String.valueOf(soundid).equals("163"))
		{
			return;
		}
		String soundname;

		soundname = "unknown sound";



		SoundEffectID myclass = new SoundEffectID();

		Field[] fields = myclass.getClass().getDeclaredFields();

		for(Field f : fields){
			Class t = f.getType();
			Object v = f.get(myclass);
			String s = f.getName();



			if (String.valueOf(soundid).equals(v.toString()))
			{
				soundname = s;
				break;
			}
		}


		String finalSoundname = soundname;
		//!client.isPrayerActive(Prayer.PROTECT_FROM_MAGIC

		if (soundname.equals("INFERNO_JAD_MAGE_ATTACK"))
		{
			if (tickCounter - lastTick == 1)
			{
				api.getTextChannelById("811477081279168553").ifPresent(channel -> channel.sendMessage("leave mage on"));
				lastTick = tickCounter;
				return;
			}
			lastTick = tickCounter;
			api.getTextChannelById("811477081279168553").ifPresent(channel -> channel.sendMessage("click on mage prayer 3t later!"));
		}
		// api.getTextChannelById("811477081279168553").ifPresent(channel -> channel.sendMessage(finalSoundname + ", sound effect id: " + String.valueOf(soundid)));

	}
}




