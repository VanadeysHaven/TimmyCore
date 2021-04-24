package me.VanadeysHaven.TimmyCore.Packages.Discord;

import github.scarsz.discordsrv.dependencies.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import github.scarsz.discordsrv.dependencies.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import github.scarsz.discordsrv.dependencies.jda.api.events.guild.voice.GuildVoiceMoveEvent;
import github.scarsz.discordsrv.dependencies.jda.api.hooks.ListenerAdapter;
import me.VanadeysHaven.TimmyCore.Utilities.Constants;
import me.VanadeysHaven.TimmyCore.Utilities.StringUtilities;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

public final class VoiceJoinLeaveListener extends ListenerAdapter {

    @Override
    public void onGuildVoiceJoin(@NotNull GuildVoiceJoinEvent event) {
        if(event.getChannelJoined().getId().equals(Constants.DISCORD_VOICE_ID)){
            sendJoin(event.getMember().getEffectiveName());
        }
    }

    @Override
    public void onGuildVoiceLeave(@NotNull GuildVoiceLeaveEvent event) {
        if(event.getChannelLeft().getId().equals(Constants.DISCORD_VOICE_ID)){
            sendLeave(event.getMember().getEffectiveName());
        }
    }

    @Override
    public void onGuildVoiceMove(@NotNull GuildVoiceMoveEvent event) {
        if(event.getChannelJoined().getId().equals(Constants.DISCORD_VOICE_ID)){
            sendJoin(event.getMember().getEffectiveName());
        } else if(event.getChannelLeft().getId().equals(Constants.DISCORD_VOICE_ID)){
            sendLeave(event.getMember().getEffectiveName());
        }
    }

    public void sendJoin(String name){
        Bukkit.broadcastMessage(StringUtilities.colorify("&8[&bDiscord&8] » &b" + name + " &2joined &athe voice channel."));
    }

    public void sendLeave(String name){
        Bukkit.broadcastMessage(StringUtilities.colorify("&8[&bDiscord&8] » &b" + name + " &cleft &athe voice channel."));
    }

}
