package net.nafana.pocketpets.objects;

import net.md_5.bungee.api.ChatColor;

public class ExpCompletion {

    private int currentExp;
    private int maxExpLevel;

    public ExpCompletion (int currentExp, int maxExpLevel) {

        this.currentExp = currentExp;
        this.maxExpLevel = maxExpLevel;

    }

    public int getCurrentExp() {
        return currentExp;
    }

    public int getMaxExpLevel() {
        return maxExpLevel;
    }

    public String getLoreReadyVersion () {

        return ChatColor.YELLOW + "" + ChatColor.BOLD + "EXP: " + currentExp + "/" + maxExpLevel;

    }
}
