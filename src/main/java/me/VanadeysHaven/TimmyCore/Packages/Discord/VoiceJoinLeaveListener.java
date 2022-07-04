package me.VanadeysHaven.TimmyCore.Packages.Discord;

import github.scarsz.discordsrv.dependencies.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import github.scarsz.discordsrv.dependencies.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import github.scarsz.discordsrv.dependencies.jda.api.events.guild.voice.GuildVoiceMoveEvent;
import github.scarsz.discordsrv.dependencies.jda.api.hooks.ListenerAdapter;
import me.VanadeysHaven.TimmyCore.Utilities.StringUtilities;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

public final class VoiceJoinLeaveListener extends ListenerAdapter {

    @Override
    public void onGuildVoiceJoin(@NotNull GuildVoiceJoinEvent event) {
        Bukkit.broadcastMessage(StringUtilities.colorify("&8[&bDiscord&8] » &b" + event.getMember().getEffectiveName() + " &2joined &avoice channel &b" + event.getChannelJoined().getName() + "&a."));
    }

    @Override
    public void onGuildVoiceLeave(@NotNull GuildVoiceLeaveEvent event) {
        Bukkit.broadcastMessage(StringUtilities.colorify("&8[&bDiscord&8] » &b" + event.getMember().getEffectiveName() + " &cleft &avoice channel &b" + event.getChannelLeft().getName() + "&a."));
    }

    @Override
    public void onGuildVoiceMove(@NotNull GuildVoiceMoveEvent event) {
        Bukkit.broadcastMessage(StringUtilities.colorify("&8[&bDiscord&8] » &b" + event.getMember().getEffectiveName() + " &6moved &afrom voice channel &b" + event.getChannelLeft().getName() + " &ato voice channel &b" + event.getChannelJoined().getName() + "&a."));
    }

}
