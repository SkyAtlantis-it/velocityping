package me.yarond.velocityping;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.Optional;

public class PingCommand implements SimpleCommand{

    private final ProxyServer server;

    public PingCommand(ProxyServer server) {
        this.server = server;
    }

    @Override
    public void execute(SimpleCommand.Invocation invocation) {
        CommandSource source = invocation.source();
        if (invocation.arguments().length == 0) {
            if (!(source instanceof Player)) {
                source.sendMessage(Component.text("cant check your ping when you running the command from console."));
                return;
            }
            Player player = (Player) source;
            String playername = player.getGameProfile().getName();
            long playerping = player.getPing();
            player.sendMessage(Component.text(playername + "'s ping: ").color(NamedTextColor.GREEN)
                    .append(Component.text(playerping + "ms").color(NamedTextColor.AQUA)));
        } else if (invocation.arguments().length > 0) {
            if (source.hasPermission("ping.use.others")) {
                String playername = invocation.arguments()[0];
                Optional<Player> optionalPlayer = server.getPlayer(playername);
                if (optionalPlayer.isPresent()) {
                    Player player = optionalPlayer.get();
                    long playerping = player.getPing();
                    source.sendMessage(Component.text(player.getGameProfile().getName() + "'s ping: ").color(NamedTextColor.GREEN)
                            .append(Component.text(playerping + "ms").color(NamedTextColor.AQUA)));
                } else {
                    source.sendMessage(Component.text("This player could not be found!").color(NamedTextColor.RED));
                }
            } else source.sendMessage(Component.text("You don't have permission to do that!").color(NamedTextColor.RED));
        }
    }

    @Override
    public boolean hasPermission(SimpleCommand.Invocation invocation) {
        return invocation.source().hasPermission("ping.use");
    }

}
