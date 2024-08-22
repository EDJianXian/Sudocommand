package com.sudocommand;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class SudoCommand extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("SudoCommand插件已加载。");
    }

    @Override
    public void onDisable() {
        getLogger().info("SudoCommand插件已卸载。");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("sudo")) {
            if (args.length < 1) {
                sender.sendMessage(ChatColor.RED + "缺少参数: /sudo <command>");
                return true;
            }

            StringBuilder commandBuilder = new StringBuilder();
            for (String arg : args) {
                commandBuilder.append(arg).append(" ");
            }
            String commandString = commandBuilder.toString().trim();

            // Check if the command is enclosed in double quotes
            if (commandString.startsWith("\"") && commandString.endsWith("\"")) {
                // Remove the enclosing quotes
                commandString = commandString.substring(1, commandString.length() - 1);
                executeConsoleCommand(sender, commandString);
            } else {
                executePlayerCommand(sender, commandString);
            }

            return true;
        }
        return false;
    }

    private void executeConsoleCommand(CommandSender sender, String command) {
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command);
        sender.sendMessage(ChatColor.GREEN + "使用控制台权限执行命令: " + ChatColor.WHITE + command);
    }

    private void executePlayerCommand(CommandSender sender, String command) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "你必须是一个玩家才能使用该命令。");
            return;
        }
        Player player = (Player) sender;
        player.performCommand(command);
        sender.sendMessage(ChatColor.GREEN + "使用玩家权限执行命令: " + ChatColor.WHITE + command);
    }
}
