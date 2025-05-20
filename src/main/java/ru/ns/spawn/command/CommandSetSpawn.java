package ru.ns.spawn.command;

import org.allaymc.api.command.SimpleCommand;
import org.allaymc.api.command.SenderType;
import org.allaymc.api.command.tree.CommandTree;
import org.allaymc.api.permission.DefaultPermissions;
import ru.ns.spawn.NSSpawn;

public class CommandSetSpawn extends SimpleCommand {
    public CommandSetSpawn() {
        super("setspawn", "Поставить точку спавна");
        getPermissions().forEach(DefaultPermissions.OPERATOR::addPermission);
    }

    @Override
    public void prepareCommandTree(CommandTree tree) {
        tree.getRoot()
                .exec((context, player) -> {
                    var location = player.getLocation();
                    NSSpawn.getInstance().getConfig().setSpawnLocation(
                            (float) location.x(),
                            (float) location.y(),
                            (float) location.z(),
                            location.dimension().getDimensionInfo().dimensionId(),
                            location.dimension().getWorld().getName()
                    );
                    player.sendText("§aТочка спавна установлена на координатах: " +
                            String.format("%.2f, %.2f, %.2f", location.x(), location.y(), location.z()));
                    return context.success();
                }, SenderType.PLAYER);
    }
}