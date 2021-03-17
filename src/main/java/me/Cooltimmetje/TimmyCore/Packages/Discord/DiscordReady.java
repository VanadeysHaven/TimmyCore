package me.Cooltimmetje.TimmyCore.Packages.Discord;

import github.scarsz.discordsrv.api.Subscribe;
import github.scarsz.discordsrv.api.events.DiscordReadyEvent;
import github.scarsz.discordsrv.util.DiscordUtil;

public final class DiscordReady {

    @Subscribe
    public void discordReadyEvent(DiscordReadyEvent event){
        DiscordUtil.getJda().addEventListener(new VoiceJoinLeaveListener());
    }

}
