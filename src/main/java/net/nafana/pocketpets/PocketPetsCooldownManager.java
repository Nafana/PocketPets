package net.nafana.pocketpets;

import net.nafana.pocketpets.enums.PetType;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class PocketPetsCooldownManager {

    private static PocketPetsCooldownManager instance;
    private HashMap<String, ArrayList<PetType>> cooldownTracker = new HashMap<>();
    private HashMap<String, ArrayList<PetType>> activeAbilityTracker = new HashMap<>();
    private List<Player> playersLeprechauned = new ArrayList<>();

    public PocketPetsCooldownManager () {instance = this;}

    public boolean isPlayerLeprechauned (Player p) {

        if (playersLeprechauned.contains(p)) {
            return true;
        } else {
            return false;
        }

    }

    public void addPlayersLeprechauned (List<Player> players) {
        for (Player p: players){

            playersLeprechauned.add(p);

        }
    }

    public void removeLeprechaunedPlayer (Player p) {

        playersLeprechauned.remove(p);

    }

    public void removeLeprechaunedPlayer (ArrayList<Player> players) {

        for (Player p: players){

            removeLeprechaunedPlayer(p);

        }

    }

    public boolean isPlayerInCooldownTracker (String playerName) {

        for (String playerN : cooldownTracker.keySet()) {

            if (playerN.equals(playerName)) {
                return true;
            }

        }

        return false;

    }

    public boolean isPlayerInCooldownTracker (String playerName, PetType petType) {

        for (String playerN : cooldownTracker.keySet()) {

            if (playerN.equals(playerName)) {

                if (cooldownTracker.get(playerN).contains(petType)) {
                    return true;
                } else {
                    return false;
                }
            }

        }

        return false;

    }

    public boolean isPlayerInActiveAbilityTracker (String playerName) {

        for (String playerN : activeAbilityTracker.keySet()) {

            if (playerN.equals(playerName)) {
                return true;
            }

        }

        return false;

    }

    public boolean isPlayerInActiveAbilityTracker(String playerName, PetType petType) {

        for (String playerN : activeAbilityTracker.keySet()) {

            if (playerN.equals(playerName)) {

                if (activeAbilityTracker.get(playerN).contains(petType)) {
                    return true;
                } else {
                    return false;
                }
            }

        }

        return false;

    }

    public void addPlayerToCooldownTracker (String playerName, PetType petType) {

        if (isPlayerInCooldownTracker(playerName)) {

            cooldownTracker.get(playerName).add(petType);

        } else {

            ArrayList<PetType> petTypes = new ArrayList<>();
            petTypes.add(petType);

            cooldownTracker.put(playerName, petTypes);

        }

    }

    public void removePlayerFromCooldownTracker (String playerName, PetType petType) {

        if (isPlayerInCooldownTracker(playerName)) {

            // Player already exists, let's add on to the cooldown they got.
            cooldownTracker.get(playerName).remove(petType);

            if (cooldownTracker.get(playerName).isEmpty()) {
                cooldownTracker.remove(playerName);
            }


        }
    }

    public void addPlayerToActiveAbilityTracker (String playerName, PetType petType) {

        if (isPlayerInActiveAbilityTracker(playerName)) {

            activeAbilityTracker.get(playerName).add(petType);

        } else {

            ArrayList<PetType> petTypes = new ArrayList<>();
            petTypes.add(petType);

            activeAbilityTracker.put(playerName, petTypes);

        }

    }

    public void removePlayerFromActiveAbilityTracker (String playerName, PetType petType) {

        if (isPlayerInActiveAbilityTracker(playerName)) {

            // Player already exists, let's add on to the cooldown they got.
            activeAbilityTracker.get(playerName).remove(petType);

            if (activeAbilityTracker.get(playerName).isEmpty()) {
                activeAbilityTracker.remove(playerName);
            }


        }
    }


    public static PocketPetsCooldownManager getInstance() {
        return instance;
    }
}
