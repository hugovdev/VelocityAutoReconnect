package de.flori4nk.velocityautoreconnect.listeners;

import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.PlayerResourcePackStatusEvent;
import com.velocitypowered.api.event.player.ServerPostConnectEvent;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ServerConnection;
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;

import java.util.Optional;

public class MusicPlayer {

    public static final MinecraftChannelIdentifier IDENTIFIER = MinecraftChannelIdentifier.from("thankmas:lobby_music");

    @Subscribe(order = PostOrder.FIRST)
    public void onPlayerPostConnect(PlayerResourcePackStatusEvent event) {
        if (event.getStatus() != PlayerResourcePackStatusEvent.Status.SUCCESSFUL) return;

        Optional<ServerConnection> currentServer = event.getPlayer().getCurrentServer();

        if (currentServer.isEmpty()) return;

        ServerConnection serverConnection = currentServer.get();

        if (!serverConnection.getServerInfo().getName().contains("lobby")) return;

        playLobbySong(serverConnection, event.getPlayer());
    }

    @Subscribe(order = PostOrder.FIRST)
    public void onPlayerPostConnect(ServerPostConnectEvent event) {
        Player player = event.getPlayer();

        Optional<ServerConnection> currentServer = player.getCurrentServer();

        if (currentServer.isEmpty()) return;

        ServerConnection currentServerConnection = currentServer.get();

        if (currentServerConnection.getServerInfo().getName().contains("lobby") && player.getAppliedResourcePack() != null) {
            playLobbySong(currentServerConnection, player);
        }
    }

    private void playLobbySong(ServerConnection server, Player player) {
        server.sendPluginMessage(IDENTIFIER, new byte[0]);
    }

}
