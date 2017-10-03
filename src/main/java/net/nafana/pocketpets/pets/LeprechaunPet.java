package net.nafana.pocketpets.pets;

import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagInt;
import net.minecraft.server.v1_8_R3.NBTTagString;
import net.nafana.pocketpets.PocketPetsCooldownManager;
import net.nafana.pocketpets.PocketPetsPlugin;
import net.nafana.pocketpets.interfaces.Pet;
import org.bukkit.Effect;
import org.bukkit.EntityEffect;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class LeprechaunPet extends Pet {

    private final static String petIdentifier = "leprechaunpet";    // PetID, this should never be changed.

    private static String uniqueName;
    private static String uniqueDescription;
    private static String abilityType;
    private static String skinID;
    private static double levelUpMultiplier;
    private static int petExpBonus;
    private static int maxLevel;
    private static int cooldownTimer;
    private static int activeAbilityTimer;
    private static int moneyPerHit;

    public LeprechaunPet() {

        // Needed Pet Attributes
        uniqueName = colorize(PocketPetsPlugin.getInstance().getConfig().getString("pets." + petIdentifier + ".uniquename"));
        uniqueDescription = colorize(PocketPetsPlugin.getInstance().getConfig().getString("pets." + petIdentifier + ".uniquedescription"));
        abilityType = colorize(PocketPetsPlugin.getInstance().getConfig().getString("pets." + petIdentifier + ".abilitytype"));
        skinID = PocketPetsPlugin.getInstance().getConfig().getString("pets." + petIdentifier + ".skinID");
        levelUpMultiplier =  PocketPetsPlugin.getInstance().getConfig().getDouble("pets." + petIdentifier + ".levelupmultiplier");
        maxLevel = PocketPetsPlugin.getInstance().getConfig().getInt("pets." + petIdentifier + ".maxlevel");
        petExpBonus = PocketPetsPlugin.getInstance().getConfig().getInt("pets." + petIdentifier + ".petexpbonus");
        cooldownTimer = PocketPetsPlugin.getInstance().getConfig().getInt("pets." + petIdentifier + ".cooldowntimer");
        activeAbilityTimer = PocketPetsPlugin.getInstance().getConfig().getInt("pets." + petIdentifier + ".abilitytimer");
        moneyPerHit = PocketPetsPlugin.getInstance().getConfig().getInt("pets." + petIdentifier + ".moneyperhit");

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

    private ItemStack craftMe () {

        return craftPet(uniqueName, abilityType, uniqueDescription, skinID);

    }

    public void useAbility (Player player, int level) {

        ArrayList<Player> playerEffected = new ArrayList<>();

        for (Entity entity : player.getNearbyEntities(level + 2, level + 2, level + 2)) {

            if (entity instanceof CraftPlayer) {

                ((CraftPlayer) entity).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, activeAbilityTimer * 20, 10));
                playerEffected.add(((CraftPlayer) entity).getPlayer());

            }

        }

        PocketPetsCooldownManager.getInstance().addPlayersLeprechauned(playerEffected);

        new BukkitRunnable() {

            final int toRunFor = activeAbilityTimer * 20;
            int ticker = 0;


            @Override
            public void run() {

                if (ticker >= toRunFor) {
                    PocketPetsCooldownManager.getInstance().removeLeprechaunedPlayer(playerEffected);
                    this.cancel();
                } else {

                    if (playerEffected.isEmpty()) {
                        this.cancel();
                    }

                    for (Player p: playerEffected) {

                        if (p.isOnline()) {
                            p.playEffect(EntityEffect.WITCH_MAGIC);
                            player.playEffect(EntityEffect.WITCH_MAGIC);
                        }

                    }

                }

                ticker += 10;
            }

        }.runTaskTimer(PocketPetsPlugin.getPlugin(PocketPetsPlugin.class), 0, 10);

    }

    public static String getPetIdentifier() {
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

    public int getPetExpBonus() {
        return petExpBonus;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public int getCooldownTimer() {return cooldownTimer;}

    public int getActiveAbilityTimer() {return activeAbilityTimer;}

    public int getMoneyPerHit() {
        return moneyPerHit;
    }
}
