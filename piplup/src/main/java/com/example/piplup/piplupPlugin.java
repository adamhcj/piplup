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
//package net.runelite.client.plugins.piplup;
package com.example.piplup;


import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.*;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;
import org.pf4j.Extension;

import javax.inject.Inject;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

@Extension
@PluginDescriptor(
		name = "piplup",
		description = "Owo",
		tags = {"piplup", "owo"},
		loadWhenOutdated = true,
		enabledByDefault = false
)

@Slf4j
public class piplupPlugin extends Plugin {

	@Inject
	private Client client;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private piplupOverlay overlay;

	@Inject
	private piplupInvenOverlay invenoverlay;

	@Inject
	private piplupConfig config;

	@Inject
	private ConfigManager configManager;

	private Clip leechClip;
	private Clip pipClip;
	private String leechPath;
	private String pipPath;

	private Clip lowhpClip;
	private String lowhpPath;

	private Clip nukeClip;
	private String nukePath;

	private Clip battleClip;
	private String battlePath;

	private Clip battle2Clip;
	private String battle2Path;

	private Clip battle3Clip;
	private String battle3Path;

	private Clip victoryClip;
	private String victoryPath;

	private int volume;

	private Boolean soundplay = false;

	private int cooldown = 0;

	private Boolean maidenStarted = false;
	private Boolean nyloStarted = false;
	private Boolean sotetStarted = false;
	private Boolean xarpusStarted = false;
	private Boolean verzikStarted = false;

	private Boolean bloatStarted = false;
	private Boolean bloatspawned = false;
	private Boolean victory = false;
	private Boolean championvictory = false;


	private int worldlocationx = 0;
	private int worldlocationy = 0;


	static final int SOTETSEG_BIG_AOE_ORB = 1604;







	@Provides
	piplupConfig provideConfig(ConfigManager configManager) {
		return configManager.getConfig(piplupConfig.class);
	}


