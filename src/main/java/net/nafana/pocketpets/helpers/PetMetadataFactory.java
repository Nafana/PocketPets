package net.nafana.pocketpets.helpers;


import net.nafana.pocketpets.objects.ExpCompletion;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public final class PetMetadataFactory {

    public PetMetadataFactory() {}
    private static PetMetadataFactory instance;
    public static PetMetadataFactory getInstance() {
        return instance;
    }

    public List<String> craftLoreMesh (String abilityType, String abilityDescription, int level, ExpCompletion expCompletion) {

        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(abilityType);
        lore.add(abilityDescription);
        lore.add("");
        lore.add(ChatColor.YELLOW + "" + ChatColor.BOLD + "LEVEL: " + level);
        lore.add(expCompletion.getLoreReadyVersion());
        lore.add(getExpBar(expCompletion));

        return lore;

    }

    public String getExpBar (ExpCompletion expCompletion) {

        double cExp = expCompletion.getCurrentExp();
        double lExp = expCompletion.getMaxExpLevel();

        String builder = ChatColor.GREEN + "";
        int filledInBars = ((int)((cExp/lExp) * 50));

        for (int i = 0; i < filledInBars ; i++) {

            builder += ":";

        }

        builder += ChatColor.RED;

        for (int i = 0; i < (50 - filledInBars) ; i++) {

            builder += ":";

        }

        return builder;

    }

    public void setNewPetMetadataFactoryInstance() {
        instance = this;
    }

}
