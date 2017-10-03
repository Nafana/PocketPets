package net.nafana.pocketpets;

import net.md_5.bungee.api.ChatColor;
import net.milkbowl.vault.economy.EconomyResponse;
import net.nafana.pocketpets.enums.PetType;
import net.nafana.pocketpets.events.PlayerUsedPetEvent;
import net.nafana.pocketpets.interfaces.Pet;
import net.nafana.pocketpets.objects.AdvancedPetContainer;
import net.nafana.pocketpets.objects.PetContainer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class PocketPetsEventManager implements Listener{

    @EventHandler
    public void onPlayerInteractEvent (PlayerInteractEvent event) {

        if (event.getAction() == Action.RIGHT_CLICK_AIR){

            if (event.getPlayer().getInventory().getItemInHand().getType() == Material.SKULL_ITEM) {

                String petID = CraftItemStack.asNMSCopy(event.getPlayer().getInventory().getItemInHand()).getTag().getString("pi");

                if (petID != null) {

                    PetType petType = PetType.getPetTypeFromID(petID);

                    if (petType == null) {
                        return;
                    }

                    Bukkit.getPluginManager().callEvent(new PlayerUsedPetEvent(event.getPlayer(),
                            new AdvancedPetContainer(
                                    new PetContainer(petType, event.getPlayer().getInventory().getHeldItemSlot()),
                                    event.getPlayer().getInventory().getItemInHand()))
                    );

                }

            }

        } else if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {

            if (event.getPlayer().getInventory().getItemInHand() != null && event.getPlayer().getInventory().getItemInHand().getData().getItemType() != Material.AIR) {

                if (event.getPlayer().getInventory().getItemInHand().getType() == Material.SKULL_ITEM) {

                    String petID = CraftItemStack.asNMSCopy(event.getPlayer().getInventory().getItemInHand()).getTag().getString("pi");

                    if (petID != null) {

                        PetType petType = PetType.getPetTypeFromID(petID);

                        if (petType == null) {
                            return;
                        }

                        Bukkit.getPluginManager().callEvent(new PlayerUsedPetEvent(event.getPlayer(),
                                new AdvancedPetContainer(
                                        new PetContainer(petType, event.getPlayer().getInventory().getHeldItemSlot()),
                                        event.getPlayer().getInventory().getItemInHand()))
                        );

                        event.setCancelled(true);

                    }

                }
            }

        }
    }

    @EventHandler
    public void onPlayerUsedPetEvent (PlayerUsedPetEvent event) {

        if (!event.getPlayer().hasPermission(permissionEquivalents(event.getAdvancedPetContainer().getPetContainer().getPetType()))) {
            event.getPlayer().sendMessage(ChatColor.RED + "You do not have permission to use the " +
                    org.bukkit.ChatColor.GRAY + event.getAdvancedPetContainer().getPetContainer().getPetType().toString() +
                    ChatColor.RED + " pet.");
            return;
        }

        switch (event.getAdvancedPetContainer().getPetContainer().getPetType()){
            case EXPERIENCE:{
                return;
            }
            case WOLF: {

                if (!(PocketPetsCooldownManager.getInstance().isPlayerInCooldownTracker(event.getPlayer().getName(), PetType.WOLF))) {
                    //event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_WOLF_HOWL, 2.0f , 1.0f);
                    event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.WOLF_GROWL, 2.0f, 1.0f);
                    // This person isn't on cooldown, let's add him!
                    PocketPetsCooldownManager.getInstance().addPlayerToCooldownTracker(event.getPlayer().getName(), PetType.WOLF);
                    PocketPetsCooldownManager.getInstance().addPlayerToActiveAbilityTracker(event.getPlayer().getName(), PetType.WOLF);

                    setUpPetAbilityUse(
                            PocketPetsLoader.getInstance().getWolfPet().getActiveAbilityTimer() * 20,
                            PocketPetsLoader.getInstance().getWolfPet().getCooldownTimer() * 20,
                            event.getPlayer().getName(),
                            event.getAdvancedPetContainer().getPetContainer().getPetType()
                    );

                    Pet.updatePetStats(
                            event.getAdvancedPetContainer().getStack(),
                            PocketPetsLoader.getInstance().getWolfPet().getPetExpBonus(),
                            PocketPetsLoader.getInstance().getWolfPet().getMaxLevel(),
                            PocketPetsLoader.getInstance().getWolfPet().getPetExpBonus(),
                            event.getPlayer(),
                            event.getAdvancedPetContainer().getPetContainer().getSlotContainedIn(),
                            PocketPetsLoader.getInstance().getWolfPet().getUniqueName()
                    );

                } else {
                    // This person is on cooldown.
                    event.getPlayer().sendMessage(ChatColor.RED + "Your are still on cooldown for this pet!");
                    return;
                }

                break;
            }
            case CHICKEN: {

                if (!(PocketPetsCooldownManager.getInstance().isPlayerInCooldownTracker(event.getPlayer().getName(), PetType.CHICKEN))) {
                    event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.CHICKEN_IDLE, 2.0f , 1.0f);
                    PocketPetsCooldownManager.getInstance().addPlayerToCooldownTracker(event.getPlayer().getName(), PetType.CHICKEN);

                    setUpPetAbilityUse(
                            PocketPetsLoader.getInstance().getChickenPet().getCooldownTimer() * 20,
                            event.getPlayer().getName(),
                            PetType.CHICKEN
                    );

                    Pet.updatePetStats(
                            event.getAdvancedPetContainer().getStack(),
                            PocketPetsLoader.getInstance().getChickenPet().getPetExpBonus(),
                            PocketPetsLoader.getInstance().getChickenPet().getMaxLevel(),
                            PocketPetsLoader.getInstance().getChickenPet().getPetExpBonus(),
                            event.getPlayer(),
                            event.getAdvancedPetContainer().getPetContainer().getSlotContainedIn(),
                            PocketPetsLoader.getInstance().getChickenPet().getUniqueName()
                    );

                    PocketPetsLoader.getInstance().getChickenPet().useAbility(event.getPlayer());

                } else {
                    // This person is on cooldown.
                    event.getPlayer().sendMessage(ChatColor.RED + "Your are still on cooldown for this pet!");
                    return;
                }
                break;
            }
            case SPIDER: {
                if (!(PocketPetsCooldownManager.getInstance().isPlayerInCooldownTracker(event.getPlayer().getName(), PetType.SPIDER))) {
                    event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.SPIDER_IDLE, 2.0f , 1.0f);
                    PocketPetsCooldownManager.getInstance().addPlayerToCooldownTracker(event.getPlayer().getName(), PetType.SPIDER);

                    setUpPetAbilityUse(
                            PocketPetsLoader.getInstance().getChickenPet().getCooldownTimer() * 20,
                            event.getPlayer().getName(),
                            PetType.SPIDER
                    );

                    Pet.updatePetStats(
                            event.getAdvancedPetContainer().getStack(),
                            PocketPetsLoader.getInstance().getSpiderPet().getPetExpBonus(),
                            PocketPetsLoader.getInstance().getSpiderPet().getMaxLevel(),
                            PocketPetsLoader.getInstance().getSpiderPet().getPetExpBonus(),
                            event.getPlayer(),
                            event.getAdvancedPetContainer().getPetContainer().getSlotContainedIn(),
                            PocketPetsLoader.getInstance().getSpiderPet().getUniqueName()
                    );

                    PocketPetsLoader.getInstance().getSpiderPet().useAbility(event.getPlayer(),
                            CraftItemStack.asNMSCopy(
                                    event.getAdvancedPetContainer().getStack()).getTag().getInt("level")
                    );

                } else {
                    // The person is still on cooldown
                    event.getPlayer().sendMessage(ChatColor.RED + "Your are still on cooldown for this pet!");
                    return;
                }
                break;
            }
            case LEPRECHAUN: {
                if (!(PocketPetsCooldownManager.getInstance().isPlayerInCooldownTracker(event.getPlayer().getName(), PetType.LEPRECHAUN))) {
                    event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENDERDRAGON_GROWL, 1.0f , 1.0f);
                    PocketPetsCooldownManager.getInstance().addPlayerToCooldownTracker(event.getPlayer().getName(), PetType.LEPRECHAUN);
                    PocketPetsCooldownManager.getInstance().addPlayerToActiveAbilityTracker(event.getPlayer().getName(), PetType.LEPRECHAUN);

                    setUpPetAbilityUse(
                            PocketPetsLoader.getInstance().getLeprechaunPet().getActiveAbilityTimer() * 20,
                            PocketPetsLoader.getInstance().getLeprechaunPet().getCooldownTimer() * 20,
                            event.getPlayer().getName(),
                            PetType.LEPRECHAUN
                    );

                    Pet.updatePetStats(
                            event.getAdvancedPetContainer().getStack(),
                            PocketPetsLoader.getInstance().getLeprechaunPet().getPetExpBonus(),
                            PocketPetsLoader.getInstance().getLeprechaunPet().getMaxLevel(),
                            PocketPetsLoader.getInstance().getLeprechaunPet().getPetExpBonus(),
                            event.getPlayer(),
                            event.getAdvancedPetContainer().getPetContainer().getSlotContainedIn(),
                            PocketPetsLoader.getInstance().getLeprechaunPet().getUniqueName()
                    );

                    //PocketPetsLoader.getInstance().getSpiderPet().useAbility(event.getPlayer());
                    PocketPetsLoader.getInstance().getLeprechaunPet().useAbility(event.getPlayer(),
                            CraftItemStack.asNMSCopy(
                                event.getAdvancedPetContainer().getStack()).getTag().getInt("level"));

                } else {
                    // This person is on cooldown.
                    event.getPlayer().sendMessage(ChatColor.RED + "Your are still on cooldown for this pet!");
                    return;
                }
                break;
            }
            default: {

            }

        }
        event.getPlayer().sendMessage(ChatColor.GREEN + "You've activated the " + ChatColor.GRAY
                + event.getAdvancedPetContainer().getPetContainer().getPetType().toString()
                + ChatColor.GREEN + " pet!");
    }

    @EventHandler
    public void onPlayerDamagedEntityEvent (EntityDamageByEntityEvent event) {

        if (event.getDamager() instanceof CraftPlayer) {

            Player p = (Player) event.getDamager();

            if (event.getEntity() instanceof CraftPlayer) {

                if (PocketPetsCooldownManager.getInstance().isPlayerInActiveAbilityTracker(p.getName(), PetType.LEPRECHAUN)) {

                    Player beingHit = (Player) event.getEntity();

                    AdvancedPetContainer container = playerInventoryPetInstance(p, PetType.LEPRECHAUN);

                    if (container == null) {

                        p.sendMessage(ChatColor.YELLOW + "You lost your pet, therefore you did not get the money bonus!");

                    } else {

                        int level = CraftItemStack.asNMSCopy(container.getStack()).getTag().getInt("level");
                        int moneyTransaction = PocketPetsLoader.getInstance().getLeprechaunPet().getMoneyPerHit() * level;

                        EconomyResponse economyResponse = PocketPetsPlugin.getEcon().withdrawPlayer(beingHit, moneyTransaction);

                        if (economyResponse.transactionSuccess()) {

                            EconomyResponse secondEconomyResponse = PocketPetsPlugin.getEcon().depositPlayer(p, moneyTransaction);

                            if (secondEconomyResponse.transactionSuccess()) {

                                p.sendMessage(ChatColor.GREEN + "+ $" + moneyTransaction);
                                beingHit.sendMessage(ChatColor.RED + "- $" + moneyTransaction);

                            } else {
                                // COULD NOT MONEY TO PLAYER ACCOUNT
                            }

                        } else {

                            // COULD NOT TAKE MONEY FROM PLAYER
                            p.sendMessage(ChatColor.GREEN + "+ $0");
                        }

                        p.playSound(p.getLocation(), Sound.VILLAGER_HIT, 1.0f, 0.55f);
                        beingHit.playSound(beingHit.getLocation(), Sound.VILLAGER_HIT, 1.0f, 0.55f);

                    }
                }
            } else {

                if (PocketPetsCooldownManager.getInstance().isPlayerInActiveAbilityTracker(p.getName(), PetType.WOLF)) {

                    new EntityDamageEvent(event.getDamager(), EntityDamageEvent.DamageCause.ENTITY_ATTACK, 100.0D);

                }
            }

        }

    }

    @EventHandler
    public void playerPickedUpExpEvent (PlayerExpChangeEvent event) {

        AdvancedPetContainer petContainer = playerInventoryPetInstance(event.getPlayer(), PetType.EXPERIENCE);

        if (petContainer == null) {
            return;
        }

        ItemStack pet = petContainer.getStack();

        Pet.updatePetStats (
                pet,
                PocketPetsLoader.getInstance().getExperiencePet().getPetExpBonus(),
                PocketPetsLoader.getInstance().getExperiencePet().getMaxLevel(),
                PocketPetsLoader.getInstance().getExperiencePet().getExpMultiplier(),
                event.getPlayer(),
                petContainer.getPetContainer().getSlotContainedIn(),
                PocketPetsLoader.getInstance().getExperiencePet().getUniqueName()

        );

        PocketPetsLoader.getInstance().getExperiencePet().useAbility(
                event.getPlayer(),
                event.getAmount(),
                CraftItemStack.asNMSCopy(pet).getTag().getInt("level")
        );

        //do pet ability


    }

    public boolean isItemStackPet (ItemStack stack) {

        // Quick check for the material.
        if (stack.getType() == Material.SKULL_ITEM) {

            //Checks for our global pet identifier
            if (CraftItemStack.asNMSCopy(stack).getTag().c().contains("pi")) {

                return true;

            } else {
                return false;
            }

        } else {
            return false;
        }


    }

    public ArrayList<PetContainer> getPetsContainedInInventory (Inventory inventory) {

        ArrayList<PetContainer> containedPets = new ArrayList<>();

        for (int i = 0; i <inventory.getContents().length ; i++) {

            if (isItemStackPet(inventory.getContents()[i])) {

                //We know for sure it's a pet we're dealing with
                containedPets.add(new PetContainer(
                        PetType.getPetTypeFromID(
                                CraftItemStack.asNMSCopy(inventory.getContents()[i]).getTag().getString("pi")), i)
                );

            }

        }

        return containedPets;

    }

    public AdvancedPetContainer getSinglePetOfType (Inventory inventory, PetType petType) {

        final ArrayList<PetContainer> pets = getPetsContainedInInventory(inventory);
        for (PetContainer pet : pets){

            if (pet.getPetType() == petType) {

                return new AdvancedPetContainer(pet, inventory.getItem(pet.getSlotContainedIn()));

            }

        }

        return null;

    }

    public AdvancedPetContainer playerInventoryPetInstance(Player p, PetType petType) {

        for (int i = 0; i < p.getInventory().getContents().length; i++) {

            if (p.getInventory().getContents()[i] != null) {

                if (p.getInventory().getContents()[i].getType() == Material.SKULL_ITEM) {

                    String petID = CraftItemStack.asNMSCopy(p.getInventory().getContents()[i]).getTag().getString("pi");

                    if (petID != null) {

                        if (petID.equals(petType.getUniquepetID())) {

                            PetType type = PetType.getPetTypeFromID(petID);

                            return new AdvancedPetContainer(new PetContainer(type, i), p.getInventory().getContents()[i]);

                        }
                    }
                }
            }
        }

        return null;

    }

    public void setUpPetAbilityUse (final int activeAbilityTimer, final int cooldownAbilityTimer, final String playerNameToRemove, final PetType petTypeToRemove) {

        new BukkitRunnable() {

            int ticker = 0;
            boolean isActiveAbilityFinished = false;
            boolean isCooldownTimerFinished = false;

            @Override
            public void run() {

                if (ticker >= activeAbilityTimer && !isActiveAbilityFinished) {
                    PocketPetsCooldownManager.getInstance().removePlayerFromActiveAbilityTracker(playerNameToRemove, petTypeToRemove);
                    Player p = getPlayerFromString(playerNameToRemove);

                    if (p != null && p.isOnline()) {
                        p.sendMessage(ChatColor.GREEN + "Your ability timer ran out for the " + ChatColor.GRAY + petTypeToRemove.toString() +  ChatColor.GREEN + " pet!");
                    }

                    isActiveAbilityFinished = true;
                }

                if (ticker >= cooldownAbilityTimer && !isCooldownTimerFinished) {
                    PocketPetsCooldownManager.getInstance().removePlayerFromCooldownTracker(playerNameToRemove, petTypeToRemove);
                    Player p = getPlayerFromString(playerNameToRemove);

                    if (p != null && p.isOnline()) {
                        p.sendMessage(ChatColor.GREEN + "Your cooldown timer ran out for the " + ChatColor.GRAY + petTypeToRemove.toString() +  ChatColor.GREEN + " pet!");
                    }

                    isCooldownTimerFinished = true;
                }

                if (isActiveAbilityFinished && isCooldownTimerFinished) {
                    this.cancel();
                }

                ticker += 10;

            }

        }.runTaskTimer(PocketPetsPlugin.getPlugin(PocketPetsPlugin.class), 0, 10);

    }

    public void setUpPetAbilityUse (final int cooldownAbilityTimer, final String playerNameToRemove, final PetType petTypeToRemove) {

        new BukkitRunnable() {

            int ticker = 0;

            @Override
            public void run() {

                if (ticker >= cooldownAbilityTimer) {
                    PocketPetsCooldownManager.getInstance().removePlayerFromCooldownTracker(playerNameToRemove, petTypeToRemove);
                    Player p = getPlayerFromString(playerNameToRemove);

                    if (!(p == null) && p.isOnline()) {
                        p.sendMessage(ChatColor.GREEN + "Your cooldown timer ran out for the " + ChatColor.GRAY + petTypeToRemove.toString() +  ChatColor.GREEN + " pet!");
                    }

                    this.cancel();
                }

                ticker += 10;
            }

        }.runTaskTimer(PocketPetsPlugin.getPlugin(PocketPetsPlugin.class), 0, 10);

    }

    public final Player getPlayerFromString (String string) {

        for (Player p : Bukkit.getOnlinePlayers()){

            if (p.getName().equals(string)) {

                return p;

            }

        }

        return null;

    }

    public String permissionEquivalents (PetType petType){

        switch (petType){
            case LEPRECHAUN:{
                return "pocketpets.use.leprechaunpet";
            }
            case SPIDER:{
                return "pocketpets.use.spiderpet";
            }
            case CHICKEN:{
                return "pocketpets.use.chickenpet";
            }
            case WOLF:{
                return "pocketpets.use.wolfpet";
            }
            case EXPERIENCE:{
                return "pocketpets.use.experiencepet";
            }
            default: {
                return null;
            }
        }

    }

}
