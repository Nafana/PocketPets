package net.nafana.pocketpets.commands;

import net.md_5.bungee.api.ChatColor;
import net.nafana.pocketpets.PocketPetsLoader;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PetGiveCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (!(sender instanceof Player)) {
            return false;
        }

        /*
        *  /petgive <Nafana> <PetName>
        *  /petgive <PetName>
        *
        * */
        Player p = (Player) sender;
        if (command.getName().equals("petgive")) {


            if (args.length == 1) {

                if (!p.hasPermission("pocketpets.giveself")) {
                    p.sendMessage(ChatColor.RED + "Invalid permissions to perform this command!");
                    return true;
                }

                givePlayerPet(p, args[0]);
                return true;

            } else if (args.length == 2) {

                if (!p.hasPermission("pocketpets.giveothers")) {
                    p.sendMessage(ChatColor.RED + "Invalid permissions to perform this command!");
                    return true;
                }

                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {

                    if (onlinePlayer.getName().equals(args[0])) {

                        givePlayerPet(onlinePlayer, args[1]);
                        return true;

                    }

                }

                p.sendMessage(ChatColor.RED + "Could not find that player on the server. We can't give them the pet.");
                return true;

            }
            else {
                p.sendMessage(ChatColor.RED + "Invalid arguments exception. Try /petgive <Nafana> <PetName> or /petgive <PetName>");
                return true;
            }

        } else {
            p.sendMessage(ChatColor.RED + "Invalid arguments exception. Try /petgive <Nafana> <PetName> or /petgive <PetName>");
            return true;
        }

    }

    public void givePlayerPet (Player p, String petNameEntered) {

        switch (petNameEntered.toLowerCase()){

            case "experiencepet":
                p.getInventory().addItem(PocketPetsLoader.getInstance().getExperiencePet().produce());
                break;
            case "wolfpet":
                p.getInventory().addItem(PocketPetsLoader.getInstance().getWolfPet().produce());
                break;
            case "chickenpet":
                p.getInventory().addItem(PocketPetsLoader.getInstance().getChickenPet().produce());
                break;
            case "spiderpet":
                p.getInventory().addItem(PocketPetsLoader.getInstance().getSpiderPet().produce());
                break;
            case "leprechaunpet":
                p.getInventory().addItem(PocketPetsLoader.getInstance().getLeprechaunPet().produce());
                break;
            default:{
                break;
            }
        }


    }



}
