package net.nafana.pocketpets.helpers;

import com.mojang.authlib.GameProfile;
import net.nafana.pocketpets.enums.PetIdType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.UUID;

public class SkinUtils {

    public SkinUtils() {}

    public ItemStack getSkullFromURL(UUID uuid) {
        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);

        if (uuid == null)
            return skull;

        ItemMeta skullMeta = skull.getItemMeta();
        GameProfile profile = new GameProfile(uuid, null);
        //byte[] encodedData = Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());
        //profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
        Field profileField = null;

        try {
            profileField = skullMeta.getClass().getDeclaredField("profile");
        } catch (NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        }

        profileField.setAccessible(true);

        try {
            profileField.set(skullMeta, profile);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }

        skull.setItemMeta(skullMeta);
        return skull;
    }

    public ItemStack getSkullFromName (PetIdType type) {

        ItemStack myAwesomeSkull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);

        SkullMeta myAwesomeSkullMeta = (SkullMeta) myAwesomeSkull.getItemMeta();
        myAwesomeSkullMeta.setOwner(type.getSkullID());

        myAwesomeSkull.setItemMeta(myAwesomeSkullMeta);

        return myAwesomeSkull;
    }

    public UUID playerGameProfileInfo (String playerName){

        //Player player = Bukkit.getPlayer(playerName);

        Bukkit.broadcastMessage("Unique ID: " + Bukkit.getOfflinePlayer(playerName).getUniqueId().toString());
        return Bukkit.getOfflinePlayer(playerName).getUniqueId();
    }

    public ItemStack getPetSkull(String name) {
        ItemStack itemStack = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta meta = (SkullMeta) itemStack.getItemMeta();
        meta.setOwner(name);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

}
