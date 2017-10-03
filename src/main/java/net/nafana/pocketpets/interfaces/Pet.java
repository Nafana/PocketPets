package net.nafana.pocketpets.interfaces;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagInt;
import net.nafana.pocketpets.helpers.PetMetadataFactory;
import net.nafana.pocketpets.helpers.SkinUtils;
import net.nafana.pocketpets.objects.ExpCompletion;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.List;

public abstract class Pet {

    protected ItemStack craftPet (String uniqueName, String abilityType, String uniqueDescription, String skinID) {

        ItemStack petSkull = new SkinUtils().getPetSkull(skinID);

        SkullMeta meta = (SkullMeta) petSkull.getItemMeta();

        meta.setDisplayName(uniqueName);
        meta.setLore(PetMetadataFactory.getInstance().craftLoreMesh(abilityType, uniqueDescription, 0, new ExpCompletion(0, 100)));

        petSkull.setItemMeta(meta);

        return petSkull;

    }

    public static void updatePetStats (ItemStack stack, int expIncrease, int maxLevel, double expLevelMultiplier, Player p, int itemSlot, String petName) {

        // NBT STATS UPDATE //
        net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(stack);
        NBTTagCompound compound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();

        int level = compound.getInt("level");
        int expcurrent = compound.getInt("expcurrent");
        int expLevel = compound.getInt("explevel");

        if (expcurrent + expIncrease >= expLevel) {
            // Promote the pet
            if (level < maxLevel) {

                level += 1;
                expcurrent = (expIncrease + expcurrent) - expLevel;
                expLevel = ((int) (expLevel * expLevelMultiplier));

            } else {
                // Max level has already been reached.
                // We don't need to do anything for this.
            }

        } else {
            // Adjust the pet values
            expcurrent += expIncrease;
        }

        compound.set("level", new NBTTagInt(level));
        compound.set("expcurrent", new NBTTagInt(expcurrent));
        compound.set("explevel", new NBTTagInt(expLevel));


        nmsStack.setTag(compound);
        ItemStack tempStack = CraftItemStack.asBukkitCopy(nmsStack);

        // VISUAL STATS UPDATE //

        SkullMeta meta = (SkullMeta) tempStack.getItemMeta();
        List<String> lore = meta.getLore();
        for (int i = 0; i < lore.size() ; i++) {

            if (ChatColor.stripColor(meta.getLore().get(i)).contains("LEVEL:")) {

                lore.set(i, ChatColor.YELLOW + "" + org.bukkit.ChatColor.BOLD + "LEVEL: " + org.bukkit.ChatColor.YELLOW + level);

            } else if (ChatColor.stripColor(meta.getLore().get(i)).contains("EXP:")) {

                lore.set(i, ChatColor.YELLOW + "" + org.bukkit.ChatColor.BOLD +  "EXP: " + org.bukkit.ChatColor.YELLOW + expcurrent + "/" + expLevel);

            } else if (ChatColor.stripColor(meta.getLore().get(i)).contains("::::::::::")) {

                lore.set(i, PetMetadataFactory.getInstance().getExpBar(new ExpCompletion(expcurrent, expLevel)));

            }

        }

        meta.setDisplayName(petName + ChatColor.RESET + "" + ChatColor.GRAY + " [LVL " + level + "]");
        meta.setLore(lore);
        tempStack.setItemMeta(meta);
        p.getInventory().setItem(itemSlot, tempStack);

    }

    public String colorize (String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}
