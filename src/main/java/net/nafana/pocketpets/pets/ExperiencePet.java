package net.nafana.pocketpets.pets;

import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagInt;
import net.minecraft.server.v1_8_R3.NBTTagString;
import net.nafana.pocketpets.PocketPetsPlugin;
import net.nafana.pocketpets.interfaces.Pet;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public final class ExperiencePet extends Pet {

    private final static String petIdentifier = "experiencepet";    // PetID, this should never be changed.

    private static String uniqueName;
    private static String uniqueDescription;
    private static String abilityType;
    private static String skinID;
    private static double levelUpMultiplier;
    private static double expMultiplier;
    private static int petExpBonus;
    private static int maxLevel;

    public ExperiencePet() {

        // Needed Pet Attributes
        uniqueName = colorize(PocketPetsPlugin.getInstance().getConfig().getString("pets." + petIdentifier + ".uniquename"));
        uniqueDescription = colorize(PocketPetsPlugin.getInstance().getConfig().getString("pets." + petIdentifier + ".uniquedescription"));
        abilityType = colorize(PocketPetsPlugin.getInstance().getConfig().getString("pets." + petIdentifier + ".abilitytype"));
        skinID = PocketPetsPlugin.getInstance().getConfig().getString("pets." + petIdentifier + ".skinID");
        levelUpMultiplier =  PocketPetsPlugin.getInstance().getConfig().getDouble("pets." + petIdentifier + ".levelupmultiplier");
        expMultiplier = PocketPetsPlugin.getInstance().getConfig().getDouble("pets." + petIdentifier + ".expmultiplier");
        maxLevel = PocketPetsPlugin.getInstance().getConfig().getInt("pets." + petIdentifier + ".maxlevel");
        petExpBonus = PocketPetsPlugin.getInstance().getConfig().getInt("pets." + petIdentifier + ".petexpbonus");

    }

    public ItemStack produce () {

        net.minecraft.server.v1_8_R3.ItemStack nmsPetStack = CraftItemStack.asNMSCopy(craftMe());

        NBTTagCompound compound = (nmsPetStack.hasTag()) ? nmsPetStack.getTag() : new NBTTagCompound();
        compound.set("pi", new NBTTagString(petIdentifier));
        compound.set("level", new NBTTagInt(1));
        compound.set("expcurrent", new NBTTagInt(0));
        compound.set("explevel", new NBTTagInt(100));

        nmsPetStack.setTag(compound);

        return CraftItemStack.asBukkitCopy(nmsPetStack);

    }

    public ItemStack craftMe () {

        return craftPet(uniqueName, abilityType, uniqueDescription, skinID);

    }

    public void useAbility(Player p, int expPickedUp, int level) {

        int earnedExp = ((int)(level * getExpMultiplier() * expPickedUp));
        p.giveExp(earnedExp);
        p.sendMessage(ChatColor.GOLD + "+" + earnedExp + " exp");
    }

    public String getPetIdentifier() {
        return petIdentifier;
    }

    public String getUniqueName() {
        return uniqueName;
    }

    public String getUniqueDescription() {
        return uniqueDescription;
    }

    public String getAbilityType() {
        return abilityType;
    }

    public String getSkinID() {
        return skinID;
    }

    public double getLevelUpMultiplier() {
        return levelUpMultiplier;
    }

    public double getExpMultiplier() {
        return expMultiplier;
    }

    public int getPetExpBonus() {
        return petExpBonus;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

}
