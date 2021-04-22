package me.yarond.velocityping;

import com.google.inject.Inject;
import com.moandjiezana.toml.Toml;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import org.slf4j.Logger;

import java.nio.file.Path;

@Plugin(id = "velocityping", name = "Velocityping", version = "1.0-SNAPSHOT")
public class Velocityping {

    private final ProxyServer server;

    @Inject
    private Logger logger;

    @Inject
    public Velocityping(ProxyServer server, Logger logger) {
        this.server = server;
        this.logger = logger;
        logger.info("Plugin pinger abilitato!");
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        CommandManager commandManager = server.getCommandManager();
        CommandMeta pingMeta = commandManager.metaBuilder("ping").build();
        commandManager.register(pingMeta, new PingCommand(server));
    }
}
