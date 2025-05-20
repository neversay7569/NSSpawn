package ru.ns.spawn.command;

import org.allaymc.api.command.SimpleCommand;
import org.allaymc.api.command.SenderType;
import org.allaymc.api.command.tree.CommandTree;
import org.allaymc.api.math.location.Location3d;
import org.allaymc.api.permission.DefaultPermissions;
import org.allaymc.api.server.Server;
import ru.ns.spawn.NSSpawn;
import ru.ns.spawn.utils.Config;

public class CommandSpawn extends SimpleCommand {
    public CommandSpawn() {
        super("spawn", "Телепорт на спавн");
        getPermissions().forEach(DefaultPermissions.MEMBER::addPermission);
    }

    @Override
    public void prepareCommandTree(CommandTree tree) {
        tree.getRoot()
                .exec((context, player) -> {
                    Config.Location spawnLoc = NSSpawn.getInstance().getConfig().getSpawnLocation();

                    if (spawnLoc == null) {
                        player.sendText("§cТочка спавна не установлена!");
                        return context.fail();
                    }

                    var world = Server.getInstance().getWorldPool().getWorld(spawnLoc.getWorld());
                    if (world == null) {
                        player.sendText("§cМир спавна не найден!");
                        return context.fail();
                    }

                    var dimension = world.getDimension(spawnLoc.getDimension());
                    if (dimension == null) {
                        player.sendText("§cИзмерение спавна не найдено!");
                        return context.fail();
                    }

                    var location = new Location3d(spawnLoc.getX(), spawnLoc.getY(), spawnLoc.getZ(), dimension);
                    player.teleport(location);
                    player.sendText("§aВы были телепортированы на спавн!");

                    return context.success();
                }, SenderType.PLAYER);
    }
}