	private Clip GetAudioClip(String path) {
		File audioFile = new File(path);
		if (!audioFile.exists()) {
			return null;
		}

		try (AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile)) {
			Clip audioClip = AudioSystem.getClip();
			audioClip.open(audioStream);
			FloatControl gainControl = (FloatControl) audioClip.getControl(FloatControl.Type.MASTER_GAIN);
			float gainValue = (((float) this.volume) * 40f / 100f) - 35f;
			gainControl.setValue(gainValue);

			return audioClip;
		} catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {

			return null;
		}
	}


	@Override
	protected void startUp() {
		updateConfig();

		leechClip = GetAudioClip(this.leechPath);
		pipClip = GetAudioClip(this.pipPath);
		lowhpClip = GetAudioClip(this.lowhpPath);
		nukeClip = GetAudioClip(this.nukePath);
		battleClip = GetAudioClip(this.battlePath);
		battle2Clip = GetAudioClip(this.battle2Path);
		battle3Clip = GetAudioClip(this.battle3Path);
		victoryClip = GetAudioClip(this.victoryPath);


		overlayManager.add(overlay);
		overlayManager.add(invenoverlay);

	}

	private void updateConfig() {
		this.leechPath = config.leechPath();
		this.pipPath = config.pipPath();
		this.lowhpPath = config.lowhpPath();
		this.nukePath = config.nukePath();
		this.battlePath = config.battlePath();
		this.battle2Path = config.battle2Path();
		this.battle3Path = config.battle3Path();
		this.victoryPath = config.victoryPath();
		this.volume = config.volume();

	}


	@Override
	protected void shutDown() throws Exception
	{
		overlayManager.remove(overlay);
		overlayManager.remove(invenoverlay);
		victory = false;
		maidenStarted = false;
		nyloStarted = false;
		sotetStarted = false;
		xarpusStarted = false;
		verzikStarted = false;
		bloatspawned = false;
		bloatStarted = false;

		if (victoryClip.isRunning()){
			victoryClip.stop();
		}

		if (battleClip.isRunning()){
			battleClip.stop();
		}

		if (battle2Clip.isRunning()){
			battle2Clip.stop();
		}

		if (battle3Clip.isRunning()){
			battle3Clip.stop();
		}

		if (lowhpClip.isRunning()){
			lowhpClip.stop();
		}

		if (nukeClip.isRunning()){
			nukeClip.stop();
		}

		if (leechClip.isRunning()){
			leechClip.stop();
		}

		if (pipClip.isRunning()){
			pipClip.stop();
		}
	}





	@Subscribe
	public void onChatMessage(ChatMessage chatMessage) {
		MessageNode messageNode = chatMessage.getMessageNode();

		final String message = messageNode.getValue();


		if (1 == 1) {


			if (message.contains("attacked at ") || message.contains("scythed without piety.") || message.contains("bowed without rigour.")) {

				if (config.booleanleechConfig()) {
					if (leechClip.isRunning()) {
						leechClip.stop();
					}
					leechClip.setFramePosition(0);
					leechClip.start();
				}
			} else if (message.toLowerCase().contains("piplup") && config.booleanpipConfig()) {

				if (pipClip.isRunning()) {
					pipClip.stop();
				}
				pipClip.setFramePosition(0);
				pipClip.start();
			}

			if (message.toLowerCase().equals("s")) {
				cooldown = 10;
				victory = false;
				maidenStarted = false;
				nyloStarted = false;
				sotetStarted = false;
				xarpusStarted = false;
				verzikStarted = false;
				bloatspawned = false;
				bloatStarted = false;

				if (lowhpClip.isRunning()) {
					lowhpClip.stop();
				}

				if (battleClip.isRunning()) {
					battleClip.stop();
				}

				if (battle2Clip.isRunning()) {
					battle2Clip.stop();
				}

				if (battle3Clip.isRunning()) {
					battle3Clip.stop();
				}

				if (victoryClip.isRunning()) {
					victoryClip.stop();
				}

			}
			if (message.toLowerCase().equals("p")) {
				maidenStarted = true;
				victory = false;

			}



		}


	}


	@Subscribe
	public void onGameTick(GameTick event) {



		Player localplayer = client.getLocalPlayer();



		WorldPoint worldlocation = localplayer.getWorldLocation();





		if (Math.abs(worldlocationx - worldlocation.getX()) > 30 || Math.abs(worldlocationy - worldlocation.getY()) > 30 ){
			victory = false;
			maidenStarted = false;
			nyloStarted = false;
			sotetStarted = false;
			xarpusStarted = false;
			verzikStarted = false;
			bloatspawned = false;
			bloatStarted = false;
		}
		worldlocationx = worldlocation.getX();
		worldlocationy = worldlocation.getY();


		//System.out.println(String.valueOf(worldlocationx) + " x" + String.valueOf(worldlocationy) + " y");



		boolean foundBigOrb = false;
		for (Projectile p : client.getProjectiles())
		{
			if (p.getId() == SOTETSEG_BIG_AOE_ORB)
			{
				foundBigOrb = true;
				if (nukeClip.isRunning()) {
					nukeClip.stop();
				}
				nukeClip.setFramePosition(0);
				nukeClip.start();
				break;
			}
		}

		//put sound effects below cooldown
		if (cooldown > 0){
			cooldown--;
			return;
		}

		if (config.booleansoundConfig()) {

			client.playSoundEffect(3220);
		}


		float maxhealth = client.getRealSkillLevel(Skill.HITPOINTS);
		float currenthealth = client.getBoostedSkillLevel(Skill.HITPOINTS);

		float healthratio = currenthealth/maxhealth;



		//System.out.println("player health ratio: "+String.valueOf(healthratio));

		if (healthratio <= 0.33 && !soundplay) {
			soundplay = true;

			if (lowhpClip.isRunning()) {
				lowhpClip.stop();
			}
			lowhpClip.setFramePosition(0);
			lowhpClip.start();

		}
		else if (healthratio > 0.33 && soundplay) {
			soundplay = false;
			if (lowhpClip.isRunning()) {
				lowhpClip.stop();
			}
		}
		else if (healthratio <= 0.33 && soundplay){
			if (lowhpClip.isRunning() == false) {
				lowhpClip.setFramePosition(0);
				lowhpClip.start();
			}
		}

		if (maidenStarted || nyloStarted || sotetStarted || xarpusStarted){
			if (!victory && !battleClip.isRunning()){
				battleClip.setFramePosition(0);
				battleClip.start();
			}

		}
		else {
			if (battleClip.isRunning()) {
				battleClip.stop();
			}
		}

		if (bloatStarted){
			if (!victory && !battle2Clip.isRunning()){
				battle2Clip.setFramePosition(0);
				battle2Clip.start();
			}

		}
		else {
			if (battle2Clip.isRunning()) {
				battle2Clip.stop();
			}
		}

		if (verzikStarted){
			if (!championvictory && !battle3Clip.isRunning()){
				battle3Clip.setFramePosition(0);
				battle3Clip.start();
			}

		}
		else {
			if (battle3Clip.isRunning()) {
				battle3Clip.stop();
			}
		}

		if (victory) {
			if (battleClip.isRunning()) {
				battleClip.stop();
			}
			if (battle2Clip.isRunning()) {
				battle2Clip.stop();
			}
			if (!victoryClip.isRunning()) {
				victoryClip.setFramePosition(0);
				victoryClip.start();
			}
		}
		else{
			if (victoryClip.isRunning()){
				victoryClip.stop();
			}
		}
	}


	@Subscribe
	public void onNpcSpawned(NpcSpawned npcSpawned) {

		NPC npc = npcSpawned.getNpc();

		String npcName = npc.getName();

		int npcid = npc.getId();

		//System.out.println(npcName + " has spawned");
		//System.out.println(String.valueOf(npcid) + " npc id");


		if (npcid == NpcID.THE_MAIDEN_OF_SUGADINTI){
			maidenStarted = true;
			//System.out.println("maiden started");

		}

		if (npcid == NpcID.PESTILENT_BLOAT){
			bloatspawned = true;
			//System.out.println("bloat spawned");

		}


		if (npcid == NpcID.NYLOCAS_VASILIAS){
			nyloStarted = true;
			//System.out.println("nylo started");

		}

		if (npcid == NpcID.SOTETSEG){
			sotetStarted = true;
			//System.out.println("sotet started");

		}

		if (npcid == NpcID.XARPUS){
			xarpusStarted = true;
			//System.out.println("xarpus started");

		}

		if (npcid == NpcID.VERZIK_VITUR_8370){
			verzikStarted = true;
			//System.out.println("verzik started");

		}




	}


	@Subscribe
	public void onVarbitChanged(VarbitChanged event)
	{
		if (client.getVarbitValue(6447) == 1 && !bloatStarted && bloatspawned)
		{
			bloatStarted = true;
		}
	}


	public Actor getLocalPlayer(){
		return client.getLocalPlayer();
	}



	@Subscribe
	public void onNpcDespawned(NpcDespawned npcDespawned)
	{
		NPC npc = npcDespawned.getNpc();

		if (npc.getId() == NpcID.THE_MAIDEN_OF_SUGADINTI_8365)
		{
			victory = true;
			maidenStarted = false;
			nyloStarted = false;
			sotetStarted = false;
			xarpusStarted = false;
			verzikStarted = false;
			bloatspawned = false;
			bloatStarted = false;
		}


		if (npc.getId() == NpcID.PESTILENT_BLOAT)
		{
			victory = true;
			maidenStarted = false;
			nyloStarted = false;
			sotetStarted = false;
			xarpusStarted = false;
			verzikStarted = false;
			bloatspawned = false;
			bloatStarted = false;
		}

		if (npc.getId() == NpcID.NYLOCAS_VASILIAS)
		{
			victory = true;
			maidenStarted = false;
			nyloStarted = false;
			sotetStarted = false;
			xarpusStarted = false;
			verzikStarted = false;
			bloatspawned = false;
			bloatStarted = false;
		}

		if (npc.getId() == NpcID.SOTETSEG)
		{
			victory = true;
			maidenStarted = false;
			nyloStarted = false;
			sotetStarted = false;
			xarpusStarted = false;
			verzikStarted = false;
			bloatspawned = false;
			bloatStarted = false;
		}

		if (npc.getId() == NpcID.XARPUS)
		{
			victory = true;
			maidenStarted = false;
			nyloStarted = false;
			sotetStarted = false;
			xarpusStarted = false;
			verzikStarted = false;
			bloatspawned = false;
			bloatStarted = false;
		}

		if (npc.getId() == NpcID.VERZIK_VITUR_8375)
		{
			victory = true;
			maidenStarted = false;
			nyloStarted = false;
			sotetStarted = false;
			xarpusStarted = false;
			verzikStarted = false;
			bloatspawned = false;
			bloatStarted = false;
		}

	}

}




